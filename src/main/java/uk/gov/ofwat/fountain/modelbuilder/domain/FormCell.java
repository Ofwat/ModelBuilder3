package uk.gov.ofwat.fountain.modelbuilder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A FormCell.
 */
@Entity
@Table(name = "form_cell")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "formcell")
public class FormCell implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cell_code")
    private String cellCode;

    @Column(name = "use_confidence_grade")
    private Boolean useConfidenceGrade;

    @Column(name = "input_confidence_grade")
    private Boolean inputConfidenceGrade;

    @Column(name = "confidence_grade_input_code")
    private String confidenceGradeInputCode;

    @Column(name = "row_heading_source")
    private Boolean rowHeadingSource;

    @Column(name = "column_heading_source")
    private Boolean columnHeadingSource;

    @Column(name = "group_description_code")
    private String groupDescriptionCode;

    @NotNull
    @Column(name = "row", nullable = false)
    private String row;

    @NotNull
    @Column(name = "form_column", nullable = false)
    private String formColumn;

    @Column(name = "row_span")
    private String rowSpan;

    @Column(name = "form_column_span")
    private String formColumnSpan;

    @Column(name = "width")
    private String width;

    @ManyToOne
    private Form form;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCellCode() {
        return cellCode;
    }

    public FormCell cellCode(String cellCode) {
        this.cellCode = cellCode;
        return this;
    }

    public void setCellCode(String cellCode) {
        this.cellCode = cellCode;
    }

    public Boolean isUseConfidenceGrade() {
        return useConfidenceGrade;
    }

    public FormCell useConfidenceGrade(Boolean useConfidenceGrade) {
        this.useConfidenceGrade = useConfidenceGrade;
        return this;
    }

    public void setUseConfidenceGrade(Boolean useConfidenceGrade) {
        this.useConfidenceGrade = useConfidenceGrade;
    }

    public Boolean isInputConfidenceGrade() {
        return inputConfidenceGrade;
    }

    public FormCell inputConfidenceGrade(Boolean inputConfidenceGrade) {
        this.inputConfidenceGrade = inputConfidenceGrade;
        return this;
    }

    public void setInputConfidenceGrade(Boolean inputConfidenceGrade) {
        this.inputConfidenceGrade = inputConfidenceGrade;
    }

    public String getConfidenceGradeInputCode() {
        return confidenceGradeInputCode;
    }

    public FormCell confidenceGradeInputCode(String confidenceGradeInputCode) {
        this.confidenceGradeInputCode = confidenceGradeInputCode;
        return this;
    }

    public void setConfidenceGradeInputCode(String confidenceGradeInputCode) {
        this.confidenceGradeInputCode = confidenceGradeInputCode;
    }

    public Boolean isRowHeadingSource() {
        return rowHeadingSource;
    }

    public FormCell rowHeadingSource(Boolean rowHeadingSource) {
        this.rowHeadingSource = rowHeadingSource;
        return this;
    }

    public void setRowHeadingSource(Boolean rowHeadingSource) {
        this.rowHeadingSource = rowHeadingSource;
    }

    public Boolean isColumnHeadingSource() {
        return columnHeadingSource;
    }

    public FormCell columnHeadingSource(Boolean columnHeadingSource) {
        this.columnHeadingSource = columnHeadingSource;
        return this;
    }

    public void setColumnHeadingSource(Boolean columnHeadingSource) {
        this.columnHeadingSource = columnHeadingSource;
    }

    public String getGroupDescriptionCode() {
        return groupDescriptionCode;
    }

    public FormCell groupDescriptionCode(String groupDescriptionCode) {
        this.groupDescriptionCode = groupDescriptionCode;
        return this;
    }

    public void setGroupDescriptionCode(String groupDescriptionCode) {
        this.groupDescriptionCode = groupDescriptionCode;
    }

    public String getRow() {
        return row;
    }

    public FormCell row(String row) {
        this.row = row;
        return this;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getFormColumn() {
        return formColumn;
    }

    public FormCell formColumn(String formColumn) {
        this.formColumn = formColumn;
        return this;
    }

    public void setFormColumn(String formColumn) {
        this.formColumn = formColumn;
    }

    public String getRowSpan() {
        return rowSpan;
    }

    public FormCell rowSpan(String rowSpan) {
        this.rowSpan = rowSpan;
        return this;
    }

    public void setRowSpan(String rowSpan) {
        this.rowSpan = rowSpan;
    }

    public String getFormColumnSpan() {
        return formColumnSpan;
    }

    public FormCell formColumnSpan(String formColumnSpan) {
        this.formColumnSpan = formColumnSpan;
        return this;
    }

    public void setFormColumnSpan(String formColumnSpan) {
        this.formColumnSpan = formColumnSpan;
    }

    public String getWidth() {
        return width;
    }

    public FormCell width(String width) {
        this.width = width;
        return this;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public Form getForm() {
        return form;
    }

    public FormCell form(Form form) {
        this.form = form;
        return this;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FormCell formCell = (FormCell) o;
        if (formCell.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, formCell.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FormCell{" +
            "id=" + id +
            ", cellCode='" + cellCode + "'" +
            ", useConfidenceGrade='" + useConfidenceGrade + "'" +
            ", inputConfidenceGrade='" + inputConfidenceGrade + "'" +
            ", confidenceGradeInputCode='" + confidenceGradeInputCode + "'" +
            ", rowHeadingSource='" + rowHeadingSource + "'" +
            ", columnHeadingSource='" + columnHeadingSource + "'" +
            ", groupDescriptionCode='" + groupDescriptionCode + "'" +
            ", row='" + row + "'" +
            ", formColumn='" + formColumn + "'" +
            ", rowSpan='" + rowSpan + "'" +
            ", formColumnSpan='" + formColumnSpan + "'" +
            ", width='" + width + "'" +
            '}';
    }
}
