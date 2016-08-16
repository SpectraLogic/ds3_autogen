<#include "../copyright.ftl"/>

package ${packageName};

<#include "common/response_imports.ftl"/>
import java.util.List;

public class ${name} extends AbstractResponse {

${javaHelper.createAllResponseResultClassVars(responseCodes)}
    private Integer pagingTruncated;
    private Integer pagingTotalResultCount;

<#include "common/response_constructor.ftl"/>

    @Override
    protected void processResponse() throws IOException {
        try {
            this.checkStatusCode(${helper.getResponseCodes(responseCodes)});

            switch (this.getStatusCode()) {
            case 200:
                try (final InputStream content = getResponse().getResponseStream()) {
                    this.s3ObjectListResult = XmlOutput.fromXml(content, S3ObjectList.class);
                    this.pagingTruncated = parseIntHeader("page-truncated");
                    this.pagingTotalResultCount = parseIntHeader("total-result-count");
                }
                break;
            default:
                assert false : "checkStatusCode should have made it impossible to reach this line.";
            }
        } finally {
            this.getResponse().close();
        }
    }

    private Integer parseIntHeader(final String key) {
        final List<String> list = getResponse().getHeaders().get(key);
        switch (list.size()) {
            case 0:
                return null;
            case 1:
                return Integer.parseInt(list.get(0));
            default:
                throw new IllegalArgumentException("Response has more than one header value for " + key);
        }
    }

    public Integer getPagingTruncated() {
        return pagingTruncated;
    }

    public Integer getPagingTotalResultCount() {
        return pagingTotalResultCount;
    }

${javaHelper.createAllResponseResultGetters(responseCodes)}
}