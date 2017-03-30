package uk.gov.ofwat.fountain.modelbuilder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TransferCondition.
 */
@Entity
@Table(name = "transfer_condition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "transfercondition")
public class TransferCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "item_code", nullable = false)
    private String itemCode;

    @NotNull
    @Column(name = "year_code", nullable = false)
    private String yearCode;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    @NotNull
    @Column(name = "failure_message", nullable = false)
    private String failureMessage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemCode() {
        return itemCode;
    }

    public TransferCondition itemCode(String itemCode) {
        this.itemCode = itemCode;
        return this;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getYearCode() {
        return yearCode;
    }

    public TransferCondition yearCode(String yearCode) {
        this.yearCode = yearCode;
        return this;
    }

    public void setYearCode(String yearCode) {
        this.yearCode = yearCode;
    }

    public String getValue() {
        return value;
    }

    public TransferCondition value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public TransferCondition failureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
        return this;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransferCondition transferCondition = (TransferCondition) o;
        if (transferCondition.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, transferCondition.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TransferCondition{" +
            "id=" + id +
            ", itemCode='" + itemCode + "'" +
            ", yearCode='" + yearCode + "'" +
            ", value='" + value + "'" +
            ", failureMessage='" + failureMessage + "'" +
            '}';
    }
}
