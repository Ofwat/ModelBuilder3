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
 * A TransferBlock.
 */
@Entity
@Table(name = "transfer_block")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "transferblock")
public class TransferBlock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private TransferBlockDetails transferBlockDetails;

    @OneToMany(mappedBy = "transferBLock")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TransferBlockItem> transferBlockItems = new HashSet<>();

    @ManyToOne
    private Transfer transfer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransferBlockDetails getTransferBlockDetails() {
        return transferBlockDetails;
    }

    public TransferBlock transferBlockDetails(TransferBlockDetails transferBlockDetails) {
        this.transferBlockDetails = transferBlockDetails;
        return this;
    }

    public void setTransferBlockDetails(TransferBlockDetails transferBlockDetails) {
        this.transferBlockDetails = transferBlockDetails;
    }

    public Set<TransferBlockItem> getTransferBlockItems() {
        return transferBlockItems;
    }

    public TransferBlock transferBlockItems(Set<TransferBlockItem> transferBlockItems) {
        this.transferBlockItems = transferBlockItems;
        return this;
    }

    public TransferBlock addTransferBlockItems(TransferBlockItem transferBlockItem) {
        this.transferBlockItems.add(transferBlockItem);
        transferBlockItem.setTransferBLock(this);
        return this;
    }

    public TransferBlock removeTransferBlockItems(TransferBlockItem transferBlockItem) {
        this.transferBlockItems.remove(transferBlockItem);
        transferBlockItem.setTransferBLock(null);
        return this;
    }

    public void setTransferBlockItems(Set<TransferBlockItem> transferBlockItems) {
        this.transferBlockItems = transferBlockItems;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public TransferBlock transfer(Transfer transfer) {
        this.transfer = transfer;
        return this;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransferBlock transferBlock = (TransferBlock) o;
        if (transferBlock.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, transferBlock.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TransferBlock{" +
            "id=" + id +
            '}';
    }
}
