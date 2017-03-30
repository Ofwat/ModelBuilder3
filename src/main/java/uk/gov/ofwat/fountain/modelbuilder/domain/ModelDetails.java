package uk.gov.ofwat.fountain.modelbuilder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ModelDetails.
 */
@Entity
@Table(name = "model_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "modeldetails")
public class ModelDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @NotNull
    @Column(name = "model_type", nullable = false)
    private String modelType;

    @Column(name = "text_code")
    private String textCode;

    @Column(name = "base_year_code")
    private String baseYearCode;

    @Column(name = "report_year_code")
    private String reportYearCode;

    @Column(name = "allow_data_changes")
    private Boolean allowDataChanges;

    @Column(name = "model_family_code")
    private String modelFamilyCode;

    @NotNull
    @Column(name = "model_family_parent", nullable = false)
    private Boolean modelFamilyParent;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "branch_tag")
    private String branchTag;

    @Column(name = "run_code")
    private String runCode;

    @Column(name = "last_modified")
    private ZonedDateTime lastModified;

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "fountain_model_id")
    private Integer fountainModelId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public ModelDetails code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public ModelDetails name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public ModelDetails version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getModelType() {
        return modelType;
    }

    public ModelDetails modelType(String modelType) {
        this.modelType = modelType;
        return this;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getTextCode() {
        return textCode;
    }

    public ModelDetails textCode(String textCode) {
        this.textCode = textCode;
        return this;
    }

    public void setTextCode(String textCode) {
        this.textCode = textCode;
    }

    public String getBaseYearCode() {
        return baseYearCode;
    }

    public ModelDetails baseYearCode(String baseYearCode) {
        this.baseYearCode = baseYearCode;
        return this;
    }

    public void setBaseYearCode(String baseYearCode) {
        this.baseYearCode = baseYearCode;
    }

    public String getReportYearCode() {
        return reportYearCode;
    }

    public ModelDetails reportYearCode(String reportYearCode) {
        this.reportYearCode = reportYearCode;
        return this;
    }

    public void setReportYearCode(String reportYearCode) {
        this.reportYearCode = reportYearCode;
    }

    public Boolean isAllowDataChanges() {
        return allowDataChanges;
    }

    public ModelDetails allowDataChanges(Boolean allowDataChanges) {
        this.allowDataChanges = allowDataChanges;
        return this;
    }

    public void setAllowDataChanges(Boolean allowDataChanges) {
        this.allowDataChanges = allowDataChanges;
    }

    public String getModelFamilyCode() {
        return modelFamilyCode;
    }

    public ModelDetails modelFamilyCode(String modelFamilyCode) {
        this.modelFamilyCode = modelFamilyCode;
        return this;
    }

    public void setModelFamilyCode(String modelFamilyCode) {
        this.modelFamilyCode = modelFamilyCode;
    }

    public Boolean isModelFamilyParent() {
        return modelFamilyParent;
    }

    public ModelDetails modelFamilyParent(Boolean modelFamilyParent) {
        this.modelFamilyParent = modelFamilyParent;
        return this;
    }

    public void setModelFamilyParent(Boolean modelFamilyParent) {
        this.modelFamilyParent = modelFamilyParent;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public ModelDetails displayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
        return this;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getBranchTag() {
        return branchTag;
    }

    public ModelDetails branchTag(String branchTag) {
        this.branchTag = branchTag;
        return this;
    }

    public void setBranchTag(String branchTag) {
        this.branchTag = branchTag;
    }

    public String getRunCode() {
        return runCode;
    }

    public ModelDetails runCode(String runCode) {
        this.runCode = runCode;
        return this;
    }

    public void setRunCode(String runCode) {
        this.runCode = runCode;
    }

    public ZonedDateTime getLastModified() {
        return lastModified;
    }

    public ModelDetails lastModified(ZonedDateTime lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    public void setLastModified(ZonedDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public ModelDetails created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ModelDetails createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public ModelDetails lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Integer getFountainModelId() {
        return fountainModelId;
    }

    public ModelDetails fountainModelId(Integer fountainModelId) {
        this.fountainModelId = fountainModelId;
        return this;
    }

    public void setFountainModelId(Integer fountainModelId) {
        this.fountainModelId = fountainModelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ModelDetails modelDetails = (ModelDetails) o;
        if (modelDetails.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, modelDetails.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ModelDetails{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            ", version='" + version + "'" +
            ", modelType='" + modelType + "'" +
            ", textCode='" + textCode + "'" +
            ", baseYearCode='" + baseYearCode + "'" +
            ", reportYearCode='" + reportYearCode + "'" +
            ", allowDataChanges='" + allowDataChanges + "'" +
            ", modelFamilyCode='" + modelFamilyCode + "'" +
            ", modelFamilyParent='" + modelFamilyParent + "'" +
            ", displayOrder='" + displayOrder + "'" +
            ", branchTag='" + branchTag + "'" +
            ", runCode='" + runCode + "'" +
            ", lastModified='" + lastModified + "'" +
            ", created='" + created + "'" +
            ", createdBy='" + createdBy + "'" +
            ", lastModifiedBy='" + lastModifiedBy + "'" +
            ", fountainModelId='" + fountainModelId + "'" +
            '}';
    }
}
