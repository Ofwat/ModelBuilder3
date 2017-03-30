package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;


import uk.gov.ofwat.fountain.modelbuilder.domain.FormHeadingCell;


/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class FormHeadingCellTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {

        if (!(entity instanceof FormHeadingCell)) {
            throw new TranslatorException("This is not a FormHeadingCell");
        }

        FormHeadingCell formHeadingCell = (FormHeadingCell)entity;

        uk.gov.ofwat.fountain.modelbuilder.model.generated.FormHeadingCell jaxbFormHeadingCell =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.FormHeadingCell();
        jaxbFormHeadingCell.setCellCode(formHeadingCell.getCellCode());
        jaxbFormHeadingCell.setColumn(formHeadingCell.getFormHeadingColumn());
        jaxbFormHeadingCell.setColumnSpan(formHeadingCell.getFormHeadingColumnSpan());
        jaxbFormHeadingCell.setGroupDescriptionCode(formHeadingCell.getGroupDescriptionCode());
        jaxbFormHeadingCell.setItemCode(formHeadingCell.getItemCode());
        jaxbFormHeadingCell.setRow(formHeadingCell.getRow());
        jaxbFormHeadingCell.setRowSpan(formHeadingCell.getRowSpan());
        jaxbFormHeadingCell.setText(formHeadingCell.getText());
        jaxbFormHeadingCell.setUseConfidenceGrades(formHeadingCell.isUseConfidenceGrades());
        jaxbFormHeadingCell.setUseItemCode(formHeadingCell.isUseItemCode());
        jaxbFormHeadingCell.setUseItemDescription(formHeadingCell.isUseItemDescription());
        jaxbFormHeadingCell.setUseLineNumber(formHeadingCell.isUseLineNumber());
        jaxbFormHeadingCell.setUseUnit(formHeadingCell.isUseUnit());
        jaxbFormHeadingCell.setUseYearCode(formHeadingCell.isUseYearCode());
        jaxbFormHeadingCell.setWidth(formHeadingCell.getWidth());
        jaxbFormHeadingCell.setYearCode(formHeadingCell.getYearCode());

        return jaxbFormHeadingCell;
    }
}
