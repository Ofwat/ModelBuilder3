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
 * A Form.
 */
@Entity
@Table(name = "form")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "form")
public class Form implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private FormDetails formDetail;

    @OneToMany(mappedBy = "form")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FormCell> formCells = new HashSet<>();

    @OneToMany(mappedBy = "formTop")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FormHeadingCell> formHeadingsTops = new HashSet<>();

    @OneToMany(mappedBy = "formLeft")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FormHeadingCell> formHeadingsLefts = new HashSet<>();

    @ManyToOne
    private Section section;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FormDetails getFormDetail() {
        return formDetail;
    }

    public Form formDetail(FormDetails formDetails) {
        this.formDetail = formDetails;
        return this;
    }

    public void setFormDetail(FormDetails formDetails) {
        this.formDetail = formDetails;
    }

    public Set<FormCell> getFormCells() {
        return formCells;
    }

    public Form formCells(Set<FormCell> formCells) {
        this.formCells = formCells;
        return this;
    }

    public Form addFormCells(FormCell formCell) {
        this.formCells.add(formCell);
        formCell.setForm(this);
        return this;
    }

    public Form removeFormCells(FormCell formCell) {
        this.formCells.remove(formCell);
        formCell.setForm(null);
        return this;
    }

    public void setFormCells(Set<FormCell> formCells) {
        this.formCells = formCells;
    }

    public Set<FormHeadingCell> getFormHeadingsTops() {
        return formHeadingsTops;
    }

    public Form formHeadingsTops(Set<FormHeadingCell> formHeadingCells) {
        this.formHeadingsTops = formHeadingCells;
        return this;
    }

    public Form addFormHeadingsTop(FormHeadingCell formHeadingCell) {
        this.formHeadingsTops.add(formHeadingCell);
        formHeadingCell.setFormTop(this);
        return this;
    }

    public Form removeFormHeadingsTop(FormHeadingCell formHeadingCell) {
        this.formHeadingsTops.remove(formHeadingCell);
        formHeadingCell.setFormTop(null);
        return this;
    }

    public void setFormHeadingsTops(Set<FormHeadingCell> formHeadingCells) {
        this.formHeadingsTops = formHeadingCells;
    }

    public Set<FormHeadingCell> getFormHeadingsLefts() {
        return formHeadingsLefts;
    }

    public Form formHeadingsLefts(Set<FormHeadingCell> formHeadingCells) {
        this.formHeadingsLefts = formHeadingCells;
        return this;
    }

    public Form addFormHeadingsLeft(FormHeadingCell formHeadingCell) {
        this.formHeadingsLefts.add(formHeadingCell);
        formHeadingCell.setFormLeft(this);
        return this;
    }

    public Form removeFormHeadingsLeft(FormHeadingCell formHeadingCell) {
        this.formHeadingsLefts.remove(formHeadingCell);
        formHeadingCell.setFormLeft(null);
        return this;
    }

    public void setFormHeadingsLefts(Set<FormHeadingCell> formHeadingCells) {
        this.formHeadingsLefts = formHeadingCells;
    }

    public Section getSection() {
        return section;
    }

    public Form section(Section section) {
        this.section = section;
        return this;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Form form = (Form) o;
        if (form.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, form.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Form{" +
            "id=" + id +
            '}';
    }
}
