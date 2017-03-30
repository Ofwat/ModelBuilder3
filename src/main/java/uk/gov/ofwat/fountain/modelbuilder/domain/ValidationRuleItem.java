package uk.gov.ofwat.fountain.modelbuilder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ValidationRuleItem.
 */
@Entity
@Table(name = "validation_rule_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "validationruleitem")
public class ValidationRuleItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "evaluate")
    private String evaluate;

    @Column(name = "value")
    private String value;

    @ManyToOne
    private ValidationRule validationRule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public ValidationRuleItem type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public ValidationRuleItem evaluate(String evaluate) {
        this.evaluate = evaluate;
        return this;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getValue() {
        return value;
    }

    public ValidationRuleItem value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ValidationRule getValidationRule() {
        return validationRule;
    }

    public ValidationRuleItem validationRule(ValidationRule validationRule) {
        this.validationRule = validationRule;
        return this;
    }

    public void setValidationRule(ValidationRule validationRule) {
        this.validationRule = validationRule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ValidationRuleItem validationRuleItem = (ValidationRuleItem) o;
        if (validationRuleItem.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, validationRuleItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ValidationRuleItem{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", evaluate='" + evaluate + "'" +
            ", value='" + value + "'" +
            '}';
    }
}
