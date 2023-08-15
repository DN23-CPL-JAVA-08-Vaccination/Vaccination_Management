// console.log('Hàm callback được thực thi');
// document.addEventListener('DOMContentLoaded', function () {
//     document.getElementById('registerForm').addEventListener('submit', function (event) {
//         // Kiểm tra nếu người lớn đã chọn để trống thông tin người giám hộ
//         if ($('#guardianName2').val() === '' && $('#guardianPhone2').val() === '') {
//             // Ngăn chặn việc submit form mặc định
//             event.preventDefault();
//
//             // Hiển thị hộp thoại xác nhận
//             Swal.fire({
//                 icon: 'question',
//                 title: 'Xác nhận đăng ký',
//                 text: 'Bạn có muốn để trống thông tin người giám hộ?',
//                 showCancelButton: true,
//                 confirmButtonText: 'Có',
//                 cancelButtonText: 'Không',
//                 confirmButtonColor: '#0d6efd',
//             }).then((result) => {
//                 if (result.isConfirmed) {
//                     // Tiến hành submit form khi người dùng chọn "Có"
//                     document.getElementById('registerForm').submit();
//                 }
//             });
//         }
//     });
// });
// validation.js
// validation.js
function validateForm(event) {
    var guardianName = document.getElementById("guardianName2").value;
    var guardianPhone = document.getElementById("guardianPhone2").value;

    if (guardianName.trim() === "" && guardianPhone.trim() === "") {
        event.preventDefault(); // Ngăn chặn việc submit form mặc định

        Swal.fire({
            icon: 'question',
            title: 'Xác nhận đăng ký',
            text: 'Bạn có muốn để trống thông tin người giám hộ?',
            showCancelButton: true,
            confirmButtonText: 'Có',
            cancelButtonText: 'Không',
            confirmButtonColor: '#0d6efd',
        }).then((result) => {
            if (result.isConfirmed) {
                // Sử dụng phương thức submit() của biểu mẫu để gửi biểu mẫu
                event.target.submit();
            }
        });
    }
}


