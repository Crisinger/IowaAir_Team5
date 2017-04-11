/**
 * Created by johnn on 4/10/2017.
 */

function checkCapacity(){
    var capacity = parseInt(document.getElementById("planeCapacity").value);
    var seatsEcon = parseInt(document.getElementById("planeEconomySeats").value);
    var seatsBus = parseInt(document.getElementById("planeBusinessSeats").value);
    var seatsFirst = parseInt(document.getElementById("planeFirstSeats").value);
    var planeButton = document.getElementById("addPlaneButton");

    planeButton.disabled = capacity < seatsEcon + seatsBus + seatsFirst;
}

function modalCheckCapacity(){
    var capacity = parseInt(document.getElementById("planeModalCapacity").value);
    var seatsEcon = parseInt(document.getElementById("planeModalEconomySeats").value);
    var seatsBus = parseInt(document.getElementById("planeModalBusinessSeats").value);
    var seatsFirst = parseInt(document.getElementById("planeModalFirstSeats").value);
    var planeButton = document.getElementById("updatePlaneButton");
    planeButton.disabled = capacity < seatsEcon + seatsBus + seatsFirst;
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

function alterModalForm(planeID){
    var modelPlaneSelection = document.getElementById("planeModalModelSelect"+planeID);
    var modelPlaneID = modelPlaneSelection.options[modelPlaneSelection.selectedIndex].value;
    var modelHasEconomy = document.getElementById("planeModelEconomy"+modelPlaneID).innerText;
    var modelHasBusiness = document.getElementById("planeModelBusiness"+modelPlaneID).innerText;
    var modelHasFirst = document.getElementById("planeModelFirst"+modelPlaneID).innerText;
    var modelCapacity = document.getElementById("planeModelCapacity"+modelPlaneID).innerText;
    var inputCapacity = document.getElementById("planeModalCapacity"+planeID);
    var seatsEcon = document.getElementById("planeModalEconomySeats"+planeID);
    var seatsBus = document.getElementById("planeModalBusinessSeats"+planeID);
    var seatsFirst = document.getElementById("planeModalFirstSeats"+planeID);
    var multipleEcon = document.getElementById("planeModalEconomyMultiple"+planeID);
    var multipleBus = document.getElementById("planeModalBusinessMultiple"+planeID);
    var multipleFirst = document.getElementById("planeModalFirstMultiple"+planeID);

    console.log("--------------------");
    console.log(modelPlaneID);
    console.log(modelHasEconomy);
    console.log(modelHasBusiness);
    console.log(modelHasFirst);
    console.log(modelCapacity);
    console.log(inputCapacity);
    console.log(seatsEcon);
    console.log(seatsBus);
    console.log(seatsFirst);
    console.log(multipleEcon);
    console.log(multipleBus);
    console.log(multipleFirst);



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
        if(inputNumberField.value == 0){
            inputNumberField.value = 1;
        }
        inputNumberField.readOnly = false;
        inputNumberField.disabled = false;
    } else {
        inputNumberField.value = 0;
        inputNumberField.readOnly = true;
        inputNumberField.disabled = true;
    }
}