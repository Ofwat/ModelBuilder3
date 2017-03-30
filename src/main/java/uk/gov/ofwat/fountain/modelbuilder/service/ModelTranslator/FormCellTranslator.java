package uk.gov.ofwat.fountain.modelbuilder.service.ModelTranslator;


import uk.gov.ofwat.fountain.modelbuilder.domain.FormCell;


/**
 * Created by Adam Edgar on 10/05/2016.
 */
public class FormCellTranslator implements Translator {
    @Override
    public Object toJaxB(Object entity) throws TranslatorException {

        if (!(entity instanceof FormCell)) {
            throw new TranslatorException("This is not a FormCells");
        }

        FormCell formCell = (FormCell)entity;

        uk.gov.ofwat.fountain.modelbuilder.model.generated.FormCell jaxbFormCell =
            new uk.gov.ofwat.fountain.modelbuilder.model.generated.FormCell();

        jaxbFormCell.setCellCode(formCell.getCellCode());
        jaxbFormCell.setColumn(formCell.getFormColumn());
        jaxbFormCell.setColumnHeadingSource(formCell.isColumnHeadingSource());
        jaxbFormCell.setColumnSpan(formCell.getFormColumnSpan());
        jaxbFormCell.setConfidenceGradeInputCode(formCell.getConfidenceGradeInputCode());
        jaxbFormCell.setGroupDescriptionCode(formCell.getGroupDescriptionCode());
        jaxbFormCell.setInputConfidenceGrade(formCell.isInputConfidenceGrade());
        jaxbFormCell.setRow(formCell.getRow());
        jaxbFormCell.setRowHeadingSource(formCell.isRowHeadingSource());
        jaxbFormCell.setRowSpan(formCell.getRowSpan());
        jaxbFormCell.setUseConfidenceGrade(formCell.isUseConfidenceGrade());
        jaxbFormCell.setWidth(formCell.getWidth());

        return jaxbFormCell;
    }
}
