/**
 * Created by johnn on 4/17/2017.
 */
var activeLocationList = "";

$(function(){

    $.post("General.ActiveLocations","",function(msg){
        if(msg.length>0){
            activeLocationList = JSON.parse(msg);
            setUpLocationSelection("flightQueryDepartState","flightQueryDepartCity");
            setUpLocationSelection("flightQueryArrivalState","flightQueryArrivalCity");
        }else{
            alert("Could not connect to server");
        }
    });



});

function setUpLocationSelection(stateElement, cityElement){
    var stateSelection = document.getElementById(stateElement);
    var citySelection = document.getElementById(cityElement);

    for(var state=0; state<activeLocationList.states.; state++){

    }

    for(var city=0; city<activeLocationList.length; city++){

    }


}

function setUpOptions(parent){

}