package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;


import uk.gov.ofwat.fountain.modelbuilder.domain.Heading;

import java.util.Set;


/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class HeadingsTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {

        if (!(entity instanceof Set)) {
            throw new TranslatorException("This is not a Headingss");
        }
        for (Object o : (Set)entity) {
            if (!(o instanceof Heading)) {
                throw new TranslatorException("This is not a Heading");
            }
        }

        Set<Heading> headings = (Set<Heading>)entity;

        uk.gov.ofwat.fountain.modelbuilder.model.generated.Headings jaxbHeadings =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.Headings();

        for (Heading heading: headings) {
            HeadingTranslator headingTranslator =  new HeadingTranslator();
            uk.gov.ofwat.fountain.modelbuilder.model.generated.Heading jaxbHeading =
                (uk.gov.ofwat.fountain.modelbuilder.model.generated.Heading)headingTranslator.toJaxB(heading);
            jaxbHeadings.getHeading().add(jaxbHeading);
        }
        return jaxbHeadings;
    }
}
