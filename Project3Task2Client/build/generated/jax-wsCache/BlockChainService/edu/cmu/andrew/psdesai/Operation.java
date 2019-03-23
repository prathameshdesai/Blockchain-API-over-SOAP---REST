
package edu.cmu.andrew.psdesai;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for operation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="operation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="inputstring" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "operation", propOrder = {
    "inputstring"
})
public class Operation {

    protected String inputstring;

    /**
     * Gets the value of the inputstring property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInputstring() {
        return inputstring;
    }

    /**
     * Sets the value of the inputstring property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInputstring(String value) {
        this.inputstring = value;
    }

}
