package uk.gov.ofwat.fountain.modelbuilder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CellRange.
 */
@Entity
@Table(name = "cell_range")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cellrange")
public class CellRange implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "start_year", nullable = false)
    private String startYear;

    @NotNull
    @Column(name = "end_year", nullable = false)
    private String endYear;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartYear() {
        return startYear;
    }

    public CellRange startYear(String startYear) {
        this.startYear = startYear;
        return this;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public CellRange endYear(String endYear) {
        this.endYear = endYear;
        return this;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CellRange cellRange = (CellRange) o;
        if (cellRange.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cellRange.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CellRange{" +
            "id=" + id +
            ", startYear='" + startYear + "'" +
            ", endYear='" + endYear + "'" +
            '}';
    }
}
