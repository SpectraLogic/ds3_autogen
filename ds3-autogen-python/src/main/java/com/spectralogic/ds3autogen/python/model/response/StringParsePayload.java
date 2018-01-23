package com.spectralogic.ds3autogen.python.model.response;

import static com.spectralogic.ds3autogen.python.helpers.PythonHelper.pythonIndent;

public class StringParsePayload implements ParsePayload {
    private final Integer code;

    public StringParsePayload(final int code) {
        this.code = code;
    }

    @Override
    public String toPythonCode() {
        final StringBuilder builder = new StringBuilder("if self.response.status == ");
        builder.append(code).append(":\n")
                .append(pythonIndent(3))
                .append("self.result = response.read()");
        return builder.toString();
    }
}
