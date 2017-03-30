package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;


import uk.gov.ofwat.fountain.modelbuilder.domain.Form;


/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class FormTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {

        if (!(entity instanceof Form)) {
            throw new TranslatorException("This is not a Form");
        }

        Form form = (Form)entity;

        uk.gov.ofwat.fountain.modelbuilder.model.generated.Form jaxbForm =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.Form();

        FormDetailTranslator formDetailTranslator = new FormDetailTranslator();
        uk.gov.ofwat.fountain.modelbuilder.model.generated.FormDetails jaxbFormDetails =
            (uk.gov.ofwat.fountain.modelbuilder.model.generated.FormDetails)formDetailTranslator.toJaxB(form.getFormDetail());
        jaxbForm.setFormDetails(jaxbFormDetails);

        FormCellsTranslator formCellsTranslator = new FormCellsTranslator();
        uk.gov.ofwat.fountain.modelbuilder.model.generated.FormCells jaxbFormCells =
            (uk.gov.ofwat.fountain.modelbuilder.model.generated.FormCells)formCellsTranslator.toJaxB(form.getFormCells());
        jaxbForm.setFormCells(jaxbFormCells);

        FormHeadingsTopTranslator formHeadingsTopTranslator = new FormHeadingsTopTranslator();
        uk.gov.ofwat.fountain.modelbuilder.model.generated.FormHeadingsTop jaxbFormHeadingsTop =
            (uk.gov.ofwat.fountain.modelbuilder.model.generated.FormHeadingsTop)formHeadingsTopTranslator.toJaxB(form.getFormHeadingsTops());
        jaxbForm.setFormHeadingsTop(jaxbFormHeadingsTop);

        FormHeadingsLeftTranslator formHeadingsLeftTranslator = new FormHeadingsLeftTranslator();
        uk.gov.ofwat.fountain.modelbuilder.model.generated.FormHeadingsLeft jaxbFormHeadingsLeft =
            (uk.gov.ofwat.fountain.modelbuilder.model.generated.FormHeadingsLeft)formHeadingsLeftTranslator.toJaxB(form.getFormHeadingsLefts());
        jaxbForm.setFormHeadingsLeft(jaxbFormHeadingsLeft);

        return jaxbForm;
    }
}
