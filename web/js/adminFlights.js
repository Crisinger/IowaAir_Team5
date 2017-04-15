/**
 * Created by johnn on 4/11/2017.
 */

var availablePlaneList = "";

/**
 * Date picker for Flight Departure
 */
$( function() { $( "#flightDeparturedatepicker" ).datepicker({
    beforeShow: function(input,inst){
        var today = new Date();
        today.setDate(today.getDate()+1);
        $(this).datepicker('option','minDate',today);
    }
});} );

/**
 * Date picker for Flight Arrival
 */
$( function() { $( "#flightArrivaldatepicker" ).datepicker({
    beforeShow: function(input,inst){
        var today = new Date();
        today.setDate(today.getDate()+1);
        $(this).datepicker('option','minDate',today);
    }
});} );

/**
 * Time picker for Flight Departure
 */
$( function() {
    $( "#flightDeparturetimepicker" ).timepicker({
    timeFormat: 'HH:mm',
    minTime: new Date(0,0,0,0,0)
    });
} );

/**
 * Time picker for Flight Arrival
 */
$( function() {
    $( "#flightArrivaltimepicker" ).timepicker({
        timeFormat: 'HH:mm',
        minTime: new Date(0,0,0,0,0)
    });
} );

function enableNextEntry(number){
    if(number == 1){
        document.getElementById("flightDeparturetimepicker").disabled = false;
    } else if (number == 2){
        document.getElementById("flightDepartureLocationState").disabled = false;
    } else if (number == 3){
        document.getElementById("flightDepartureLocationCity").disabled = false;
    } else if (number == 4){
        document.getElementById("flightArrivalLocationState").disabled = false;
    } else if (number == 5){
        document.getElementById("flightArrivalLocationCity").disabled = false;
    } else if (number == 6){
        document.getElementById("flightPlaneModelSelect").disabled = false;
    } else if (number == 7){
        document.getElementById("addFlightButton").disabled = false;
    } else {
        document.getElementById("flightDeparturetimepicker").disabled = true;
        document.getElementById("flightDepartureLocationState").disabled = true;
        document.getElementById("flightDepartureLocationCity").disabled = true;
        document.getElementById("flightArrivalLocationState").disabled = true;
        document.getElementById("flightArrivalLocationCity").disabled = true;
        document.getElementById("flightPlaneModelSelect").disabled = true;
        document.getElementById("addFlightButton").disabled = true;
    }
}

/**
 *
 */
$( function() {
    $(".adminAddFlightModalButtons").onclick=function(){
        var prev = document.getElementById("adminAddFlightModalPreviousPage");
        var next = document.getElementById("adminAddFlightModalCurrentPage");
        if(this == prev){
            getAvailableAirplanes(prev);
        }else if(this == next){
            getAvailableAirplanes(next);
        }
    };
});

/**
 * Implemented to only show cities that go with the Departure State
 * @param classID or the class state ID
 */
function alterDepartureCitySelect(classID){
    var allCities = document.getElementById("flightDepartureLocationCity").getElementsByClassName("adminAllCities");

    for(var city=0; city<allCities.length; city++){
        allCities[city].style.display = "none";
    }

    var stateCities = document.getElementById("flightDepartureLocationCity").getElementsByClassName(classID);

    for(var stateCity=0; stateCity<stateCities.length; stateCity++){
        stateCities[stateCity].style.display = "block";
    }

}

/**
 * Implemented to only show cities that go with the Arrival State
 * @param classID or state ID class name
 */
function alterArrivalCitySelect(classID){

    var departStateSelect = document.getElementById("flightDepartureLocationState");
    var departState = departStateSelect.options[departStateSelect.selectedIndex].value;
    var departCitySelect = document.getElementById("flightDepartureLocationCity");
    var departCity = departCitySelect.options[departCitySelect.selectedIndex].value;
    var arrivalStateSelect = document.getElementById("flightArrivalLocationState");
    var arrivalState = arrivalStateSelect.options[arrivalStateSelect.selectedIndex].value;
    var allCities = document.getElementById("flightArrivalLocationCity").getElementsByClassName("adminAllCities");

    for(var city=0; city<allCities.length; city++){
        allCities[city].style.display = "none";
    }

    var stateCities = document.getElementById("flightArrivalLocationCity").getElementsByClassName(classID);

    for(var stateCity=0; stateCity<stateCities.length; stateCity++){
        stateCities[stateCity].style.display = "block";
        if(departState==arrivalState && departCity==stateCities[stateCity].value){
            stateCities[stateCity].style.display = "none";
        }
    }

}

/**
 * Gets the departure and arrival location information
 * @returns {*|jQuery} serialized information for jquery send
 */
function getLocationInfo(){
    var locations;
    locations =  $("#flightDepartureLocationState").serialize()+"&";
    locations +=  $("#flightDepartureLocationCity").serialize()+"&";
    locations +=  $("#flightArrivalLocationState").serialize()+"&";
    locations +=  $("#flightArrivalLocationCity").serialize();
    return locations;
}


/**
 * Determines if the selected plane model can travel the distance
 * "doGets" the information to "AirFunctions.Admin.AdminFlights"
 * The returned information includes a simple boolean, trip time,
 * and price adjustment due to distance traveled
 */
function canModelMakeTheDistance(){
    document.getElementById("flightDemandSlider").value = 1; // resets slider
    updateSliderText(); // updates slider text to new value
    availablePlaneList = "";
    var planeModelSelect = document.getElementById("flightPlaneModelSelect");
    var planeModel = planeModelSelect.options[planeModelSelect.selectedIndex].value;
    var series = getLocationInfo() + "&flightPlaneModelSelect="+planeModel;

   $.get(
        "AirFunctions.Admin.AdminFlights", series,
       function(msg){
           var ableTimedPrice = JSON.parse(msg);
            if(ableTimedPrice.canTravel == 1){
                modifyArrivalDateAndTime(ableTimedPrice.timed);
                document.getElementById("flightDistancePrice").value = ableTimedPrice.distancePrice;
                document.getElementById("adminAddFlightModalCurrentPage").innerHTML = "0";
                getAvailableAirplanes(0);
            } else{
                alert("this plane can't make the trip");
            }
       }
    );

}

/**
 * Sets the Arrival Date and Time once locations known.
 * @param tripTime
 */
function modifyArrivalDateAndTime(tripTime){
    var departDate = $("#flightDeparturedatepicker").datepicker('getDate');
    var departTime = $("#flightDeparturetimepicker").timepicker('getTime');
    var arrivalDate = new Date();

    console.log(tripTime);

    arrivalDate.setFullYear(departDate.getFullYear());
    arrivalDate.setMonth(departDate.getMonth());
    arrivalDate.setDate(departDate.getDate());
    arrivalDate.setHours(departTime.getHours());
    arrivalDate.setMinutes(departTime.getMinutes()+tripTime);

    $("#flightArrivaldatepicker").datepicker('setDate',arrivalDate);
    $("#flightArrivaltimepicker").timepicker('setTime',arrivalDate);
}


function getAvailableAirplanes(page){

    //String[] list = {"planeType","id","capacity","maxEcon","maxBus","maxFirst","basePrice"};
    $.post("AirFunctions.Admin.AdminFlights",getAvailabilityInfo(),
        function(msg){
            var response = JSON.parse(msg);
            removePreviousPlaneQuery();
            availablePlaneList = response.planes;
            if(response.numberOfPlanes == 0){
                // Code for when the number of planes that match the criteria is 0
            }else{
                setModalHeader();
                document.getElementById("adminAddFlightModalFormNumber").value = availablePlaneList.length;
                console.log(availablePlaneList.length);
                whatAirplanesToDisplay(0);
            }
        }
    )

}

function whatAirplanesToDisplay(page){


    for(var i=page*10; i<page*10+10; i++){
        if(i < availablePlaneList.length) {
            displayAvailablePlane(availablePlaneList[i].id);
        }
    }
    hideShowButtons(page)
}

/**
 * Gets the information to send to get list of available airplanes from server
 * @returns {string|*} of serialized information to pass to server
 */
function getAvailabilityInfo(){
    var departDate = $("#flightDeparturedatepicker").datepicker('getDate');
    var departTime = $("#flightDeparturetimepicker").timepicker('getTime');
    var arrivalDate = $("#flightArrivaldatepicker").datepicker('getDate');
    var arrivalTime = $("#flightArrivaltimepicker").timepicker('getTime');
    var planeModelSelect = document.getElementById("flightPlaneModelSelect");
    var nextSeries;
    nextSeries =    "flightDepartureDate="+returnDateInMSQLFormat(departDate,0) + "&";
    nextSeries +=   "flightDepartureTime="+returnTimeInMSQLFormat(departTime)+":00&";
    nextSeries +=   "flightArrivalDate="+returnDateInMSQLFormat(arrivalDate,0)+"&";
    nextSeries +=   "flightArrivalTime="+returnTimeInMSQLFormat(arrivalTime)+":00&";
    nextSeries +=   "flightPlaneModelSelect="+planeModelSelect.options[planeModelSelect.selectedIndex].text;
    return nextSeries;
}

function returnDateInMSQLFormat(date, adjust){
    var adjustMonth = date.getMonth() + adjust; // 0 if going to be parsed. 1 if going to be stored in database
    return date.getFullYear() + "-" + ((adjustMonth< 10) ? "0" : "") + adjustMonth + "-" + ((date.getDate() < 10) ? "0" : "") + date.getDate();
}

function returnTimeInMSQLFormat(time){
    return ((time.getHours() < 10) ? "0" : "") + time.getHours() + ":" + ((time.getMinutes() < 10) ? "0" : "") + time.getMinutes();
}

//String[] list = {"planeType","id","capacity","maxEcon","maxBus","maxFirst","basePrice"};
function displayAvailablePlane(id){
    var ul = document.getElementById("adminAddFlightModalUL");
    var li = document.createElement("li");
    var liDivPlane = document.createElement("div");
    var liDivCenter = document.createElement("div")
    var liDivResponse = document.createElement("div")
    var liDivP = document.createElement("p");
    var liDivInput = document.createElement("input");


    liDivInput.type="checkbox";
    liDivInput.class="addFlightCheckBoxes";
    liDivInput.value=id;
    liDivInput.onclick = function(){
        console.log(this.value);
      document.getElementById("adminAddFlightModalFormButton").value = this.value;
    };

    liDivP.innerHTML="ID: "+id;
    liDivCenter.innerHTML="";
    liDivPlane.appendChild(liDivP);
    liDivResponse.appendChild(liDivInput);

    li.appendChild(liDivPlane);
    li.appendChild(liDivCenter);
    li.appendChild(document.createElement("div"));
    li.appendChild(liDivResponse);
    li.name = "flightAddFlightFormOption";

    ul.appendChild(li);
}

/**
 * Opens up the modal that shows all available airplanes
 */
function viewAddFlightModal() {
    document.getElementById("adminAddFlightModal").style.display = "block";
}

/**
 * Closes the modal that shows all available airplanes
 */
function closeAddFlightModal(){
    document.getElementById("adminAddFlightModal").style.display = "none";
}

/**
 * Removes the previous searches available airplane list
 */
function removePreviousPlaneQuery(){
    var parent = document.getElementById("adminAddFlightModalUL");
    while(parent.hasChildNodes()) {
        parent.removeChild(parent.firstChild);
    }
}

function hideShowButtons(page){
    var current = document.getElementById("adminAddFlightModalCurrentPage");
    var prev = document.getElementById("adminAddFlightModalPreviousPage");
    var next = document.getElementById("adminAddFlightModalNextPage");
    var prevPage = page-1;
    current.innerHTML=page;
    if(prevPage<1){
        prev.hidden = true;
    }else{
        prev.show = true;
    }

    if(page == 0){
        current.hidden = true;
    } else {
        current.show = true;
    }

    if((page+1)*10<availablePlaneList.length){
        next.show = true;
    } else {
        next.hidden = true;
    }
}

function setModalHeader(){
    var planeModelSelect = document.getElementById("flightPlaneModelSelect");
    var departStateSelect = document.getElementById("flightDepartureLocationState");
    var departState = departStateSelect.options[departStateSelect.selectedIndex].value;
    var departCitySelect = document.getElementById("flightDepartureLocationCity");
    var departCity = departCitySelect.options[departCitySelect.selectedIndex].value;
    var arrivalStateSelect = document.getElementById("flightArrivalLocationState");
    var arrivalState = arrivalStateSelect.options[arrivalStateSelect.selectedIndex].value;
    var arrivalCitySelect = document.getElementById("flightArrivalLocationCity");
    var arrivalCity = arrivalCitySelect.options[arrivalCitySelect.selectedIndex].value;

    document.getElementById("adminFlightModalDepartDate").innerText = returnDateInMSQLFormat($("#flightDeparturedatepicker").datepicker('getDate'), 1);
    document.getElementById("adminFlightModalDepartTime").innerText = returnTimeInMSQLFormat($("#flightDeparturetimepicker").timepicker('getTime'));
    document.getElementById("adminFlightModalArriveDate").innerText = returnDateInMSQLFormat($("#flightArrivaldatepicker").datepicker('getDate'), 1);
    document.getElementById("adminFlightModalArriveTime").innerText = returnTimeInMSQLFormat($("#flightArrivaltimepicker").timepicker('getTime'));
    document.getElementById("adminFlightModalDepartLocation").innerText = departCity + ", " + departState;
    document.getElementById("adminFlightModalArriveLocation").innerText = arrivalCity + ", " + arrivalState;
    document.getElementById("adminAddFlightModalTitle").innerText = planeModelSelect.options[planeModelSelect.selectedIndex].text;
}


function submitToDatabase(){
    if(confirm("Are you sure?")){

        var data = getAvailabilityInfo() + "&" + getLocationInfo() + "&value=";
        data += document.getElementById("adminAddFlightModalFormButton").value;
        data += "&distancePrice="+document.getElementById("flightDistancePrice").value;
        data += "&flightDemandSlider="+document.getElementById("flightDemandSlider").value;

        $.get("AirFunctions.Admin.AdminAddFlight", data,
            function(msg){
                temp = JSON.parse(msg);
                if(temp.isAdded == 1){
                    // put something here (?)
                }else{
                    alert("something went wrong");
                }
                closeAddFlightModal();
                canModelMakeTheDistance();
            }
        );

    } else {

    }
}

function updateSliderText(){
    document.getElementById("flightDemandValue").value = document.getElementById("flightDemandSlider").value;
}