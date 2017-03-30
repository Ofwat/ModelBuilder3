package uk.gov.ofwat.fountain.modelbuilder.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TransferBlockItem.
 */
@Entity
@Table(name = "transfer_block_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "transferblockitem")
public class TransferBlockItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "item_code", nullable = false)
    private String itemCode;

    @Column(name = "company_type")
    private String companyType;

    @OneToMany(mappedBy = "transferBlockItem")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<YearCode> yearCodes = new HashSet<>();

    @ManyToOne
    private TransferBlock transferBLock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemCode() {
        return itemCode;
    }

    public TransferBlockItem itemCode(String itemCode) {
        this.itemCode = itemCode;
        return this;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getCompanyType() {
        return companyType;
    }

    public TransferBlockItem companyType(String companyType) {
        this.companyType = companyType;
        return this;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public Set<YearCode> getYearCodes() {
        return yearCodes;
    }

    public TransferBlockItem yearCodes(Set<YearCode> yearCodes) {
        this.yearCodes = yearCodes;
        return this;
    }

    public TransferBlockItem addYearCodes(YearCode yearCode) {
        this.yearCodes.add(yearCode);
        yearCode.setTransferBlockItem(this);
        return this;
    }

    public TransferBlockItem removeYearCodes(YearCode yearCode) {
        this.yearCodes.remove(yearCode);
        yearCode.setTransferBlockItem(null);
        return this;
    }

    public void setYearCodes(Set<YearCode> yearCodes) {
        this.yearCodes = yearCodes;
    }

    public TransferBlock getTransferBLock() {
        return transferBLock;
    }

    public TransferBlockItem transferBLock(TransferBlock transferBlock) {
        this.transferBLock = transferBlock;
        return this;
    }

    public void setTransferBLock(TransferBlock transferBlock) {
        this.transferBLock = transferBlock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransferBlockItem transferBlockItem = (TransferBlockItem) o;
        if (transferBlockItem.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, transferBlockItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TransferBlockItem{" +
            "id=" + id +
            ", itemCode='" + itemCode + "'" +
            ", companyType='" + companyType + "'" +
            '}';
    }
}
