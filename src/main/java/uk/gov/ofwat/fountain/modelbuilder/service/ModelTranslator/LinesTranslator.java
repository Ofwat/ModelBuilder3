package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;


import uk.gov.ofwat.fountain.modelbuilder.domain.Line;

import java.util.Set;


/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class LinesTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {

        if (!(entity instanceof Set)) {
            throw new TranslatorException("This is not a Lines");
        }
        for (Object o : (Set)entity) {
            if (!(o instanceof Line)) {
                throw new TranslatorException("This is not a Line");
            }
        }

        Set<Line> lines = (Set<Line>)entity;

        uk.gov.ofwat.fountain.modelbuilder.model.generated.Lines jaxbLines =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.Lines();

        for (Line line: lines) {
            LineTranslator lineTranslator =  new LineTranslator();
            uk.gov.ofwat.fountain.modelbuilder.model.generated.Line jaxbLine =
                (uk.gov.ofwat.fountain.modelbuilder.model.generated.Line)lineTranslator.toJaxB(line);
            jaxbLines.getLine().add(jaxbLine);
        }
        return jaxbLines;
    }
}
