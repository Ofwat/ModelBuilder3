package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;

import uk.gov.ofwat.fountain.modelbuilder.domain.Section;

import java.util.Set;


/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class SectionsTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {

        if (!(entity instanceof Set)) {
            throw new TranslatorException("This is not a Sectionss");
        }
        for (Object o : (Set)entity) {
            if (!(o instanceof Section)) {
                throw new TranslatorException("This is not a Page");
            }
        }

        Set<Section> sections = (Set<Section>)entity;

        uk.gov.ofwat.fountain.modelbuilder.model.generated.Sections jaxbSections =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.Sections();

        for (Section section: sections) {
            SectionTranslator sectionTranslator =  new SectionTranslator();
            uk.gov.ofwat.fountain.modelbuilder.model.generated.Section jaxbSection =
                (uk.gov.ofwat.fountain.modelbuilder.model.generated.Section)sectionTranslator.toJaxB(section);
            jaxbSections.getSection().add(jaxbSection);
        }
        return jaxbSections;
    }
}
