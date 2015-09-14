package io.crm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Consumer {
    Consumer() {}

    public static final String name = "name";
    public static final String fatherName = "fatherName";
    public static final String mobile = "mobile";
    public static final String occupation = "occupation";

    public static final String age = "age";
}
