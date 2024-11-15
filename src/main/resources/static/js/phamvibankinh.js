    document.getElementById('toggleSidebar').addEventListener('click', function() {
        document.getElementById('sidebar').classList.add('show');
        document.getElementById('toggleSidebar').style.display = 'none';
    });

    document.getElementById('closeSidebar').addEventListener('click', function() {
        document.getElementById('sidebar').classList.remove('show');
        document.getElementById('toggleSidebar').style.display = 'block';
    });



    // Xử lý hiển thị và ẩn modal Tìm kiếm theo phạm vi bán kính
    document.getElementById('helpIcon').addEventListener('click', function () {
        document.getElementById('helpModal').classList.remove('hidden');
    });

    document.getElementById('closeHelpModal').addEventListener('click', function () {
        document.getElementById('helpModal').classList.add('hidden');
    });

    document.getElementById('helpModal').addEventListener('click', function (e) {
        if (e.target === this) {
            this.classList.add('hidden');
        }
    });

// Lắng nghe sự kiện click vào nút để đọc nội dung modal
    document.getElementById('readTextButton').addEventListener('click', function () {
    // Lấy nội dung văn bản từ modal
    let textContent = document.getElementById('helpModal').innerText;

    // Tạo một đối tượng SpeechSynthesisUtterance từ văn bản
    let utterance = new SpeechSynthesisUtterance(textContent);

    // Lấy danh sách giọng nói hiện có
    let voices = window.speechSynthesis.getVoices();

    // Tìm giọng đọc tiếng Việt
    let vietnameseVoice = voices.find(voice => voice.lang === 'vi-VN');

    // Nếu tìm thấy giọng tiếng Việt, gán vào utterance
    if (vietnameseVoice) {
        utterance.voice = vietnameseVoice;
    } else {
        alert("Thiết bị của bạn không hỗ trợ giọng tiếng Việt!");
        return; // Kết thúc nếu không có giọng tiếng Việt
    }

    // Đọc nội dung
    speechSynthesis.speak(utterance);
});

// Đảm bảo giọng nói được tải đúng
window.speechSynthesis.onvoiceschanged = function () {
    console.log("Danh sách giọng nói đã tải:", window.speechSynthesis.getVoices());
};

