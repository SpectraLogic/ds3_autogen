package com.spectralogic.ds3autogen.api.models.namemap;

public class TypeRename {

    private final String sdkName;
    private final NameMapperType type;

    public TypeRename(final String sdkName, final NameMapperType type) {
        this.sdkName = sdkName;
        this.type = type;
    }

    public String getSdkName() {
        return sdkName;
    }

    public NameMapperType getType() {
        return type;
    }
}
