//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.03.01 at 10:28:45 AM GMT 
//


package uk.gov.ofwat.fountain.modelbuilder.model.generated;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element ref="{http://www.ofwat.gov.uk/model2}validation-rule-item" maxOccurs="3"/&gt;
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
    "validationRuleItem"
})
@XmlRootElement(name = "validation-rule-items")
public class ValidationRuleItems {

    @XmlElement(name = "validation-rule-item", required = true)
    protected List<ValidationRuleItem> validationRuleItem;

    /**
     * Gets the value of the validationRuleItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the validationRuleItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValidationRuleItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ValidationRuleItem }
     * 
     * 
     */
    public List<ValidationRuleItem> getValidationRuleItem() {
        if (validationRuleItem == null) {
            validationRuleItem = new ArrayList<ValidationRuleItem>();
        }
        return this.validationRuleItem;
    }

}
