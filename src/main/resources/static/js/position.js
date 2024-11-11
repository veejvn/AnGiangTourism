let marker; // Biến để lưu marker
let locationEnabled = false; // Trạng thái hiển thị vị trí

// Hàm lấy vị trí hiện tại và tạo/xóa marker
function toggleLocation(map) {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                const lat = position.coords.latitude;
                const lon = position.coords.longitude;



                const customIcon = L.icon({
                    iconUrl: 'https://res.cloudinary.com/dxo7j3yib/image/upload/v1730696842/angiangtourism/images/kqqeohpjxe1ygiarfyak.png',
                    iconSize: [30, 30],
                    iconAnchor: [15, 30],
                    popupAnchor: [-3, -30]
                });

                // Nếu vị trí đã bật thì xóa marker, nếu chưa thì thêm marker
                if (locationEnabled) {
                    if (marker) {
                        map.removeLayer(marker);
                        marker = null;
                    }
                    locationEnabled = false;
                } else {
                    marker = L.marker([lat, lon], { icon: customIcon }).addTo(map)
                        .bindPopup("Vị trí hiện tại").openPopup();
                    map.setView([lat, lon], 13);
                    locationEnabled = true;
                }
                 // Cập nhật ô input với giá trị vị trí hiện tại (vĩ độ và kinh độ)
                                 document.getElementById('centerInput').value = `Lat: ${lat}, Lng: ${lon}`;
            },
            () => {
                alert("Không thể lấy vị trí hiện tại.");
            }
        );
    } else {
        alert("Trình duyệt của bạn không hỗ trợ Geolocation.");
    }
}

// Sự kiện click cho nút để bật/tắt vị trí
document.getElementById('toggleLocationBtn').addEventListener('click', function() {
    toggleLocation(map);

    // Cập nhật icon của nút
    if (locationEnabled) {
        this.innerHTML = '<i class="fa-solid fa-map-location-dot fa-beat-fade"></i>';
    } else {
        this.innerHTML = '<i class="fa-solid fa-map-location fa-beat-fade"></i>';
    }
});
