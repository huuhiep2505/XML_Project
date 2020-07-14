/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hiepnh.dao;

import hiepnh.connection.DBConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author nguye
 */
public class AccountDAO implements Serializable{
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
    
    public String checkLogin(String username, String password) throws Exception{
        String role = "failed";
        try {
            String sql = "Select role From Accounts Where Username = ? and Password = ? ";
            conn=DBConnection.getMyConnection();
            preStm=conn.prepareStatement(sql);
            preStm.setString(1, username);
            preStm.setString(2, password);
            rs=preStm.executeQuery();
            if(rs.next()){
                role=rs.getString("role").trim();
            }
        } catch (Exception e) {
        }finally{
            closeConnection();
        }
        return role;
    }
}
