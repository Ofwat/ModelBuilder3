package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;


import uk.gov.ofwat.fountain.modelbuilder.domain.FormCell;

import java.util.Set;


/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class FormCellsTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {

        if (!(entity instanceof Set)) {
            throw new TranslatorException("This is not a FormCellss");
        }
        for (Object o : (Set)entity) {
            if (!(o instanceof FormCell)) {
                throw new TranslatorException("This is not a FormCell");
            }
        }

        Set<FormCell> formCells = (Set<FormCell>)entity;

        uk.gov.ofwat.fountain.modelbuilder.model.generated.FormCells jaxbFormCells =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.FormCells();

        for (FormCell formCell: formCells) {
            FormCellTranslator formCellTranslator =  new FormCellTranslator();
            uk.gov.ofwat.fountain.modelbuilder.model.generated.FormCell jaxbFormCell =
                (uk.gov.ofwat.fountain.modelbuilder.model.generated.FormCell)formCellTranslator.toJaxB(formCell);
            jaxbFormCells.getFormCell().add(jaxbFormCell);
        }
        return jaxbFormCells;
    }
}
