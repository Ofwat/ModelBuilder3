package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;

import uk.gov.ofwat.fountain.modelbuilder.domain.FormDetails;

/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class FormDetailTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {
        if (!(entity instanceof FormDetails)) {
            throw new TranslatorException("This is not a FormDetails");
        }
        FormDetails formDetails = (FormDetails)entity;
        uk.gov.ofwat.fountain.modelbuilder.model.generated.FormDetails jaxbFormDetails =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.FormDetails();

        jaxbFormDetails.setCompanyType(formDetails.getCompanyType());

        return jaxbFormDetails;
    }
}
