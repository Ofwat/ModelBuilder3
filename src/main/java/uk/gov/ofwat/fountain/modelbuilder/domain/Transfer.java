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
 * A Transfer.
 */
@Entity
@Table(name = "transfer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "transfer")
public class Transfer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    private TransferCondition transferCondition;

    @OneToMany(mappedBy = "transfer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TransferBlock> transferBlocks = new HashSet<>();

    @ManyToOne
    private Model model;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Transfer description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransferCondition getTransferCondition() {
        return transferCondition;
    }

    public Transfer transferCondition(TransferCondition transferCondition) {
        this.transferCondition = transferCondition;
        return this;
    }

    public void setTransferCondition(TransferCondition transferCondition) {
        this.transferCondition = transferCondition;
    }

    public Set<TransferBlock> getTransferBlocks() {
        return transferBlocks;
    }

    public Transfer transferBlocks(Set<TransferBlock> transferBlocks) {
        this.transferBlocks = transferBlocks;
        return this;
    }

    public Transfer addTransferBlocks(TransferBlock transferBlock) {
        this.transferBlocks.add(transferBlock);
        transferBlock.setTransfer(this);
        return this;
    }

    public Transfer removeTransferBlocks(TransferBlock transferBlock) {
        this.transferBlocks.remove(transferBlock);
        transferBlock.setTransfer(null);
        return this;
    }

    public void setTransferBlocks(Set<TransferBlock> transferBlocks) {
        this.transferBlocks = transferBlocks;
    }

    public Model getModel() {
        return model;
    }

    public Transfer model(Model model) {
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
        Transfer transfer = (Transfer) o;
        if (transfer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, transfer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Transfer{" +
            "id=" + id +
            ", description='" + description + "'" +
            '}';
    }
}
