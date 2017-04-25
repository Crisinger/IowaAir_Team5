<%--
  Created by IntelliJ IDEA.
  User: johnn
  Date: 3/26/2017
  Time: 5:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String logPage, logSet, accountText, paymentPage, activePage, historyPage;

    if(session.getAttribute("userEmail") != null){
        accountText = session.getAttribute("userEmail").toString();
        logPage = "logout.jsp";
        logSet = "Log Out";
        paymentPage = "payment.jsp";
        activePage = "activeFlights.jsp";
        historyPage = "flightHistory.jsp";
    }else{
        accountText = "My Account";
        logPage = "login.jsp";
        logSet = "Log In";
        paymentPage = "login.jsp";
        activePage = "login.jsp";
        historyPage = "login.jsp";
    }

%>

<html>
<head>
    <title>Managers</title>
</head>
<body>
<form action="Manager">
    First name:<br>
    <input type="text" name="firstname" value="" autofocus required><br>
    Last name:<br>
    <input type="text" name="lastname" value="" required><br><br>
    Flight ID Number:<br>
    <input type="text" name="flightID" value="" required><br>
    Number of Bags (between 0 and 4):<br>
    <input type="number" name="baggage" min="0" max="4" required><br><br>
    <input type="submit" value="Submit">
    <input type="reset">
</form>
</body>
</html>
