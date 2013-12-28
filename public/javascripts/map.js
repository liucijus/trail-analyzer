var map;
var trailLines = [];

function initialize() {
    var mapOptions = {
        zoom: 12,
        center: new google.maps.LatLng(54.687272, 25.279657),
        disableDefaultUI: true
    };
    map = new google.maps.Map(document.getElementById('map'), mapOptions);
}

google.maps.event.addDomListener(window, 'load', initialize);

var selectFile = function () {
    $("#file-upload-input").click();
};

var handleFiles = function (files) {
    if (files) {
        $("#upload-file").removeAttr("disabled");
        $("#selected-file").html($("#file-upload-input").val());
    }
};

var uploadFile = function (uploadUrl) {
    var formData = new FormData();
    formData.append('file', $("#file-upload-input")[0].files[0]);
    $.ajax({
        url: uploadUrl,
        type: 'post',
        success: completeHandler = successHandler,
        error: errorHandler = failureHandler,
        data: formData,
        cache: false,
        contentType: false,
        processData: false
    }, 'json');
};

var clearMapTracks = function () {
    for (var i = 0; i < trailLines.length; i++) {
        trailLines[i].setMap(null);
    }

    trailLines = [];
};

var createPolyLine = function (coordinates) {
    return new google.maps.Polyline({
        path: coordinates,
        geodesic: true,
        strokeColor: '#FF0000',
        strokeOpacity: 2.0,
        strokeWeight: 4
    });
};

var createCoordinates = function (trackData) {
    var coordinates = [];
    for (var i = 0; i < trackData.length; i++) {
        var point = trackData[i];
        coordinates[i] = new google.maps.LatLng(point.lat, point.lng);
    }
    return coordinates;
};

var drawOnMap = function (trackData) {
    var coordinates = createCoordinates(trackData);
    var trailPath = createPolyLine(coordinates);
    trailPath.setMap(map);
    trailLines.push(trailPath);
};


var successHandler = function (data) {
    clearMapTracks();
    for (var j = 0; j < data.length; j++) {
        drawOnMap(data[j]);
    }
};

var failureHandler = function () {
    alert("failure");
};