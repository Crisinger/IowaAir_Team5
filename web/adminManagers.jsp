<%--
  Created by IntelliJ IDEA.
  User: johnn
  Date: 3/26/2017
  Time: 5:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Admin.Managerial.AddManager" %>
<%@ page import="Admin.Managerial.RemoveManager" %>
<%@ page import="Admin.Managerial.ManagerList" %>
<%@ page import="Admin.Managerial.UpdateManager" %>




<!DOCTYPE html>
<html lang="en">
<%
    String accountText = "";
    if(session.getAttribute("role").toString().equals("ADMIN")){
        accountText = session.getAttribute("userEmail").toString();
    } else {
        response.sendRedirect("logout.jsp");
    }

%>
<head>
    <meta charset="UTF-8">
    <title>Iowa Air</title>
    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/main.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.0.min.js"></script>
    <script src="js/Admin/Managerial.js" ></script>

    <style>

        /* The Modal (background) */
        #managerModal {
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
        #managerModalContent {
            background-color: #fefefe;
            margin: auto;
            padding: 20px;
            border: 1px solid #888;
            width: 50%;
        }

        /* The Close Button */
        #managerModalClose {
            color: #aaaaaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        #managerModalClose:hover,
        #managerModalClose:focus {
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
                        <button class="account-dropbutton">Planes</button>
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
                        <button class="account-dropbutton selected">Managers</button>
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

<div id="adminManagers">
    <h1>Managers</h1>
    <br>
    <form id="adminManagerAddForm">
        Full Name: <input type="text" id="managerName" name="managerName" placeholder="Full Name" required>
        Email: <input type="email" id="managerEmail" name="managerEmail" placeholder="Email" required>
        Confirm Email: <input type="email" id="managerEmailConfirm" name="managerEmail" placeholder="Email" required>
        <br>
        <div id="managerErrorMessage" style="visibility:hidden;">
            <p><span style="color: red;" >Emails do not match</span></p>
        </div>
    </form>
    <button  id="addManagerButton">Add Manager</button>
    <table class="admin_man_table">
        <tr>
            <th><b>ID</b></th>
            <th><b>Name</b></th>
            <th><b>Email</b></th>
            <th><b>Password</b></th>
            <th><b>Action</b></th>
        </tr>
    </table>
    <table id="adminAllManagers" class="admin_man_table">
        <%-- Information being retrieved from server. Check Managerial.js for function updateManagerTable
        --%>
    </table>
</div>

<div id="managerModal">
    <div id="managerModalContent">
        <span id="managerModalClose">&times;</span>
        <form id="managerModalForm">
            <div>
                <p>ID: </p>
                <input type="hidden" id="managerModalID" name="managerModalID" required>
            </div>
            <div>
                <p>Full Name: </p>
                <input type="text" id="managerModalName" name="managerModalName" placeholder="Full Name" required>
            </div>
            <div>
                <p>Email: </p>
                <input type="email" id="managerModalEmail" name="managerModalEmail" placeholder="Email" required>
            </div>
            <div>
                <p>Password: </p>
                <input type="text" id="managerModalPassword" name="managerModalPassword" placeholder="Password" required>
            </div>
        </form>
        <button id="removeManagerButton" name="removeManagerButton" >Remove Manager</button>
        <button id="updateManagerButton" name="updateManagerButton" >Update Manager</button>
    </div>
</div>

<footer>

</footer>

</body>

</html>