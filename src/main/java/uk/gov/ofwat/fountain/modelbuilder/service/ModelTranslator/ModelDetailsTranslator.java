package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;

import uk.gov.ofwat.fountain.modelbuilder.domain.ModelDetails;

import java.math.BigInteger;

/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class ModelDetailsTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {
        if (!(entity instanceof ModelDetails)) {
            throw new TranslatorException("This is not a ModelDetails");
        }
        ModelDetails modelDetails = (ModelDetails)entity;
        uk.gov.ofwat.fountain.modelbuilder.model.generated.Modeldetails jaxbModeldetails = new uk.gov.ofwat.fountain.modelbuilder.model.generated.Modeldetails();

        jaxbModeldetails.setBaseYearCode(modelDetails.getBaseYearCode());
        jaxbModeldetails.setBranchTag(modelDetails.getBranchTag());
        jaxbModeldetails.setCode(modelDetails.getCode());
        if (null != modelDetails.getDisplayOrder()) {
            jaxbModeldetails.setDisplayOrder(new BigInteger("" + modelDetails.getDisplayOrder()));
        }
        jaxbModeldetails.setModelFamilyCode(modelDetails.getModelFamilyCode());
        jaxbModeldetails.setName(modelDetails.getName());
        jaxbModeldetails.setReportYearCode(modelDetails.getReportYearCode());
        jaxbModeldetails.setRunCode(modelDetails.getRunCode());
        jaxbModeldetails.setTextCode(modelDetails.getTextCode());
        jaxbModeldetails.setType(modelDetails.getModelType());
        jaxbModeldetails.setVersion(modelDetails.getVersion());

        return jaxbModeldetails;
    }
}
