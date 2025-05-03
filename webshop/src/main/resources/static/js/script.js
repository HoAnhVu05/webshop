function showForm(productId) {
    console.log('Đang hiển thị form cho sản phẩm có ID:', productId);  // Debug

    // Ẩn tất cả các form trước khi hiển thị form của sản phẩm hiện tại
    var allForms = document.querySelectorAll('.product-form');
    allForms.forEach(function(form) {
        form.style.display = 'none';
    });

    // Hiển thị form của sản phẩm được chọn
    var formDiv = document.getElementById('form-' + productId);
    if (formDiv) {
        console.log('Form tìm thấy và hiển thị:', 'form-' + productId);  // Debug
        formDiv.style.display = 'block';
    } else {
        console.log('Không tìm thấy form với ID:', 'form-' + productId);  // Debug
    }
}

function confirmDelete(orderId) {
    if (confirm("Bạn có chắc chắn muốn hủy đơn hàng này không?")) {
        fetch('/order/delete/' + orderId, {
            method: 'POST'
        })
        .then(response => {
            if (response.ok) {
                location.reload();
            } else {
                alert('Có lỗi xảy ra khi hủy đơn.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Không thể kết nối đến server.');
        });
    }
}


  // JavaScript để thiết lập orderId khi nhấn vào nút hủy
  function setOrderId(button) {
      var orderId = button.getAttribute('data-id');
      console.log("Setting orderId: " + orderId); // Debug
      document.getElementById('orderId').value = orderId;
  }

