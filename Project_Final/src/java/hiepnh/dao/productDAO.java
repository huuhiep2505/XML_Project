/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hiepnh.dao;

import hiepnh.connection.DBConnection;
import hiepnh.dto.*;
import hiepnh.jaxb.ProductDetails;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nguye
 */
public class productDAO implements Serializable {

    Connection conn;
    PreparedStatement preStm;
    ResultSet rs;

    public void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public boolean insertProducts(String name, String image, String linkDetails, float price, String category, float minArea, float maxArea, String powerConsumption, String inverter, String type) throws Exception {
        boolean result = false;
        String sql = "Insert into ProductDetails (name, image, linkDetails, price, category, minArea, maxArea, powerConsumption, inverter, type) Values(?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getMyConnection();
                PreparedStatement preStm = conn.prepareStatement(sql);) {
            preStm.setString(1, name);
            preStm.setString(2, image);
            preStm.setString(3, linkDetails);
            preStm.setFloat(4, price);
            preStm.setString(5, category);
            preStm.setFloat(6, minArea);
            preStm.setFloat(7, maxArea);
            preStm.setString(8, powerConsumption);
            preStm.setString(9, inverter);
            preStm.setString(10, type);
            result = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return result;
    }

    public boolean insertCategory(String name) throws Exception {
        boolean result = false;
        String sql = "Insert into Categories (categoryName) Values(?)";
        try (Connection conn = DBConnection.getMyConnection();
                PreparedStatement preStm = conn.prepareStatement(sql);) {
            preStm.setString(1, name);
            result = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return result;
    }
    public boolean insertPowerConsumption(String name) throws Exception {
        boolean result = false;
        String sql = "Insert into PowerConsumption (powerConsumption) Values(?)";
        try (Connection conn = DBConnection.getMyConnection();
                PreparedStatement preStm = conn.prepareStatement(sql);) {
            preStm.setString(1, name);
            result = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return result;
    }

    public List<String> getCategoryName() throws Exception {
        List<String> result = new ArrayList();
        ProductDetails dto = null;
        String categoryName = null;
        try {
            conn = DBConnection.getMyConnection();
            if (conn != null) {
                String sql = "Select categoryName From Categories";
                preStm = conn.prepareStatement(sql);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    categoryName = rs.getString("categoryName");
                    dto = new ProductDetails(categoryName);
                    String id = dto.getCategory();
                    result.add(id);

                }
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    public List<String> getLinkDetails() throws Exception {
        List<String> result = new ArrayList();
        ProductDetails dto = null;
        String linkDetails = null;
        try {
            conn = DBConnection.getMyConnection();
            if (conn != null) {
                String sql = "Select linkDetails From ProductDetails";
                preStm = conn.prepareStatement(sql);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    linkDetails = rs.getString("linkDetails");
                    result.add(linkDetails);

                }
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    public List<String> getPowerConsumption() throws Exception {
        List<String> result = new ArrayList();
        ProductDetails dto = null;
        String powerConsumption = null;
        try {
            conn = DBConnection.getMyConnection();
            if (conn != null) {
                String sql = "Select powerConsumption From PowerConsumption";
                preStm = conn.prepareStatement(sql);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    powerConsumption = rs.getString("powerConsumption");
                    result.add(powerConsumption);

                }
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<ProductDTO> viewProduct() {
        List<ProductDTO> list = new ArrayList<>();
        try {
            String sql = "Select name, image, linkDetails, price, category, minArea, maxArea, powerConsumption, inverter, type From ProductDetails";
            conn = DBConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String image = rs.getString("image");
                String link = rs.getString("linkDetails");
                float price = Float.parseFloat(rs.getString("price"));
                String category = rs.getString("category");
                float minArea = Float.parseFloat(rs.getString("minArea"));
                float maxArea = Float.parseFloat(rs.getString("maxArea"));
                String powerConsumption = rs.getString("powerConsumption");
                String inverter = rs.getString("inverter");
                String type = rs.getString("type");
                ProductDTO dto = new ProductDTO(name, image, link, category, powerConsumption, type, inverter, price, minArea, maxArea);
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            closeConnection();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    public List<ProductDTO> loadProductByCategory(String catetoryName) {
        List<ProductDTO> list = new ArrayList<>();
        try {
            String sql = "Select name, image, linkDetails, price, category, minArea, maxArea, powerConsumption, inverter, type From ProductDetails Where category = ?";
            conn = DBConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, catetoryName);
            rs = preStm.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String image = rs.getString("image");
                String link = rs.getString("linkDetails");
                float price = Float.parseFloat(rs.getString("price"));
                String category = rs.getString("category");
                float minArea = Float.parseFloat(rs.getString("minArea"));
                float maxArea = Float.parseFloat(rs.getString("maxArea"));
                String powerConsumption = rs.getString("powerConsumption");
                String inverter = rs.getString("inverter");
                String type = rs.getString("type");
                ProductDTO dto = new ProductDTO(name, image, link, category, powerConsumption, type, inverter, price, minArea, maxArea);
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            closeConnection();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    
    public List<ProductDTO> findByPrce(float priceSearch) throws SQLException{
       List<ProductDTO> list = new ArrayList<>();
        try {
            String sql="Select top 20 name, image, linkDetails, price, category, minArea, maxArea, powerConsumption, inverter, type From ProductDetails Where price > ? and price <= ? ORDER BY price desc";
            conn= DBConnection.getMyConnection();
            preStm=conn.prepareStatement(sql);
            preStm.setFloat(1, 1000000);
            preStm.setFloat(2, priceSearch);
            rs=preStm.executeQuery();
            while(rs.next()){
                String name = rs.getString("name");
                String image = rs.getString("image");
                String link = rs.getString("linkDetails");
                float price = Float.parseFloat(rs.getString("price"));
                String category = rs.getString("category");
                float minArea = Float.parseFloat(rs.getString("minArea"));
                float maxArea = Float.parseFloat(rs.getString("maxArea"));
                String powerConsumption = rs.getString("powerConsumption");
                String inverter = rs.getString("inverter");
                String type = rs.getString("type");
                ProductDTO dto = new ProductDTO(name, image, link, category, powerConsumption, type, inverter, price, minArea, maxArea);
                list.add(dto);
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            closeConnection();
        }
        return list;
    }
}
