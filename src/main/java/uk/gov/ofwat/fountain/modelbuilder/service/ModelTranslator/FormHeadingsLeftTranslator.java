package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;


import uk.gov.ofwat.fountain.modelbuilder.domain.FormHeadingCell;

import java.util.Set;


/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class FormHeadingsLeftTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {

        if (!(entity instanceof Set)) {
            throw new TranslatorException("This is not a FormHeadingsLeft");
        }
        for (Object o : (Set)entity) {
            if (!(o instanceof FormHeadingCell)) {
                throw new TranslatorException("This is not a FormHeadingCell");
            }
        }

        Set<FormHeadingCell> formHeadingsLeft = (Set<FormHeadingCell>)entity;

        uk.gov.ofwat.fountain.modelbuilder.model.generated.FormHeadingsLeft jaxbFormHeadingsLeft =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.FormHeadingsLeft();

        for (FormHeadingCell formHeadingCell: formHeadingsLeft) {
            FormHeadingCellTranslator formHeadingCellTranslator =  new FormHeadingCellTranslator();
            uk.gov.ofwat.fountain.modelbuilder.model.generated.FormHeadingCell jaxbFormHeadingCell =
                (uk.gov.ofwat.fountain.modelbuilder.model.generated.FormHeadingCell)formHeadingCellTranslator.toJaxB(formHeadingCell);
            jaxbFormHeadingsLeft.getFormHeadingCell().add(jaxbFormHeadingCell);
        }
        return jaxbFormHeadingsLeft;
    }
}
