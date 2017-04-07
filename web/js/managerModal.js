/**
 * Created by johnn on 4/7/2017.
 */


function viewModal(managerID) {
    document.getElementById("managerModal"+managerID).style.display = "block";
    console.log("I AM JOHNNY AND I AM AWESOME!");
}

function closeEditManager(managerID){
    document.getElementById("managerModal"+managerID).style.display = "none";
}

/*window.onclick = function(event){
    if(event.target == document.getElementById("managerModal")){
        document.getElementById("managerModal").style.display = "none";
    }
}*/

/*

 */