package com.vigi.criteria;

import lombok.Data;

/**
 * Created by vigi on 5/13/2017.
 */
@Data
public class Requirement {

    private String id;

    private String description;

    private Object value;

    private ResponseType responseType;
}
