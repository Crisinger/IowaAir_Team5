<%--
  Created by IntelliJ IDEA.
  User: ReedS
  Date: 3/25/2017
  Time: 8:37 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Login</title>
</head>
<style>
    body {
        margin:0;
        background: url("airplane.jpg");
        background-size: 100% 170%;
        background-repeat: no-repeat;
    }
    ul {
        list-style-type: none;
        margin: 0;
        padding: 0;
        overflow: hidden;
        background-color: #333;
        position: fixed;
        top: 0;
        width: 100%;
    }
    li {
        float: left;
    }
    li a {
        display: block;
        color: White;
        text-align: center;
        padding: 14px 16px;
        text-decoration: none;
    }
    li a:hover:not(.active) {
        background-color: #111;
    }
    .active {
        background-color: #ffe11e;
    }
</style>
</head>
<body>

<ul>
    <li><a href="home.jsp">Home</a></li>
    <li><a href="#news">News</a></li>
    <li><a href="#contact">Contact</a></li>
</ul>

<div
        style="padding:20px;
margin-top:30px;
height:20px;
width:0%;">
</div>


<body style = "text-align:center;">

<form action="CreateAccount">
    <br>
    <br>
    <br>
    <br>
    User email:
    <br>
    <input type="text" name="userEmail" maxlength="40" align="middle" placeholder="Email" required>
    <br>
    User password:
    <br>
    <input type="password" name="userPassword" maxlength="10" align="middle" placeholder="Password" required>
    <br>
    Confirm user password:
    <br>
    <input type="password" name="userPassword" maxlength="10" align="middle">
    <br>
    <br>
    <input type="button" value="Create Account">
</form>

</body>

</body>
</html>
