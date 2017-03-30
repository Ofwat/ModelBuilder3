package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;


import uk.gov.ofwat.fountain.modelbuilder.domain.CellRange;


/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class CellRangeTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {

        if (!(entity instanceof CellRange)) {
            throw new TranslatorException("This is not a CellRange");
        }

        CellRange cellRange = (CellRange)entity;

        uk.gov.ofwat.fountain.modelbuilder.model.generated.Cellrange jaxbCellRange =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.Cellrange();

        jaxbCellRange.setStartyear(cellRange.getStartYear());
        jaxbCellRange.setEndyear(cellRange.getEndYear());

        return jaxbCellRange;
    }
}
