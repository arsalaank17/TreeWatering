
var FilterQueryWLStr;
var FilterQueryTypeStr;
var FilterQueryCityStr;


function handleWlCheckbox()  {
    var array=[];
    var markedCheckbox = document.getElementsByName('filter-by-wl');
    for (var checkbox of markedCheckbox) {
        if (checkbox.checked){
            array.push(checkbox.value);
        }
    }
    if(array.length>0){
        FilterQueryWLStr = array.join("&");
        filterByWaterlevel(FilterQueryWLStr);
    }else {
        FilterQueryWLStr = "NoQuery";

    }
    filterByWaterlevel(FilterQueryWLStr);
}
function handleTypeCheckbox () {
    var array=[];
    var markedCheckbox = document.getElementsByName('filter-by-type');
    for (var checkbox of markedCheckbox) {
        if (checkbox.checked){
            array.push(checkbox.value);
        }
    }
    if(array.length>0){
        FilterQueryTypeStr = array.join("&");
        filterByType(FilterQueryTypeStr);
    }else{
        FilterQueryTypeStr ="NoQuery";

    }
    filterByType(FilterQueryTypeStr);
}

function handleCityCheckbox() {
    var array=[];
    var markedCheckbox = document.getElementsByName('filter-by-city');
    for (var checkbox of markedCheckbox) {
        if (checkbox.checked) {
            array.push(checkbox.value);
        }
    }
    if(array.length>0){
        FilterQueryCityStr = array.join("&");


    }else{
        FilterQueryCityStr ="NoQeury";

    }
    console.log(FilterQueryCityStr);
    filterByCity(FilterQueryCityStr);
}

function filterByWaterlevel(filterWaterlevel) {
    var xhpPost = new XMLHttpRequest();

    xhpPost.onreadystatechange = function () {
        if (this.readyState === 4 & this.status == 200) {
            console.log(xhpPost.responseText);
            removeAllMarkersOnMap();
            AllMarkersJsonString = JSON.parse(xhpPost.responseText);
            PutMakersWithDatabaseResult(AllMarkersJsonString);
        }
    }
    var url = new URL("http://grybb-4.paas.hosted-by-previder.com/GRYBB/rest/home/waterlevel/" + filterWaterlevel);
    xhpPost.open("GET", url, true);
    xhpPost.setRequestHeader("Accept", "text/plain");
    xhpPost.send();
}

function filterByType(filterType) {
    var xhpPost = new XMLHttpRequest();
    xhpPost.onreadystatechange = function () {
        if (this.readyState === 4 & this.status == 200) {
            console.log(xhpPost.responseText);
            removeAllMarkersOnMap();
            AllMarkersJsonString = JSON.parse(xhpPost.responseText);
            PutMakersWithDatabaseResult(AllMarkersJsonString);
        }
    }
    var url = new URL("http://grybb-4.paas.hosted-by-previder.com/GRYBB/rest/home/type/" + filterType);
    xhpPost.open("GET", url, true);
    xhpPost.setRequestHeader("Accept", "text/plain");
    xhpPost.send();
}

function filterByCity(filterCity) {
    var xhpPost = new XMLHttpRequest();
    xhpPost.onreadystatechange = function () {
        if (this.readyState === 4 & this.status == 200) {
            console.log(xhpPost.responseText);
            AllMarkersJsonString = JSON.parse(xhpPost.responseText);
            removeAllMarkersOnMap();
            PutMakersWithDatabaseResult(AllMarkersJsonString);
        }
    }
    var url = new URL("http://grybb-4.paas.hosted-by-previder.com/GRYBB/rest/home/city/" + filterCity);
    xhpPost.open("GET", url, true);
    xhpPost.setRequestHeader("Accept", "text/plain");
    xhpPost.send();
}

function  searchByID(){
    let id = document.getElementById("search").value;
    var xhpPost = new XMLHttpRequest();
    xhpPost.onreadystatechange = function () {
        if (this.readyState === 4 & this.status == 200) {
            console.log(xhpPost.responseText);
            AllMarkersJsonString = JSON.parse(xhpPost.responseText);
            removeAllMarkersOnMap();
            PutMakersWithDatabaseResult(AllMarkersJsonString);
        }
    }
    var url = new URL("http://grybb-4.paas.hosted-by-previder.com/GRYBB/rest/home/search");
    url.searchParams.set("id",id);
    xhpPost.open("GET", url, true);
    xhpPost.setRequestHeader("Accept", "text/plain");
    xhpPost.send();
}



