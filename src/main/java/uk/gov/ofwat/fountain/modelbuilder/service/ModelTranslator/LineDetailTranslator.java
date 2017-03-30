package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;

import uk.gov.ofwat.fountain.modelbuilder.domain.LineDetails;

import java.math.BigInteger;

/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class LineDetailTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {
        if (!(entity instanceof LineDetails)) {
            throw new TranslatorException("This is not a LineDetails");
        }
        LineDetails lineDetails = (LineDetails)entity;
        uk.gov.ofwat.fountain.modelbuilder.model.generated.Linedetails jaxbLinedetails =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.Linedetails();

        jaxbLinedetails.setCode(lineDetails.getCode());
        jaxbLinedetails.setCompanyType(lineDetails.getCompanyType());
        if (null != lineDetails.getDecimalPlaces()) {
            jaxbLinedetails.setDecimalPlaces(new BigInteger("" + lineDetails.getDecimalPlaces()));
        }
        jaxbLinedetails.setDescription(lineDetails.getDescription());
        jaxbLinedetails.setEquation(lineDetails.getEquation());
        jaxbLinedetails.setHeading(lineDetails.isHeading());
        jaxbLinedetails.setLinenumber(lineDetails.getLineNumber());
        jaxbLinedetails.setRuletext(lineDetails.getRuleText());
        jaxbLinedetails.setTextCode(lineDetails.getTextCode());
        jaxbLinedetails.setType(lineDetails.getLineType());
        jaxbLinedetails.setUnit(lineDetails.getUnit());
        jaxbLinedetails.setUseConfidenceGrade(lineDetails.isUseConfidenceGrade());
        jaxbLinedetails.setValidationRuleCode(lineDetails.getValidationRuleCode());


        return jaxbLinedetails;
    }
}
