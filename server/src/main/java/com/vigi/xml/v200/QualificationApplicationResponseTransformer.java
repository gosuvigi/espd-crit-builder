package com.vigi.xml.v200;

import com.vigi.criteria.Criterion;
import com.vigi.criteria.Requirement;
import com.vigi.criteria.RequirementGroup;
import com.vigi.criteria.ResponseType;
import com.vigi.edm.LocalDateAdapter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import test.x.ubl.pre_award.commonaggregate.*;
import test.x.ubl.pre_award.commonbasic.*;
import test.x.ubl.pre_award.qualificationapplicationresponse.QualificationApplicationResponseType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * Created by vigi on 5/14/2017.
 */
@Component
class QualificationApplicationResponseTransformer {

    private final EvidenceType evidenceType;

    QualificationApplicationResponseTransformer() {
        evidenceType = buildEvidenceType();
    }

    private EvidenceType buildEvidenceType() {
        EvidenceType evidenceType = new EvidenceType();

        evidenceType.setID(buildIdType(UUID.randomUUID().toString()));
        evidenceType.getDescription().add(buildDescriptionType("Proof of administrative error."));

        DocumentReferenceType referenceType = new DocumentReferenceType();
        referenceType.setID(buildIdType("CERT-189892212390"));

        AttachmentType attachment = new AttachmentType();
        ExternalReferenceType externalReference = new ExternalReferenceType();
        URIType uri = new URIType();
        uri.setValue("http://taxagency.lv/certificates/cert?ID=189892212390");
        externalReference.setURI(uri);
        attachment.setExternalReference(externalReference);
        referenceType.setAttachment(attachment);

        PartyType party = new PartyType();
        PartyNameType nameType = new PartyNameType();
        NameType name = new NameType();
        name.setValue("LIAA, Investment and Development Agency of Latvia");
        nameType.setName(name);
        party.getPartyName().add(nameType);
        referenceType.setIssuerParty(party);

        evidenceType.getDocumentReference().add(referenceType);

        return evidenceType;
    }

    QualificationApplicationResponseType buildResponseType(List<Criterion> criteria) {
        QualificationApplicationResponseType responseType = new QualificationApplicationResponseType();

        addCriteria(criteria, responseType);

        responseType.getEvidence().add(evidenceType);

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

        if (StringUtils.hasText(criterion.getUuid())) {
            criterionType.setID(buildIdType(criterion.getUuid()));
        } else {
            criterionType.setID(buildIdType(UUID.randomUUID().toString()));
        }
        addName(criterion, criterionType);
        if (StringUtils.hasText(criterion.getDescription())) {
            criterionType.getDescription().add(buildDescriptionType(criterion.getDescription()));
        }
        addGroups(criterion, criterionType, responseType);

        return criterionType;
    }

    private void addName(Criterion criterion, TenderingCriterionType criterionType) {
        NameType nameType = new NameType();
        nameType.setValue(criterion.getName());
        criterionType.setName(nameType);
    }

    private void addGroups(Criterion criterion, TenderingCriterionType criterionType,
                           QualificationApplicationResponseType responseType) {
        if (isEmpty(criterion.getGroups())) {
            return;
        }

        List<TenderingCriterionPropertyGroupType> groupTypes = new ArrayList<>();
        for (RequirementGroup group : criterion.getGroups()) {
            groupTypes.add(buildGroupType(group, responseType));
        }
        criterionType.getTenderingCriterionPropertyGroup().addAll(groupTypes);
    }

    private TenderingCriterionPropertyGroupType buildGroupType(RequirementGroup requirementGroup,
                                                               QualificationApplicationResponseType responseType) {
        TenderingCriterionPropertyGroupType groupType = new TenderingCriterionPropertyGroupType();

        addGroupId(requirementGroup, groupType);
        addRequirements(requirementGroup, groupType, responseType);
        addSubGroups(requirementGroup, groupType, responseType);

        return groupType;
    }

    private void addGroupId(RequirementGroup requirementGroup, TenderingCriterionPropertyGroupType groupType) {
        if (StringUtils.hasText(requirementGroup.getId())) {
            groupType.setID(buildIdType(requirementGroup.getId()));
        } else {
            groupType.setID(buildIdType(UUID.randomUUID().toString()));
        }
    }

    private void addRequirements(RequirementGroup group, TenderingCriterionPropertyGroupType groupType,
                                 QualificationApplicationResponseType responseType) {
        if (isEmpty(group.getRequirements())) {
            return;
        }
        List<TenderingCriterionPropertyType> requirementTypes = new ArrayList<>();
        List<TenderingCriterionResponseType> responseTypes = new ArrayList<>();
        for (Requirement req : group.getRequirements()) {
            final String reqId;
            if (StringUtils.hasText(req.getId())) {
                reqId = req.getId();
            } else {
                reqId = UUID.randomUUID().toString();
            }
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
        ResponseValueType responseValueType = buildResponseValueType(requirement);
        responseType.getResponseValue().add(responseValueType);

        ValidatedCriterionRequirementIDType idType = new ValidatedCriterionRequirementIDType();
        idType.setSchemeAgencyID("EU-COM-GROW");
        idType.setSchemeVersionID("2.0.0");
        idType.setValue(requirement.getId());
        responseType.setValidatedCriterionRequirementID(idType);

        if (ResponseType.EVIDENCE.equals(requirement.getResponseType())) {
            EvidenceSuppliedType evidenceSuppliedType = new EvidenceSuppliedType();
            evidenceSuppliedType.setID(evidenceType.getID());
            responseType.getEvidenceSupplied().add(evidenceSuppliedType);
        } else if (ResponseType.PERIOD.equals(requirement.getResponseType())) {
            PeriodType periodType = new PeriodType();

            StartDateType startDate = new StartDateType();
            startDate.setValue(LocalDate.now());
            periodType.setStartDate(startDate);

            EndDateType endDate = new EndDateType();
            endDate.setValue(LocalDate.now().plusDays(666));
            periodType.setEndDate(endDate);

            responseType.getApplicablePeriod().add(periodType);
        }


        return responseType;
    }

    private ResponseValueType buildResponseValueType(Requirement requirement) {
        ResponseValueType responseValueType = new ResponseValueType();
        if (requirement.getValue() == null) {
            return null;
        }

        if (ResponseType.AMOUNT.equals(requirement.getResponseType())) {
            ResponseAmountType amount = new ResponseAmountType();
            amount.setValue(new BigDecimal((String) requirement.getValue()));
            amount.setCurrencyID("EUR");
            responseValueType.setResponseAmount(amount);
        } else if (ResponseType.DATE.equals(requirement.getResponseType())) {
            ResponseDateType date = new ResponseDateType();
            date.setValue(LocalDateAdapter.unmarshal((String) requirement.getValue()));
            responseValueType.setResponseDate(date);
        } else if (ResponseType.DESCRIPTION.equals(requirement.getResponseType())) {
            responseValueType.getDescription().add(buildDescriptionType(requirement.getValue().toString()));
        } else if (ResponseType.INDICATOR.equals(requirement.getResponseType())) {
            ResponseIndicatorType indicator = new ResponseIndicatorType();
            boolean val = "true".equalsIgnoreCase((String) requirement.getValue());
            indicator.setValue(val);
            responseValueType.setResponseIndicator(indicator);
        } else if (ResponseType.NUMBER.equals(requirement.getResponseType())) {
            ResponseNumericType numeric = new ResponseNumericType();
            numeric.setValue(new BigDecimal((String) requirement.getValue()));
            responseValueType.setResponseNumeric(numeric);
        } else if (ResponseType.CODE.equals(requirement.getResponseType())) {
            ResponseCodeType responseCodeType = new ResponseCodeType();
            responseCodeType.setValue(requirement.getValue().toString());
            responseValueType.setResponseCode(responseCodeType);
        } else if (ResponseType.PERIOD.equals(requirement.getResponseType())) {
            System.out.println("TODO how to fill a PERIOD type");
        }
        return responseValueType;
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
        if (isEmpty(ccvGroup.getSubgroups())) {
            return;
        }

        List<TenderingCriterionPropertyGroupType> subGroups = new ArrayList<>();
        for (RequirementGroup ccvSubGroup : ccvGroup.getSubgroups()) {
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
