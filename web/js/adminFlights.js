/**
 * Created by johnn on 4/11/2017.
 */

$( function() { $( "#flightDeparturedatepicker" ).datepicker({
    beforeShow: function(input,inst){
        var today = new Date();
        today.setDate(today.getDate()+1);
        $(this).datepicker('option','minDate',today);
    }
});} );

$( function() { $( "#flightArrivaldatepicker" ).datepicker({
    beforeShow: function(input,inst){
        var today = new Date();
        today.setDate(today.getDate()+1);
        $(this).datepicker('option','minDate',today);
    }
});} );

$( function() {
    $( "#flightDeparturetimepicker" ).timepicker({
    timeFormat: 'HH:mm',
    minTime: new Date(0,0,0,0,0)
    });
} );

$( function() {
    $( "#flightArrivaltimepicker" ).timepicker({
        timeFormat: 'HH:mm',
        minTime: new Date(0,0,0,0,0)
    });
} );

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

function canModelMakeTheDistance(){

   /* var departStateSelect = document.getElementById("flightDepartureLocationState");
    var departState = departStateSelect.options[departStateSelect.selectedIndex].value;
    console.log(departState);

    var departCitySelect = document.getElementById("flightDepartureLocationCity");
    var departCity = departCitySelect.options[departCitySelect.selectedIndex].value;
    console.log(departCity);


    var arrivalStateSelect = document.getElementById("flightArrivalLocationState");
    var arrivalState = arrivalStateSelect.options[arrivalStateSelect.selectedIndex].value;
    console.log(arrivalState);

    var arrivalCitySelect = document.getElementById("flightArrivalLocationCity");
    var arrivalCity = arrivalCitySelect.options[arrivalCitySelect.selectedIndex].value;
    console.log(arrivalCity);

    var departDate = $( "#flightDeparturedatepicker" ).datepicker('getDate');
    console.log(departDate);
*/
    var planeModelSelect = document.getElementById("flightPlaneModelSelect");
    var planeModel = planeModelSelect.options[planeModelSelect.selectedIndex].value;

    var series = $("#flightDepartureLocationState").serialize()+"&";
    series = series + $("#flightDepartureLocationCity").serialize()+"&";
    series = series + $("#flightArrivalLocationState").serialize()+"&";
    series = series + $("#flightArrivalLocationCity").serialize()+"&";
    series = series + "flightPlaneModelSelect="+planeModel;

   $.get(
        "AirFunctions.Admin.AdminFlights", series,
       function(msg){
            ableTimedPrice = JSON.parse(msg);
            if(ableTimedPrice.canTravel){
                modifyArrivalDateAndTime(ableTimedPrice.timed);
            } else{
                alert("pick a different plane. This one can't make it");
            }
       }
    );


}

function modifyArrivalDateAndTime(tripTime){
    var departDate = $("#flightDeparturedatepicker").datepicker('getDate');
    var departTime = $("#flightDeparturetimepicker").timepicker('getTime');
    var arrivalDate = new Date();

    arrivalDate.setFullYear(departDate.getFullYear());
    arrivalDate.setMonth(departDate.getMonth());
    arrivalDate.setDate(departDate.getDate());
    arrivalDate.setHours(departTime.getHours());
    arrivalDate.setMinutes(departTime.getMinutes()+tripTime);

    $("#flightArrivaldatepicker").datepicker('setDate',arrivalDate);
    $("#flightArrivaltimepicker").timepicker('setTime',arrivalDate);
}
