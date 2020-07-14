/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hiepnh.controller;

import hiepnh.dao.productDAO;
import hiepnh.dto.ProductDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nguye
 */
@WebServlet(name = "SearchController", urlPatterns = {"/SearchController"})
public class SearchController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            float priceSearch = Float.parseFloat(request.getParameter("txtPrice"));
            productDAO dao=new productDAO();
            ArrayList<String> listProduct = new ArrayList<>();
            TreeMap<Float, ProductDTO> tree = new TreeMap<>();
            List<ProductDTO> result=dao.findByPrce(priceSearch);
            List<ProductDTO> finalResult=new ArrayList<>();
            for(int i = 0; i < result.size() ; i ++){
                float total = 0;
                float mark = 0;
                float markFinal = 0;
                if(result.get(i).getInverter().equals("yes")){
                    mark =1;
                    total = total + mark;
                }else{
                    mark = 0;
                    total = total + mark;
                }
                if(result.get(i).getPowerConsumption().contains("7000") || result.get(i).getPowerConsumption().contains("7.000") ){
                    mark = 0;
                    total = total + mark ;
                }
                else if(result.get(i).getPowerConsumption().contains("9000") || result.get(i).getPowerConsumption().contains("9.000") ){
                    mark = 1;
                    total = total + mark ;
                }
                else if(result.get(i).getPowerConsumption().contains("10000") || result.get(i).getPowerConsumption().contains("10.000") ){
                    mark = 2;
                    total = total + mark ;
                }
                else if(result.get(i).getPowerConsumption().contains("12000") || result.get(i).getPowerConsumption().contains("12.000") ){
                    mark = 3;
                    total = total + mark ;
                }
                else if(result.get(i).getPowerConsumption().contains("13000") || result.get(i).getPowerConsumption().contains("13.000") ){
                    mark = 4;
                    total = total + mark ;
                }
                else if(result.get(i).getPowerConsumption().contains("18000") || result.get(i).getPowerConsumption().contains("18.000") ){
                    mark = 5;
                    total = total + mark ;
                }
                else if(result.get(i).getPowerConsumption().contains("21000") || result.get(i).getPowerConsumption().contains("21.000") ){
                    mark = 6;
                    total = total + mark ;
                }
                else if(result.get(i).getPowerConsumption().contains("22000") || result.get(i).getPowerConsumption().contains("22.000") ){
                    mark = 7;
                    total = total + mark ;
                }
                else if(result.get(i).getPowerConsumption().contains("24000") || result.get(i).getPowerConsumption().contains("24.000") ){
                    mark = 8;
                    total = total + mark ;
                }
                else if(result.get(i).getPowerConsumption().contains("28000") || result.get(i).getPowerConsumption().contains("28.000") ){
                    mark = 9;
                    total = total + mark ;
                }
                else if(result.get(i).getPowerConsumption().contains("125000") ){
                    mark = 10;
                    total = total + mark ;
                }
                if(result.get(i).getType().contains("2")){
                    mark = 1;
                    total = total + mark;
                }else{
                    mark = 0;
                    total = total + mark;
                }
                markFinal = total/result.get(i).getPrice();
                tree.put(markFinal, result.get(i));
            }
            List<ProductDTO> listtest = new ArrayList<>();
         
            Set<Float> set = tree.descendingKeySet();
            for(Float key : set){
                System.out.println(key + " " + tree.get(key));
                listtest.add(tree.get(key));
            }
          
//            Set<Float> set = map.keySet();
//            for(int i = 0 ; i < set.size() ; i ++){
//                
//            }
            request.setAttribute("PRICE", listtest);
            
            
            
            
        } catch (Exception e) {
        e.printStackTrace();
        }finally{
            request.getRequestDispatcher("advice.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
