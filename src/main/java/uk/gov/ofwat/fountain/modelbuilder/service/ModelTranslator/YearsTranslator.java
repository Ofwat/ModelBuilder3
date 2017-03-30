package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;


import uk.gov.ofwat.fountain.modelbuilder.domain.Year;

import java.util.Set;


/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class YearsTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {

        if (!(entity instanceof Set)) {
            throw new TranslatorException("This is not a Yearss");
        }
        for (Object o : (Set)entity) {
            if (!(o instanceof Year)) {
                throw new TranslatorException("This is not a Year");
            }
        }

        Set<Year> years = (Set<Year>)entity;

        uk.gov.ofwat.fountain.modelbuilder.model.generated.Years jaxbYears =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.Years();

        for (Year year: years) {
            uk.gov.ofwat.fountain.modelbuilder.model.generated.Year jaxbYear =
                new uk.gov.ofwat.fountain.modelbuilder.model.generated.Year();
            jaxbYear.setValue(year.getBase());
            jaxbYears.getYear().add(jaxbYear);
        }
        return jaxbYears;
    }
}
