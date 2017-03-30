package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;


import uk.gov.ofwat.fountain.modelbuilder.domain.Input;

import java.util.Set;


/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class InputsTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {

        if (!(entity instanceof Set)) {
            throw new TranslatorException("This is not a Inputss");
        }
        for (Object o : (Set)entity) {
            if (!(o instanceof Input)) {
                throw new TranslatorException("This is not a Input");
            }
        }

        Set<Input> inputs = (Set<Input>)entity;

        uk.gov.ofwat.fountain.modelbuilder.model.generated.Inputs jaxbInputs =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.Inputs();

        for (Input input: inputs) {
            InputTranslator inputTranslator =  new InputTranslator();
            uk.gov.ofwat.fountain.modelbuilder.model.generated.Input jaxbInput =
                (uk.gov.ofwat.fountain.modelbuilder.model.generated.Input)inputTranslator.toJaxB(input);
            jaxbInputs.getInput().add(jaxbInput);
        }
        return jaxbInputs;
    }
}
