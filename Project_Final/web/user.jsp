<%-- 
    Document   : user
    Created on : Jul 11, 2020, 4:05:59 PM
    Author     : nguye
--%>

<%@page import="java.util.Random"%>
<%@page import="java.util.List"%>
<%@page import="hiepnh.dto.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title>Document</title>
        
        <style>
            .products {
                list-style-type: none;
            }
            .all{
               margin:  50px;
       font-size: 20px;
            }
            .products li {
                display: block;
                float: left;
                margin: 50px;
                width: 225px; 
                text-align: center; 
            }
        </style>
    </head>
    <body>
        <section class="all">  
        <form action="MainController" method="POST">
            <input type="submit" value="Smart Advice" name="action"/>
        </form>
        <form action="ViewProductByCategory" method="POST">
            <strong>Search by Category</strong>
            <select onchange="this.form.submit();" name="txtCategory">
                <option>Select Category</option>
                <%
                    List<String> category = (List<String>) request.getAttribute("CATEGORY");
                    if (category != null) {
                        for (String categories : category) {

                %>
                <option value="<%= categories%>"><%= categories%></option>
                <%
                        }
                    }
                %>
            </select>
        </section>
            <ul class="products">
                <%
                    int count = 0;
                    List<ProductDTO> result = (List<ProductDTO>) request.getAttribute("INFO");
                    List<ProductDTO> resultByCategory = (List<ProductDTO>) request.getAttribute("RESULT");
                    Random rand = new Random();
                    if (result != null) {
                        for (int i = 0; i < 50; i++) {
                            int randomIndex = rand.nextInt(result.size());
                %>

                <li>
                    <a href="liên kết đến chi tiết sản phẩm">
                        <img src="<%= result.get(randomIndex).getImage()%>" style="width: 200px; height: 200px;">
                        <span><%= result.get(randomIndex).getName()%></span>
                    </a> </br>
                    <strong style="color: red;" ><%= result.get(randomIndex).getPrice()%></strong>
                </li>

                <%
                            result.remove(randomIndex);
                        }
                    }
                    if (resultByCategory != null) {
                        for (int i = 0; i < resultByCategory.size(); i++) {

                %>
                <li>
                    <a href="liên kết đến chi tiết sản phẩm">
                        <img src="<%= resultByCategory.get(i).getImage()%>" style="width: 200px; height: 200px;">
                        <span><%= resultByCategory.get(i).getName()%></span>
                    </a> </br>
                    <strong style="color: red;" ><%= resultByCategory.get(i).getPrice()%></strong>
                </li>
                <%
                        }
                    }
                %>
            </ul>
        </form>
    </body>
</html>