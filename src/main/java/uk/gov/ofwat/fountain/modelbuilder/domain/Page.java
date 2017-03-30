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
 * A Page.
 */
@Entity
@Table(name = "page")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "page")
public class Page implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private PageDetails pageDetail;

    @OneToMany(mappedBy = "page")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Section> sections = new HashSet<>();

    @OneToMany(mappedBy = "page")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ModelBuilderDocument> documents = new HashSet<>();

    @ManyToOne
    private Model model;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PageDetails getPageDetail() {
        return pageDetail;
    }

    public Page pageDetail(PageDetails pageDetails) {
        this.pageDetail = pageDetails;
        return this;
    }

    public void setPageDetail(PageDetails pageDetails) {
        this.pageDetail = pageDetails;
    }

    public Set<Section> getSections() {
        return sections;
    }

    public Page sections(Set<Section> sections) {
        this.sections = sections;
        return this;
    }

    public Page addSections(Section section) {
        this.sections.add(section);
        section.setPage(this);
        return this;
    }

    public Page removeSections(Section section) {
        this.sections.remove(section);
        section.setPage(null);
        return this;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }

    public Set<ModelBuilderDocument> getDocuments() {
        return documents;
    }

    public Page documents(Set<ModelBuilderDocument> modelBuilderDocuments) {
        this.documents = modelBuilderDocuments;
        return this;
    }

    public Page addDocuments(ModelBuilderDocument modelBuilderDocument) {
        this.documents.add(modelBuilderDocument);
        modelBuilderDocument.setPage(this);
        return this;
    }

    public Page removeDocuments(ModelBuilderDocument modelBuilderDocument) {
        this.documents.remove(modelBuilderDocument);
        modelBuilderDocument.setPage(null);
        return this;
    }

    public void setDocuments(Set<ModelBuilderDocument> modelBuilderDocuments) {
        this.documents = modelBuilderDocuments;
    }

    public Model getModel() {
        return model;
    }

    public Page model(Model model) {
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
        Page page = (Page) o;
        if (page.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, page.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Page{" +
            "id=" + id +
            '}';
    }
}
