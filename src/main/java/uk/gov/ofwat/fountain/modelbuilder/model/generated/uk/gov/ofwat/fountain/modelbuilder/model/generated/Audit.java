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
 *         &lt;element ref="{http://www.ofwat.gov.uk/model2}auditdetails"/&gt;
 *         &lt;element ref="{http://www.ofwat.gov.uk/model2}changes"/&gt;
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
@XmlRootElement(name = "audit")
public class Audit {

    @XmlElement(required = true)
    protected Auditdetails auditdetails;
    @XmlElement(required = true)
    protected Changes changes;

    /**
     * Gets the value of the auditdetails property.
     * 
     * @return
     *     possible object is
     *     {@link Auditdetails }
     *     
     */
    public Auditdetails getAuditdetails() {
        return auditdetails;
    }

    /**
     * Sets the value of the auditdetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link Auditdetails }
     *     
     */
    public void setAuditdetails(Auditdetails value) {
        this.auditdetails = value;
    }

    /**
     * Gets the value of the changes property.
     * 
     * @return
     *     possible object is
     *     {@link Changes }
     *     
     */
    public Changes getChanges() {
        return changes;
    }

    /**
     * Sets the value of the changes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Changes }
     *     
     */
    public void setChanges(Changes value) {
        this.changes = value;
    }

}