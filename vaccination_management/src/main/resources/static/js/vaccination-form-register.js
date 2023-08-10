// Sử dụng jQuery để đảm bảo rằng form người lớn được tải khi trang web được mở
$(document).ready(function() {
    // Kiểm tra nếu age >= 16, chuyển tab và hiển thị form người lớn
    var age = parseInt($('#ageValue').text());
    if (age >= 16) {
        $('#nguoi-lon-tab').tab('show');
    }
});