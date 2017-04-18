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
            console.log(msg);
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
        if(msg>0){
            querriedFlights = JSON.parse(msg).flights;

        }
    });




}

