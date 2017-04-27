    private static int parseRetryAfter(final WebResponse webResponse) {
        final String retryAfter = webResponse.getHeaders().get("Retry-After").get(0);
        if (retryAfter == null) {
            throw new RetryAfterExpectedException();
        }
        return Integer.parseInt(retryAfter);
    }
