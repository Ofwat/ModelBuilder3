package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;

import uk.gov.ofwat.fountain.modelbuilder.domain.PageDetails;

/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class PageDetailsTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {
        if (!(entity instanceof PageDetails)) {
            throw new TranslatorException("This is not a PageDetails");
        }
        PageDetails pageDetails = (PageDetails)entity;
        uk.gov.ofwat.fountain.modelbuilder.model.generated.Pagedetails jaxbPagedetails = new uk.gov.ofwat.fountain.modelbuilder.model.generated.Pagedetails();

        jaxbPagedetails.setCode(pageDetails.getCode());
        jaxbPagedetails.setDescription(pageDetails.getDescription());
        jaxbPagedetails.setCommercialInConfidence(pageDetails.isCommercialInConfidence());
        jaxbPagedetails.setCompanyType(pageDetails.getCompanyType());
        jaxbPagedetails.setHeading(pageDetails.getHeading());
        jaxbPagedetails.setHidden(pageDetails.isHidden());
        jaxbPagedetails.setText(pageDetails.getPageText());
        jaxbPagedetails.setTextCode(pageDetails.getTextCode());

        return jaxbPagedetails;
    }
}
