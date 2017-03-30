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
 * A Line.
 */
@Entity
@Table(name = "line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "line")
public class Line implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private LineDetails lineDetail;

    @OneToOne
    @JoinColumn(unique = true)
    private CellRange cellRange;

    @OneToMany(mappedBy = "line")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cell> cells = new HashSet<>();

    @OneToMany(mappedBy = "line")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ModelBuilderDocument> documents = new HashSet<>();

    @ManyToOne
    private Section section;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LineDetails getLineDetail() {
        return lineDetail;
    }

    public Line lineDetail(LineDetails lineDetails) {
        this.lineDetail = lineDetails;
        return this;
    }

    public void setLineDetail(LineDetails lineDetails) {
        this.lineDetail = lineDetails;
    }

    public CellRange getCellRange() {
        return cellRange;
    }

    public Line cellRange(CellRange cellRange) {
        this.cellRange = cellRange;
        return this;
    }

    public void setCellRange(CellRange cellRange) {
        this.cellRange = cellRange;
    }

    public Set<Cell> getCells() {
        return cells;
    }

    public Line cells(Set<Cell> cells) {
        this.cells = cells;
        return this;
    }

    public Line addCells(Cell cell) {
        this.cells.add(cell);
        cell.setLine(this);
        return this;
    }

    public Line removeCells(Cell cell) {
        this.cells.remove(cell);
        cell.setLine(null);
        return this;
    }

    public void setCells(Set<Cell> cells) {
        this.cells = cells;
    }

    public Set<ModelBuilderDocument> getDocuments() {
        return documents;
    }

    public Line documents(Set<ModelBuilderDocument> modelBuilderDocuments) {
        this.documents = modelBuilderDocuments;
        return this;
    }

    public Line addDocuments(ModelBuilderDocument modelBuilderDocument) {
        this.documents.add(modelBuilderDocument);
        modelBuilderDocument.setLine(this);
        return this;
    }

    public Line removeDocuments(ModelBuilderDocument modelBuilderDocument) {
        this.documents.remove(modelBuilderDocument);
        modelBuilderDocument.setLine(null);
        return this;
    }

    public void setDocuments(Set<ModelBuilderDocument> modelBuilderDocuments) {
        this.documents = modelBuilderDocuments;
    }

    public Section getSection() {
        return section;
    }

    public Line section(Section section) {
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
        Line line = (Line) o;
        if (line.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, line.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Line{" +
            "id=" + id +
            '}';
    }
}
