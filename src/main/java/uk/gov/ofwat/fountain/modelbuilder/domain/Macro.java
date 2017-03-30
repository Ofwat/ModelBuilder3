package uk.gov.ofwat.fountain.modelbuilder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Macro.
 */
@Entity
@Table(name = "macro")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "macro")
public class Macro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "block", nullable = false)
    private String block;

    @Column(name = "conditional_item_code")
    private String conditionalItemCode;

    @Column(name = "page_code")
    private String pageCode;

    @ManyToOne
    private Model model;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Macro name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Macro description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBlock() {
        return block;
    }

    public Macro block(String block) {
        this.block = block;
        return this;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getConditionalItemCode() {
        return conditionalItemCode;
    }

    public Macro conditionalItemCode(String conditionalItemCode) {
        this.conditionalItemCode = conditionalItemCode;
        return this;
    }

    public void setConditionalItemCode(String conditionalItemCode) {
        this.conditionalItemCode = conditionalItemCode;
    }

    public String getPageCode() {
        return pageCode;
    }

    public Macro pageCode(String pageCode) {
        this.pageCode = pageCode;
        return this;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }

    public Model getModel() {
        return model;
    }

    public Macro model(Model model) {
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
        Macro macro = (Macro) o;
        if (macro.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, macro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Macro{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", block='" + block + "'" +
            ", conditionalItemCode='" + conditionalItemCode + "'" +
            ", pageCode='" + pageCode + "'" +
            '}';
    }
}
