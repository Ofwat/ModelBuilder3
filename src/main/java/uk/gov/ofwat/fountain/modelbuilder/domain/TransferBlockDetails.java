package uk.gov.ofwat.fountain.modelbuilder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TransferBlockDetails.
 */
@Entity
@Table(name = "transfer_block_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "transferblockdetails")
public class TransferBlockDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_model_code")
    private String fromModelCode;

    @Column(name = "from_version_code")
    private String fromVersionCode;

    @Column(name = "from_page_code")
    private String fromPageCode;

    @Column(name = "to_model_code")
    private String toModelCode;

    @Column(name = "to_version_code")
    private String toVersionCode;

    @Column(name = "to_page_code")
    private String toPageCode;

    @Column(name = "to_macro_code")
    private String toMacroCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromModelCode() {
        return fromModelCode;
    }

    public TransferBlockDetails fromModelCode(String fromModelCode) {
        this.fromModelCode = fromModelCode;
        return this;
    }

    public void setFromModelCode(String fromModelCode) {
        this.fromModelCode = fromModelCode;
    }

    public String getFromVersionCode() {
        return fromVersionCode;
    }

    public TransferBlockDetails fromVersionCode(String fromVersionCode) {
        this.fromVersionCode = fromVersionCode;
        return this;
    }

    public void setFromVersionCode(String fromVersionCode) {
        this.fromVersionCode = fromVersionCode;
    }

    public String getFromPageCode() {
        return fromPageCode;
    }

    public TransferBlockDetails fromPageCode(String fromPageCode) {
        this.fromPageCode = fromPageCode;
        return this;
    }

    public void setFromPageCode(String fromPageCode) {
        this.fromPageCode = fromPageCode;
    }

    public String getToModelCode() {
        return toModelCode;
    }

    public TransferBlockDetails toModelCode(String toModelCode) {
        this.toModelCode = toModelCode;
        return this;
    }

    public void setToModelCode(String toModelCode) {
        this.toModelCode = toModelCode;
    }

    public String getToVersionCode() {
        return toVersionCode;
    }

    public TransferBlockDetails toVersionCode(String toVersionCode) {
        this.toVersionCode = toVersionCode;
        return this;
    }

    public void setToVersionCode(String toVersionCode) {
        this.toVersionCode = toVersionCode;
    }

    public String getToPageCode() {
        return toPageCode;
    }

    public TransferBlockDetails toPageCode(String toPageCode) {
        this.toPageCode = toPageCode;
        return this;
    }

    public void setToPageCode(String toPageCode) {
        this.toPageCode = toPageCode;
    }

    public String getToMacroCode() {
        return toMacroCode;
    }

    public TransferBlockDetails toMacroCode(String toMacroCode) {
        this.toMacroCode = toMacroCode;
        return this;
    }

    public void setToMacroCode(String toMacroCode) {
        this.toMacroCode = toMacroCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransferBlockDetails transferBlockDetails = (TransferBlockDetails) o;
        if (transferBlockDetails.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, transferBlockDetails.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TransferBlockDetails{" +
            "id=" + id +
            ", fromModelCode='" + fromModelCode + "'" +
            ", fromVersionCode='" + fromVersionCode + "'" +
            ", fromPageCode='" + fromPageCode + "'" +
            ", toModelCode='" + toModelCode + "'" +
            ", toVersionCode='" + toVersionCode + "'" +
            ", toPageCode='" + toPageCode + "'" +
            ", toMacroCode='" + toMacroCode + "'" +
            '}';
    }
}
