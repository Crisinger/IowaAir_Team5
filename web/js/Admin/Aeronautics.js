/**
 * Created by johnn on 4/16/2017.
 */
var modelList ="";
var planeList ="";

// FINISH THESE THREE
//  remove plane model
// remove plane
// update plane

function removePlaneModel(){
    /*


     ENTER CODE TO REMOVE PLANE MODEL



     */
}

function removePlane(){
    /*
     ENTER CODE TO REMOVE PLANE



     */
}

function updatePlane(){
    /*
     ENTER CODE TO UPDATE PLANE
     */
}


$(function(){

    // Once file is loaded, perform the following
    updatePlaneModelTable();
    alterText();
    updatePlanesTable();

    $("#addPlaneModelButton").click(function(){
        if(document.getElementById("planeModel").value!=""){
            addPlaneModel();
        }else{
            alert("Please enter a model name!");
        }
    });

    $("#updatePlaneModelModalButton").click(function(){
        if(document.getElementById("modelModal").value!=""){
            updatePlaneModel();
        }else{
            alert("Please enter a model name!");
        }
    });

    $("#removePlaneModelModalButton").click(function(){
        removePlaneModel();
    });

    $("#planeModelModalClose").click(function(){
        closeEditPlaneModel();
    });

    $("#addPlaneButton").click(function(){
        attemptAddAirplane();
    });

    $("#updatePlaneModalButton").click(function(){
        updatePlane();
    });

    $("#removePlaneModalButton").click(function(){
        removePlane();
    });

    $("#planeModalClose").click(function(){
        closeEditPlane();
    });

});



function attemptAddAirplane(){
    var modelSelection = document.getElementById("planeModelSelect");
    var model = "modelID="+modelSelection.options[modelSelection.selectedIndex].value;

    $.get("Admin.Planes.AddPlane",model, function(msg){
        if(msg.length>0){
            if(msg=="Added"){
                alert("Plane was added!");
            } else {
                alert("Plane was not added!");
            }
            updatePlanesTable();
        }
    });
}

function updatePlanesTable(){

    console.log("building planes table");

    $.post("Admin.Planes.PlaneList","",function(msg){
        console.log("plane msg = "+msg);
        if(msg.length>0){
            planeList = JSON.parse(msg).planes;
            console.log("finished parsing planes msg");
            resetPlaneTable();
            for (var i = 0; i < planeList.length; i++) {
                console.log("at plane #"+i);
                displayPlane(i);
            }
        }
    });
}

function displayPlane(count){
    var plane = planeList[count];
    var parent = document.getElementById("planeTable");
    var child = document.createElement("tr");
    var modelActionButton = document.createElement("button");

    var list = [plane.pID, plane.pModID, modelActionButton];

    child.id = "planeID"+plane.pID;
    modelActionButton.setAttribute("onclick","sendInfoToPlaneModal("+count+")");
    modelActionButton.innerText="Edit";

    for(var i=0; i<list.length; i++){
        var subChild = document.createElement("td");
        if(i!=list.length-1) {
            subChild.innerText = list[i]; // for all other table entities
        } else{
            subChild.append(list[i]); // keeps action button last in table
        }
        subChild.name="col"+i;
        child.append(subChild); // adds table data to table row
    }

    parent.append(child);

    console.log("Finished forming plane table");
}

function resetPlaneTable(){
    var parent = document.getElementById("planeTable");
    while(parent.hasChildNodes()){
        parent.removeChild(parent.firstChild);
    }
}

function updatePlaneModel(){
    var info = $("#planeModelModalForm").serialize();
    console.log(info);

    $.post("Admin.Planes.UpdatePlaneModel", info, function(msg){
        if(msg.length>0){
            if(msg=="true"){
                alert("Model was updated!");
                closeEditPlaneModel();
            }else{
                alert("Model was not updated!");
            }
            updatePlaneModelTable();
        }
    })
}

function addPlaneModel(){
    var info = $("#planeModelForm").serialize();

    console.log(info);

    $.post("Admin.Planes.AddPlaneModel",info, function(msg){
        if(msg.length>0){
            if(msg=="true"){
                alert("Model was added!");
                resetForms();
            } else {
                alert("Model was not added!");
            }
            updatePlaneModelTable();
        }
    });
}

function resetForms(){
    /*document.getElementById("planeModel").value = "";
    document.getElementById("modelCapacity").value = 450;
    document.getElementById("modelFuel").value = 160;
    document.getElementById("modelBurn").value=5.6;
    document.getElementById("modelVelocity").value=1250;

    document.getElementById("seatsEconomyRange").value=0;
    document.getElementById("seatsBusinessRange").value=0;
    document.getElementById("seatsFirstRange").value=0;
    alterText();*/
    document.getElementById("planeModelForm").reset();
    document.getElementById("planeModelModalForm").reset();

}

function alterSlider(value){
    var economy = document.getElementById("seatsEconomyRange");
    var business = document.getElementById("seatsBusinessRange");
    var first = document.getElementById("seatsFirstRange");
    var tempSlide;
    if(value==0){
        tempSlide = economy;
    }else if(value==1){
        tempSlide = business;
    }else if(value==2){
        tempSlide = first;
    }
    if(tempSlide.disabled==true){
        tempSlide.disabled=false;
        tempSlide.value = 5;
    }else{
        tempSlide.disabled=true;
        tempSlide.value = 0;
    }

    alterText();
}

function alterModalSlider(value){
    var economy = document.getElementById("seatsEconomyRangeModal");
    var business = document.getElementById("seatsBusinessRangeModal");
    var first = document.getElementById("seatsFirstRangeModal");
    var tempSlide;
    if(value==0){
        tempSlide = economy;
    }else if(value==1){
        tempSlide = business;
    }else if(value==2){
        tempSlide = first;
    }
    if(tempSlide.disabled==true){
        tempSlide.disabled=false;
        tempSlide.value = 5;
    }else{
        tempSlide.disabled=true;
        tempSlide.value = 0;
    }

    alterModalText();
}

function alterText(){
    document.getElementById("seatsEconomy").value = document.getElementById("seatsEconomyRange").value + " seats";
    document.getElementById("seatsBusiness").value = document.getElementById("seatsBusinessRange").value + " seats";
    document.getElementById("seatsFirst").value = document.getElementById("seatsFirstRange").value + " seats";

    document.getElementById("modelCapacityText").value = document.getElementById("modelCapacity").value + " persons";
    document.getElementById("modelFuelText").value = document.getElementById("modelFuel").value + " tonnes";
    document.getElementById("modelBurnText").value = document.getElementById("modelBurn").value + " kg/km";
    document.getElementById("modelVelocityText").value = document.getElementById("modelVelocity").value + " km/hr";
    checkCapacity();
}

function alterModalText(){
    document.getElementById("seatsEconomyModalText").value = document.getElementById("seatsEconomyRangeModal").value + " seats";
    document.getElementById("seatsBusinessModalText").value = document.getElementById("seatsBusinessRangeModal").value + " seats";
    document.getElementById("seatsFirstModalText").value = document.getElementById("seatsFirstRangeModal").value + " seats";

    document.getElementById("modelModalCapacityText").value = document.getElementById("modelModalCapacity").value + " persons";
    document.getElementById("modelModalFuelText").value = document.getElementById("modelModalFuel").value + " tonnes";
    document.getElementById("modelModalBurnText").value = document.getElementById("modelModalBurn").value + " kg/km";
    document.getElementById("modelModalVelocityText").value = document.getElementById("modelModalVelocity").value + " km/hr";
    checkModalCapacity();
}

function checkCapacity(){
    var capacity = parseInt(document.getElementById("modelCapacity").value);
    var seatsEcon = parseInt(document.getElementById("seatsEconomyRange").value);
    var seatsBus = parseInt(document.getElementById("seatsBusinessRange").value);
    var seatsFirst = parseInt(document.getElementById("seatsFirstRange").value);
    var planeButton = document.getElementById("addPlaneModelButton");

    planeButton.disabled = !(capacity == seatsEcon + seatsBus + seatsFirst);
}

function checkModalCapacity(){
    var capacity = parseInt(document.getElementById("modelModalCapacity").value);
    var seatsEcon = parseInt(document.getElementById("seatsEconomyRangeModal").value);
    var seatsBus = parseInt(document.getElementById("seatsBusinessRangeModal").value);
    var seatsFirst = parseInt(document.getElementById("seatsFirstRangeModal").value);
    var updateButton = document.getElementById("updatePlaneModelModalButton");

    updateButton.disabled = !(capacity == seatsEcon + seatsBus + seatsFirst);
}

function updatePlaneModelTable(){
    console.log("building plane model table...");
    $.get("Admin.Planes.PlaneModelsList","", function(msg){
        if(msg.length>0) {
            modelList = JSON.parse(msg).models;
            console.log("finished parsing msg")
            resetPlaneModelTable();
            for (var i = 0; i < modelList.length; i++) {
                console.log("at model #"+i);
                displayPlaneModel(i);
            }
            updatePlaneModelSelection();
        }
    });

}

function resetPlaneModelTable(){
    var parent = document.getElementById("planeModelListTable");
    while(parent.hasChildNodes()){
        parent.removeChild(parent.firstChild);
    }
}

function displayPlaneModel(count){
    var model = modelList[count];
    var parent = document.getElementById("planeModelListTable");
    var child = document.createElement("tr");
    var modelActionButton = document.createElement("button");

    var list = [model.mModel,model.mCap, model.hEcon, model.hBus, model.hFirst,
    model.sEcon, model.sBus, model.sFirst, modelActionButton];

    child.id = "planeModel"+model.mID;
    modelActionButton.setAttribute("onclick","sendInfoToPlaneModelModal("+count+")");
    modelActionButton.innerText="Edit";


    for(var i=0; i<list.length; i++){
        var subChild = document.createElement("td");
        if(i!=list.length-1) {
            subChild.innerText = list[i]; // for all other table entities
        } else{
            subChild.append(list[i]); // keeps action button last in table
        }
        subChild.name="col"+i;
        child.append(subChild); // adds table data to table row
    }

    parent.append(child);

    console.log("Finished forming table");
}

function sendInfoToPlaneModelModal(count){

    console.log("Testing edit button");
    console.log(modelList[count].mID);
    console.log(modelList[count].mModel);
    console.log(modelList[count].mBurn);
    document.getElementById("modelModalID").value = modelList[count].mID;
    document.getElementById("modelModal").value = modelList[count].mModel;
    document.getElementById("modelModalCapacity").value = modelList[count].mCap;
    document.getElementById("modelModalFuel").value = modelList[count].mFuel;
    document.getElementById("modelModalBurn").value = modelList[count].mBurn;
    document.getElementById("modelModalVelocity").value = modelList[count].mAvgV;
    var hEcon = document.getElementById("hasEconomyModal");
    var hBus = document.getElementById("hasBusinessModal");
    var hFirst = document.getElementById("hasFirstModal");
    var sEcon = document.getElementById("seatsEconomyRangeModal");
    var sBus = document.getElementById("seatsBusinessRangeModal");
    var sFirst = document.getElementById("seatsFirstRangeModal");

    console.log(modelList[count].hEcon);
    console.log(modelList[count].hEcon==1);


    if(modelList[count].hEcon==1){
        hEcon.checked = true;
        sEcon.disabled = false;

    }else{
        sEcon.disabled = true;
    }

    if(modelList[count].hBus==1){
        hBus.checked = true;
        sBus.disabled = false;

    }else{
        sBus.disabled = true;
    }

    if(modelList[count].hFirst==1){
        hFirst.checked = true;
        sFirst.disabled = false;

    }else{
        sFirst.disabled = true;
    }

    sEcon.value = modelList[count].sEcon;
    sBus.value = modelList[count].sBus;
    sFirst.value = modelList[count].sFirst;

    alterModalText();

    viewPlaneModelModal();

}

function sendInfoToPlaneModal(count){
    var planeID = planeList[count].pID;
    var planeModelID = planeList[count].pModID;

    document.getElementById("planeModalID").value = planeID;
    document.getElementById("planeModalModelID").value = planeModelID;
    viewPlaneModal();
}

function viewPlaneModelModal() {
    document.getElementById("planeModelModal").style.display = "block";
}

function closeEditPlaneModel(){
    document.getElementById("planeModelModal").style.display = "none";
    resetForms();
}

function viewPlaneModal() {
    document.getElementById("planeModal").style.display = "block";
}

function closeEditPlane(){
    document.getElementById("planeModal").style.display = "none";
}

function updatePlaneModelSelection(){
    resetOptionsMenu("planeModelSelect");

    for(var i=0; i<modelList.length; i++){
        addOptionToSelection("planeModelSelect",i);
    }

}

function addOptionToSelection(selectionID, count){
    var parent = document.getElementById(selectionID);
    var child = document.createElement("option");

    child.innerText = modelList[count].mModel;
    child.value = modelList[count].mID;
    child.setAttribute("onclick","updatePlaneSpecsInfo("+count+")");
    parent.appendChild(child);

}

function resetOptionsMenu(selectionID){
    var parent = document.getElementById(selectionID);
    while(parent.hasChildNodes()){
        parent.removeChild(parent.firstChild);
    }
    var baseChild = document.createElement("option");
    baseChild.innerText = "---Select Model---";
    baseChild.disabled = true;
    baseChild.selected = true;
    parent.appendChild(baseChild);
    resetOptionsText();
}

function resetOptionsText(){
    document.getElementById("planeCapacity").value = "0 persons";
    document.getElementById("planeEconomySeats").value = "0 seats";
    document.getElementById("planeBusinessSeats").value = "0 seats";
    document.getElementById("planeFirstSeats").value = "0 seats";
}

function updatePlaneSpecsInfo(count){

    document.getElementById("planeCapacity").value = modelList[count].mCap+" persons";
    document.getElementById("planeEconomySeats").value = modelList[count].sEcon+" seats";
    document.getElementById("planeBusinessSeats").value = modelList[count].sBus+" seats";
    document.getElementById("planeFirstSeats").value = modelList[count].sFirst+" seats";

}

