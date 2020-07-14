<%-- 
    Document   : advice
    Created on : Jul 13, 2020, 7:06:23 PM
    Author     : nguye
--%>

<%@page import="hiepnh.dto.ProductDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ADVICE</title>
        <style>
            .products {
   list-style-type: none;
}
            .all {
   margin-left: 150px;
       margin-top: 75px;
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
            <h1 style="color: blue">Hello, Can I Help You ? </h1>
        <a>How much are you willing to spend to buy an air conditioner ? Give me a price</a>
        <form action="MainController" method="POST">
            Price : <input type="text" name="txtPrice">
            <input type="submit" name="action" value="Submit">            
        </form>
        </section>
                                <ul class="products">
                    <%
                        int count = 0;
                        List<ProductDTO> result = (List<ProductDTO>) request.getAttribute("PRICE");
                        if (result != null) {
                            for (int i = 0; i < 3; i++) {
                    %>                    
   <li>
      <a href="liên kết đến chi tiết sản phẩm">
         <img src="<%= result.get(i).getImage()%>" style="width: 200px; height: 200px;">
         <span><%= result.get(i).getName()%></span>
      </a> </br>
      <strong style="color: red;" ><%= result.get(i).getPrice()%></strong>
   </li>

                    <%
                            }
                        }
                    %>
</ul>
    </body>
</html>
