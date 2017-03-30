package uk.gov.ofwat.fountain.modelbuilder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Cell.
 */
@Entity
@Table(name = "cell")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cell")
public class Cell implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "year", nullable = false)
    private String year;

    @Column(name = "equation")
    private String equation;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "cg_type")
    private String cgType;

    @ManyToOne
    private Line line;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Cell code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getYear() {
        return year;
    }

    public Cell year(String year) {
        this.year = year;
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEquation() {
        return equation;
    }

    public Cell equation(String equation) {
        this.equation = equation;
        return this;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public String getType() {
        return type;
    }

    public Cell type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCgType() {
        return cgType;
    }

    public Cell cgType(String cgType) {
        this.cgType = cgType;
        return this;
    }

    public void setCgType(String cgType) {
        this.cgType = cgType;
    }

    public Line getLine() {
        return line;
    }

    public Cell line(Line line) {
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
        Cell cell = (Cell) o;
        if (cell.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cell.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cell{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", year='" + year + "'" +
            ", equation='" + equation + "'" +
            ", type='" + type + "'" +
            ", cgType='" + cgType + "'" +
            '}';
    }
}
