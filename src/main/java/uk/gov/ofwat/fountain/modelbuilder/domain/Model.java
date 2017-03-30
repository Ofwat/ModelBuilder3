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
 * A Model.
 */
@Entity
@Table(name = "model")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "model")
public class Model implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private ModelDetails modelDetails;

    @OneToMany(mappedBy = "model")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ModelAudit> modelAudits = new HashSet<>();

    @OneToMany(mappedBy = "model")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Item> items = new HashSet<>();

    @OneToMany(mappedBy = "model")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Year> years = new HashSet<>();

    @OneToMany(mappedBy = "model")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Heading> headings = new HashSet<>();

    @OneToMany(mappedBy = "model")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ValidationRule> validationRules = new HashSet<>();

    @OneToMany(mappedBy = "model")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompanyPage> companyPages = new HashSet<>();

    @OneToMany(mappedBy = "model")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ModelBuilderDocument> documents = new HashSet<>();

    @OneToMany(mappedBy = "model")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Page> pages = new HashSet<>();

    @OneToMany(mappedBy = "model")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Transfer> transfers = new HashSet<>();

    @OneToMany(mappedBy = "model")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Macro> macros = new HashSet<>();

    @OneToMany(mappedBy = "model")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Text> texts = new HashSet<>();

    @OneToMany(mappedBy = "model")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Input> inputs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ModelDetails getModelDetails() {
        return modelDetails;
    }

    public Model modelDetails(ModelDetails modelDetails) {
        this.modelDetails = modelDetails;
        return this;
    }

    public void setModelDetails(ModelDetails modelDetails) {
        this.modelDetails = modelDetails;
    }

    public Set<ModelAudit> getModelAudits() {
        return modelAudits;
    }

    public Model modelAudits(Set<ModelAudit> modelAudits) {
        this.modelAudits = modelAudits;
        return this;
    }

    public Model addModelAudits(ModelAudit modelAudit) {
        this.modelAudits.add(modelAudit);
        modelAudit.setModel(this);
        return this;
    }

    public Model removeModelAudits(ModelAudit modelAudit) {
        this.modelAudits.remove(modelAudit);
        modelAudit.setModel(null);
        return this;
    }

    public void setModelAudits(Set<ModelAudit> modelAudits) {
        this.modelAudits = modelAudits;
    }

    public Set<Item> getItems() {
        return items;
    }

    public Model items(Set<Item> items) {
        this.items = items;
        return this;
    }

    public Model addItems(Item item) {
        this.items.add(item);
        item.setModel(this);
        return this;
    }

    public Model removeItems(Item item) {
        this.items.remove(item);
        item.setModel(null);
        return this;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Set<Year> getYears() {
        return years;
    }

    public Model years(Set<Year> years) {
        this.years = years;
        return this;
    }

    public Model addYears(Year year) {
        this.years.add(year);
        year.setModel(this);
        return this;
    }

    public Model removeYears(Year year) {
        this.years.remove(year);
        year.setModel(null);
        return this;
    }

    public void setYears(Set<Year> years) {
        this.years = years;
    }

    public Set<Heading> getHeadings() {
        return headings;
    }

    public Model headings(Set<Heading> headings) {
        this.headings = headings;
        return this;
    }

    public Model addHeadings(Heading heading) {
        this.headings.add(heading);
        heading.setModel(this);
        return this;
    }

    public Model removeHeadings(Heading heading) {
        this.headings.remove(heading);
        heading.setModel(null);
        return this;
    }

    public void setHeadings(Set<Heading> headings) {
        this.headings = headings;
    }

    public Set<ValidationRule> getValidationRules() {
        return validationRules;
    }

    public Model validationRules(Set<ValidationRule> validationRules) {
        this.validationRules = validationRules;
        return this;
    }

    public Model addValidationRules(ValidationRule validationRule) {
        this.validationRules.add(validationRule);
        validationRule.setModel(this);
        return this;
    }

    public Model removeValidationRules(ValidationRule validationRule) {
        this.validationRules.remove(validationRule);
        validationRule.setModel(null);
        return this;
    }

    public void setValidationRules(Set<ValidationRule> validationRules) {
        this.validationRules = validationRules;
    }

    public Set<CompanyPage> getCompanyPages() {
        return companyPages;
    }

    public Model companyPages(Set<CompanyPage> companyPages) {
        this.companyPages = companyPages;
        return this;
    }

    public Model addCompanyPages(CompanyPage companyPage) {
        this.companyPages.add(companyPage);
        companyPage.setModel(this);
        return this;
    }

    public Model removeCompanyPages(CompanyPage companyPage) {
        this.companyPages.remove(companyPage);
        companyPage.setModel(null);
        return this;
    }

    public void setCompanyPages(Set<CompanyPage> companyPages) {
        this.companyPages = companyPages;
    }

    public Set<ModelBuilderDocument> getDocuments() {
        return documents;
    }

    public Model documents(Set<ModelBuilderDocument> modelBuilderDocuments) {
        this.documents = modelBuilderDocuments;
        return this;
    }

    public Model addDocuments(ModelBuilderDocument modelBuilderDocument) {
        this.documents.add(modelBuilderDocument);
        modelBuilderDocument.setModel(this);
        return this;
    }

    public Model removeDocuments(ModelBuilderDocument modelBuilderDocument) {
        this.documents.remove(modelBuilderDocument);
        modelBuilderDocument.setModel(null);
        return this;
    }

    public void setDocuments(Set<ModelBuilderDocument> modelBuilderDocuments) {
        this.documents = modelBuilderDocuments;
    }

    public Set<Page> getPages() {
        return pages;
    }

    public Model pages(Set<Page> pages) {
        this.pages = pages;
        return this;
    }

    public Model addPages(Page page) {
        this.pages.add(page);
        page.setModel(this);
        return this;
    }

    public Model removePages(Page page) {
        this.pages.remove(page);
        page.setModel(null);
        return this;
    }

    public void setPages(Set<Page> pages) {
        this.pages = pages;
    }

    public Set<Transfer> getTransfers() {
        return transfers;
    }

    public Model transfers(Set<Transfer> transfers) {
        this.transfers = transfers;
        return this;
    }

    public Model addTransfers(Transfer transfer) {
        this.transfers.add(transfer);
        transfer.setModel(this);
        return this;
    }

    public Model removeTransfers(Transfer transfer) {
        this.transfers.remove(transfer);
        transfer.setModel(null);
        return this;
    }

    public void setTransfers(Set<Transfer> transfers) {
        this.transfers = transfers;
    }

    public Set<Macro> getMacros() {
        return macros;
    }

    public Model macros(Set<Macro> macros) {
        this.macros = macros;
        return this;
    }

    public Model addMacros(Macro macro) {
        this.macros.add(macro);
        macro.setModel(this);
        return this;
    }

    public Model removeMacros(Macro macro) {
        this.macros.remove(macro);
        macro.setModel(null);
        return this;
    }

    public void setMacros(Set<Macro> macros) {
        this.macros = macros;
    }

    public Set<Text> getTexts() {
        return texts;
    }

    public Model texts(Set<Text> texts) {
        this.texts = texts;
        return this;
    }

    public Model addTexts(Text text) {
        this.texts.add(text);
        text.setModel(this);
        return this;
    }

    public Model removeTexts(Text text) {
        this.texts.remove(text);
        text.setModel(null);
        return this;
    }

    public void setTexts(Set<Text> texts) {
        this.texts = texts;
    }

    public Set<Input> getInputs() {
        return inputs;
    }

    public Model inputs(Set<Input> inputs) {
        this.inputs = inputs;
        return this;
    }

    public Model addInput(Input input) {
        this.inputs.add(input);
        input.setModel(this);
        return this;
    }

    public Model removeInput(Input input) {
        this.inputs.remove(input);
        input.setModel(null);
        return this;
    }

    public void setInputs(Set<Input> inputs) {
        this.inputs = inputs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Model model = (Model) o;
        if (model.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, model.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Model{" +
            "id=" + id +
            '}';
    }
}
