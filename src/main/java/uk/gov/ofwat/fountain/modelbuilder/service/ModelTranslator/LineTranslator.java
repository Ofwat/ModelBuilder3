package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;


import uk.gov.ofwat.fountain.modelbuilder.domain.Line;


/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class LineTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {

        if (!(entity instanceof Line)) {
            throw new TranslatorException("This is not a Line");
        }

        Line line = (Line)entity;

        uk.gov.ofwat.fountain.modelbuilder.model.generated.Line jaxbLine =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.Line();

//        jaxbLine.setDocuments();

// line.getCellRange() is returning null
        if (null != line.getCellRange()) {
            CellRangeTranslator cellRangeTranslator = new CellRangeTranslator();
            uk.gov.ofwat.fountain.modelbuilder.model.generated.Cellrange jaxbCellrange =
                (uk.gov.ofwat.fountain.modelbuilder.model.generated.Cellrange) cellRangeTranslator.toJaxB(line.getCellRange());
            jaxbLine.setCellrange(jaxbCellrange);
        }

        LineDetailTranslator lineDetailTranslator = new LineDetailTranslator();
        uk.gov.ofwat.fountain.modelbuilder.model.generated.Linedetails jaxbLinedetails =
            (uk.gov.ofwat.fountain.modelbuilder.model.generated.Linedetails)lineDetailTranslator.toJaxB(line.getLineDetail());
        jaxbLine.setLinedetails(jaxbLinedetails);

        CellsTranslator cellsTranslator = new CellsTranslator();
        uk.gov.ofwat.fountain.modelbuilder.model.generated.Cells jaxbCells =
            (uk.gov.ofwat.fountain.modelbuilder.model.generated.Cells)cellsTranslator.toJaxB(line.getCells());
        jaxbLine.setCells(jaxbCells);


        return jaxbLine;
    }
}
