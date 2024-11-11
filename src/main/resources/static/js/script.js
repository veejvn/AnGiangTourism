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
            <div class="card" style="width: 28rem;">
                <img src="${features.properties.image}" class="card-img-top img-fluid" alt="Place Image" style="max-height: 200px; object-fit: cover;">
                <div class="card-body">
                    <h5 class="card-title">${features.properties.name}</h5>
                    <p class="card-text"><strong>Địa chỉ:</strong> ${features.properties.address}</p>
                    <p class="card-text"><strong>Số điện thoại:</strong> ${features.properties.hotLine}</p>
                    <p class="card-text">${features.properties.description}</p>
                    <h6 class="card-text">${features.properties.minPrice} - ${features.properties.maxPrice}</h6>
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
    });
    L.geoJSON(featureCollection, {
        pointToLayer: function (feature, latlng) {
            // Tạo icon với hình ảnh từ category
            const markerIcon = L.icon({
                iconUrl: feature.properties.imageCategory || 'default-marker.png', // URL hình ảnh marker từ category
                iconSize: [30, 30], // Kích thước của marker
                iconAnchor: [15, 30], // Điểm neo
                popupAnchor: [-3, -30]
            });

            return L.marker(latlng, { icon: markerIcon });
        },
        onEachFeature: onEachFeature
    }).addTo(layer2);
});

// ===================================================Thêm đường viền từ angiang.json===========================================
// Biến để theo dõi trạng thái viền
let borderLayer; // Biến để lưu lớp viền
let borderVisible = false; // Trạng thái hiển thị viền

// Hàm thêm viền
function addBorder() {
    $.getJSON('/angiang.json', function (borderData) {
        borderLayer = L.geoJSON(borderData, {
            style: function () {
                return { color: 'red', weight: 2 }; // Màu đỏ cho đường viền
            }
        }).addTo(map);
    });
}

// Hàm xóa viền
function removeBorder() {
    if (borderLayer) {
        map.removeLayer(borderLayer);
        borderLayer = null; // Đặt lại biến borderLayer
    }
}

// Xử lý sự kiện cho nút
document.getElementById('toggleBorder').addEventListener('click', function () {
    if (borderVisible) {
        removeBorder(); // Nếu viền đang hiển thị, xóa nó
        this.innerHTML = '<i class="fa-solid fa-vector-square"></i>'; // Cập nhật nội dung nút
    } else {
        addBorder(); // Nếu viền không hiển thị, thêm nó
        this.innerHTML = '<i class="fa-solid fa-stop"></i>'; // Cập nhật nội dung nút
    }
    borderVisible = !borderVisible; // Đảo ngược trạng thái
});

window.map = map;
