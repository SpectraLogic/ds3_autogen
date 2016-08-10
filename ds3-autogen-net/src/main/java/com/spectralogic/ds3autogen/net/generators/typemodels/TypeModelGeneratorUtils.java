package com.spectralogic.ds3autogen.net.generators.typemodels;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3EnumConstant;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type;
import com.spectralogic.ds3autogen.net.model.common.NetNullableVariable;
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
     * Converts a list of Ds3Elements into a list of NetNullableVariables
     */
    ImmutableList<NetNullableVariable> toElementsList(final ImmutableList<Ds3Element> ds3Elements, final ImmutableMap<String, Ds3Type> typeMap);

    /**
     * Converts a Ds3Element into an NetNullableVariable
     */
    NetNullableVariable toElement(final Ds3Element ds3Element, final ImmutableMap<String, Ds3Type> typeMap);

    /**
     * Converts the contract name of an element into the SDK element name
     */
    String toElementName(final String elementName);
}
