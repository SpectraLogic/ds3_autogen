package com.spectralogic.ds3autogen.models;

import java.util.Objects;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

/**
 * Stores the SDK name of a type, and the original contract name of
 * the type if the type was renamed. Else, the original name is null.
 */
public class EncapsulatingTypeNames {

    private final String sdkName;
    private final String originalName;

    public EncapsulatingTypeNames(final String sdkName, final String originalName) {
        this.sdkName = sdkName;
        this.originalName = originalName;
    }

    public String getSdkName() {
        return sdkName;
    }

    public String getOriginalName() {
        return originalName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSdkName());
    }

    /**
     * Checks if the Sdk names are the same.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof EncapsulatingTypeNames)) {
            return false;
        }
        final EncapsulatingTypeNames encapsulatingTypeNames = (EncapsulatingTypeNames) obj;
        return isEmpty(this.getSdkName()) == isEmpty(encapsulatingTypeNames.getSdkName())
                && ((isEmpty(this.getSdkName()) && isEmpty(encapsulatingTypeNames.getSdkName()))
                || this.getSdkName().equals(encapsulatingTypeNames.getSdkName()));
    }
}
