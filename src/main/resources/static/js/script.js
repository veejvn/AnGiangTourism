var map = L.map("map", {center: [10.030249, 105.772097], zoom: 17});
        L.tileLayer(
            "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
            {
                attribution: '&copy;<a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
            }
        ).addTo(map);

    function onEachFeature(features, layer) {
        if (features.properties && features.properties.name) {
            layer.bindPopup(features.properties.name);
        }
    }

    var layer2 = L.layerGroup().addTo(map);

    var url = "http://localhost:8080/api/data";

    $.getJSON(url, function (featureCollection) {
      featureCollection.features.forEach(feature => {
        feature.geometry = JSON.parse(feature.geometry);
      })
      L.geoJSON(featureCollection, {
        onEachFeature: onEachFeature
      }).addTo(layer2)
    });