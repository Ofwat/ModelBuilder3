package uk.gov.ofwat.fountain.modelbuilder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ModelBuilderDocument.
 */
@Entity
@Table(name = "model_builder_document")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "modelbuilderdocument")
public class ModelBuilderDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "reporter", nullable = false)
    private String reporter;

    @NotNull
    @Column(name = "auditor", nullable = false)
    private String auditor;

    @ManyToOne
    private Model model;

    @ManyToOne
    private Page page;

    @ManyToOne
    private Section section;

    @ManyToOne
    private Line line;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReporter() {
        return reporter;
    }

    public ModelBuilderDocument reporter(String reporter) {
        this.reporter = reporter;
        return this;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getAuditor() {
        return auditor;
    }

    public ModelBuilderDocument auditor(String auditor) {
        this.auditor = auditor;
        return this;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public Model getModel() {
        return model;
    }

    public ModelBuilderDocument model(Model model) {
        this.model = model;
        return this;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Page getPage() {
        return page;
    }

    public ModelBuilderDocument page(Page page) {
        this.page = page;
        return this;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Section getSection() {
        return section;
    }

    public ModelBuilderDocument section(Section section) {
        this.section = section;
        return this;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Line getLine() {
        return line;
    }

    public ModelBuilderDocument line(Line line) {
        this.line = line;
        return this;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ModelBuilderDocument modelBuilderDocument = (ModelBuilderDocument) o;
        if (modelBuilderDocument.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, modelBuilderDocument.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ModelBuilderDocument{" +
            "id=" + id +
            ", reporter='" + reporter + "'" +
            ", auditor='" + auditor + "'" +
            '}';
    }
}
