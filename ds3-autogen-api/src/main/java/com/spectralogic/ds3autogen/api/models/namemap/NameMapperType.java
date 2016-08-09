package com.spectralogic.ds3autogen.api.models.namemap;

/**
 * Used to denote what is being renamed within the name mapper:
 * AmazonS3 request, SpectraDs3 request, or none for types
 */
public enum NameMapperType {
    NONE,
    AMAZONS3,
    SPECTRADS3,
}
