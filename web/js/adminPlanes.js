/**
 * Created by johnn on 4/10/2017.
 */

function checkCapacity(){
    var capacity = document.getElementById("planeCapacity").value;
    var seatsEcon = document.getElementById("planeEconomySeats").value;
    var seatsBus = document.getElementById("planeBusinessSeats").value;
    var seatsFirst = document.getElementById("planeFirstSeats").value;
    var planeButton = document.getElementById("addPlaneButton");

    planeButton.disabled = (capacity <= seatsEcon + seatsBus + seatsFirst);
}

function alterForm(){
    var modelPlaneSelection = document.getElementById("planeModelSelect");
    var modelPlaneID = modelPlaneSelection.options[modelPlaneSelection.selectedIndex].value;
    var modelHasEconomy = document.getElementById("planeModelEconomy"+modelPlaneID).innerText;
    var modelHasBusiness = document.getElementById("planeModelBusiness"+modelPlaneID).innerText;
    var modelHasFirst = document.getElementById("planeModelFirst"+modelPlaneID).innerText;
    var modelCapacity = document.getElementById("planeModelCapacity"+modelPlaneID).innerText;
    var inputCapacity = document.getElementById("planeCapacity");
    var seatsEcon = document.getElementById("planeEconomySeats");
    var seatsBus = document.getElementById("planeBusinessSeats");
    var seatsFirst = document.getElementById("planeFirstSeats");
    var multipleEcon = document.getElementById("planeEconomyMultiple");
    var multipleBus = document.getElementById("planeBusinessMultiple");
    var multipleFirst = document.getElementById("planeFirstMultiple");

    inputCapacity.max = modelCapacity;

    displayNumberInput(modelHasEconomy, seatsEcon);
    displayNumberInput(modelHasBusiness, seatsBus);
    displayNumberInput(modelHasFirst, seatsFirst);

    displayNumberInput(modelHasEconomy, multipleEcon);
    displayNumberInput(modelHasBusiness, multipleBus);
    displayNumberInput(modelHasFirst, multipleFirst);

}

function displayNumberInput(condition, inputNumberField){
    if(condition == '1'){
        inputNumberField.value = 1;
        inputNumberField.readOnly = false;
        inputNumberField.disabled = false;
    } else {
        inputNumberField.value = 0;
        inputNumberField.readOnly = true;
        inputNumberField.disabled = true;
    }
}