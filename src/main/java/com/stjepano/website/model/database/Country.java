package com.stjepano.website.model.database;

/**
 * Write short comment about this class/interface/whatever ... What is it's responsibility?
 */
public class Country {
    private String code;
    private String code3;
    private Integer numericCode;
    private String name;
    private String flagIconUri;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode3() {
        return code3;
    }

    public void setCode3(String code3) {
        this.code3 = code3;
    }

    public Integer getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(Integer numericCode) {
        this.numericCode = numericCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlagIconUri() {
        return flagIconUri;
    }

    public void setFlagIconUri(String flagIconUri) {
        this.flagIconUri = flagIconUri;
    }
}
