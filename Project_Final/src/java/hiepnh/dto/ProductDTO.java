/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hiepnh.dto;

import java.io.Serializable;

/**
 *
 * @author nguye
 */
public class ProductDTO implements Serializable{
    String name, image, link,  category, powerConsumption, type, inverter;
    float price, minArea, maxArea;

    public ProductDTO() {
    }

    public ProductDTO(String name, String image, String link, String category, String powerConsumption, String type, String inverter, float price, float minArea, float maxArea) {
        this.name = name;
        this.image = image;
        this.link = link;
        this.category = category;
        this.powerConsumption = powerConsumption;
        this.type = type;
        this.inverter = inverter;
        this.price = price;
        this.minArea = minArea;
        this.maxArea = maxArea;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(String powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInverter() {
        return inverter;
    }

    public void setInverter(String inverter) {
        this.inverter = inverter;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getMinArea() {
        return minArea;
    }

    public void setMinArea(float minArea) {
        this.minArea = minArea;
    }

    public float getMaxArea() {
        return maxArea;
    }

    public void setMaxArea(float maxArea) {
        this.maxArea = maxArea;
    }
    
    
    
}
