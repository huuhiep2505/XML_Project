<%-- 
    Document   : admin
    Created on : Jul 11, 2020, 4:05:39 PM
    Author     : nguye
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello Admin!</h1>
        <form action="MainController" method="POST">
        <input type="submit" value="Manage User" name="action" />
        <input type="submit" value="Manage Product" name="action" />
        </form>
    </body>
</html>
