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
    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/manager.css">
    <title>Managers</title>
</head>
<body>
    <ul>
        <li><a href="ManagerUserLookup.jsp">User Lookup</a></li>
        <li><a href="ManagerSearchFlight.jsp">Search Flight</a></li>
        <li><a href="ManagerCancelFlight.jsp">Cancel Flight</a></li>
        <li><a href="ManagerAddFlight.jsp">Book Flight</a></li>
        <li style="float:right"><a class="active" href="index.jsp">Logout</a></li>
    </ul>

    <br>


    <form id="CheckinServlet">

        <div class = checkin>
            Customer Information:<br>
            <input type="text" name="firstname" placeholder="First Name" required><br>
            <input type="text" name="lastname" placeholder="Last Name" required><br>
            <input type="text" name="flightID" placeholder="Flight ID Number" required><br><br>
        </div>

        <br>

        <div class = checkin>
            Baggage:<br>
            <div class="input_fields_wrap">
                <button class="add_field_button">Add Bag</button>
                <div></div>
            </div>
        </div>

        <br>
        <div class = submitButton>
            <input type="submit" value="submit">
        </div>
    </form>
</body>
</html>



<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script>
    $(document).ready(function() {
        var max_fields      = 5; //maximum input boxes allowed
        var wrapper         = $(".input_fields_wrap"); //Fields wrapper
        var add_button      = $(".add_field_button"); //Add button ID

        var x = 1; //initlal text box count
        $(add_button).click(function(e){ //on add input button click
            e.preventDefault();
            if(x < max_fields){ //max input box allowed
                x++; //text box increment
                $(wrapper).append('<div><input type="text" name="mytext[]" placeholder="Bag " readonly/><input type="text" name="mytext[]" placeholder="bag lbs"/>' +
                    '<a href="#" class="remove_field" font color="red">Remove</a></div>'); //add input box
            }
        });

        $(wrapper).on("click",".remove_field", function(e){ //user click on remove text
            e.preventDefault(); $(this).parent('div').remove(); x--;
        })
    });
</script>

