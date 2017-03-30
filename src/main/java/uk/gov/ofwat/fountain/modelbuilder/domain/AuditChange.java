package uk.gov.ofwat.fountain.modelbuilder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AuditChange.
 */
@Entity
@Table(name = "audit_change")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "auditchange")
public class AuditChange implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "change_text")
    private String changeText;

    @ManyToOne
    private ModelAudit modelAudit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChangeText() {
        return changeText;
    }

    public AuditChange changeText(String changeText) {
        this.changeText = changeText;
        return this;
    }

    public void setChangeText(String changeText) {
        this.changeText = changeText;
    }

    public ModelAudit getModelAudit() {
        return modelAudit;
    }

    public AuditChange modelAudit(ModelAudit modelAudit) {
        this.modelAudit = modelAudit;
        return this;
    }

    public void setModelAudit(ModelAudit modelAudit) {
        this.modelAudit = modelAudit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuditChange auditChange = (AuditChange) o;
        if (auditChange.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, auditChange.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AuditChange{" +
            "id=" + id +
            ", changeText='" + changeText + "'" +
            '}';
    }
}
