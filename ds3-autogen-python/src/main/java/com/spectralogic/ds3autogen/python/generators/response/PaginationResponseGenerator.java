package com.spectralogic.ds3autogen.python.generators.response;

import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.python.model.response.ParsePayload;

import static com.spectralogic.ds3autogen.python.helpers.PythonHelper.pythonIndent;

/**
 * Generates the python response model for commands that have pagination headers
 * that need parsing
 */
public class PaginationResponseGenerator extends BaseResponseGenerator {

    /**
     * Gets the python code that initializes the response payload
     */
    @Override
    public String toInitResponse() {
        return "def __init__(self, response, request):\n" +
                pythonIndent(2) + "self.paging_truncated = None\n" +
                pythonIndent(2) + "self.paging_total_result_count = None\n" +
                pythonIndent(2) + "super(self.__class__, self).__init__(response, request)\n";
    }

    /**
     * Gets the python code that will parse the response payload
     */
    @Override
    public String toParseResponsePayload(final Ds3Request ds3Request) {
        final ParsePayload parsePayload = getParsePayload(ds3Request);
        return parsePayload.toPythonCode() + "\n"
                + pythonIndent(3) + "self.paging_truncated = self.parse_int_header('page-truncated', response.getheaders())\n"
                + pythonIndent(3) + "self.paging_total_result_count = self.parse_int_header('total-result-count', response.getheaders())";
    }
}
