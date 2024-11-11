//------------- Start Chọn địa điểm trên bản đồ
// Biến để kiểm tra xem có đang trong chế độ chọn vị trí trên bản đồ hay không
let isSelectingLocation = false; // Biến trạng thái chế độ chọn vị trí
let currentMarker = null; // Biến lưu trữ marker hiện tại

// Lắng nghe sự kiện click vào ô input "Vị trí tâm"
document.getElementById('centerInput').addEventListener('click', function() {
  // Khi click vào ô input, kích hoạt chế độ chọn điểm trên bản đồ
  isSelectingLocation = true;

  // Thay đổi con trỏ khi chọn điểm
  document.body.style.cursor = 'crosshair';  // Đổi con trỏ thành chữ thập

  // Xóa marker cũ nếu có
  if (currentMarker) {
    currentMarker.remove();
  }

  alert("Click vào bản đồ để chọn vị trí tâm!");
});

// Lắng nghe sự kiện click trên bản đồ để chọn vị trí
window.map.on('click', function(e) {
  if (isSelectingLocation) {
    const latitude = e.latlng.lat;
    const longitude = e.latlng.lng;

    // Cập nhật ô input với vị trí người dùng chọn
    document.getElementById('centerInput').value = `Lat: ${latitude}, Lng: ${longitude}`;

    // Di chuyển bản đồ đến vị trí người dùng đã chọn
    window.map.setView([latitude, longitude], 13);

    // Thêm marker vào vị trí người dùng chọn
    if (currentMarker) {
      currentMarker.remove();  // Xóa marker cũ nếu có
    }
    currentMarker = L.marker([latitude, longitude]).addTo(window.map);

    // Tắt chế độ chọn điểm trên bản đồ
    isSelectingLocation = false;

    // Khôi phục con trỏ lại bình thường sau khi chọn điểm
    document.body.style.cursor = 'default';
  }
});
//------------- End Chọn địa điểm trên bản đồ

//------------- Start Tìm kiếm và vẽ vòng tròn
document.getElementById('submitSearch').addEventListener('click', async function () {
    // Lấy các giá trị từ dropdown và input
    const categoryId = document.getElementById('categoryDropdown').value;
    const radius = parseFloat(document.getElementById('radiusSelect').value);
    const centerInput = document.getElementById('centerInput').value;
    console.log("category: "+ categoryId);
    console.log("radius: "+ radius);
    console.log("centerInput: "+ centerInput);
    // Kiểm tra nếu tâm, bán kính, và loại được chọn
    if (!categoryId || !radius || !centerInput) {
        alert("Vui lòng điền đầy đủ thông tin tìm kiếm.");
        return;
    }

    // Chuyển đổi tâm từ chuỗi thành tọa độ
    // Sử dụng biểu thức chính quy để tìm và tách giá trị Lat và Lng
        const latLngMatch = centerInput.match(/Lat:\s*([-+]?\d*\.?\d+),\s*Lng:\s*([-+]?\d*\.?\d+)/);
        if (!latLngMatch) {
            alert("Định dạng tọa độ tâm không hợp lệ. Hãy nhập theo định dạng 'Lat: [giá trị], Lng: [giá trị]'.");
            return;
        }

        // Chuyển đổi giá trị Lat và Lng từ chuỗi sang số
        const centerLat = parseFloat(latLngMatch[1]);
        const centerLng = parseFloat(latLngMatch[2])
        console.log("centerLat" + centerLat);
        console.log("centerLng" + centerLng);
    try {
        // Gửi yêu cầu đến API lấy các Place theo categoryId
        const response = await fetch(`/api/data/searchByCategoryId?categoryId=${categoryId}`);
        const places = await response.json();
        console.log("Places:", places); // In ra đối tượng places để kiểm tra
        // Khởi tạo bản đồ nếu chưa có
        if (!window.map) {
            window.map = L.map('map').setView([centerLat, centerLng], 13);
            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                maxZoom: 19,
                attribution: '© OpenStreetMap'
            }).addTo(window.map);
        }

        // Xóa các layer cũ (nếu có) để làm mới hiển thị
        if (window.mapCircle) {
            window.map.removeLayer(window.mapCircle);
        }
        if (window.placeMarkers) {
            window.placeMarkers.forEach(marker => window.map.removeLayer(marker));
        }
        window.placeMarkers = [];

        // Vẽ đường tròn phạm vi trên bản đồ
        window.mapCircle = L.circle([centerLat, centerLng], {
            color: 'blue',
            fillColor: '#30a3dc',
            fillOpacity: 0.3,
            radius: radius
        }).addTo(window.map);

        const customIcon = L.icon({
                            iconUrl: 'https://res.cloudinary.com/dxo7j3yib/image/upload/v1730696842/angiangtourism/images/kqqeohpjxe1ygiarfyak.png',
                            iconSize: [30, 30],
                            iconAnchor: [15, 30],
                            popupAnchor: [-3, -30]
                        });

        // Lọc các Place trong bán kính
        places.forEach(place => {
            const distance = window.map.distance([centerLat, centerLng], [place.lat, place.lon]);
            if (distance <= radius) {
                // Thêm marker cho Place trên bản đồ
                const marker = L.marker([place.lat, place.lon], { icon: customIcon }).addTo(window.map)
                    .bindPopup(`
                      <div class="card" style="width: 28rem;">
                                      <img src="${place.image}" class="card-img-top img-fluid" alt="Place Image" style="max-height: 200px; object-fit: cover;">
                                      <div class="card-body">
                                          <h5 class="card-title">${place.name}</h5>
                                          <p class="card-text"><strong>Địa chỉ:</strong> ${place.address}</p>
                                          <p class="card-text"><strong>Số điện thoại:</strong> ${place.hotLine}</p>
                                          <p class="card-text">${place.description}</p>
                                          <h6 class="card-text">${place.minPrice} - ${place.maxPrice}</h6>
                                      </div>
                                  </div>
                    `);
                window.placeMarkers.push(marker);
            }
        });

        // Điều chỉnh view của bản đồ để bao gồm đường tròn và các Place
        window.map.fitBounds(window.mapCircle.getBounds());
    } catch (error) {
        console.error("Lỗi khi lấy dữ liệu:", error);
        alert("Không thể lấy dữ liệu, vui lòng thử lại sau.");
    }
});
//------------- End Chọn địa điểm trên bản đồ
