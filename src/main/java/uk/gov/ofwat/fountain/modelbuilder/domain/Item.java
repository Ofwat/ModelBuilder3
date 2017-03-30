package uk.gov.ofwat.fountain.modelbuilder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Item.
 */
@Entity
@Table(name = "item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "item")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "version_number", nullable = false)
    private String versionNumber;

    @Column(name = "pricebase_code")
    private String pricebaseCode;

    @Column(name = "purpose_code")
    private String purposeCode;

    @Column(name = "text_code")
    private String textCode;

    @ManyToOne
    private Model model;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Item code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public Item versionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
        return this;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getPricebaseCode() {
        return pricebaseCode;
    }

    public Item pricebaseCode(String pricebaseCode) {
        this.pricebaseCode = pricebaseCode;
        return this;
    }

    public void setPricebaseCode(String pricebaseCode) {
        this.pricebaseCode = pricebaseCode;
    }

    public String getPurposeCode() {
        return purposeCode;
    }

    public Item purposeCode(String purposeCode) {
        this.purposeCode = purposeCode;
        return this;
    }

    public void setPurposeCode(String purposeCode) {
        this.purposeCode = purposeCode;
    }

    public String getTextCode() {
        return textCode;
    }

    public Item textCode(String textCode) {
        this.textCode = textCode;
        return this;
    }

    public void setTextCode(String textCode) {
        this.textCode = textCode;
    }

    public Model getModel() {
        return model;
    }

    public Item model(Model model) {
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
        Item item = (Item) o;
        if (item.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Item{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", versionNumber='" + versionNumber + "'" +
            ", pricebaseCode='" + pricebaseCode + "'" +
            ", purposeCode='" + purposeCode + "'" +
            ", textCode='" + textCode + "'" +
            '}';
    }
}
