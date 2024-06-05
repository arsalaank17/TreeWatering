var slider = document.getElementById("myrange");
var output = document.getElementById("newValue");
var outputValue = output.innerHTML;




function getNewValue() {
    var newvalue = slider.value;
    return newvalue;
}
function confirmchange() {
    console.log("new value is:" + getNewValue());
    updateWaterLevel(getNewValue());
    document.getElementById("changewlpopup").classList.toggle("active");
    setTimeout(function(){
        window.location.reload(1);
    }, 20);
    slider.value = 0;
}

slider.oninput = function () {
    outputValue = slider.value;
    output.innerHTML = "new value: " + this.value;
}


function updateWaterLevel(newWaterLevel) {

    var xhpPost = new XMLHttpRequest();
    let lat = latitute;
    let lng = longitute;
    xhpPost.onreadystatechange = function () {
        if (this.readyState === 4 ) {
            console.log(xhpPost.responseText);

        }
    }
    var url = new URL("http://grybb-4.paas.hosted-by-previder.com/GRYBB/rest/home/" + newWaterLevel);
    url.searchParams.set("lat", lat);
    url.searchParams.set("lng", lng);

    xhpPost.open("GET", url, true);
    xhpPost.setRequestHeader("Accept", "text/plain");
    xhpPost.send();
}

