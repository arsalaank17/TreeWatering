var map = L.map('map').setView([52.3, 6.9], 12);
var osm = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
});
osm.addTo(map);
var AllMarkersJsonString;
var AllMarkersJsonObject = [];
var markersOnMap = [];


getAllMarkers();

function getAllMarkers() {
    var xmlhttpPost = new XMLHttpRequest();
    xmlhttpPost.onreadystatechange = function () {
        if(this.readyState === 4) {
            console.log(xmlhttpPost.responseText);
            AllMarkersJsonString = JSON.parse(xmlhttpPost.responseText);
            PutMakersWithDatabaseResult(AllMarkersJsonString);
        } else {
        }
    }
    xmlhttpPost.open("GET","http://grybb-4.paas.hosted-by-previder.com/GRYBB/rest/home", true);
    xmlhttpPost.setRequestHeader("Accept", "text/plain");
    xmlhttpPost.send();
}



function createMakerFromJSONObject(jsonString) {
    return {
        coords: {lat: jsonString.lat, lng: jsonString.lng},
        city: jsonString.city,
        id : jsonString.id,
        type: jsonString.type,
        lastwatered: jsonString.lastwatered,
        waterlevel: jsonString.waterlevel,
        btn: '<button id ="changewater"  onclick = "changeWLPopup();">change waterlevel</button>'
    };
}
function PutMakersWithDatabaseResult(message) {

    for (let i = 0; i < message.length; i++) {
        var marker = createMakerFromJSONObject(message[i]);
        AllMarkersJsonObject.push(marker);
        let addMarker1 = addMarker;
        addMarker1(marker);
    }

}
function removeAllMarkersOnMap(){
    for (let i = 0; i < markersOnMap.length; i++) {
        map.removeLayer(markersOnMap[i]);
    }
}


function addMarker(markers) {
    var redCheckBox = document.getElementById("redTree");

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
    var popup = singleMarker.bindPopup('<p>'+'city: '+markers.city + '</p>'
        +'<p>'+'id: '+markers.id + '</p>'
        +'<p>'+'type: ' +markers.type+ '</p>'
        +'<p>'+'lastwatered: '+markers.lastwatered + '</p>'
        +'<p>'+'waterlevel: '+ markers.waterlevel+'</p>'+markers.btn).openPopup()
    markersOnMap.push(singleMarker);
    popup.addTo(map).on('click',getLat);
    popup.addTo(map).on('click',getLng);

}

var latitute;
var longitute;

function getLat(e) {
    lati = e.target.getLatLng().lat;
    // longi = e.target.getLatLng().lng;

    latitute = lati;
    console.log(latitute);
}

function getLng(e) {
    longi = e.target.getLatLng().lng;
    longitute = longi;
    console.log(longitute);
}



var slider = document.getElementById("myrange");
var output = document.getElementById("value");

slider.oninput = function() {
    output.innerHTML = this.value;
}


function changeWLPopup() {
    document.getElementById("changewlpopup").classList.toggle("active");
    var slider = document.getElementById("myrange");
    var output = document.getElementById("newValue");
    var outputValue = output.innerHTML;

    slider.oninput = function() {
        output.innerHTML = "new value: " +this.value;
        outputValue= slider.value;
    }

}