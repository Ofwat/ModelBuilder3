package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;

import uk.gov.ofwat.fountain.modelbuilder.domain.SectionDetails;

import java.math.BigInteger;

/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class SectionDetailsTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {
        if (!(entity instanceof SectionDetails)) {
            throw new TranslatorException("This is not a SectionDetails");
        }
        SectionDetails sectionDetails = (SectionDetails)entity;
        uk.gov.ofwat.fountain.modelbuilder.model.generated.Sectiondetails jaxbSectiondetails =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.Sectiondetails();

        jaxbSectiondetails.setAllowDataChanges(sectionDetails.isAllowDataChanges());
        jaxbSectiondetails.setAllowGroupSelection(sectionDetails.isAllowGroupSelection());
        jaxbSectiondetails.setCode(sectionDetails.getCode());
        jaxbSectiondetails.setDisplay(sectionDetails.getDisplay());
        jaxbSectiondetails.setGrouptype(sectionDetails.getGroupType());
        if (null != sectionDetails.getItemCodeColumn()) {
            jaxbSectiondetails.setItemCodeColumn(new BigInteger("" + sectionDetails.getItemCodeColumn()));
        }
        jaxbSectiondetails.setSectionType(sectionDetails.getSectionType());
        jaxbSectiondetails.setUseConfidenceGrades(sectionDetails.isUseConfidenceGrades());
        jaxbSectiondetails.setUseItemCode(sectionDetails.isUseItemCode());
        jaxbSectiondetails.setUseItemDescription(sectionDetails.isUseItemDescription());
        jaxbSectiondetails.setUseLineNumber(sectionDetails.isUseLineNumber());
        jaxbSectiondetails.setUseUnit(sectionDetails.isUseUnit());
        jaxbSectiondetails.setUseYearCode(sectionDetails.isUseYearCode());


        return jaxbSectiondetails;
    }
}
