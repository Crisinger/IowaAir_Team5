/**
 * Created by johnn on 4/17/2017.
 */
var placeList = "";
var modelList = "";
var querriedFlights = "";

$(function(){

    $.post("General.ListLocations","activity=1",function(msg){
        if(msg.length>0){
            placeList = JSON.parse(msg).states;
            setUpStateSelection("flightQueryDepartState","flightQueryDepartCity",0);
            setUpStateSelection("flightQueryArrivalState","flightQueryArrivalCity", 1);
        }else{
            alert("Could not retrieve information");
        }
    });

    $("#flightQueryButton").click(function(){
        if(validateTripLocations()){
            attemptFlightQuery();
        }else{
            alert("Please enter departure and arrival locations");
        }
    });

    $.post("General.BasicPlaneModels","",function(msg){
        if(msg.length>0){
            modelList = JSON.parse(msg).models;
            //console.log(msg);
            var modelSelect = document.getElementById("flightQueryPlaneModel");
            resetParentSelection(modelSelect,"Plane Model");
            addChildrenToParent(modelSelect,modelList,3,"");
        }
    });



});

function setUpStateSelection(stateElement, cityElement, travelType){
    var stateSelection = document.getElementById(stateElement);
    var citySelection = document.getElementById(cityElement);
    resetParentSelection(stateSelection, "State");
    resetParentSelection(citySelection, "City");
    addChildrenToParent(stateSelection,placeList,0,travelType,cityElement);
}
// this is made during the option tags creation process.
function setUpCitySelection(cityElement, sID, travelType){
    var citySelection = document.getElementById(cityElement);
    resetParentSelection(citySelection,"City");
    addChildrenToParent(citySelection,placeList[sID].cities,1, travelType, cityElement);
}
function resetParentSelection(parent, type){
    while(parent.hasChildNodes()){
        parent.removeChild(parent.firstChild);
    }
    var defaultChild = document.createElement("option");
    defaultChild.innerText = "--Select "+type+"--";
    defaultChild.value="default";
    defaultChild.disabled = true;
    defaultChild.selected = true;
    parent.appendChild(defaultChild);
}
function addChildrenToParent(parent, array, type, travelType, posChild){
    for(var count=0; count<array.length; count++){
        var child = document.createElement("option");
        child.innerText=array[count].NM;
        child.value = array[count].ID;
        if(type==0){
            child.setAttribute("onclick",
                "setUpCitySelection(\""+posChild+"\","+array[count].ID+","+travelType+")");
        }
        parent.appendChild(child);
    }
}

function validateTripLocations(){
    var dState = document.getElementById("flightQueryDepartState").selectedIndex;
    var dCity = document.getElementById("flightQueryDepartCity").selectedIndex;
    var aState = document.getElementById("flightQueryArrivalState").selectedIndex;
    var aCity = document.getElementById("flightQueryArrivalCity").selectedIndex;

    return !(dState==0 || dCity==0 || aState==0 || aCity==0)
}

function attemptFlightQuery(){
    var criteria = $("#flightQueryForm").serialize();

    $.post("General.FlightQuery", criteria, function(msg){
        if(msg.length>0){
            console.log(msg);
            querriedFlights = JSON.parse(msg).flights;
            if(querriedFlights.length>0) {
                for (var i = 0; i < querriedFlights.length; i++) {
                    buildFlightInfo(querriedFlights[i]);
                }
            }else{
                alert("No flights are currently set for this criteria");
            }

        }
    });

}

function buildFlightInfo(flight){
    //String[] list = {"fID","pID","mID","dLoc","aLoc","aEcon","aBus","aFirst","Dem","DP","dDate","dTime","aDate","aTime"};
    var parent = document.getElementById("flightQueryView");
    var container = document.createElement("div");
    var leftSide = document.createElement("div").appendChild(buildFlightLeftSide(flight));
    var rightSide = document.createElement("div").appendChild(buildFlightRightSide(flight));
    container.appendChild(leftSide);
    container.appendChild(rightSide);

    parent.appendChild(container);
    container.setAttribute("display","block");
    container.setAttribute("border-bottom","solid black 1px");
    container.setAttribute("width","80%");
}

function buildFlightLeftSide(flight){
    var unorderedList = document.createElement("ul");
    var dates = document.createElement("li");
    var times = document.createElement("li");
    var locations = document.createElement("li");
    var planeInfo = document.createElement("li");
    var dateText = document.createElement("p");
    var timeText = document.createElement("p");
    var locationsText = document.createElement("p");
    var planeInfoText = document.createElement("p");

    dateText.innerText = flight.dDate + " -> " + flight.aDate;
    timeText.innerText = flight.dTime + " -> " + flight.aTime;
    locationsText.innerText = flight.dLoc + " -> " + flight.aLoc;
    planeInfoText.innerText = flight.mID + " : " +flight.pID;

    dates.appendChild(dateText);
    times.appendChild(timeText);
    locations.appendChild(locationsText);
    planeInfo.appendChild(planeInfoText);

    unorderedList.append(times);
    unorderedList.append(dates);
    unorderedList.append(locations);
    unorderedList.append(planeInfo);
    //unorderedList.setAttribute("display","inline");
    unorderedList.setAttribute("float","left");
    return unorderedList;
}

function buildFlightRightSide(flight){
    var unorderedList = document.createElement("ul");
    var firstList = document.createElement("li");
    var busList = document.createElement("li");
    var econList = document.createElement("li");
    var seatList = document.createElement("li");
    var first = document.createElement("button");
    var business = document.createElement("button");
    var economy = document.createElement("button");
    var seatInfo = document.createElement("p");

    first.innerText="F: $ 200";
    business.innerText="B: $ 100";
    economy.innerText="E: $  50";
    seatInfo.innerText="Available Seats: #F #B #E";

    first.setAttribute("class","buyFirstClass");
    business.setAttribute("class","buyBusinessClass");
    economy.setAttribute("class","buyEconomyClass");

    if(flight.aEcon==0){
        economy.disabled=true;
    }
    if(flight.aBus==0){
        business.disabled=true;
    }
    if(flight.aFirst==0){
        first.disabled=true;
    }

    firstList.appendChild(first);
    busList.appendChild(business);
    econList.appendChild(economy);
    seatList.appendChild(seatInfo);

    unorderedList.appendChild(firstList);
    unorderedList.appendChild(busList);
    unorderedList.appendChild(econList);
    unorderedList.appendChild(seatList);

    //unorderedList.setAttribute("display","inline");
    unorderedList.setAttribute("float","right");
    return unorderedList;
}
