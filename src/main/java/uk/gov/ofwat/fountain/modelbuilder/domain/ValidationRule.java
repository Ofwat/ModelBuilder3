package uk.gov.ofwat.fountain.modelbuilder.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ValidationRule.
 */
@Entity
@Table(name = "validation_rule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "validationrule")
public class ValidationRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private ValidationRuleDetails validationRuleDetail;

    @OneToMany(mappedBy = "validationRule")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ValidationRuleItem> validationRuleItems = new HashSet<>();

    @ManyToOne
    private Model model;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ValidationRuleDetails getValidationRuleDetail() {
        return validationRuleDetail;
    }

    public ValidationRule validationRuleDetail(ValidationRuleDetails validationRuleDetails) {
        this.validationRuleDetail = validationRuleDetails;
        return this;
    }

    public void setValidationRuleDetail(ValidationRuleDetails validationRuleDetails) {
        this.validationRuleDetail = validationRuleDetails;
    }

    public Set<ValidationRuleItem> getValidationRuleItems() {
        return validationRuleItems;
    }

    public ValidationRule validationRuleItems(Set<ValidationRuleItem> validationRuleItems) {
        this.validationRuleItems = validationRuleItems;
        return this;
    }

    public ValidationRule addValidationRuleItems(ValidationRuleItem validationRuleItem) {
        this.validationRuleItems.add(validationRuleItem);
        validationRuleItem.setValidationRule(this);
        return this;
    }

    public ValidationRule removeValidationRuleItems(ValidationRuleItem validationRuleItem) {
        this.validationRuleItems.remove(validationRuleItem);
        validationRuleItem.setValidationRule(null);
        return this;
    }

    public void setValidationRuleItems(Set<ValidationRuleItem> validationRuleItems) {
        this.validationRuleItems = validationRuleItems;
    }

    public Model getModel() {
        return model;
    }

    public ValidationRule model(Model model) {
        this.model = model;
        return this;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ValidationRule validationRule = (ValidationRule) o;
        if (validationRule.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, validationRule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ValidationRule{" +
            "id=" + id +
            '}';
    }
}
