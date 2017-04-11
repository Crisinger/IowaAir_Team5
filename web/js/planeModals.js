/**
 * Created by johnn on 4/8/2017.
 */
function viewPlaneModelModal(planemodelID) {
    document.getElementById("planeModelModal"+planemodelID).style.display = "block";
}

function closeEditPlaneModel(planemodelID){
    document.getElementById("planeModelModal"+planemodelID).style.display = "none";
}

function viewPlaneModal(planeID) {
    console.log(planeID);
    document.getElementById("planeModal"+planeID).style.display = "block";
}

function closeEditPlane(planeID){
    document.getElementById("planeModal"+planeID).style.display = "none";
}
