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

    // Hàm tìm kiếm địa điểm
    async function searchLocation() {
        const query = document.getElementById('searchInput').value;
        if (!query) {
            alert('Vui lòng nhập tên địa điểm.');
            return;
        }

        const url = `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(query)}`;

        try {
            const response = await fetch(url);
            const results = await response.json();

            if (results.length > 0) {
                const firstResult = results[0];
                const lat = firstResult.lat;
                const lon = firstResult.lon;

                // Cập nhật bản đồ đến địa điểm tìm thấy
                map.setView([lat, lon], 15); // Thay đổi mức zoom nếu cần

                // Thêm marker vào địa điểm tìm thấy
                L.marker([lat, lon]).addTo(map).bindPopup(firstResult.display_name).openPopup();
            } else {
                alert('Không tìm thấy địa điểm nào.');
            }
        } catch (error) {
            console.error('Lỗi khi tìm kiếm địa điểm:', error);
            alert('Đã có lỗi xảy ra khi tìm kiếm địa điểm.');
        }
    }

    // Thêm sự kiện click cho nút tìm kiếm
    document.getElementById('searchButton').addEventListener('click', searchLocation);