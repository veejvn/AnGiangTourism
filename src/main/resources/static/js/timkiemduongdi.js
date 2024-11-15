    document.getElementById('toggleSidebar1').addEventListener('click', function() {
            document.getElementById('sidebar1').classList.add('show');
            document.getElementById('toggleSidebar1').style.display = 'none';
        });

        document.getElementById('closeSidebar1').addEventListener('click', function() {
            document.getElementById('sidebar1').classList.remove('show');
            document.getElementById('toggleSidebar1').style.display = 'block';
        });

// Xử lý hiển thị và ẩn modal Tìm kiếm đường đi
    document.getElementById('helpIcon1').addEventListener('click', function () {
        document.getElementById('helpModal1').classList.remove('hidden');
    });

    document.getElementById('closeHelpModal1').addEventListener('click', function () {
        document.getElementById('helpModal1').classList.add('hidden');
    });

    document.getElementById('helpModal1').addEventListener('click', function (e) {
        if (e.target === this) {
            this.classList.add('hidden');
        }
    });

    // Lắng nghe sự kiện click vào nút để đọc nội dung modal
    document.getElementById('modal1_readTextButton').addEventListener('click', function () {
    // Lấy nội dung văn bản từ modal
    let textContent = document.getElementById('helpModal1').innerText;

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