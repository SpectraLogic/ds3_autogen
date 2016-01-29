package com.spectralogic.ds3autogen.c.models;

public class C_Type {
    final private String type;
    final private boolean isPrimitive;
    final private boolean isArray;
    //private boolean isString;  // ds3_str
    //private boolean isDs3Type; // ds3_bucket etc
    //private boolean isEnum;

    public C_Type(
            final String type,
            final boolean isPrimitive,
            final boolean isArray) {
        this.type = type;
        this.isArray = isArray;
        this.isPrimitive = isPrimitive;
    }

    public boolean isPrimitive() {
        return isPrimitive;
    }

    /*
    public boolean isEnum() {
        return isEnum;
    }
    */

    public boolean isArray() {
        return isArray;
    }

    public String getTypeRoot() {
        return type;
    }

    @Override
    public String toString() {
        String ret = type;
        if (!isPrimitive()) {
            ret += "*";
        }
        if (isArray()) {
            ret += "*";
        }

        return ret;
    }
}

// Ds3String
//
