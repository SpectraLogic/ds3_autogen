package com.spectralogic.ds3autogen.models.xml.typemap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TypeMapElement {

    @JsonProperty("ContractType")
    private String contractType;

    @JsonProperty("SdkType")
    private String sdkType;

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getSdkType() {
        return sdkType;
    }

    public void setSdkType(String sdkType) {
        this.sdkType = sdkType;
    }
}