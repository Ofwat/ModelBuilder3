//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.03.01 at 10:28:45 AM GMT 
//


package uk.gov.ofwat.fountain.modelbuilder.model.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="text" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="use-line-number" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="use-item-code" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="use-item-description" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="use-unit" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="use-year-code" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="use-confidence-grades" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="row" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="column" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="row-span" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="column-span" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="item-code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="year-code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="width" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cell-code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="group-description-code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "form-heading-cell")
public class FormHeadingCell {

    protected String text;
    @XmlElement(name = "use-line-number")
    protected Boolean useLineNumber;
    @XmlElement(name = "use-item-code")
    protected Boolean useItemCode;
    @XmlElement(name = "use-item-description")
    protected Boolean useItemDescription;
    @XmlElement(name = "use-unit")
    protected Boolean useUnit;
    @XmlElement(name = "use-year-code")
    protected Boolean useYearCode;
    @XmlElement(name = "use-confidence-grades")
    protected Boolean useConfidenceGrades;
    @XmlElement(required = true)
    protected String row;
    @XmlElement(required = true)
    protected String column;
    @XmlElement(name = "row-span")
    protected String rowSpan;
    @XmlElement(name = "column-span")
    protected String columnSpan;
    @XmlElement(name = "item-code")
    protected String itemCode;
    @XmlElement(name = "year-code")
    protected String yearCode;
    protected String width;
    @XmlElement(name = "cell-code")
    protected String cellCode;
    @XmlElement(name = "group-description-code")
    protected String groupDescriptionCode;

    /**
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText(String value) {
        this.text = value;
    }

    /**
     * Gets the value of the useLineNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUseLineNumber() {
        return useLineNumber;
    }

    /**
     * Sets the value of the useLineNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseLineNumber(Boolean value) {
        this.useLineNumber = value;
    }

    /**
     * Gets the value of the useItemCode property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUseItemCode() {
        return useItemCode;
    }

    /**
     * Sets the value of the useItemCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseItemCode(Boolean value) {
        this.useItemCode = value;
    }

    /**
     * Gets the value of the useItemDescription property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUseItemDescription() {
        return useItemDescription;
    }

    /**
     * Sets the value of the useItemDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseItemDescription(Boolean value) {
        this.useItemDescription = value;
    }

    /**
     * Gets the value of the useUnit property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUseUnit() {
        return useUnit;
    }

    /**
     * Sets the value of the useUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseUnit(Boolean value) {
        this.useUnit = value;
    }

    /**
     * Gets the value of the useYearCode property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUseYearCode() {
        return useYearCode;
    }

    /**
     * Sets the value of the useYearCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseYearCode(Boolean value) {
        this.useYearCode = value;
    }

    /**
     * Gets the value of the useConfidenceGrades property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUseConfidenceGrades() {
        return useConfidenceGrades;
    }

    /**
     * Sets the value of the useConfidenceGrades property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseConfidenceGrades(Boolean value) {
        this.useConfidenceGrades = value;
    }

    /**
     * Gets the value of the row property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRow() {
        return row;
    }

    /**
     * Sets the value of the row property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRow(String value) {
        this.row = value;
    }

    /**
     * Gets the value of the column property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumn() {
        return column;
    }

    /**
     * Sets the value of the column property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumn(String value) {
        this.column = value;
    }

    /**
     * Gets the value of the rowSpan property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRowSpan() {
        return rowSpan;
    }

    /**
     * Sets the value of the rowSpan property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRowSpan(String value) {
        this.rowSpan = value;
    }

    /**
     * Gets the value of the columnSpan property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumnSpan() {
        return columnSpan;
    }

    /**
     * Sets the value of the columnSpan property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumnSpan(String value) {
        this.columnSpan = value;
    }

    /**
     * Gets the value of the itemCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * Sets the value of the itemCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemCode(String value) {
        this.itemCode = value;
    }

    /**
     * Gets the value of the yearCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYearCode() {
        return yearCode;
    }

    /**
     * Sets the value of the yearCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYearCode(String value) {
        this.yearCode = value;
    }

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWidth(String value) {
        this.width = value;
    }

    /**
     * Gets the value of the cellCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCellCode() {
        return cellCode;
    }

    /**
     * Sets the value of the cellCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCellCode(String value) {
        this.cellCode = value;
    }

    /**
     * Gets the value of the groupDescriptionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupDescriptionCode() {
        return groupDescriptionCode;
    }

    /**
     * Sets the value of the groupDescriptionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupDescriptionCode(String value) {
        this.groupDescriptionCode = value;
    }

}
