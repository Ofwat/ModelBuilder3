package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;

import uk.gov.ofwat.fountain.modelbuilder.domain.Page;


/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class PageTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {

        if (!(entity instanceof Page)) {
            throw new TranslatorException("This is not a Pagess");
        }

        Page page = (Page)entity;

        uk.gov.ofwat.fountain.modelbuilder.model.generated.Page jaxbPage =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.Page();

//            jaxbPage.setDocuments();

        PageDetailsTranslator pageDetailsTranslator =  new PageDetailsTranslator();
        uk.gov.ofwat.fountain.modelbuilder.model.generated.Pagedetails jaxbPagedetails =
            (uk.gov.ofwat.fountain.modelbuilder.model.generated.Pagedetails)pageDetailsTranslator.toJaxB(page.getPageDetail());
        jaxbPage.setPagedetails(jaxbPagedetails);

        SectionsTranslator sectionsTranslator =  new SectionsTranslator();
        uk.gov.ofwat.fountain.modelbuilder.model.generated.Sections jaxbSections =
            (uk.gov.ofwat.fountain.modelbuilder.model.generated.Sections)sectionsTranslator.toJaxB(page.getSections());
        jaxbPage.setSections(jaxbSections);

        return jaxbPage;
    }
}
