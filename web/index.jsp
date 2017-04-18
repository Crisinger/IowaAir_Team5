<%--
  Created by IntelliJ IDEA.
  User: crisi_000
  Date: 3/23/2017
  Time: 1:49 PM
  To change this template use File | Settings | File Templates.

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="General.FlightQuery"%>
<%
    String logPage, logSet, accountText, paymentPage, activePage, historyPage, flightQuery;

    if (session.getAttribute("userEmail") != null) {
        accountText = session.getAttribute("userEmail").toString();
        logPage = "logout.jsp";
        logSet = "Log Out";
        paymentPage = "payment.jsp";
        activePage = "activeFlights.jsp";
        historyPage = "flightHistory.jsp";
    } else {
        accountText = "My Account";
        logPage = "login.jsp";
        logSet = "Log In";
        paymentPage = "login.jsp";
        activePage = "login.jsp";
        historyPage = "login.jsp";
    }

    if(session.getAttribute("flightQuery") != null){
        flightQuery = session.getAttribute("flightQuery").toString();
    } else {
        flightQuery = "nothing to show here";
    }

%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Iowa Air</title>
    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/responsive.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.0.min.js"></script>

    <script src="js/General/FlightQuery.js"></script>
</head>

<body>
<header>
    <a href="index.jsp" id="logo">
        <h1>Iowa Air</h1>
        <h2>Travel Like A Hawkeye</h2>
    </a>
    <nav>
        <ul>
            <li>
                <div class="account-dropdown">
                    <a href="index.jsp">
                        <button class="account-dropbutton selected">Search</button>
                    </a>
                </div>
            </li>

            <li>
                <div class="account-dropdown">
                    <a href="contact.jsp">
                        <button class="account-dropbutton">Contact Us</button>
                    </a>
                </div>
            </li>

            <li>
                <div class="account-dropdown">
                    <a href="myAccount.jsp">
                        <button class="account-dropbutton"><%=accountText%>
                        </button>
                    </a>
                    <div class="account-dropdown-content">
                        <a href="<%=logPage%>"><%=logSet%>
                        </a>
                        <a href="<%=activePage%>">Active Flights</a>
                        <a href="<%=historyPage%>">Flight History</a>
                        <a href="<%=paymentPage%>">Payment Info</a>
                    </div>
                </div>
            </li>
        </ul>
    </nav>
</header>


<div id="viewwrapper">

    <section id="sidebar">
        <form id="flightQueryForm">
            Departure Location:
            <select id="flightQueryDepartState" name="departState" required>
            </select>
            <br>
            <select id="flightQueryDepartCity" name="departCity" required>
            </select>
            <br>
            Arrival Location:
            <select id="flightQueryArrivalState" name="arriveState" required>
            </select>
            <br>
            <select id="flightQueryArrivalCity" name="arriveCity" required>
            </select>
            Plane Model:
            <br>
            <select id="flightQueryPlaneModel" name="planeModel">
            </select>
            <br>
        </form>
        <button id="flightQueryButton">Search</button>
    </section>

    <section>
        <%=flightQuery%>
        <%session.setAttribute("flightQuery",""); %>
    </section>

</div>

<footer>

</footer>

</body>

</html>