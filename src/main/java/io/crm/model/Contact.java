package io.crm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact implements Serializable {
    public static final String campaign = "campaign";
    public static final String br = "br";
    public static final String consumer = "consumer";
    public static final String brand = "brand";

    public static final String description = "description";

    public static final String ptr = "ptr";
    public static final String swp = "swp";

    public static final String latitude = "latitude";
    public static final String longitude = "longitude";
    public static final String accuracy = "accuracy";
    public static final String date = "date";
    public static String receive_date = "receive_date";

    Contact() {
    }
}
