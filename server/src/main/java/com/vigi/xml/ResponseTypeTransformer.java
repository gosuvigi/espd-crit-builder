package com.vigi.xml;

import com.vigi.criteria.Criterion;
import com.vigi.criteria.Requirement;
import com.vigi.criteria.RequirementGroup;
import grow.names.specification.ubl.schema.xsd.espdresponse_1.ESPDResponseType;
import isa.names.specification.ubl.schema.xsd.ccv_commonaggregatecomponents_1.CriterionType;
import isa.names.specification.ubl.schema.xsd.ccv_commonaggregatecomponents_1.RequirementGroupType;
import isa.names.specification.ubl.schema.xsd.ccv_commonaggregatecomponents_1.RequirementType;
import isa.names.specification.ubl.schema.xsd.ccv_commonaggregatecomponents_1.ResponseType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NameType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * Created by vigi on 5/13/2017.
 */
@Component
public class ResponseTypeTransformer {

    public ESPDResponseType buildResponseType(Criterion criterion) {
        ESPDResponseType responseType = new ESPDResponseType();

        addCriteria(criterion, responseType);

        return responseType;
    }

    private void addCriteria(Criterion criterion, ESPDResponseType responseType) {
        responseType.getCriterion().add(buildCriterionType(criterion));
    }

    private CriterionType buildCriterionType(Criterion criterion) {
        CriterionType criterionType = new CriterionType();

        addCriterionID(criterionType);
        addName(criterion, criterionType);
        addGroups(criterion, criterionType);

        return criterionType;
    }

    private void addCriterionID(CriterionType criterionType) {
        IDType idType = buildIdType();
        criterionType.setID(idType);
    }


    private void addName(Criterion input, CriterionType criterionType) {
        NameType nameType = new NameType();
        nameType.setValue(input.getName());
        criterionType.setName(nameType);
    }

    private void addGroups(Criterion ccvCriterion, CriterionType criterionType) {
        if (isEmpty(ccvCriterion.getRequirementGroups())) {
            return;
        }

        List<RequirementGroupType> groupTypes = new ArrayList<>(ccvCriterion.getRequirementGroups().size() + 1);
        for (RequirementGroup group : ccvCriterion.getRequirementGroups()) {
            groupTypes.add(buildGroupType(group));
        }
        criterionType.getRequirementGroup().addAll(groupTypes);
    }

    private RequirementGroupType buildGroupType(RequirementGroup ccvGroup) {
        RequirementGroupType groupType = new RequirementGroupType();

        addGroupId(groupType);
        addRequirements(ccvGroup, groupType);
        addSubGroups(ccvGroup, groupType);

        return groupType;
    }

    private void addGroupId(RequirementGroupType groupType) {
        IDType idType = buildIdType();
        groupType.setID(idType);
    }

    private void addRequirements(RequirementGroup group, RequirementGroupType groupType) {
        if (isEmpty(group.getRequirements())) {
            return;
        }

        List<RequirementType> requirementTypes = new ArrayList<>(group.getRequirements().size() + 1);
        for (Requirement req : group.getRequirements()) {
            requirementTypes.add(buildRequirementType(req));
        }
        groupType.getRequirement().addAll(requirementTypes);
    }

    private RequirementType buildRequirementType(Requirement requirement) {
        RequirementType requirementType = new RequirementType();

        IDType idType = buildIdType();
        idType.setSchemeID("CriterionRelatedIDs");
        idType.setSchemeVersionID("1.0.2");
        requirementType.setID(idType);


        requirementType.setResponseDataType(requirement.getResponseType().name());

        requirementType.getResponse().add(buildResponse(requirement));

        return requirementType;
    }

    private ResponseType buildResponse(Requirement requirement) {
        ResponseType responseType = new ResponseType();

        responseType.setDescription(buildDescriptionType((String) requirement.getValue()));

        return responseType;
    }

    private static DescriptionType buildDescriptionType(String description) {
        if (!StringUtils.hasText(description)) {
            // we don't want empty Description elements
            return null;
        }
        DescriptionType descriptionType = new DescriptionType();
        descriptionType.setValue(description);
        return descriptionType;
    }

    private void addSubGroups(RequirementGroup ccvGroup, RequirementGroupType parentGroup) {
        if (isEmpty(ccvGroup.getRequirementGroups())) {
            return;
        }

        List<RequirementGroupType> subGroups = new ArrayList<>(parentGroup.getRequirementGroup().size() + 1);
        for (RequirementGroup ccvSubGroup : ccvGroup.getRequirementGroups()) {
            subGroups.add(buildGroupType(ccvSubGroup));
        }
        parentGroup.getRequirementGroup().addAll(subGroups);
    }

    private static IDType buildIdType() {
        IDType idType = new IDType();
        idType.setValue(UUID.randomUUID().toString());
        idType.setSchemeAgencyID("VIGI");
        idType.setSchemeVersionID("1.0.2");
        idType.setSchemeID("1.0.2");
        return idType;
    }

}
