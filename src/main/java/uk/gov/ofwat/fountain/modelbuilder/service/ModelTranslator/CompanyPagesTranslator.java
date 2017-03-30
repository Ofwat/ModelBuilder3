package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;

import uk.gov.ofwat.fountain.modelbuilder.domain.CompanyPage;
import java.util.Set;



/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class CompanyPagesTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {

        if (!(entity instanceof Set)) {
            throw new TranslatorException("This is not a CompanyPagess");
        }
        for (Object o : (Set)entity) {
            if (!(o instanceof CompanyPage)) {
                throw new TranslatorException("This is not a CompanyPage");
            }
        }

        Set<CompanyPage> companyPages = (Set<CompanyPage>)entity;

        uk.gov.ofwat.fountain.modelbuilder.model.generated.CompanyPages jaxbCompanyPages =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.CompanyPages();

        for (CompanyPage companyPage: companyPages) {
            CompanyPageTranslator companyPageTranslator =  new CompanyPageTranslator();
            uk.gov.ofwat.fountain.modelbuilder.model.generated.CompanyPage jaxbCompanyPage =
                (uk.gov.ofwat.fountain.modelbuilder.model.generated.CompanyPage)companyPageTranslator.toJaxB(companyPage);
            jaxbCompanyPages.getCompanyPage().add(jaxbCompanyPage);
        }
        return jaxbCompanyPages;
    }
}
