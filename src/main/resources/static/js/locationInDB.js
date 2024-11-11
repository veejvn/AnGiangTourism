async function searchLocation() {
    const query = document.getElementById('searchInput').value;
    if (!query) {
        alert('Vui lòng nhập tên địa điểm.');
        return;
    }

    const url = `/api/data/search?name=${encodeURIComponent(query)}`;

    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error('Không tìm thấy địa điểm nào.');
        }

        const places = await response.json();
        if (places.length === 0) {
            alert('Không tìm thấy địa điểm nào.');
            return;
        }

        // Xóa tất cả các marker cũ trên bản đồ (nếu cần)
        map.eachLayer((layer) => {
            if (layer instanceof L.Marker) {
                map.removeLayer(layer);
            }
        });

        const customIcon = L.icon({
            iconUrl: 'https://res.cloudinary.com/dxo7j3yib/image/upload/v1730693981/angiangtourism/images/zzmptixwfb25nlshxh0k.png', // Đường dẫn đến hình ảnh icon
            iconSize: [30, 30],
            iconAnchor: [15, 30],
            popupAnchor: [-3, -30]
        });

        // Lặp qua tất cả các địa điểm và thêm marker
        places.forEach(place => {
            const lat = place.lat;
            const lon = place.lon;

            // Thêm marker vào bản đồ
            L.marker([lat, lon], { icon: customIcon }).addTo(map)
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
                `)
                .openPopup();

            // Di chuyển mượt mà đến vị trí tìm thấy
            map.flyTo([lat, lon], 15, { animate: true, duration: 5 }); // Thay đổi độ cao và thời gian nếu cần
        });

        // Nếu muốn zoom ra để hiển thị tất cả các địa điểm
        if (places.length > 0) {
            const bounds = L.latLngBounds(places.map(place => [place.lat, place.lon]));
            map.fitBounds(bounds);
        }
    } catch (error) {
        console.error('Lỗi khi tìm kiếm địa điểm:', error);
        alert(error.message);
    }
}

document.getElementById('searchButton').addEventListener('click', searchLocation);
