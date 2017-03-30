package uk.gov.ofwat.fountain.modelbuilder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CompanyPage.
 */
@Entity
@Table(name = "company_page")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "companypage")
public class CompanyPage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "company_code", nullable = false)
    private String companyCode;

    @NotNull
    @Column(name = "page_code", nullable = false)
    private String pageCode;

    @ManyToOne
    private Model model;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public CompanyPage companyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getPageCode() {
        return pageCode;
    }

    public CompanyPage pageCode(String pageCode) {
        this.pageCode = pageCode;
        return this;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }

    public Model getModel() {
        return model;
    }

    public CompanyPage model(Model model) {
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
        CompanyPage companyPage = (CompanyPage) o;
        if (companyPage.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, companyPage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CompanyPage{" +
            "id=" + id +
            ", companyCode='" + companyCode + "'" +
            ", pageCode='" + pageCode + "'" +
            '}';
    }
}
