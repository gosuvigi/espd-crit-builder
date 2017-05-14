package com.vigi.xml.v200;

import com.vigi.criteria.Criterion;
import com.vigi.criteria.Requirement;
import com.vigi.criteria.RequirementGroup;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import test.x.ubl.pre_award.commonaggregate.*;
import test.x.ubl.pre_award.commonbasic.*;
import test.x.ubl.pre_award.qualificationapplicationresponse.QualificationApplicationResponseType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * Created by vigi on 5/14/2017.
 */
@Component
class QualificationApplicationResponseTransformer {

    QualificationApplicationResponseType buildResponseType(List<Criterion> criteria) {
        QualificationApplicationResponseType responseType = new QualificationApplicationResponseType();

        addCriteria(criteria, responseType);

        return responseType;
    }

    private void addCriteria(List<Criterion> criteria, QualificationApplicationResponseType responseType) {
        for (Criterion crit : criteria) {
            responseType.getTenderingCriterion().add(buildCriterionType(crit, responseType));
        }
    }

    private TenderingCriterionType buildCriterionType(Criterion criterion,
                                                      QualificationApplicationResponseType responseType) {
        TenderingCriterionType criterionType = new TenderingCriterionType();

        addCriterionID(criterionType);
        addName(criterion, criterionType);
        addGroups(criterion, criterionType, responseType);

        return criterionType;
    }

    private void addCriterionID(TenderingCriterionType criterionType) {
        IDType idType = buildIdType(UUID.randomUUID().toString());
        criterionType.setID(idType);
    }

    private void addName(Criterion criterion, TenderingCriterionType criterionType) {
        NameType nameType = new NameType();
        nameType.setValue(criterion.getName());
        criterionType.setName(nameType);
    }

    private void addGroups(Criterion criterion, TenderingCriterionType criterionType,
                           QualificationApplicationResponseType responseType) {
        if (isEmpty(criterion.getRequirementGroups())) {
            return;
        }

        List<TenderingCriterionPropertyGroupType> groupTypes = new ArrayList<>(criterion.getRequirementGroups().size() + 1);
        for (RequirementGroup group : criterion.getRequirementGroups()) {
            groupTypes.add(buildGroupType(group, responseType));
        }
        criterionType.getTenderingCriterionPropertyGroup().addAll(groupTypes);
    }

    private TenderingCriterionPropertyGroupType buildGroupType(RequirementGroup requirementGroup,
                                                               QualificationApplicationResponseType responseType) {
        TenderingCriterionPropertyGroupType groupType = new TenderingCriterionPropertyGroupType();

        addGroupId(groupType);
        addRequirements(requirementGroup, groupType, responseType);
        addSubGroups(requirementGroup, groupType, responseType);

        return groupType;
    }

    private void addGroupId(TenderingCriterionPropertyGroupType groupType) {
        IDType idType = buildIdType(UUID.randomUUID().toString());
        groupType.setID(idType);
    }

    private void addRequirements(RequirementGroup group, TenderingCriterionPropertyGroupType groupType,
                                 QualificationApplicationResponseType responseType) {
        if (isEmpty(group.getRequirements())) {
            return;
        }
        List<TenderingCriterionPropertyType> requirementTypes = new ArrayList<>();
        List<TenderingCriterionResponseType> responseTypes = new ArrayList<>();
        for (Requirement req : group.getRequirements()) {
            String reqId = UUID.randomUUID().toString();
            req.setId(reqId);
            requirementTypes.add(buildRequirementType(req));
            responseTypes.add(buildResponse(req));
        }
        groupType.getTenderingCriterionProperty().addAll(requirementTypes);
        responseType.getTenderingCriterionResponse().addAll(responseTypes);
    }

    private TenderingCriterionPropertyType buildRequirementType(Requirement requirement) {
        TenderingCriterionPropertyType requirementType = new TenderingCriterionPropertyType();

        IDType idType = buildIdType(requirement.getId());
        idType.setSchemeID("CriterionRelatedIDs");
        idType.setSchemeVersionID("2.0.0");
        requirementType.setID(idType);

        requirementType.getDescription().add(buildDescriptionType(requirement.getDescription()));

        if (requirement.getResponseType() != null) {
            ValueDataTypeCodeType dataTypeCodeType = new ValueDataTypeCodeType();
            dataTypeCodeType.setListID("ResponseDataType");
            dataTypeCodeType.setListAgencyID("EU-COM-GROW");
            dataTypeCodeType.setListVersionID("2.0.0");
            dataTypeCodeType.setValue(requirement.getResponseType().name());
            requirementType.setValueDataTypeCode(dataTypeCodeType);
        }

        return requirementType;
    }

    private TenderingCriterionResponseType buildResponse(Requirement requirement) {
        TenderingCriterionResponseType responseType = new TenderingCriterionResponseType();
        ResponseValueType responseValueType = new ResponseValueType();
        ResponseCodeType responseCodeType = new ResponseCodeType();
        responseCodeType.setValue(requirement.getValue() != null ? requirement.getValue().toString() : "");
        responseValueType.setResponseCode(responseCodeType);
        responseType.getResponseValue().add(responseValueType);

        ValidatedCriterionRequirementIDType idType = new ValidatedCriterionRequirementIDType();
        idType.setSchemeAgencyID("EU-COM-GROW");
        idType.setSchemeVersionID("2.0.0");
        idType.setValue(requirement.getId());
        responseType.setValidatedCriterionRequirementID(idType);


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

    private void addSubGroups(RequirementGroup ccvGroup, TenderingCriterionPropertyGroupType parentGroup,
                              QualificationApplicationResponseType responseType) {
        if (isEmpty(ccvGroup.getRequirementGroups())) {
            return;
        }

        List<TenderingCriterionPropertyGroupType> subGroups = new ArrayList<>();
        for (RequirementGroup ccvSubGroup : ccvGroup.getRequirementGroups()) {
            subGroups.add(buildGroupType(ccvSubGroup, responseType));
        }
        parentGroup.getSubsidiaryTenderingCriterionPropertyGroup().addAll(subGroups);
    }

    private static IDType buildIdType(String id) {
        IDType idType = new IDType();
        idType.setValue(id);
        idType.setSchemeAgencyID("EU-COM-GROW");
        idType.setSchemeVersionID("2.0.0");
        return idType;
    }
}
