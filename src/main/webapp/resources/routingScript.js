
var map = L.map('map').setView([52.35,6.8], 12);
var osm = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
});
osm.addTo(map);

//routingLayer
var routingLayer = L.geoJSON().addTo(map);
var TreeClickLayer = L.geoJSON();
var databaseTreesLayer = L.geoJSON().addTo(map);

var selectedTrees = [];
var selectedTreesCoords = [];

var currentLocation = [];

var AllMarkersJsonString;
var AllMarkersJsonObject = [];


var myStyle = {
    "color": "#ffffff",
    "weight": 5,
    "opacity": 0.8
};
//every elementArray has as first element the height/lat and second the width/lng
var coords = [[52.21412550697374, 6.006091214497894],[52.23960483029934, 6.849922687031806],[52.2560483029934, 6.86222687031806]];

addRouteWithDestinatedCoord(coords);

console.log("Done AddPoint to ghRouting");



//TODO get current location of the user


function addRouteWithDestinatedCoord(coordsArray) {  //first one is height/lat, second one is width/lng

    var ghRouting = new GraphHopper.Routing({
        key: "697cf432-c286-4495-b907-6695b627abcd",
        vehicle: "car",
        elevation: false
    });

    if(coordsArray.length === 1){
        ghRouting.addPoint(new GHInput(52.2560483029934, 6.86222687031806));
    }

    for (var i = 0; i < coordsArray.length; i++) {
        ghRouting.addPoint(new GHInput(coordsArray[i][0], coordsArray[i][1]));
    }
    ghRouting.doRequest()
        .then(function(json) {
            // Add your own result handling herez
            console.log(json);

            var routeLine = json.paths[0].points; //contains the line of route
            console.log(routeLine);
            routingLayer.addData(routeLine, {Style: myStyle});

            //only if succeeded, add markers for the destinated points
            for (var i = 0; i < coordsArray.length; i++) {
                L.marker({lat:coordsArray[i][0], lng: coordsArray[i][1]}).addTo(map );
            }
        })
        .catch(function(err) {
            console.error(err.message);
        });
}

function CreateRouteOfCertainTrees() {
    routingLayer.clearLayers();
    var xmlhttpPost = new XMLHttpRequest();
    xmlhttpPost.onreadystatechange = function () {
        if(this.readyState === 4) {
            var coordsPoints = [];
            AllMarkersJsonString = JSON.parse(xmlhttpPost.responseText);
            for (let i = 0; i < AllMarkersJsonString.length; i++) {
                var lngAndLat = [];
                lngAndLat.push(AllMarkersJsonString[i].lat);
                lngAndLat.push(AllMarkersJsonString[i].lng);
                coordsPoints.push(lngAndLat);
            }
            console.log(coordsPoints);
            addRouteWithDestinatedCoord(coordsPoints);
        }
    }
    xmlhttpPost.open("GET","http://grybb-4.paas.hosted-by-previder.com/GRYBB/rest/home/",true);
    xmlhttpPost.setRequestHeader("Accept", "text/plain");
    xmlhttpPost.send();
}

function CreateRouteOfSelectTrees() {
    routingLayer.clearLayers();
    //check if selectedTreescoords is not zero or 1
    if(selectedTreesCoords.length >0) {
        routingLayer.clearLayers();
        map.addLayer(routingLayer);
        addRouteWithDestinatedCoord(selectedTreesCoords)
        selectedTreesCoords = [];
        selectedTrees = [];

    } else {
        alert("Choose atleast one Tree!")
    }

}

function SelectTreesOnMap() {
    var filterDiv = document.getElementById("filter");
    var selectDiv = document.getElementById("selectTrees");


    if (map.hasLayer(routingLayer)) {
        console.log("delete routingLayer + databaseLayer and add TreeClickLayer ")
        map.removeLayer(routingLayer);
        map.removeLayer(databaseTreesLayer);
        map.addLayer(TreeClickLayer);
        filterDiv.style.display = "none";
        selectDiv.style.display = "inline";
    } else {
        console.log("add routingLayer + databaseLayer and remove TreeClickLayer")
        map.addLayer(routingLayer);
        map.addLayer(databaseTreesLayer);
        map.removeLayer(TreeClickLayer);
        selectedTrees = [];
        selectedTreesCoords = [];
        filterDiv.style.display = "inline";
        selectDiv.style.display = "none";
    }
}

function ClickOnTree(e) {
    var coordsTree = e.latlng;
    console.log("You clicked on a tree with coords are: " + coordsTree.toString());
    var coordsArray = [];
    coordsArray.push(e.latlng.lat);
    coordsArray.push(e.latlng.lng);
    if(selectedTrees.includes(coordsTree)) {
        console.log("delete Selectedtree at " +coordsTree.toString() )
        var index = selectedTrees.indexOf(coordsTree);
        selectedTrees.splice(index, 1);
        selectedTreesCoords.splice(index, 1);
    } else {
        selectedTrees.push(coordsTree);
        selectedTreesCoords.push(coordsArray);
    }

    console.log(selectedTreesCoords.toString())
}


filterAndReceiveTreesDataJSONString();



function filterAndReceiveTreesDataJSONString() { //filter is the number for the filter 0,1,2,3
    var xmlhttpPost = new XMLHttpRequest();
    xmlhttpPost.onreadystatechange = function () {
        if(this.readyState === 4) {
            AllMarkersJsonString = JSON.parse(xmlhttpPost.responseText);
            PutMakersWithDatabaseResult(AllMarkersJsonString);
        } else {
        }
    }
    xmlhttpPost.open("GET","http://grybb-4.paas.hosted-by-previder.com/GRYBB/rest/home/",true);
    xmlhttpPost.setRequestHeader("Accept", "text/plain")
    xmlhttpPost.send();
}

function createMakerFromJSONObject(jsonString) {
    return {
        coords: {lat: jsonString.lat, lng: jsonString.lng},
        content: '<h4> city: ' + jsonString.city + ' </h4><p>id: ' + jsonString.id + '<p>' +
            ' <p>Boombeeld: ' + jsonString.type + ' </p><p>Gewijzigd Op: ' + jsonString.lastwatered +' </p>',
        waterlevel: jsonString.waterlevel,
        id: jsonString.id
    };
}
function PutMakersWithDatabaseResult(message) {

    for (let i = 0; i < message.length; i++) {
        var marker = createMakerFromJSONObject(message[i]);
        AllMarkersJsonObject.push(marker);
        addMarker(marker);
        addMarkerClickTree(marker);
    }

}

function addMarkerClickTree(markers) {
    if (markers.waterlevel < 4 && markers.waterlevel > 0) {

        var myIcon = L.icon({
            iconUrl: 'http://grybb-4.paas.hosted-by-previder.com/GRYBB/resources/image/redtree.png',
            iconSize: [20, 20],
        });
    }
    if (markers.waterlevel < 7 && markers.waterlevel >= 4) {
        var myIcon = L.icon({
            iconUrl: 'http://grybb-4.paas.hosted-by-previder.com/GRYBB/resources/image/yellowtree.png',
            iconSize: [20, 20],
        });
    }
    if (markers.waterlevel <= 10 && markers.waterlevel >= 7) {
        var myIcon = L.icon({
            iconUrl: 'http://grybb-4.paas.hosted-by-previder.com/GRYBB/resources/image/greentree.png',
            iconSize: [20, 20],
        });
    }
    var singleMarker = L.marker(markers.coords, { icon: myIcon, draggable: false });
    singleMarker.addTo(TreeClickLayer).on('click', ClickOnTree);

}



function addMarker(markers) {

    if (markers.waterlevel < 4 && markers.waterlevel > 0) {

        var myIcon = L.icon({
            iconUrl: 'http://grybb-4.paas.hosted-by-previder.com/GRYBB/resources/image/redtree.png',
            iconSize: [20, 20],
        });
    }
    if (markers.waterlevel < 7 && markers.waterlevel >= 4) {
        var myIcon = L.icon({
            iconUrl: 'http://grybb-4.paas.hosted-by-previder.com/GRYBB/resources/image/yellowtree.png',
            iconSize: [20, 20],
        });
    }
    if (markers.waterlevel <= 10 && markers.waterlevel >= 7) {
        var myIcon = L.icon({
            iconUrl: 'http://grybb-4.paas.hosted-by-previder.com/GRYBB/resources/image/greentree.png',
            iconSize: [20, 20],
        });
    }
    var singleMarker = L.marker(markers.coords, { icon: myIcon, draggable: false });
    var popup = singleMarker.bindPopup(markers.content + 'waterlevel:' + markers.waterlevel + '\n').openPopup()
    popup.addTo(databaseTreesLayer);

}
