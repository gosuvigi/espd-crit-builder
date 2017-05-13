package com.vigi.criteria;

import lombok.Data;

import java.util.List;

/**
 * Created by vigi on 5/13/2017.
 */
@Data
public class RequirementGroup {

    private String name;

    private List<Requirement> requirements;

    private List<RequirementGroup> requirementGroups;
}
