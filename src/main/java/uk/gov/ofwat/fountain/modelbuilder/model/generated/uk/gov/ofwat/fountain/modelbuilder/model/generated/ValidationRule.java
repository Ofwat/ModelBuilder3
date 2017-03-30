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
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.ofwat.gov.uk/model2}validation-rule-details"/&gt;
 *         &lt;element ref="{http://www.ofwat.gov.uk/model2}validation-rule-items"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "validationRuleDetails",
    "validationRuleItems"
})
@XmlRootElement(name = "validation-rule")
public class ValidationRule {

    @XmlElement(name = "validation-rule-details", required = true)
    protected ValidationRuleDetails validationRuleDetails;
    @XmlElement(name = "validation-rule-items", required = true)
    protected ValidationRuleItems validationRuleItems;

    /**
     * Gets the value of the validationRuleDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ValidationRuleDetails }
     *     
     */
    public ValidationRuleDetails getValidationRuleDetails() {
        return validationRuleDetails;
    }

    /**
     * Sets the value of the validationRuleDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidationRuleDetails }
     *     
     */
    public void setValidationRuleDetails(ValidationRuleDetails value) {
        this.validationRuleDetails = value;
    }

    /**
     * Gets the value of the validationRuleItems property.
     * 
     * @return
     *     possible object is
     *     {@link ValidationRuleItems }
     *     
     */
    public ValidationRuleItems getValidationRuleItems() {
        return validationRuleItems;
    }

    /**
     * Sets the value of the validationRuleItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidationRuleItems }
     *     
     */
    public void setValidationRuleItems(ValidationRuleItems value) {
        this.validationRuleItems = value;
    }

}
