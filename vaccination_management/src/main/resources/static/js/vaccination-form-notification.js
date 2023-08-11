document.addEventListener('DOMContentLoaded', function () {
    Swal.fire({
        icon: 'success',
        title: 'Đăng ký tiêm chủng thành công!',
        text: 'Cảm ơn bạn đã đăng ký tiêm chủng. Chúng tôi sẽ liên hệ với bạn trong thời gian sớm nhất.',
        confirmButtonText: 'Quay lại trang chủ',
        confirmButtonColor: '#0d6efd',
        allowOutsideClick: false,
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = '/vaccine/list-vaccine'; // Điều hướng về trang chủ khi người dùng nhấp vào nút "Quay lại trang chủ"
        }
    });
});