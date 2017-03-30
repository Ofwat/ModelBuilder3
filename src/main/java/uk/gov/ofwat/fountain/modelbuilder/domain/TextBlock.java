package uk.gov.ofwat.fountain.modelbuilder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TextBlock.
 */
@Entity
@Table(name = "text_block")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "textblock")
public class TextBlock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "version_number", nullable = false)
    private String versionNumber;

    @NotNull
    @Column(name = "text_format_code", nullable = false)
    private String textFormatCode;

    @NotNull
    @Column(name = "text_type_code", nullable = false)
    private String textTypeCode;

    @Column(name = "retired")
    private Boolean retired;

    @NotNull
    @Column(name = "data", nullable = false)
    private String data;

    @ManyToOne
    private Text text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public TextBlock description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public TextBlock versionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
        return this;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getTextFormatCode() {
        return textFormatCode;
    }

    public TextBlock textFormatCode(String textFormatCode) {
        this.textFormatCode = textFormatCode;
        return this;
    }

    public void setTextFormatCode(String textFormatCode) {
        this.textFormatCode = textFormatCode;
    }

    public String getTextTypeCode() {
        return textTypeCode;
    }

    public TextBlock textTypeCode(String textTypeCode) {
        this.textTypeCode = textTypeCode;
        return this;
    }

    public void setTextTypeCode(String textTypeCode) {
        this.textTypeCode = textTypeCode;
    }

    public Boolean isRetired() {
        return retired;
    }

    public TextBlock retired(Boolean retired) {
        this.retired = retired;
        return this;
    }

    public void setRetired(Boolean retired) {
        this.retired = retired;
    }

    public String getData() {
        return data;
    }

    public TextBlock data(String data) {
        this.data = data;
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Text getText() {
        return text;
    }

    public TextBlock text(Text text) {
        this.text = text;
        return this;
    }

    public void setText(Text text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TextBlock textBlock = (TextBlock) o;
        if (textBlock.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, textBlock.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TextBlock{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", versionNumber='" + versionNumber + "'" +
            ", textFormatCode='" + textFormatCode + "'" +
            ", textTypeCode='" + textTypeCode + "'" +
            ", retired='" + retired + "'" +
            ", data='" + data + "'" +
            '}';
    }
}
