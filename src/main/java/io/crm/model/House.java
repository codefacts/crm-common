package io.crm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class House implements Serializable {
    public static final String name = "name";
    public static final String area = "area";
    public static final String locations = "locations";
    public static final String active = "active";

    House() {
    }
}
