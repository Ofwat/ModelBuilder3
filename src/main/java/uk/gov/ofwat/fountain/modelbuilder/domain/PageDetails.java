package uk.gov.ofwat.fountain.modelbuilder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PageDetails.
 */
@Entity
@Table(name = "page_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pagedetails")
public class PageDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "page_text")
    private String pageText;

    @Column(name = "company_type")
    private String companyType;

    @Column(name = "heading")
    private String heading;

    @Column(name = "commercial_in_confidence")
    private Boolean commercialInConfidence;

    @Column(name = "hidden")
    private Boolean hidden;

    @Column(name = "text_code")
    private String textCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public PageDetails code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public PageDetails description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPageText() {
        return pageText;
    }

    public PageDetails pageText(String pageText) {
        this.pageText = pageText;
        return this;
    }

    public void setPageText(String pageText) {
        this.pageText = pageText;
    }

    public String getCompanyType() {
        return companyType;
    }

    public PageDetails companyType(String companyType) {
        this.companyType = companyType;
        return this;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getHeading() {
        return heading;
    }

    public PageDetails heading(String heading) {
        this.heading = heading;
        return this;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public Boolean isCommercialInConfidence() {
        return commercialInConfidence;
    }

    public PageDetails commercialInConfidence(Boolean commercialInConfidence) {
        this.commercialInConfidence = commercialInConfidence;
        return this;
    }

    public void setCommercialInConfidence(Boolean commercialInConfidence) {
        this.commercialInConfidence = commercialInConfidence;
    }

    public Boolean isHidden() {
        return hidden;
    }

    public PageDetails hidden(Boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public String getTextCode() {
        return textCode;
    }

    public PageDetails textCode(String textCode) {
        this.textCode = textCode;
        return this;
    }

    public void setTextCode(String textCode) {
        this.textCode = textCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PageDetails pageDetails = (PageDetails) o;
        if (pageDetails.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pageDetails.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PageDetails{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", description='" + description + "'" +
            ", pageText='" + pageText + "'" +
            ", companyType='" + companyType + "'" +
            ", heading='" + heading + "'" +
            ", commercialInConfidence='" + commercialInConfidence + "'" +
            ", hidden='" + hidden + "'" +
            ", textCode='" + textCode + "'" +
            '}';
    }
}
