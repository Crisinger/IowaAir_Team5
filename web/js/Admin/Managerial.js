/**
 * Created by johnn on 4/15/2017.
 */

$(function(){

    console.log("document is ready");
    updateManagerTable();

    $("#addManagerButton").click(function(){
        console.log("pressed add manager button");
        if(document.getElementById("managerName").value != "") {
            if (emailCheck()) {
                console.log("attempting add manager");
                attemptAddManager();
            }
            console.log("finished with add manager button");
        }else{
            alert("Please enter an email");
        }
    });

    $("#updateManagerButton").click(function(){
        console.log("pressed update manager button");
        sendUpdateInformation();
    });

    $("#removeManagerButton").click(function(){
        console.log("pressed remove manager button");
        if(confirm("Are you sure you want to remove this manager?")){
            sendRemoveInformation();
        }
    });

    $("#managerModalClose").click(function(){
        closeManagerModal();
    });

});

function sendRemoveInformation(){
    var removeData = "removeManager=" + document.getElementById("managerModalID").value;
    console.log("removeData: "+ removeData);

    $.get("Admin.Managerial.RemoveManager", removeData,
        function(msg) {
            console.log(msg);
            if(msg=="Removed"){
                alert("Manager Removed!");
            } else {
                alert("Manager not removed!");
            }
            removeTableContents();
            updateManagerTable();
            closeManagerModal();
        }
    );
}

function sendUpdateInformation(){
    var updateData = $("#managerModalForm").serialize();
    console.log("updateData: "+updateData);
    $.post( "Admin.Managerial.UpdateManager", updateData,
        function(msg){
            console.log("update msg= "+msg);
            if(msg=="Updated"){
                alert("Manager Updated");
            } else {
                alert("Manager Not Updated");
            }
            removeTableContents();
            updateManagerTable();
            closeManagerModal();
        }
    );
}

function viewManagerModal() {
    document.getElementById("managerModal").style.display = "block";
}

function closeManagerModal(){
    document.getElementById("managerModal").style.display = "none";
}

/**
 * Checks to see if email and confirm email match
 */
function emailCheck(){
    var email = document.getElementById("managerEmail").value;
    var confirmEmail = document.getElementById("managerEmailConfirm").value;
    console.log("checking emails");
    if(email == confirmEmail){
        $("#managerErrorMessage").css("visibility","hidden");
    } else {
        $("#managerErrorMessage").css("visibility","visible");
    }
    console.log(email==confirmEmail);
    return (email==confirmEmail);
}

function attemptAddManager(){

    var sendInfo = "managerName="+ document.getElementById("managerName").value+"&";
    sendInfo += "managerEmail="+document.getElementById("managerEmail").value;

    console.log("sendInfo: "+sendInfo);

    $.get( "Admin.Managerial.AddManager", sendInfo,
        function(msg){
            console.log(msg);
            if(msg == "Added"){
                alert("Manager added!");
                removeTableContents();
                updateManagerTable();
            } else{
                alert("Manager not added!");
            }
        }
    );


}

function updateManagerTable(){
    console.log("Getting manager table...");
    $.post("Admin.Managerial.ManagerList","value=1",
        function(msg){
            if(msg != "") {
                console.log("about to parse");
                var table = JSON.parse(msg);
                table = table.manager;
                console.log("Finished Parsing");

                for (var i = 0; i < table.length; i++) {
                    console.log("value=" + i);
                    displayRow(table[i].manID, table[i].manName, table[i].manEmail, table[i].manPass);
                }
            }
        }
    );
}

function displayRow(manID,manName,manEmail,manPassword){
    console.log("inserting "+manName+" into table");
    var parent = document.getElementById("adminAllManagers");
    var actionBtn = document.createElement("Button");
    var child = document.createElement("tr");
    var column =[manID,manName, manEmail, manPassword, actionBtn];

    child.id = "managerRow"+manID;
    //var sendInformation = manID+","+manName+","+manEmail+","+manPassword;
    actionBtn.setAttribute("onclick","sendEditInfoToModal("+manID+",\""+manName+"\",\""+manEmail+"\",\""+manPassword+"\")");
    actionBtn.innerText="Edit";


    for(var i=0; i<column.length; i++){
        var subChild = document.createElement("td");
        if(i!=column.length-1) {
            subChild.innerText = column[i]; // for all other table entities
        } else{
            subChild.append(column[i]); // keeps action button last in table
        }
        subChild.name="col"+i;
        child.append(subChild); // adds table data to table row
    }

    parent.append(child); // adds table row to table
}

function updateModal(manId, manName, manEmail, manPass){
    document.getElementById("managerModalName").value = manName;
    document.getElementById("managerModalEmail").value = manEmail;
    document.getElementById("managerModalPassword").value = manPass;
    document.getElementById("managerModalID").value = manId;
}

function removeTableContents(){
    var parent = document.getElementById("adminAllManagers");
    while(parent.hasChildNodes()){
        parent.removeChild(parent.firstChild);
    }

}

function sendEditInfoToModal(manID,manName,manEmail,manPassword){
    console.log("pressed edit manager button");
    updateModal(manID, manName,manEmail,manPassword);
    viewManagerModal();
}


