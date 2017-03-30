package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;


import uk.gov.ofwat.fountain.modelbuilder.domain.Input;


/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class InputTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {

        if (!(entity instanceof Input)) {
            throw new TranslatorException("This is not a Input");
        }

        Input input = (Input)entity;

        uk.gov.ofwat.fountain.modelbuilder.model.generated.Input jaxbInput =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.Input();
        jaxbInput.setCode(input.getCode());
        jaxbInput.setCompany(input.getCompany());
        jaxbInput.setDefault(input.isDefaultInput());
        jaxbInput.setModel(input.getModelReference());
        jaxbInput.setTag(input.getTag());
        jaxbInput.setVersion(input.getVersion());

        return jaxbInput;
    }
}
