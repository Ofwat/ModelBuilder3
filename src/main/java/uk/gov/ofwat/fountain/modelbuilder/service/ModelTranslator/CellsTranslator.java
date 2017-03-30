package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;

import uk.gov.ofwat.fountain.modelbuilder.domain.Cell;

import java.util.Set;


/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class CellsTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {

        if (!(entity instanceof Set)) {
            throw new TranslatorException("This is not a Cellss");
        }
        for (Object o : (Set)entity) {
            if (!(o instanceof Cell)) {
                throw new TranslatorException("This is not a Cell");
            }
        }

        Set<Cell> cells = (Set<Cell>)entity;

        uk.gov.ofwat.fountain.modelbuilder.model.generated.Cells jaxbCells =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.Cells();

        for (Cell cell: cells) {
            CellTranslator cellTranslator =  new CellTranslator();
            uk.gov.ofwat.fountain.modelbuilder.model.generated.Cell jaxbCell =
                (uk.gov.ofwat.fountain.modelbuilder.model.generated.Cell)cellTranslator.toJaxB(cell);
            jaxbCells.getCell().add(jaxbCell);
        }
        return jaxbCells;
    }
}
