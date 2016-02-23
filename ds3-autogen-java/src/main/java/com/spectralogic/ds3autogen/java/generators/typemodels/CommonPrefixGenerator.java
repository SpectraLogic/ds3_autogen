package com.spectralogic.ds3autogen.java.generators.typemodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.java.models.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

/**
 * Special cases the data from a Ds3Type containing a "CommonPrefixes" element
 * to conform to generation requirements. This changes the CommonPrefixes element
 * to reference a non-generated class CommonPrefix instead of the specified array
 * of strings to handle the issue caused by each prefix having its own block.
 */
public class CommonPrefixGenerator extends BaseTypeGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(CommonPrefixGenerator.class);

    @Override
    public ImmutableList<Element> toElementList(final ImmutableList<Ds3Element> ds3Elements) {
        if (isEmpty(ds3Elements)) {
            LOG.error("There are no elements when the CommonPrefixes element was expected");
            return ImmutableList.of();
        }

        final ImmutableList.Builder<Element> builder = ImmutableList.builder();
        for (final Ds3Element ds3Element : ds3Elements) {
            if (ds3Element.getName().equals("CommonPrefixes")) {
                final Ds3Element commonPrefixes = new Ds3Element("CommonPrefixes", "array", "CommonPrefixes");
                builder.add(toElement(commonPrefixes));
            } else {
                builder.add(toElement(ds3Element));
            }
        }
        return builder.build();
    }
}
