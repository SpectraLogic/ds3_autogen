<#include "../copyright.ftl"/>

package ${packageName};

<#include "../imports.ftl"/>

public class ${name} implements ${parentClass}<${responseName}> {

    /**
     * This executor is used for error parsing
     */
    private final static ListeningExecutorService EXECUTOR_SERVICE = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
    private final static Logger LOG = LoggerFactory.getLogger(${name}.class);

    private final int[] expectedStatusCodes = new int[]{${expectedStatusCodes}};

    private final WritableByteChannel byteChannel;

    private final FutureTask<${responseName}> responseFuture;
    private final ResponseFutureCallable<${responseName}> responseFutureCallable;

    private NettyBlockingByteChannel errorChannel = null;
    private ${responseName} ${responseName?uncap_first} = null;

    public ${name}(final WritableByteChannel byteChannel) {
        this.byteChannel = byteChannel;
        this.responseFutureCallable = new ResponseFutureCallable<>();
        this.responseFuture = new FutureTask<>(responseFutureCallable);
    }

    @Override
    public FutureTask<${responseName}> responseFuture() {
        return responseFuture;
    }

    @Override
    public WritableByteChannel getChannel() {
        if (errorChannel != null) {
            return errorChannel;
        }
        return byteChannel;
    }

    @Override
    public void startResponse(final WebResponse response) {
        if (ResponseParserUtils.validateStatusCode(response.statusCode(), expectedStatusCodes)) {
            final Headers headers = response.headers();
            ${responseName?uncap_first} = new ${responseName}(
                    ResponseParserUtils.getFirstHeaderValue(headers, "Content-Type"),
                    ResponseParserUtils.determineChecksumType(headers),
                    new MetadataImpl(headers),
                    ResponseParserUtils.getSizeFromHeaders(headers)
            );
        } else {
            errorChannel = new NettyBlockingByteChannel();
            EXECUTOR_SERVICE.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        responseFutureCallable.setError(ResponseParserUtils.createFailedRequest(response, errorChannel, expectedStatusCodes));
                    } catch (final IOException e) {
                        LOG.error("Failed to create FailedResponseException", e);
                        responseFutureCallable.setError(e);
                    } finally {
                        responseFuture.run();
                    }
                }
            });
        }
    }

    @Override
    public void endResponse() {
        if (errorChannel == null) {
            responseFutureCallable.setResponse(${responseName?uncap_first});
            responseFuture.run();
        }
    }

    @Override
    public void error(final Exception e) {
        responseFutureCallable.setError(e);
        responseFuture.run();
    }
}