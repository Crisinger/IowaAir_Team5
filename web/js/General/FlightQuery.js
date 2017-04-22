/**
 * Created by johnn on 4/17/2017.
 */
var placeList = "";
var modelList = "";
var queryFlights = "";

var map;
var depart;
var arrive;
var path;

function myMap() {
    var mapOptions = {
        center: new google.maps.LatLng(44.580207622, -103.461760283),
        zoom: 3,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map(document.getElementById("googlemap"), mapOptions);
}

function placeMarker(marker, latt, longg){
    var make = new google.maps.LatLng(latt,longg);
    if(marker==0){
        if(depart!=null){
            depart.setPosition(make);
        }else{
            depart = new google.maps.Marker({
                position: make,
                map:map,
                animation: google.maps.Animation.DROP,
            });
        }
    }else if(marker==1){
        if(arrive!=null){
            arrive.setPosition(make);
        }else{
            arrive = new google.maps.Marker({
                position: make,
                map:map,
                animation: google.maps.Animation.DROP,
            });
        }
    }
    createFlightPath();
}

function createFlightPath(){


    if(depart!=null && arrive!=null && path!=null){
        path.setMap(null);
        path.setPath([depart.getPosition(),arrive.getPosition()]);
        path.setMap(map);
    } else{
        path = new google.maps.Polyline({
            path:[depart.getPosition(), arrive.getPosition()],
            geodesic:true,
            strokeColor: '#FF0000',
            strokeOpacity: 1.0,
            strokeWeight: 2,
            map: map
        });
    }



}




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
            $("#googlemapContainer").slideUp();
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
            resetParentSelection(modelSelect,"Model");
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
        if(type==1){
            child.setAttribute("onclick","placeMarker(\""+travelType+"\","+array[count].LAT+","+array[count].LONG+")");
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
        console.log("what the fuck is going on");
        if(msg.length>0){
            console.log(msg);
            queryFlights = JSON.parse(msg).flights;
            console.log(queryFlights);
            console.log(queryFlights.length);
            if(queryFlights.length>0) {
                resetBuildFlightSection();
                for (var i = 0; i < queryFlights.length; i++) {
                    console.log("building a flight...");
                    buildFlightInfo(queryFlights[i]);
                    console.log("finishing a flight...");
                }
            }else{
                alert("No flights are currently set for this criteria");
            }

        }
    });

}





function resetBuildFlightSection(){
    var parent = document.getElementById("flightQueryView");
    while(parent.hasChildNodes()){
        parent.removeChild(parent.lastChild);
    }
}


function buildFlightInfo(flight){
    //String[] list = {"fID","pID","mID","dLoc","aLoc","aEcon","aBus","aFirst","Dem","DP","dDate","dTime","aDate","aTime"};
    var parent = document.getElementById("flightQueryView");
    var container = document.createElement("div");
    var leftSide = document.createElement("div");
    leftSide.appendChild(buildFlightLeftSide(flight));
    var rightSide = document.createElement("div");
    rightSide.appendChild(buildFlightRightSide(flight));
    container.appendChild(leftSide);
    container.appendChild(rightSide);
    parent.appendChild(container);
}

function buildFlightLeftSide(flight){
    var unorderedList = document.createElement("ul");
    var dates = document.createElement("li");
    var times = document.createElement("li");
    var locations = document.createElement("li");
    var planeInfo = document.createElement("li");
    var timeText = document.createElement("p");

    timeText.innerHTML = timeFormatter(flight.dTime) + "&nbsp;&nbsp;&rightarrow;&nbsp;&nbsp;" +timeFormatter(flight.aTime);
    timeText.setAttribute("class","flightQueryTime");
    dates.innerHTML = dateFormatter(flight.dDate, flight.aDate);
    locations.innerHTML = locationFormatter(flight.dLoc,flight.aLoc);
    planeInfo.innerText = modelList[modelFinder(flight.mID)].NM + " : IA" +flight.pID;

    console.log("finished model finder");
    times.appendChild(timeText);
    unorderedList.append(times);
    unorderedList.appendChild(document.createElement("br"));
    unorderedList.append(dates);
    unorderedList.append(locations);
    unorderedList.append(planeInfo);
    return unorderedList;
}

function buildFlightRightSide(flight){
    //String[] list = {"fID","pID","mID","dLoc","aLoc","aEcon","aBus","aFirst","Dem","DP","dDate","dTime","aDate","aTime"};
    //String[] mList = {"ID","NM","BP"};
    var firstWrap = document.createElement("div");
    var busWrap = document.createElement("div");
    var ecoWrap = document.createElement("div");
    var sideBySide = document.createElement("div");
    var first = document.createElement("button");
    var business = document.createElement("button");
    var economy = document.createElement("button");

    console.log("calculating prices...");
    var basePrice = parseInt(modelList[modelFinder(flight.mID)].BP);
    var adjustPrice = parseInt(flight.DP)*parseInt(flight.Dem);
    console.log("basePrice = " +basePrice);
    console.log("adjustedPrice = "+adjustPrice);
    first.innerText="F: $ "+ Math.floor(basePrice + adjustPrice*(2)); // 2 for first class
    business.innerText="B: $ "+ Math.floor(basePrice + adjustPrice*(1.5)); // 1.5 for first class
    economy.innerText="E: $  "+ Math.floor(basePrice + adjustPrice*(1)); // 1 for first class

    first.setAttribute("class","buyFirstClass");
    business.setAttribute("class","buyBusinessClass");
    economy.setAttribute("class","buyEconomyClass");

    if(flight.aEcon=="0"){
        economy.disabled=true;
    }
    if(flight.aBus=="0"){
        business.disabled=true;
    }
    if(flight.aFirst=="0"){
        first.disabled=true;
    }

    firstWrap.appendChild(first);
    busWrap.appendChild(business);
    ecoWrap.appendChild(economy);

    sideBySide.appendChild(ecoWrap);
    sideBySide.appendChild(busWrap);
    sideBySide.appendChild(firstWrap);

    return sideBySide;
}

function timeFormatter(time){

    var array = time.split(":");
    var hours = parseInt(array[0]);
    var mins = parseInt(array[1]);
    var half = "UTC";

    if(hours<12){
        if(hours==0){
            hours=12;
        }
        if(hours<10){
            hours = "0"+String(hours);
        }
        half = "am";
    }else{
        hours = hours-12;
        half = "pm";
    }

    if(mins<10){
        mins = "0"+String(mins);
    }

    return (String(hours) + ":" + String(mins) + " " + half);
}

function dateFormatter(date1, date2){

    var monthNM = ["January","February","March","April","May","June","July","August","September","October","November","December"];
    var dayNM = ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"];

    var array1 = date1.split("-");
    var array2 = date2.split("-");

    var formatDate1 = new Date(parseInt(array1[0]),parseInt(array1[1])-1,parseInt(array1[2]));
    var formatDate2 = new Date(parseInt(array2[0]),parseInt(array2[1])-1,parseInt(array2[2]));

    //var year =[formatDate1.getYear(),formatDate2.getYear()];
    var month =[monthNM[formatDate1.getMonth()],monthNM[formatDate2.getMonth()]];
    var day =[dayNM[formatDate1.getDay()],dayNM[formatDate2.getDay()]];

    var totalDate = day[0]+", "+month[0]+" "+array1[2];

    if(date1!=date2){
        totalDate += "&nbsp;&rightarrow;&nbsp;" + day[1]+", "+month[1]+" "+array2[2]
    }
    return totalDate;
}

function modelFinder(modelID){
    var modelListID="";
    for(var i=0; i<modelList.length; i++){
        if(modelList[i].ID == modelID){
            modelListID = i;
            i = modelList.length+1;
        }
    }
    return modelListID;
}

function locationFormatter(place1, place2){

    var dState = document.getElementById("flightQueryDepartState").selectedIndex - 1;
    var dCityIndex = placeList[dState].cities[document.getElementById("flightQueryDepartCity").selectedIndex -1];
    var aState = document.getElementById("flightQueryArrivalState").selectedIndex-1;
    var aCityIndex = placeList[aState].cities[document.getElementById("flightQueryArrivalCity").selectedIndex-1];
    var location ="";

    if(place1==dCityIndex.ID) {
        location += dCityIndex.NM + ", " + placeList[dState].NM;
    }
    location+="&nbsp;&nbsp;&nbsp;&rightarrow;&nbsp;&nbsp;&nbsp;";
    if(place2==aCityIndex.ID){
        location += aCityIndex.NM + ", "+placeList[aState].NM;
    }
    return location;

}