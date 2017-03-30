package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;

import uk.gov.ofwat.fountain.modelbuilder.domain.Cell;


/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class CellTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {

        if (!(entity instanceof Cell)) {
            throw new TranslatorException("This is not a Cell");
        }

        Cell cell = (Cell)entity;

        uk.gov.ofwat.fountain.modelbuilder.model.generated.Cell jaxbCell =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.Cell();

        jaxbCell.setCgtype(cell.getCgType());
        jaxbCell.setCode(cell.getCode());
        jaxbCell.setEquation(cell.getEquation());
        jaxbCell.setType(cell.getType());
        jaxbCell.setYear(cell.getYear());

        return jaxbCell;
    }
}
