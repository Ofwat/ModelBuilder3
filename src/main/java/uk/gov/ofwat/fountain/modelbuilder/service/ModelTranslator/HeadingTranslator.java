package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;


import uk.gov.ofwat.fountain.modelbuilder.domain.Heading;


/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class HeadingTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {

        if (!(entity instanceof Heading)) {
            throw new TranslatorException("This is not a Headingss");
        }

        Heading heading = (Heading)entity;

        uk.gov.ofwat.fountain.modelbuilder.model.generated.Heading jaxbHeading =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.Heading();
        jaxbHeading.setCode(heading.getCode());
        jaxbHeading.setAnnotation(heading.getAnnotation());
        jaxbHeading.setDescription(heading.getDescription());
        jaxbHeading.setNotes(heading.getNotes());
        jaxbHeading.setParent(heading.getParent());

        return jaxbHeading;
    }
}
