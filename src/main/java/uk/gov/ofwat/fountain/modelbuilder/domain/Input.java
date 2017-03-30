package uk.gov.ofwat.fountain.modelbuilder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Input.
 */
@Entity
@Table(name = "input")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "input")
public class Input implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "tag")
    private String tag;

    @NotNull
    @Column(name = "version", nullable = false)
    private String version;

    @Column(name = "company")
    private String company;

    @NotNull
    @Column(name = "default_input", nullable = false)
    private Boolean defaultInput;

    @NotNull
    @Size(max = 20)
    @Column(name = "model_reference", length = 20, nullable = false)
    private String modelReference;

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

    public Input code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTag() {
        return tag;
    }

    public Input tag(String tag) {
        this.tag = tag;
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getVersion() {
        return version;
    }

    public Input version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCompany() {
        return company;
    }

    public Input company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Boolean isDefaultInput() {
        return defaultInput;
    }

    public Input defaultInput(Boolean defaultInput) {
        this.defaultInput = defaultInput;
        return this;
    }

    public void setDefaultInput(Boolean defaultInput) {
        this.defaultInput = defaultInput;
    }

    public String getModelReference() {
        return modelReference;
    }

    public Input modelReference(String modelReference) {
        this.modelReference = modelReference;
        return this;
    }

    public void setModelReference(String modelReference) {
        this.modelReference = modelReference;
    }

    public Model getModel() {
        return model;
    }

    public Input model(Model model) {
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
        Input input = (Input) o;
        if (input.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, input.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Input{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", tag='" + tag + "'" +
            ", version='" + version + "'" +
            ", company='" + company + "'" +
            ", defaultInput='" + defaultInput + "'" +
            ", modelReference='" + modelReference + "'" +
            '}';
    }
}
