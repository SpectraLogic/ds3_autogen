/*package com.spectralogic.ds3autogen.c.models;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Annotation;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.api.models.Ds3EnumConstant;
import com.spectralogic.ds3autogen.c.helpers.CHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;


public class Element {
    private static final Logger LOG = LoggerFactory.getLogger(Element.class);

    private final String name;
    private final String type;
    private final ImmutableList<Ds3Annotation> annotations;

    public Element(
            final String name,
            final String type,
            final ImmutableList<Ds3Annotation> annotations) {
        this.name = name;
        this.type = type;
        this.annotations = annotations;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }


    public String generateResponseParser() throws ParseException {
        final StringBuilder outputBuilder = new StringBuilder();

        for (final Ds3Element element : this.elements) {
            final String freeFunc = CHelper.elementTypeToParseFunction(element);

            if (freeFunc.length() == 0) continue;

            for (int currentIndex = 0; currentIndex < element.; currentIndex++) {
                outputBuilder.append(CHelper.indent(1));

                if (currentIndex > 0) {
                    outputBuilder.append("} else ");
                }

                final String currentEnumString = this.enumConstants.get(currentIndex).getName();
                outputBuilder.append("if (xmlStrcmp(text, (const xmlChar*) \"").append(currentEnumString).append("\") == 0) {").append(System.lineSeparator());
                outputBuilder.append(CHelper.indent(2)).append("return ").append(currentEnumString).append(";").append(System.lineSeparator());
            }

            outputBuilder.append(CHelper.indent(1)).append("} else {").append(System.lineSeparator()); // Shouldn't need this else, since we are autogenerating from all possible values.
            outputBuilder.append(CHelper.indent(2)).append("ds3_log_message(log, DS3_ERROR, \"ERROR: Unknown ").append(getNameUnderscores()).append(" value of '%s'.  Returning ").append(getEnumConstants().get(0).getName()).append(" for safety.").append(System.lineSeparator());
            outputBuilder.append(CHelper.indent(2)).append("return ").append(getEnumConstants().get(0).getName()).append(";").append(System.lineSeparator()); // Special case? How do we determine default "safe" response enum?  Probably not always element 0
            outputBuilder.append(CHelper.indent(1)).append("}").append(System.lineSeparator());
        }

        return outputBuilder.toString();
    }
}
*/