$(document).ready(function() {
    // Tính toán chiều cao lớn nhất của các card
    var maxHeight = 0;
    $('.card').each(function() {
        if ($(this).height() > maxHeight) {
            maxHeight = $(this).height();
        }
    });

    // Đặt chiều cao cho tất cả các card
    $('.card').height(maxHeight);
});