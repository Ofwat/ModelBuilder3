package uk.gov.ofwat.fountain.modelbuilder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A YearCode.
 */
@Entity
@Table(name = "year_code")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "yearcode")
public class YearCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "year_code", nullable = false)
    private String yearCode;

    @ManyToOne
    private TransferBlockItem transferBlockItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYearCode() {
        return yearCode;
    }

    public YearCode yearCode(String yearCode) {
        this.yearCode = yearCode;
        return this;
    }

    public void setYearCode(String yearCode) {
        this.yearCode = yearCode;
    }

    public TransferBlockItem getTransferBlockItem() {
        return transferBlockItem;
    }

    public YearCode transferBlockItem(TransferBlockItem transferBlockItem) {
        this.transferBlockItem = transferBlockItem;
        return this;
    }

    public void setTransferBlockItem(TransferBlockItem transferBlockItem) {
        this.transferBlockItem = transferBlockItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        YearCode yearCode = (YearCode) o;
        if (yearCode.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, yearCode.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "YearCode{" +
            "id=" + id +
            ", yearCode='" + yearCode + "'" +
            '}';
    }
}
