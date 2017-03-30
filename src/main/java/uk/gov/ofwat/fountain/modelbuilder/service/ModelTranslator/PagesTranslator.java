package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;

import uk.gov.ofwat.fountain.modelbuilder.domain.Page;

import java.util.Set;


/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class PagesTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {

        if (!(entity instanceof Set)) {
            throw new TranslatorException("This is not a Pagess");
        }
        for (Object o : (Set)entity) {
            if (!(o instanceof Page)) {
                throw new TranslatorException("This is not a Page");
            }
        }

        Set<Page> pages = (Set<Page>)entity;

        uk.gov.ofwat.fountain.modelbuilder.model.generated.Pages jaxbPages =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.Pages();

        for (Page page: pages) {
            PageTranslator pageTranslator =  new PageTranslator();
            uk.gov.ofwat.fountain.modelbuilder.model.generated.Page jaxbPage =
                (uk.gov.ofwat.fountain.modelbuilder.model.generated.Page)pageTranslator.toJaxB(page);
            jaxbPages.getPage().add(jaxbPage);
        }
        return jaxbPages;
    }
}
