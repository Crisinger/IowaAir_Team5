<%--
  Created by IntelliJ IDEA.
  User: johnn
  Date: 3/26/2017
  Time: 5:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="AirFunctions.Admin.AdminFunctions" %>
<%@ page import="AirFunctions.FlightQuery" %>
<%@ page import="AirFunctions.Admin.AdminPlaneModels" %>
<%@ page import="AirFunctions.CityFunctions" %>

<%
    String accountText = "";
    if(session.getAttribute("role").toString().equals("admin")){
        accountText = session.getAttribute("userEmail").toString();
    } else {
        response.sendRedirect("logout.jsp");
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


    <%=FlightQuery.getDateAndTimeSrc()%>
    <%//=FlightQuery.addPickers("flightDeparture")%>
    <%//=FlightQuery.addPickers("flightArrival")%>
    <script src="js/adminFlights.js"></script>

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
                    <a href="adminLocations.jsp">
                        <button class="account-dropbutton">Locations</button>
                    </a>
                </div>
            </li>

            <li>
                <div class="account-dropdown">
                    <a href="adminPlanes.jsp">
                        <button class="account-dropbutton">Planes</button>
                    </a>
                </div>
            </li>

            <li>
                <div class="account-dropdown">
                    <a href="adminFlights.jsp">
                        <button class="account-dropbutton selected">Flights</button>
                    </a>
                </div>
            </li>

            <li>
                <div class="account-dropdown">
                    <a href="adminManagers.jsp">
                        <button class="account-dropbutton">Managers</button>
                    </a>
                </div>
            </li>

            <li>
                <div class="account-dropdown">
                    <a href="admin.jsp">
                        <button class="account-dropbutton"><%=accountText%>
                        </button>
                    </a>
                    <div class="account-dropdown-content">
                        <a href="logout.jsp">Log Out</a>
                    </div>
                </div>
            </li>
        </ul>
    </nav>
</header>

<div id="adminFlights">
    <h1>Flights</h1>
    <form action="AirFunctions.Admin.AdminFlights">
        <p class="admin_flight_type"><b>Departure</b></p>
        <p class="admin_flight_info">
            Date: <input type="text" name="flightDepartureDate" placeholder="Select Date" id="flightDeparturedatepicker" required>
            Time: <input type="text" name="flightDepartureTime" placeholder="Select Time" id="flightDeparturetimepicker" required>
            Location:
            <select id="flightDepartureLocationState" name="flightDepartureLocationState" required>
                <option disabled selected>---Select a state---</option>
                <%=CityFunctions.getActiveStatesList("alterDepartureCitySelect")%>
            </select>
            <select id="flightDepartureLocationCity" name="flightDepartureLocationCity" required>
                <option disabled selected>---Select a city---</option>
                <%=CityFunctions.getActiveCitiesList()%>
            </select>
        </p>
        <p class="admin_flight_type"><b>Arrival</b></p>
        <p class="admin_flight_info">
            Date: <input type="text" name="flightArrivalDate" placeholder="Select Date" id="flightArrivaldatepicker" disabled>
            Time: <input type="text" name="flightArrivalTime" placeholder="Select Time" id="flightArrivaltimepicker" disabled>
            Location:
            <select id="flightArrivalLocationState" name="flightArrivalLocationState">
                <option disabled selected>---Select a state---</option>
                <%=CityFunctions.getActiveStatesList("alterArrivalCitySelect")%>
            </select>
            <select id="flightArrivalLocationCity" name="flightArrivalLocationCity">
                <option disabled selected>---Select a city---</option>
                <%=CityFunctions.getActiveCitiesList()%>
            </select>
        </p>
        <p class="admin_flight_plane"><b>Plane</b></p>
        <p class = "admin_flight_plane_info">
            Type:
            <select id="flightPlaneModelSelect" name="flightPlaneModel" onchange="canModelMakeTheDistance()" >
                <option disabled selected>---Select a model---</option>
                <%=AdminPlaneModels.getPlaneModelList()%>
            </select>
        </p>
        <button type="submit" name="addFlightButton" class="admin_flight_info_button">Add Flight</button>
    </form>
    <p id="flightResponseDemo">patry</p>
</div>

<footer>

</footer>

</body>

</html>