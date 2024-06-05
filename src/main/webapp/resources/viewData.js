var TreeInfoObj;
window.onload = viewTreesInfo;
function viewTreesInfo () {
    let txt ="";
    let x;
    let xhpPost = new XMLHttpRequest();
    xhpPost.onreadystatechange = function () {
        if(this.readyState === 4 && this.status == 200) {
            console.log((xhpPost.responseText));
            TreeInfoObj = JSON.parse(xhpPost.responseText);

            txt = txt + "<div class=\"treeInfo-header\"><table><thead class=\"treeTableHeader\"><tr><th>Tree ID</th> <th>Species</th> <th>City</th> <th>waterlevel</th> <th>Lastwatered</th><th>EDIT</th><th>DELETE</th></tr> </thead></table></div><table>"
            for(x in TreeInfoObj){
                txt = txt + "<tr><td>" + TreeInfoObj[x].id + "</td><td>"
                    + TreeInfoObj[x].type+ "</td><td>"
                    +TreeInfoObj[x].city + "</td><td>"
                    + TreeInfoObj[x].waterlevel + "</td><td>"
                    + TreeInfoObj[x].lastwatered + "</td><td>"
                    +   "<button  class=\"editBtn\" id =\""+ TreeInfoObj[x].id  + "\" onclick =\"EditTreeInfoPopup(this)\" value =\""+ TreeInfoObj[x].id +"\"> Edit</button></td><td>"
                    + "<button class=\"deleteBtn\"  id =\""+ TreeInfoObj[x].id + "\" onclick =\"DeleteTreeInfoPopup(this)\" value =\""+ TreeInfoObj[x].id +"\"> Delete</button></td></tr>";

            }
            txt = txt + "</table>"
            document.getElementById("tree-info").innerHTML = txt;
        }
    }
    var url = new URL("http://grybb-4.paas.hosted-by-previder.com/GRYBB/rest/viewData");
    xhpPost.open("GET", url, true);
    xhpPost.setRequestHeader("Accept",  "text/plain");
    xhpPost.send();
}
var deleteTreeID;
var editTreeID;
function EditTreeInfoPopup(e){
    editTreeID = document.getElementById(e.id).value;
    document.getElementById("editId").value = editTreeID;
    console.log(deleteTreeID);
    document.getElementById("editInfo").classList.toggle("active");
}

function confirmEdit(){
    editTree(editTreeID);
    document.getElementById("editInfo").classList.toggle("active");
    setTimeout(function(){
        window.location.reload(1);
    }, 50)
}


function CancelEditTreeInfo(){
    document.getElementById("editInfo").classList.toggle("active");
}

function confirmDelete(){
    deleteTree(deleteTreeID);
    document.getElementById("deleteInfo").classList.toggle("active");
    //window.location.reload(true);
    setTimeout(function(){
        window.location.reload(1);
    }, 50)
}

function DeleteTreeInfoPopup(e){
    deleteTreeID = document.getElementById(e.id).value;
    console.log(deleteTreeID);
    document.getElementById("deleteInfo").classList.toggle("active");

}
function CancelDeleteTreeInfo(treeId){
    document.getElementById("deleteInfo").classList.toggle("active");
}

function deleteTree(treeId) {
    let xhpPost = new XMLHttpRequest();
    xhpPost.onreadystatechange = function () {
        if(this.readyState === 4 && this.status == 200) {
            alert(xhpPost.responseText);
            TreeInfoObj = JSON.parse(xhpPost.responseText);
        }
    }
    var url = new URL("http://grybb-4.paas.hosted-by-previder.com/GRYBB/rest/viewData/delete/"+treeId);

    xhpPost.open("GET", url, true);
    xhpPost.setRequestHeader("Accept",  "text/plain");
    xhpPost.send();
}


function editTree(treeId){
    let type = document.getElementById("editSpeices").value.capitalize();
    let waterlevel = document.getElementById("editWaterLevel").value;
    let lat = document.getElementById("editLat").value;
    let lng = document.getElementById("editLng").value;
    let city = document.getElementById("city").value.capitalize();
    var xhpPost = new XMLHttpRequest();
    xhpPost.onreadystatechange = function () {
        if (this.readyState === 4 ) {
            console.log(xhpPost.responseText);
        }
    }
    var url = new URL("http://grybb-4.paas.hosted-by-previder.com/GRYBB/rest/viewData/edit/" + treeId);
    url.searchParams.set("type", type);
    url.searchParams.set("waterlevel",waterlevel);
    url.searchParams.set ("city",city);
    url.searchParams.set("lat",lat);
    url.searchParams.set("lng", lng);

    xhpPost.open("GET", url, true);
    xhpPost.setRequestHeader("Accept", "text/plain");
    xhpPost.send();
}

String.prototype.capitalize = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
}

