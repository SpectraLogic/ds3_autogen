package com.spectralogic.ds3autogen.net.generators.typemodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.api.models.Ds3EnumConstant;
import com.spectralogic.ds3autogen.net.model.type.Element;
import com.spectralogic.ds3autogen.net.model.type.EnumConstant;

/**
 * Contains the interface for functions that are used to convert a Ds3Type into
 * the Type model within Type Generators
 */
public interface TypeModelGeneratorUtils {

    /**
     * Converts a list of Ds3EnumConstants into a list of Enum Constants
     */
    ImmutableList<EnumConstant> toEnumConstantsList(final ImmutableList<Ds3EnumConstant> ds3EnumConstants);

    /**
     * Converts a list of Ds3Elements into a list of Elements
     */
    ImmutableList<Element> toElementsList(final ImmutableList<Ds3Element> ds3Elements);
}
