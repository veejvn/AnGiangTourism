//start Chọn điểm đến và đi
let isSelectingFrom = false; // Trạng thái chọn điểm bắt đầu
let isSelectingTo = false; // Trạng thái chọn điểm đến
let fromMarker = null; // Marker cho điểm bắt đầu
let toMarker = null; // Marker cho điểm đến

// Lắng nghe sự kiện click vào ô input "điểm đi"
document.getElementById('fromInput').addEventListener('click', function() {
  isSelectingFrom = true;
  isSelectingTo = false; // Tắt chế độ chọn điểm đến
  document.body.style.cursor = 'crosshair';

  // Xóa marker cũ cho điểm bắt đầu nếu có
  if (fromMarker) {
    fromMarker.remove();
  }
  alert("Click vào bản đồ để chọn Điểm bắt đầu!");
});

// Lắng nghe sự kiện click vào ô input "điểm đến"
document.getElementById('toInput').addEventListener('click', function() {
  isSelectingTo = true;
  isSelectingFrom = false; // Tắt chế độ chọn điểm bắt đầu
  document.body.style.cursor = 'crosshair';

  // Xóa marker cũ cho điểm đến nếu có
  if (toMarker) {
    toMarker.remove();
  }
  alert("Click vào bản đồ để chọn Điểm đến!");
});

// Lắng nghe sự kiện click trên bản đồ để chọn vị trí
window.map.on('click', function(e) {
  const latitude = e.latlng.lat;
  const longitude = e.latlng.lng;

  if (isSelectingFrom) {
    document.getElementById('fromInput').value = `Lat: ${latitude}, Lng: ${longitude}`;
    if (fromMarker) {
      fromMarker.remove();
    }
    fromMarker = L.marker([latitude, longitude]).addTo(window.map);

    isSelectingFrom = false; // Tắt chế độ chọn
    document.body.style.cursor = 'default';
  } else if (isSelectingTo) {
    document.getElementById('toInput').value = `Lat: ${latitude}, Lng: ${longitude}`;
    if (toMarker) {
      toMarker.remove();
    }
    toMarker = L.marker([latitude, longitude]).addTo(window.map);

    isSelectingTo = false; // Tắt chế độ chọn
    document.body.style.cursor = 'default';
  }
});
//End Chọn điểm đến và đi

// Lắn nghe sự kiên Xử lý tìm đường đi
document.getElementById('findDirectionBtn').addEventListener('click', async function(){
    const fromInput = document.getElementById('fromInput').value;
    const toInput = document.getElementById('toInput').value;
    console.log("fromInput: " + fromInput);
    console.log("toInput: " + toInput);
    if(!fromInput || !toInput)
    {
        alert("Vui lòng chọn đầy đủ thông tin!");
        return;
    }

    const latLngFromInput = fromInput.match(/Lat:\s*([-+]?\d*\.?\d+),\s*Lng:\s*([-+]?\d*\.?\d+)/);
            if (!latLngFromInput) {
                alert("Định dạng tọa độ tâm không hợp lệ. Hãy nhập theo định dạng 'Lat: [giá trị], Lng: [giá trị]'.");
                return;
            }

            // Chuyển đổi giá trị Lat và Lng từ chuỗi sang số
            const fromLat = parseFloat(latLngFromInput[1]);
            const fromLng = parseFloat(latLngFromInput[2])
            console.log("fromLat" + fromLat);
            console.log("fromLng" + fromLng);

    const latLngToInput = toInput.match(/Lat:\s*([-+]?\d*\.?\d+),\s*Lng:\s*([-+]?\d*\.?\d+)/);
                if (!latLngToInput) {
                    alert("Định dạng tọa độ tâm không hợp lệ. Hãy nhập theo định dạng 'Lat: [giá trị], Lng: [giá trị]'.");
                    return;
                }

                // Chuyển đổi giá trị Lat và Lng từ chuỗi sang số
                const toLat = parseFloat(latLngToInput[1]);
                const toLng = parseFloat(latLngToInput[2])
                console.log("toLat" + toLat);
                console.log("toLng" + toLng);

    calculateRoute(fromLat, fromLng, toLat, toLng);


})

// hàm tìm đường đi
function calculateRoute(startLat, startLng, endLat, endLng){
    // Kiểm tra nếu chưa khởi tạo bản đồ
            if (!window.map) {
                window.map = L.map('map').setView([centerLat, centerLng], 13);

                // Thêm lớp tile từ OpenStreetMap
                L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                    maxZoom: 19,
                    attribution: '© OpenStreetMap'
                }).addTo(window.map);
            }

            // Khởi tạo các biểu tượng cho điểm bắt đầu và điểm kết thúc
            const startIcon = L.icon({
                iconUrl: 'https://res.cloudinary.com/dxo7j3yib/image/upload/v1731573017/angiangtourism/images/kovsu7hnzrpeurq0cp1w.png',
                iconSize: [40, 40],
                iconAnchor: [15, 30],
                popupAnchor: [-3, -30]
            });

            const endIcon = L.icon({
                iconUrl: 'https://res.cloudinary.com/dxo7j3yib/image/upload/v1731573025/angiangtourism/images/wff12k9t071mmhk90eco.png',
                iconSize: [40, 40],
                iconAnchor: [15, 30],
                popupAnchor: [-3, -30]
            });

            // Xóa các marker cũ nếu có
            window.map.eachLayer(function (layer) {
                if (layer instanceof L.Marker) {
                    window.map.removeLayer(layer);
                }
            });

            // Thêm marker cho điểm bắt đầu và điểm đích với biểu tượng custom
            const startMarker = L.marker([startLat, startLng], { icon: startIcon }).addTo(window.map)
                .bindPopup("Điểm bắt đầu")
                .openPopup();

            const endMarker = L.marker([endLat, endLng], { icon: endIcon }).addTo(window.map)
                .bindPopup("Điểm đích");

            // Khởi tạo tính năng chỉ đường với các điểm bắt đầu và kết thúc
            L.Routing.control({
                waypoints: [
                    L.latLng(startLat, startLng),
                    L.latLng(endLat, endLng)
                ],
                routeWhileDragging: true,
                createMarker: function() { return null; }
            }).addTo(window.map);
}