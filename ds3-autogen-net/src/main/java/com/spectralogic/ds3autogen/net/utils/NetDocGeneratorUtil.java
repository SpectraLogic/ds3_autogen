package com.spectralogic.ds3autogen.net.utils;

import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.spectralogic.ds3autogen.utils.Helper.indent;

/**
 * Used to convert optional strings into net documentation
 */
public class NetDocGeneratorUtil {

    private static final Logger LOG = LoggerFactory.getLogger(NetDocGeneratorUtil.class);

    /**
     * Retrieves the .Net documentation for the specified request
     * @param requestName The normalized sdk request name with no path.
     * @param indent The level of indentation the documentation will be
     *               generated in, excluding the first line which does
     *               not have indentation.
     */
    public static String toCommandDocumentation(
            final String requestName,
            final Ds3DocSpec docSpec,
            final int indent) {
        final Optional<String> documentation = docSpec.getRequestDocumentation(requestName);
        if (!documentation.isPresent()) {
            LOG.info("Cannot generate documentation for request because there is no documentation descriptor: {}", requestName);
            return "";
        }
        return "/// <summary>\n"
                + indent(indent) + "/// " + documentation.get() + "\n"
                + indent(indent) + "/// </summary>\n"
                + indent(indent) + "/// <param name=\"request\"></param>\n"
                + indent(indent) + "/// <returns></returns>\n";
    }
}
