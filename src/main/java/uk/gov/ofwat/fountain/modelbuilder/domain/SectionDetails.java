package uk.gov.ofwat.fountain.modelbuilder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SectionDetails.
 */
@Entity
@Table(name = "section_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sectiondetails")
public class SectionDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "display", nullable = false)
    private String display;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "group_type")
    private String groupType;

    @NotNull
    @Column(name = "use_line_number", nullable = false)
    private Boolean useLineNumber;

    @NotNull
    @Column(name = "use_item_code", nullable = false)
    private Boolean useItemCode;

    @NotNull
    @Column(name = "use_item_description", nullable = false)
    private Boolean useItemDescription;

    @NotNull
    @Column(name = "use_unit", nullable = false)
    private Boolean useUnit;

    @NotNull
    @Column(name = "use_year_code", nullable = false)
    private Boolean useYearCode;

    @NotNull
    @Column(name = "use_confidence_grades", nullable = false)
    private Boolean useConfidenceGrades;

    @Column(name = "allow_group_selection")
    private Boolean allowGroupSelection;

    @Column(name = "allow_data_changes")
    private Boolean allowDataChanges;

    @Column(name = "section_type")
    private String sectionType;

    @Column(name = "item_code_column")
    private Integer itemCodeColumn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplay() {
        return display;
    }

    public SectionDetails display(String display) {
        this.display = display;
        return this;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getCode() {
        return code;
    }

    public SectionDetails code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGroupType() {
        return groupType;
    }

    public SectionDetails groupType(String groupType) {
        this.groupType = groupType;
        return this;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public Boolean isUseLineNumber() {
        return useLineNumber;
    }

    public SectionDetails useLineNumber(Boolean useLineNumber) {
        this.useLineNumber = useLineNumber;
        return this;
    }

    public void setUseLineNumber(Boolean useLineNumber) {
        this.useLineNumber = useLineNumber;
    }

    public Boolean isUseItemCode() {
        return useItemCode;
    }

    public SectionDetails useItemCode(Boolean useItemCode) {
        this.useItemCode = useItemCode;
        return this;
    }

    public void setUseItemCode(Boolean useItemCode) {
        this.useItemCode = useItemCode;
    }

    public Boolean isUseItemDescription() {
        return useItemDescription;
    }

    public SectionDetails useItemDescription(Boolean useItemDescription) {
        this.useItemDescription = useItemDescription;
        return this;
    }

    public void setUseItemDescription(Boolean useItemDescription) {
        this.useItemDescription = useItemDescription;
    }

    public Boolean isUseUnit() {
        return useUnit;
    }

    public SectionDetails useUnit(Boolean useUnit) {
        this.useUnit = useUnit;
        return this;
    }

    public void setUseUnit(Boolean useUnit) {
        this.useUnit = useUnit;
    }

    public Boolean isUseYearCode() {
        return useYearCode;
    }

    public SectionDetails useYearCode(Boolean useYearCode) {
        this.useYearCode = useYearCode;
        return this;
    }

    public void setUseYearCode(Boolean useYearCode) {
        this.useYearCode = useYearCode;
    }

    public Boolean isUseConfidenceGrades() {
        return useConfidenceGrades;
    }

    public SectionDetails useConfidenceGrades(Boolean useConfidenceGrades) {
        this.useConfidenceGrades = useConfidenceGrades;
        return this;
    }

    public void setUseConfidenceGrades(Boolean useConfidenceGrades) {
        this.useConfidenceGrades = useConfidenceGrades;
    }

    public Boolean isAllowGroupSelection() {
        return allowGroupSelection;
    }

    public SectionDetails allowGroupSelection(Boolean allowGroupSelection) {
        this.allowGroupSelection = allowGroupSelection;
        return this;
    }

    public void setAllowGroupSelection(Boolean allowGroupSelection) {
        this.allowGroupSelection = allowGroupSelection;
    }

    public Boolean isAllowDataChanges() {
        return allowDataChanges;
    }

    public SectionDetails allowDataChanges(Boolean allowDataChanges) {
        this.allowDataChanges = allowDataChanges;
        return this;
    }

    public void setAllowDataChanges(Boolean allowDataChanges) {
        this.allowDataChanges = allowDataChanges;
    }

    public String getSectionType() {
        return sectionType;
    }

    public SectionDetails sectionType(String sectionType) {
        this.sectionType = sectionType;
        return this;
    }

    public void setSectionType(String sectionType) {
        this.sectionType = sectionType;
    }

    public Integer getItemCodeColumn() {
        return itemCodeColumn;
    }

    public SectionDetails itemCodeColumn(Integer itemCodeColumn) {
        this.itemCodeColumn = itemCodeColumn;
        return this;
    }

    public void setItemCodeColumn(Integer itemCodeColumn) {
        this.itemCodeColumn = itemCodeColumn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SectionDetails sectionDetails = (SectionDetails) o;
        if (sectionDetails.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sectionDetails.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SectionDetails{" +
            "id=" + id +
            ", display='" + display + "'" +
            ", code='" + code + "'" +
            ", groupType='" + groupType + "'" +
            ", useLineNumber='" + useLineNumber + "'" +
            ", useItemCode='" + useItemCode + "'" +
            ", useItemDescription='" + useItemDescription + "'" +
            ", useUnit='" + useUnit + "'" +
            ", useYearCode='" + useYearCode + "'" +
            ", useConfidenceGrades='" + useConfidenceGrades + "'" +
            ", allowGroupSelection='" + allowGroupSelection + "'" +
            ", allowDataChanges='" + allowDataChanges + "'" +
            ", sectionType='" + sectionType + "'" +
            ", itemCodeColumn='" + itemCodeColumn + "'" +
            '}';
    }
}
