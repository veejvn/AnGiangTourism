// Khởi tạo bản đồ
var map = L.map('map').setView([10.680175, 105.069052], 13); // Đặt mặc định vị trí ban đầu

// Thêm layer bản đồ
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
}).addTo(map);

// Xử lý sự kiện submit form
document.getElementById('locationForm').addEventListener('submit', function (e) {
    e.preventDefault(); // Ngăn chặn hành vi mặc định của form
    var locationName = document.getElementById('locationInput').value; // Lấy giá trị nhập vào

    // Sử dụng Nominatim để tìm kiếm địa điểm
    fetch(`https://nominatim.openstreetmap.org/search?q=${encodeURIComponent(locationName)}&format=json`)
        .then(response => response.json())
        .then(data => {
            if (data.length > 0) {
                var location = data[0];
                var lat = location.lat;
                var lon = location.lon;

                // Cập nhật vị trí bản đồ
                map.setView([lat, lon], 13);

                // Xóa marker cũ nếu có
                if (window.marker) {
                    map.removeLayer(window.marker);
                }

                // Thêm marker mới
                window.marker = L.marker([lat, lon]).addTo(map).bindPopup(location.display_name).openPopup();
            } else {
                alert('Không tìm thấy địa điểm này.');
            }
        })
        .catch(error => {
            console.error('Có lỗi xảy ra:', error);
        });
});
