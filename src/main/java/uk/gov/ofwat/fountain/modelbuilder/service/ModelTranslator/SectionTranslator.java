package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;

import uk.gov.ofwat.fountain.modelbuilder.domain.Section;


/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class SectionTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {

        if (!(entity instanceof Section)) {
            throw new TranslatorException("This is not a Section");
        }

        Section section = (Section)entity;

        uk.gov.ofwat.fountain.modelbuilder.model.generated.Section jaxbSection =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.Section();

        SectionDetailsTranslator sectionDetailsTranslator =  new SectionDetailsTranslator();
        uk.gov.ofwat.fountain.modelbuilder.model.generated.Sectiondetails jaxbSectiondetails =
            (uk.gov.ofwat.fountain.modelbuilder.model.generated.Sectiondetails)sectionDetailsTranslator.toJaxB(section.getSectionDetail());
        jaxbSection.setSectiondetails(jaxbSectiondetails);

//        jaxbSection.setDocuments();

        FormsTranslator formsTranslator =  new FormsTranslator();
        uk.gov.ofwat.fountain.modelbuilder.model.generated.Forms jaxbForms =
            (uk.gov.ofwat.fountain.modelbuilder.model.generated.Forms)formsTranslator.toJaxB(section.getForms());
        jaxbSection.setForms(jaxbForms);

        LinesTranslator linesTranslator =  new LinesTranslator();
        uk.gov.ofwat.fountain.modelbuilder.model.generated.Lines jaxbLines =
            (uk.gov.ofwat.fountain.modelbuilder.model.generated.Lines)linesTranslator.toJaxB(section.getLines());
        jaxbSection.setLines(jaxbLines);

        return jaxbSection;
    }
}
