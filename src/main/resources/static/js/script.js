var map = L.map("map", {center: [10.59041, 105.1932], zoom: 11});
        L.tileLayer(
            "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
            {
                attribution: '&copy;<a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
            }
        ).addTo(map);

    function onEachFeature(features, layer) {
        if (features.properties && features.properties.name) {
            layer.bindPopup(`
                <div class="card" style="width: 18rem;">
                    <img src="${features.properties.image}" class="card-img-top img-fluid" alt="Place Image" style="max-height: 200px; object-fit: cover;">
                    <div class="card-body">
                        <h5 class="card-title">${features.properties.name}</h5>
                        <p class="card-text"><strong>Địa chỉ:</strong> ${features.properties.address}</p>
                        <p class="card-text"><strong>Số điện thoại:</strong> ${features.properties.tel}</p>
                    </div>
                </div>
            `);
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

