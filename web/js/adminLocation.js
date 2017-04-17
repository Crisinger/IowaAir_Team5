/**
 * Created by johnn on 4/8/2017.
 */
/*
$(function(){

    $("#stateName").on("change" , function(){
        updateCitySelection();
    });

    $("#cityName").on("change", function(){
       updateStateSelection();
    });

});

function updateCitySelection(){
    var state = document.getElementById("stateName");
    var stateInfo = "stateName="+state.options[state.selectedIndex].value;

    console.log(stateInfo);

    $.get("Admin.Locations.Locations", "value=0",
        function(msg){

        }
    );

}

function updateStateSelection(){
    resetSelection("city");
    updateCitySelection();
}

function displayCity(type, name){
    var parent = document.getElementById(type+"Name");
    var child = document.createElement("option");
    if(type=="city"){
        child.setAttribute("onclick","updateStateSelection()");
    }else {
        child.setAttribute("onclick","updateCitySelection()");
    }

}


function resetSelection(type){
    var parent = document.getElementById(type+"Name");
    var child = document.createElement("option");
    child.innerText = "--- Select "+type+" ---";
    child.setAttribute("disabled","true");
    child.setAttribute("selected","true");
    while(parent.hasChildNodes()){
        parent.removeChild(parent.firstChild);
    }
    parent.appendChild(child);
}

 function populateSelection(type){

 $.get("General.Locations","type="+type,
 function(msg){
 var info = JSON.parse(msg);
 resetSelection(type);
 for(var i=0; i<info.length; i++){

 }
 }
 )

 }
*/
function alterCityMenu(classID){
    var allCities = document.getElementsByClassName("adminAllCities");

    for(var city=0; city<allCities.length; city++){
        allCities[city].style.display = "none";
    }

    var stateCities = document.getElementsByClassName(classID);

    for(var stateCity=0; stateCity<stateCities.length; stateCity++){
        stateCities[stateCity].style.display = "block";
    }

}





