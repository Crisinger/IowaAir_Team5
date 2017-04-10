<%--
  Created by IntelliJ IDEA.
  User: johnn
  Date: 3/26/2017
  Time: 5:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="AirFunctions.Admin.AdminPlaneModels" %>
<%@ page import="AirFunctions.Admin.AdminPlanes" %>

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

    <script src="js/planeModelModal.js" ></script>
    <script src="js/adminPlanes.js" ></script>


    <style>

        /* The Modal (background) */
        .planeModelModal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            padding-top: 100px; /* Location of the box */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgb(0,0,0); /* Fallback color */
            background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
        }
        /* Modal Content */
        .planeModelModalContent {
            background-color: #fefefe;
            margin: auto;
            padding: 20px;
            border: 1px solid #888;
            width: 50%;
        }

        /* The Close Button */
        .planeModelModalClose {
            color: #aaaaaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .planeModelModalClose:hover,
        .planeModelModalClose:focus {
            color: #000;
            text-decoration: none;
            cursor: pointer;
        }
    </style>
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
                        <button class="account-dropbutton selected">Planes</button>
                    </a>
                </div>
            </li>

            <li>
                <div class="account-dropdown">
                    <a href="adminFlights.jsp">
                        <button class="account-dropbutton">Flights</button>
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

<div id="adminPlanes">
    <h1>New Plane Model</h1>
    <form action="AirFunctions.Admin.AdminPlaneModels">
        <ul class="modelForm">
            <li><b>Plane Model: </b> <input type="text" name="planeModel" placeholder="Type" maxlength="40" required></li>
            <li><b>Carrying Capacity (persons): </b><input type="number" name="modelCapacity" placeholder="Capacity" min="1" max="999" maxlength = "3" required></li>
            <li><b>Fuel Capacity (tonnes): </b><input type="number" name="modelFuel" placeholder="tonnes" min="1" max="300" maxlength = "6" required></li>
            <li><b>Fuel Burn Rate (kg/km): </b><input type="number" name="modelBurn" placeholder="kg/km" min="1" max="10" maxlength = "4" required></li>
            <li><b>Average Velocity (km/hr): </b><input type="number" name="modelVelocity" placeholder="km/hr" min="1" max="2000" maxlength = "4" required></li>
            <li>
                <div class="classesCheckboxLabel">
                    <b>Available Classes</b>
                    <ul class="classesCheckbox" >
                        <li><input type="checkbox" name="hasEconomyClass" value="true" title="Economy">Economy</li>
                        <li><input type="checkbox" name="hasBusinessClass" value="true" title="Business">Business</li>
                        <li><input type="checkbox" name="hasFirstClass" value="true" title="First">First</li>
                    </ul>
                </div>
            </li>
        </ul>
        <button type="submit" name="addPlaneModelButton">Add Model</button>
    </form>
    <h1>New Plane</h1>
    <form action="AirFunctions.Admin.AdminPlanes">
        <ul class="planeForm">
            <li><b>Plane Type: </b><%=AdminPlaneModels.getPlaneModelList()%></li>
            <li><b>Capacity: </b><input type="number" id="planeCapacity" placeholder="123" min="1" max="999" maxlength="3" step="1" onchange="checkCapacity()" required></li>
            <li><b>Economy Seats: </b><input type="number" id="planeEconomySeats" placeholder="123" min="0" max="999" maxlength="3" step="1" onchange="checkCapacity()" required></li>
            <li><b>Business Seats: </b><input type="number" id="planeBusinessSeats" placeholder="123" min="0" max="999" maxlength="3" step="1" onchange="checkCapacity()" required></li>
            <li><b>First Seats: </b><input type="number" id="planeFirstSeats" placeholder="123" min="0" max="999" maxlength="3" step="1" onchange="checkCapacity()" required></li>
            <li><b>Base Price: </b><input type="number" id="planeBasePrice" placeholder="123" min="0" max="9999" maxlength="5" step="1.00" required></li>
            <li><b>Economy Price Multiplier: </b><input type="number" id="planeEconomyMultiple" placeholder="1.23" min="1" max="10" maxlength="4" step="0.1" required></li>
            <li><b>Business Price Multiplier: </b><input type="number" id="planeBusinessMultiple" placeholder="1.23" min="1" max="10" maxlength="4" step="0.1" required></li>
            <li><b>First Price Multiplier: </b><input type="number" id="planeFirstMultiple" placeholder="1.23" min="1" max="10" maxlength="4" step="0.1" required></li>
        </ul>
        <button type="submit" id="addPlaneButton">Add Plane</button>
    </form>

    <%=AdminPlaneModels.getPlaneModels()%>

</div>

<footer>

</footer>

</body>

</html>