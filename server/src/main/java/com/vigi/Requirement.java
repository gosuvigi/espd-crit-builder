package com.vigi;

import lombok.Data;

/**
 * Created by vigi on 5/13/2017.
 */
@Data
public class Requirement {

    private String description;

    private Object value;

    private ResponseType responseType;
}
