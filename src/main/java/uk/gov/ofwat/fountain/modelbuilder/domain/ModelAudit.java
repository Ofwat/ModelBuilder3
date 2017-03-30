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
 * A ModelAudit.
 */
@Entity
@Table(name = "model_audit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "modelaudit")
public class ModelAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private AuditDetails modelAuditDetail;

    @OneToMany(mappedBy = "modelAudit")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AuditChange> changes = new HashSet<>();

    @ManyToOne
    private Model model;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuditDetails getModelAuditDetail() {
        return modelAuditDetail;
    }

    public ModelAudit modelAuditDetail(AuditDetails auditDetails) {
        this.modelAuditDetail = auditDetails;
        return this;
    }

    public void setModelAuditDetail(AuditDetails auditDetails) {
        this.modelAuditDetail = auditDetails;
    }

    public Set<AuditChange> getChanges() {
        return changes;
    }

    public ModelAudit changes(Set<AuditChange> auditChanges) {
        this.changes = auditChanges;
        return this;
    }

    public ModelAudit addChanges(AuditChange auditChange) {
        this.changes.add(auditChange);
        auditChange.setModelAudit(this);
        return this;
    }

    public ModelAudit removeChanges(AuditChange auditChange) {
        this.changes.remove(auditChange);
        auditChange.setModelAudit(null);
        return this;
    }

    public void setChanges(Set<AuditChange> auditChanges) {
        this.changes = auditChanges;
    }

    public Model getModel() {
        return model;
    }

    public ModelAudit model(Model model) {
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
        ModelAudit modelAudit = (ModelAudit) o;
        if (modelAudit.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, modelAudit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ModelAudit{" +
            "id=" + id +
            '}';
    }
}
