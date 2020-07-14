
package hiepnh.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProductDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProductDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="image" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="linkDetails" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="category" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="minArea" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="maxArea" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="powerConsumption" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="inverter" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductDetails", propOrder = {
    "id",
    "name",
    "image",
    "linkDetails",
    "price",
    "category",
    "minArea",
    "maxArea",
    "powerConsumption",
    "inverter",
    "type"
})
public class ProductDetails {

    @XmlElement(name = "Id")
    protected int id;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String image;
    @XmlElement(required = true)
    protected String linkDetails;
    protected float price;
    @XmlElement(required = true)
    protected String category;
    protected float minArea;
    protected float maxArea;
    @XmlElement(required = true)
    protected String powerConsumption;
    @XmlElement(required = true)
    protected String inverter;
    @XmlElement(required = true)
    protected String type;

    /**
     * Gets the value of the id property.
     * 
     */
    
    public ProductDetails() {
    }

    public ProductDetails(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the image property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the value of the image property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImage(String value) {
        this.image = value;
    }

    /**
     * Gets the value of the linkDetails property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkDetails() {
        return linkDetails;
    }

    /**
     * Sets the value of the linkDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkDetails(String value) {
        this.linkDetails = value;
    }

    /**
     * Gets the value of the price property.
     * 
     */
    public float getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     */
    public void setPrice(float value) {
        this.price = value;
    }

    /**
     * Gets the value of the category property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategory(String value) {
        this.category = value;
    }

    /**
     * Gets the value of the minArea property.
     * 
     */
    public float getMinArea() {
        return minArea;
    }

    /**
     * Sets the value of the minArea property.
     * 
     */
    public void setMinArea(float value) {
        this.minArea = value;
    }

    /**
     * Gets the value of the maxArea property.
     * 
     */
    public float getMaxArea() {
        return maxArea;
    }

    /**
     * Sets the value of the maxArea property.
     * 
     */
    public void setMaxArea(float value) {
        this.maxArea = value;
    }

    /**
     * Gets the value of the powerConsumption property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPowerConsumption() {
        return powerConsumption;
    }

    /**
     * Sets the value of the powerConsumption property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPowerConsumption(String value) {
        this.powerConsumption = value;
    }

    /**
     * Gets the value of the inverter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInverter() {
        return inverter;
    }

    /**
     * Sets the value of the inverter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInverter(String value) {
        this.inverter = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
