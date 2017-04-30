/**
 * Created by johnn on 4/28/2017.
 */

var activeList;

$(function() {
    $.post("/Customer.FlightHistory.History", "", function (msg) {
        if (msg.length > 0) {
            activeList = JSON.parse(msg);
            console.log(activeList);
            setUpFlightsTable();
        }
    });

});



function setUpFlightsTable(){
        resetFlightsTable();
        showFlightsPage();
}
function resetFlightsTable(){
    var parent = document.getElementById("flightHistoryList");
    while(parent.hasChildNodes()){
        parent.removeChild(parent.firstChild);
    }
}

function generateOntoTable(id,totalTix,economyTix,businessTix,firstTix,depDate,depTime,depLoc,ariDate,ariTime,ariLoc){
    var parent = document.getElementById("flightHistoryList");
    var row = document.createElement("tr");
    var flightId = document.createElement("td");
    var totalTickets = document.createElement("td");
    var economy = document.createElement("td");
    var business = document.createElement("td");
    var first = document.createElement("td");
    var departureDate = document.createElement("td");
    var departureTime = document.createElement("td");
    var departureLocation = document.createElement("td");
    var arrivalDate = document.createElement("td");
    var arrivalTime = document.createElement("td");
    var arrivalLocation = document.createElement("td");
    var action = document.createElement("td");

    var btn = document.createElement("button");

    flightId.innerText = id;
    totalTickets.innerText = totalTix;
    economy.innerText = economyTix;
    business.innerText = businessTix;
    first.innerText = firstTix;
    departureDate.innerText = depDate;
    departureTime.innerText = depTime;
    departureLocation.innerText = depLoc;
    arrivalDate.innerText = ariDate;
    arrivalTime.innerText = ariTime;
    arrivalLocation.innerText = ariLoc;

    btn.innerText = "Remove";
    action.appendChild(btn);

    row.appendChild(flightId);
    row.appendChild(totalTickets);
    row.appendChild(economy);
    row.appendChild(business);
    row.appendChild(first);
    row.appendChild(departureDate);
    row.appendChild(departureTime);
    row.appendChild(departureLocation);
    row.appendChild(arrivalDate);
    row.appendChild(arrivalTime);
    row.appendChild(arrivalLocation);
    row.appendChild(action);
    parent.appendChild(row);
}

function showFlightsPage(){
    console.log(activeList);
    for(var i=0; i<activeList.length; i++){
        console.log(activeList[i]);
        generateOntoTable(activeList['flightID'], activeList['totalTickets'], activeList['ticketsEconomy'], activeList['ticketsBusiness'], activeList['ticketsFirst'], activeList['departureDate']
            , activeList['departureTime'], activeList['departureLocation'], activeList['arrivalDate'], activeList['arrivalTime'], activeList['arrivalLocation']);

    }
}
/*




$.post("path name", info to send... your case is "" , function(response){

});




 */