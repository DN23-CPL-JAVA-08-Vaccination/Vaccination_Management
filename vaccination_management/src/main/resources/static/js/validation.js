// Lấy tham chiếu đến trường nhập liệu
const guardianNameInput = document.getElementById('guardianName');

// Xử lý sự kiện khi người dùng nhập liệu
guardianNameInput.addEventListener('input', function () {
    const inputText = guardianNameInput.value;

    // Chuyển đổi chữ cái đầu mỗi từ thành chữ hoa
    const formattedText = inputText.replace(/\b\w/g, function (firstLetter) {
        return firstLetter.toUpperCase();
    });

    // Kiểm tra định dạng họ và tên
    const namePattern = /^[A-Z][a-zA-Z\s]*$/;
    const isValidFormat = namePattern.test(formattedText);

    if (isValidFormat) {
        guardianNameInput.setCustomValidity(''); // Xóa thông báo lỗi nếu có
    } else {
        guardianNameInput.setCustomValidity('Vui lòng nhập họ và tên đúng định dạng (ví dụ: "Nguyễn Văn" hoặc "Nguyễn Bảo")');
    }

    guardianNameInput.value = formattedText; // Gán giá trị đã định dạng trở lại cho trường nhập liệu
});
