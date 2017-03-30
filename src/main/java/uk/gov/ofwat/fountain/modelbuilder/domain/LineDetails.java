package uk.gov.ofwat.fountain.modelbuilder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LineDetails.
 */
@Entity
@Table(name = "line_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "linedetails")
public class LineDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "heading")
    private Boolean heading;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "equation")
    private String equation;

    @Column(name = "line_number")
    private String lineNumber;

    @Column(name = "rule_text")
    private String ruleText;

    @Column(name = "line_type")
    private String lineType;

    @Column(name = "company_type")
    private String companyType;

    @Column(name = "use_confidence_grade")
    private Boolean useConfidenceGrade;

    @Column(name = "validation_rule_code")
    private String validationRuleCode;

    @Column(name = "text_code")
    private String textCode;

    @Column(name = "unit")
    private String unit;

    @Column(name = "decimal_places")
    private Integer decimalPlaces;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isHeading() {
        return heading;
    }

    public LineDetails heading(Boolean heading) {
        this.heading = heading;
        return this;
    }

    public void setHeading(Boolean heading) {
        this.heading = heading;
    }

    public String getCode() {
        return code;
    }

    public LineDetails code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public LineDetails description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEquation() {
        return equation;
    }

    public LineDetails equation(String equation) {
        this.equation = equation;
        return this;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public LineDetails lineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
        return this;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getRuleText() {
        return ruleText;
    }

    public LineDetails ruleText(String ruleText) {
        this.ruleText = ruleText;
        return this;
    }

    public void setRuleText(String ruleText) {
        this.ruleText = ruleText;
    }

    public String getLineType() {
        return lineType;
    }

    public LineDetails lineType(String lineType) {
        this.lineType = lineType;
        return this;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public String getCompanyType() {
        return companyType;
    }

    public LineDetails companyType(String companyType) {
        this.companyType = companyType;
        return this;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public Boolean isUseConfidenceGrade() {
        return useConfidenceGrade;
    }

    public LineDetails useConfidenceGrade(Boolean useConfidenceGrade) {
        this.useConfidenceGrade = useConfidenceGrade;
        return this;
    }

    public void setUseConfidenceGrade(Boolean useConfidenceGrade) {
        this.useConfidenceGrade = useConfidenceGrade;
    }

    public String getValidationRuleCode() {
        return validationRuleCode;
    }

    public LineDetails validationRuleCode(String validationRuleCode) {
        this.validationRuleCode = validationRuleCode;
        return this;
    }

    public void setValidationRuleCode(String validationRuleCode) {
        this.validationRuleCode = validationRuleCode;
    }

    public String getTextCode() {
        return textCode;
    }

    public LineDetails textCode(String textCode) {
        this.textCode = textCode;
        return this;
    }

    public void setTextCode(String textCode) {
        this.textCode = textCode;
    }

    public String getUnit() {
        return unit;
    }

    public LineDetails unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getDecimalPlaces() {
        return decimalPlaces;
    }

    public LineDetails decimalPlaces(Integer decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
        return this;
    }

    public void setDecimalPlaces(Integer decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LineDetails lineDetails = (LineDetails) o;
        if (lineDetails.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lineDetails.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LineDetails{" +
            "id=" + id +
            ", heading='" + heading + "'" +
            ", code='" + code + "'" +
            ", description='" + description + "'" +
            ", equation='" + equation + "'" +
            ", lineNumber='" + lineNumber + "'" +
            ", ruleText='" + ruleText + "'" +
            ", lineType='" + lineType + "'" +
            ", companyType='" + companyType + "'" +
            ", useConfidenceGrade='" + useConfidenceGrade + "'" +
            ", validationRuleCode='" + validationRuleCode + "'" +
            ", textCode='" + textCode + "'" +
            ", unit='" + unit + "'" +
            ", decimalPlaces='" + decimalPlaces + "'" +
            '}';
    }
}
