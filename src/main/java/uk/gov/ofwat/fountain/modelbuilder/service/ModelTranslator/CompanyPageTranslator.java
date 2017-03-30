package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;

import uk.gov.ofwat.fountain.modelbuilder.domain.CompanyPage;


/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class CompanyPageTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {

        if (!(entity instanceof CompanyPage)) {
            throw new TranslatorException("This is not a CompanyPage");
        }

        CompanyPage companyPage = (CompanyPage)entity;

        uk.gov.ofwat.fountain.modelbuilder.model.generated.CompanyPage jaxbCompanyPage =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.CompanyPage();
        jaxbCompanyPage.setCompanyCode(companyPage.getCompanyCode());
        jaxbCompanyPage.setPageCode(companyPage.getPageCode());

        return jaxbCompanyPage;
    }
}
