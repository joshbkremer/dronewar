//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.28 at 07:13:47 PM EST 
//


package org.botgame.db.jaxb.fightlog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for dfdDamage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dfdDamage">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>int">
 *       &lt;attribute name="shieldAbs" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="shieldRemaining" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="hpRemaining" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dfdDamage", propOrder = {
    "value"
})
public class DfdDamage {

    @XmlValue
    protected int value;
    @XmlAttribute
    protected Integer shieldAbs;
    @XmlAttribute
    protected Integer shieldRemaining;
    @XmlAttribute
    protected Integer hpRemaining;

    /**
     * Gets the value of the value property.
     * 
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Gets the value of the shieldAbs property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getShieldAbs() {
        return shieldAbs;
    }

    /**
     * Sets the value of the shieldAbs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setShieldAbs(Integer value) {
        this.shieldAbs = value;
    }

    /**
     * Gets the value of the shieldRemaining property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getShieldRemaining() {
        return shieldRemaining;
    }

    /**
     * Sets the value of the shieldRemaining property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setShieldRemaining(Integer value) {
        this.shieldRemaining = value;
    }

    /**
     * Gets the value of the hpRemaining property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHpRemaining() {
        return hpRemaining;
    }

    /**
     * Sets the value of the hpRemaining property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHpRemaining(Integer value) {
        this.hpRemaining = value;
    }

}
