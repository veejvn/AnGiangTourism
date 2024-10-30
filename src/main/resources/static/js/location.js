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