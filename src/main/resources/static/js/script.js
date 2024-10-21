var map = L.map("map", {center: [10.030249, 105.772097], zoom: 17});
        L.tileLayer(
            "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
            {
                attribution: '&copy;<a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
            }
        ).addTo(map);



    var url = "http://localhost:8080/geojson"; // Đường dẫn tới API
    $.getJSON(url, function(data) {
        L.geoJSON(data).addTo(map);
    }).fail(function() {
    console.error("Error loading GeoJSON data");
    });