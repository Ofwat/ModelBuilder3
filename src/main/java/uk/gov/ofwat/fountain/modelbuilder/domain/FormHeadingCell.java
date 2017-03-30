package uk.gov.ofwat.fountain.modelbuilder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A FormHeadingCell.
 */
@Entity
@Table(name = "form_heading_cell")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "formheadingcell")
public class FormHeadingCell implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "use_line_number")
    private Boolean useLineNumber;

    @Column(name = "use_item_code")
    private Boolean useItemCode;

    @Column(name = "use_item_description")
    private Boolean useItemDescription;

    @Column(name = "use_unit")
    private Boolean useUnit;

    @Column(name = "use_year_code")
    private Boolean useYearCode;

    @Column(name = "use_confidence_grades")
    private Boolean useConfidenceGrades;

    @NotNull
    @Column(name = "row", nullable = false)
    private String row;

    @NotNull
    @Column(name = "form_heading_column", nullable = false)
    private String formHeadingColumn;

    @Column(name = "row_span")
    private String rowSpan;

    @Column(name = "form_heading_column_span")
    private String formHeadingColumnSpan;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "year_code")
    private String yearCode;

    @Column(name = "width")
    private String width;

    @Column(name = "cell_code")
    private String cellCode;

    @Column(name = "group_description_code")
    private String groupDescriptionCode;

    @ManyToOne
    private Form formTop;

    @ManyToOne
    private Form formLeft;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public FormHeadingCell text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean isUseLineNumber() {
        return useLineNumber;
    }

    public FormHeadingCell useLineNumber(Boolean useLineNumber) {
        this.useLineNumber = useLineNumber;
        return this;
    }

    public void setUseLineNumber(Boolean useLineNumber) {
        this.useLineNumber = useLineNumber;
    }

    public Boolean isUseItemCode() {
        return useItemCode;
    }

    public FormHeadingCell useItemCode(Boolean useItemCode) {
        this.useItemCode = useItemCode;
        return this;
    }

    public void setUseItemCode(Boolean useItemCode) {
        this.useItemCode = useItemCode;
    }

    public Boolean isUseItemDescription() {
        return useItemDescription;
    }

    public FormHeadingCell useItemDescription(Boolean useItemDescription) {
        this.useItemDescription = useItemDescription;
        return this;
    }

    public void setUseItemDescription(Boolean useItemDescription) {
        this.useItemDescription = useItemDescription;
    }

    public Boolean isUseUnit() {
        return useUnit;
    }

    public FormHeadingCell useUnit(Boolean useUnit) {
        this.useUnit = useUnit;
        return this;
    }

    public void setUseUnit(Boolean useUnit) {
        this.useUnit = useUnit;
    }

    public Boolean isUseYearCode() {
        return useYearCode;
    }

    public FormHeadingCell useYearCode(Boolean useYearCode) {
        this.useYearCode = useYearCode;
        return this;
    }

    public void setUseYearCode(Boolean useYearCode) {
        this.useYearCode = useYearCode;
    }

    public Boolean isUseConfidenceGrades() {
        return useConfidenceGrades;
    }

    public FormHeadingCell useConfidenceGrades(Boolean useConfidenceGrades) {
        this.useConfidenceGrades = useConfidenceGrades;
        return this;
    }

    public void setUseConfidenceGrades(Boolean useConfidenceGrades) {
        this.useConfidenceGrades = useConfidenceGrades;
    }

    public String getRow() {
        return row;
    }

    public FormHeadingCell row(String row) {
        this.row = row;
        return this;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getFormHeadingColumn() {
        return formHeadingColumn;
    }

    public FormHeadingCell formHeadingColumn(String formHeadingColumn) {
        this.formHeadingColumn = formHeadingColumn;
        return this;
    }

    public void setFormHeadingColumn(String formHeadingColumn) {
        this.formHeadingColumn = formHeadingColumn;
    }

    public String getRowSpan() {
        return rowSpan;
    }

    public FormHeadingCell rowSpan(String rowSpan) {
        this.rowSpan = rowSpan;
        return this;
    }

    public void setRowSpan(String rowSpan) {
        this.rowSpan = rowSpan;
    }

    public String getFormHeadingColumnSpan() {
        return formHeadingColumnSpan;
    }

    public FormHeadingCell formHeadingColumnSpan(String formHeadingColumnSpan) {
        this.formHeadingColumnSpan = formHeadingColumnSpan;
        return this;
    }

    public void setFormHeadingColumnSpan(String formHeadingColumnSpan) {
        this.formHeadingColumnSpan = formHeadingColumnSpan;
    }

    public String getItemCode() {
        return itemCode;
    }

    public FormHeadingCell itemCode(String itemCode) {
        this.itemCode = itemCode;
        return this;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getYearCode() {
        return yearCode;
    }

    public FormHeadingCell yearCode(String yearCode) {
        this.yearCode = yearCode;
        return this;
    }

    public void setYearCode(String yearCode) {
        this.yearCode = yearCode;
    }

    public String getWidth() {
        return width;
    }

    public FormHeadingCell width(String width) {
        this.width = width;
        return this;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getCellCode() {
        return cellCode;
    }

    public FormHeadingCell cellCode(String cellCode) {
        this.cellCode = cellCode;
        return this;
    }

    public void setCellCode(String cellCode) {
        this.cellCode = cellCode;
    }

    public String getGroupDescriptionCode() {
        return groupDescriptionCode;
    }

    public FormHeadingCell groupDescriptionCode(String groupDescriptionCode) {
        this.groupDescriptionCode = groupDescriptionCode;
        return this;
    }

    public void setGroupDescriptionCode(String groupDescriptionCode) {
        this.groupDescriptionCode = groupDescriptionCode;
    }

    public Form getFormTop() {
        return formTop;
    }

    public FormHeadingCell formTop(Form form) {
        this.formTop = form;
        return this;
    }

    public void setFormTop(Form form) {
        this.formTop = form;
    }

    public Form getFormLeft() {
        return formLeft;
    }

    public FormHeadingCell formLeft(Form form) {
        this.formLeft = form;
        return this;
    }

    public void setFormLeft(Form form) {
        this.formLeft = form;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FormHeadingCell formHeadingCell = (FormHeadingCell) o;
        if (formHeadingCell.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, formHeadingCell.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FormHeadingCell{" +
            "id=" + id +
            ", text='" + text + "'" +
            ", useLineNumber='" + useLineNumber + "'" +
            ", useItemCode='" + useItemCode + "'" +
            ", useItemDescription='" + useItemDescription + "'" +
            ", useUnit='" + useUnit + "'" +
            ", useYearCode='" + useYearCode + "'" +
            ", useConfidenceGrades='" + useConfidenceGrades + "'" +
            ", row='" + row + "'" +
            ", formHeadingColumn='" + formHeadingColumn + "'" +
            ", rowSpan='" + rowSpan + "'" +
            ", formHeadingColumnSpan='" + formHeadingColumnSpan + "'" +
            ", itemCode='" + itemCode + "'" +
            ", yearCode='" + yearCode + "'" +
            ", width='" + width + "'" +
            ", cellCode='" + cellCode + "'" +
            ", groupDescriptionCode='" + groupDescriptionCode + "'" +
            '}';
    }
}
