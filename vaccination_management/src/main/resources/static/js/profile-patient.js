document.addEventListener('DOMContentLoaded', function() {
    // Kiểm tra xem trạng thái đã được lưu trong localStorage chưa
    const selectedStatus = localStorage.getItem('selectedStatus');

    // Nếu đã có trạng thái được lưu, thay đổi nội dung của button thành trạng thái đã chọn
    if (selectedStatus) {
        document.getElementById('vaccination-status-dropdown').textContent = selectedStatus;
    }

    // Lắng nghe sự kiện chọn item trong dropdown menu
    document.querySelectorAll('.dropdown-item').forEach(item => {
        item.addEventListener('click', function() {
            // Lấy nội dung của item được chọn
            const selectedStatus = this.textContent.trim();

            // Thay đổi nội dung của button thành trạng thái tiêm chủng đã chọn
            document.getElementById('vaccination-status-dropdown').textContent = selectedStatus;

            // Lưu trạng thái đã chọn vào localStorage
            localStorage.setItem('selectedStatus', selectedStatus);
        });
    });
});