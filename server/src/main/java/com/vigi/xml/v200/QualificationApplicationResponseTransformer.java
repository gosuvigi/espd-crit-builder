package com.vigi.xml.v200;

import com.vigi.criteria.Criterion;
import com.vigi.criteria.Requirement;
import com.vigi.criteria.RequirementGroup;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import test.x.ubl.pre_award.commonaggregate.TenderingCriterionPropertyGroupType;
import test.x.ubl.pre_award.commonaggregate.TenderingCriterionPropertyType;
import test.x.ubl.pre_award.commonaggregate.TenderingCriterionType;
import test.x.ubl.pre_award.commonbasic.DescriptionType;
import test.x.ubl.pre_award.commonbasic.IDType;
import test.x.ubl.pre_award.commonbasic.NameType;
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
            responseType.getTenderingCriterion().add(buildCriterionType(crit));
        }
    }

    private TenderingCriterionType buildCriterionType(Criterion criterion) {
        TenderingCriterionType criterionType = new TenderingCriterionType();

        addCriterionID(criterionType);
        addName(criterion, criterionType);
        addGroups(criterion, criterionType);

        return criterionType;
    }

    private void addCriterionID(TenderingCriterionType criterionType) {
        IDType idType = buildIdType();
        criterionType.setID(idType);
    }


    private void addName(Criterion criterion, TenderingCriterionType criterionType) {
        NameType nameType = new NameType();
        nameType.setValue(criterion.getName());
        criterionType.setName(nameType);
    }

    private void addGroups(Criterion criterion, TenderingCriterionType criterionType) {
        if (isEmpty(criterion.getRequirementGroups())) {
            return;
        }

        List<TenderingCriterionPropertyGroupType> groupTypes = new ArrayList<>(criterion.getRequirementGroups().size() + 1);
        for (RequirementGroup group : criterion.getRequirementGroups()) {
            groupTypes.add(buildGroupType(group));
        }
        criterionType.getTenderingCriterionPropertyGroup().addAll(groupTypes);
    }

    private TenderingCriterionPropertyGroupType buildGroupType(RequirementGroup ccvGroup) {
        TenderingCriterionPropertyGroupType groupType = new TenderingCriterionPropertyGroupType();

        addGroupId(groupType);
        addRequirements(ccvGroup, groupType);
        addSubGroups(ccvGroup, groupType);

        return groupType;
    }

    private void addGroupId(TenderingCriterionPropertyGroupType groupType) {
        IDType idType = buildIdType();
        groupType.setID(idType);
    }

    private void addRequirements(RequirementGroup group, TenderingCriterionPropertyGroupType groupType) {
        if (isEmpty(group.getRequirements())) {
            return;
        }

        List<TenderingCriterionPropertyType> requirementTypes = new ArrayList<>(group.getRequirements().size() + 1);
        for (Requirement req : group.getRequirements()) {
            requirementTypes.add(buildRequirementType(req));
        }
        groupType.getTenderingCriterionProperty().addAll(requirementTypes);
    }

    private TenderingCriterionPropertyType buildRequirementType(Requirement requirement) {
        TenderingCriterionPropertyType requirementType = new TenderingCriterionPropertyType();

        IDType idType = buildIdType();
        idType.setSchemeID("CriterionRelatedIDs");
        idType.setSchemeVersionID("2.0.0");
        requirementType.setID(idType);

//        if (requirement.getResponseType() != null) {
//            requirementType.setResponseDataType(requirement.getResponseType().name());
//        }
//
//        requirementType.getResponse().add(buildResponse(requirement));

        return requirementType;
    }

//    private ResponseType buildResponse(Requirement requirement) {
//        ResponseType responseType = new ResponseType();
//
//        responseType.setDescription(buildDescriptionType((String) requirement.getValue()));
//
//        return responseType;
//    }

    private static DescriptionType buildDescriptionType(String description) {
        if (!StringUtils.hasText(description)) {
            // we don't want empty Description elements
            return null;
        }
        DescriptionType descriptionType = new DescriptionType();
        descriptionType.setValue(description);
        return descriptionType;
    }

    private void addSubGroups(RequirementGroup ccvGroup, TenderingCriterionPropertyGroupType parentGroup) {
        if (isEmpty(ccvGroup.getRequirementGroups())) {
            return;
        }

        List<TenderingCriterionPropertyGroupType> subGroups = new ArrayList<>();
        for (RequirementGroup ccvSubGroup : ccvGroup.getRequirementGroups()) {
            subGroups.add(buildGroupType(ccvSubGroup));
        }
        parentGroup.getSubsidiaryTenderingCriterionPropertyGroup().addAll(subGroups);
    }

    private static IDType buildIdType() {
        IDType idType = new IDType();
        idType.setValue(UUID.randomUUID().toString());
        idType.setSchemeAgencyID("VIGI");
        idType.setSchemeVersionID("2.0.0");
        idType.setSchemeID("2.0.0");
        return idType;
    }
}
