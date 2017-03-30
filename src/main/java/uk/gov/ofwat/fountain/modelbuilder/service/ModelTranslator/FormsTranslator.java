package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;


import uk.gov.ofwat.fountain.modelbuilder.domain.Form;

import java.util.Set;


/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class FormsTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {

        if (!(entity instanceof Set)) {
            throw new TranslatorException("This is not a Forms");
        }
        for (Object o : (Set)entity) {
            if (!(o instanceof Form)) {
                throw new TranslatorException("This is not a Form");
            }
        }

        Set<Form> forms = (Set<Form>)entity;

        uk.gov.ofwat.fountain.modelbuilder.model.generated.Forms jaxbForms =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.Forms();

        for (Form form: forms) {
            FormTranslator formTranslator =  new FormTranslator();
            uk.gov.ofwat.fountain.modelbuilder.model.generated.Form jaxbForm =
                (uk.gov.ofwat.fountain.modelbuilder.model.generated.Form)formTranslator.toJaxB(form);
            jaxbForms.getForm().add(jaxbForm);
        }
        return jaxbForms;
    }
}
