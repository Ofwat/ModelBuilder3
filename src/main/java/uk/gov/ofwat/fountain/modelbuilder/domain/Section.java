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
 * A Section.
 */
@Entity
@Table(name = "section")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "section")
public class Section implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private SectionDetails sectionDetail;

    @OneToMany(mappedBy = "section")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ModelBuilderDocument> documents = new HashSet<>();

    @OneToMany(mappedBy = "section")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Line> lines = new HashSet<>();

    @OneToMany(mappedBy = "section")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Form> forms = new HashSet<>();

    @ManyToOne
    private Page page;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SectionDetails getSectionDetail() {
        return sectionDetail;
    }

    public Section sectionDetail(SectionDetails sectionDetails) {
        this.sectionDetail = sectionDetails;
        return this;
    }

    public void setSectionDetail(SectionDetails sectionDetails) {
        this.sectionDetail = sectionDetails;
    }

    public Set<ModelBuilderDocument> getDocuments() {
        return documents;
    }

    public Section documents(Set<ModelBuilderDocument> modelBuilderDocuments) {
        this.documents = modelBuilderDocuments;
        return this;
    }

    public Section addDocuments(ModelBuilderDocument modelBuilderDocument) {
        this.documents.add(modelBuilderDocument);
        modelBuilderDocument.setSection(this);
        return this;
    }

    public Section removeDocuments(ModelBuilderDocument modelBuilderDocument) {
        this.documents.remove(modelBuilderDocument);
        modelBuilderDocument.setSection(null);
        return this;
    }

    public void setDocuments(Set<ModelBuilderDocument> modelBuilderDocuments) {
        this.documents = modelBuilderDocuments;
    }

    public Set<Line> getLines() {
        return lines;
    }

    public Section lines(Set<Line> lines) {
        this.lines = lines;
        return this;
    }

    public Section addLines(Line line) {
        this.lines.add(line);
        line.setSection(this);
        return this;
    }

    public Section removeLines(Line line) {
        this.lines.remove(line);
        line.setSection(null);
        return this;
    }

    public void setLines(Set<Line> lines) {
        this.lines = lines;
    }

    public Set<Form> getForms() {
        return forms;
    }

    public Section forms(Set<Form> forms) {
        this.forms = forms;
        return this;
    }

    public Section addForms(Form form) {
        this.forms.add(form);
        form.setSection(this);
        return this;
    }

    public Section removeForms(Form form) {
        this.forms.remove(form);
        form.setSection(null);
        return this;
    }

    public void setForms(Set<Form> forms) {
        this.forms = forms;
    }

    public Page getPage() {
        return page;
    }

    public Section page(Page page) {
        this.page = page;
        return this;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Section section = (Section) o;
        if (section.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, section.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Section{" +
            "id=" + id +
            '}';
    }
}
