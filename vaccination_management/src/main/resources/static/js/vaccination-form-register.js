// Sử dụng jQuery để đảm bảo rằng form người lớn được tải khi trang web được mở
$(document).ready(function() {
    // Kiểm tra nếu age >= 16, chuyển tab và hiển thị form người lớn
    var age = parseInt($('#ageValue').text());
    if (age >= 16) {
        $('#nguoi-lon-tab').tab('show');
    }
});
// $(document).ready(function() {
//     var age = parseInt($('#ageValue').text());
//     var currentTab = '';
//
//     if (age < 16) {
//         currentTab = 'tre-em-tab';
//     } else {
//         currentTab = 'nguoi-lon-tab';
//     }
//
//     $('#' + currentTab).tab('show');
//     sessionStorage.setItem('currentTab', currentTab);
// });
//
// $(document).ready(function() {
//     var currentTab = sessionStorage.getItem('currentTab');
//     $('#' + currentTab).tab('show');
//
//     // Kiểm tra lỗi validator khi tải lại trang hoặc chuyển tab
//     validateTab(currentTab);
//
//     $('.nav-link').on('shown.bs.tab', function(event) {
//         var newTab = $(event.target).attr('id');
//         validateTab(newTab);
//     });
//
//     function validateTab(tabId) {
//         if (tabId === 'nguoi-lon-tab' && hasValidatorError(tabId)) {
//             // Nếu gặp lỗi validator ở tab nguoi-lon-tab, không chuyển tab
//             $('#' + currentTab).tab('show');
//         } else {
//             // Nếu không gặp lỗi validator hoặc lỗi ở tab khác, lưu trạng thái tab hiện tại vào session
//             sessionStorage.setItem('currentTab', tabId);
//         }
//     }
//
//     function hasValidatorError(tabId) {
//         // Kiểm tra lỗi validator của tab tương ứng
//         // Trả về true nếu có lỗi, false nếu không có lỗi
//         if (tabId === 'nguoi-lon-tab') {
//             // Kiểm tra lỗi validator của tab nguoi-lon-tab
//             // Thay thế đoạn mã này bằng mã kiểm tra lỗi validator thực tế của bạn
//             return false; // Giả sử không có lỗi
//         } else {
//             // Các tab khác không cần kiểm tra lỗi validator
//             return false;
//         }
//     }
// });
