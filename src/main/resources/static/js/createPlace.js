var map = L.map('map').setView([10.7769, 106.6959], 13); // Tọa độ khởi đầu
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
  }).addTo(map);

  var marker;

  map.on('click', function(e) {
      // Xóa marker cũ nếu có
      if (marker) {
          map.removeLayer(marker);
      }
      // Thêm marker mới
      marker = L.marker(e.latlng).addTo(map);
      // Lưu tọa độ vào trường hidden
      document.getElementById('geometry').value = 'POINT(' + e.latlng.lng + ' ' + e.latlng.lat + ')';
  });