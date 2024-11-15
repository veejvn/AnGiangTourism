

    var drawnItems = new L.FeatureGroup();
    window.map.addLayer(drawnItems);

    var drawControl = new L.Control.Draw({
        edit: {
            featureGroup: drawnItems
        },
        draw: {
            marker: false,
            polygon: false,
            polyline: false,
            rectangle: true,
            circle: true
        }
    });

    window.map.addControl(drawControl);

    // Lắng nghe sự kiện vẽ polygon
    window.map.on('draw:created', function(event) {
        var layer = event.layer;
        drawnItems.addLayer(layer);

        // Lưu polygon vào localStorage
        var polygons = JSON.parse(localStorage.getItem('polygons')) || [];
        polygons.push(layer.toGeoJSON());
        localStorage.setItem('polygons', JSON.stringify(polygons));

        // Gắn sự kiện cho polygon để hiển thị dấu x khi di chuột vào
        layer.on('mouseover', function() {
            var deleteIcon = L.divIcon({
                className: 'delete-icon',
                html: 'X',
                iconSize: [20, 20]
            });
            var deleteMarker = L.marker(layer.getBounds().getCenter(), {
                icon: deleteIcon
            }).addTo(window.map);

            // Khi người dùng click vào dấu "X", xóa polygon
            deleteMarker.on('click', function() {
                drawnItems.removeLayer(layer);

                // Xóa polygon khỏi localStorage
                var polygons = JSON.parse(localStorage.getItem('polygons')) || [];
                var index = polygons.findIndex(p => p.id === layer.toGeoJSON().id);
                if (index !== -1) {
                    polygons.splice(index, 1);
                }
                localStorage.setItem('polygons', JSON.stringify(polygons));
                window.map.removeLayer(deleteMarker); // Xóa dấu "X" khi đã xóa polygon
            });
        });
    });

    // Khi reload trang, vẽ lại các polygon đã lưu trong localStorage
    window.onload = function() {
        var polygons = JSON.parse(localStorage.getItem('polygons')) || [];
        polygons.forEach(function(polygon) {
            L.geoJSON(polygon, {
                onEachFeature: function (feature, layer) {
                    // Gắn sự kiện cho polygon khi di chuột vào
                    layer.on('mouseover', function() {
                        var deleteIcon = L.divIcon({
                            className: 'delete-icon',
                            html: 'X',
                            iconSize: [20, 20]
                        });
                        var deleteMarker = L.marker(layer.getBounds().getCenter(), {
                            icon: deleteIcon
                        }).addTo(window.map);

                        // Khi click vào dấu "X", xóa polygon
                        deleteMarker.on('click', function() {
                            drawnItems.removeLayer(layer);

                            // Xóa polygon khỏi localStorage
                            var polygons = JSON.parse(localStorage.getItem('polygons')) || [];
                            var index = polygons.findIndex(p => p.id === layer.toGeoJSON().id);
                            if (index !== -1) {
                                polygons.splice(index, 1);
                            }
                            localStorage.setItem('polygons', JSON.stringify(polygons));
                            window.map.removeLayer(deleteMarker); // Xóa dấu "X"
                        });
                    });
                }
            }).addTo(drawnItems);
        });
    };

    // Kích hoạt nút vẽ hình
    document.getElementById("drawPolygonBtn").addEventListener("click", function() {
        window.map.addControl(drawControl); // Mở tính năng vẽ polygon
    });