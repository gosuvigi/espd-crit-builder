package com.vigi.criteria;

import lombok.Data;

import java.util.List;

/**
 * Created by vigi on 5/13/2017.
 */
@Data
public class Criterion {

    private String name;

    private List<RequirementGroup> requirementGroups;
}
