let currentPage = 1;
const itemsPerPage = 10;
let filteredOrders = [];
let totalItems = 0;

function cancelOrder(paymentNo, imPortId, amount) {
    if (confirm('정말로 주문을 취소하시겠습니까?')) {
        fetch('/payment/cancel', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                paymentNo: paymentNo,
                imPortId: imPortId,
                amount: parseFloat(amount)
            }),
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert(data.message);
                    location.reload();
                } else {
                    alert(data.message || '주문 취소에 실패했습니다.');
                }
            })
            .catch((error) => {
                console.error('Error:', error);
                alert('주문 취소 중 오류가 발생했습니다.');
            });
    }
}

function updateTotalItemsCount() {
    totalItems = filteredOrders.length > 0 ? filteredOrders.length : document.getElementsByClassName('order-item').length;
    document.getElementById('totalItemsCount').textContent = totalItems;
}

function displayPage(page) {
    const startIndex = (page - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    const orderItems = filteredOrders.length > 0 ? filteredOrders : document.getElementsByClassName('order-item');

    for (let i = 0; i < orderItems.length; i++) {
        if (i >= startIndex && i < endIndex) {
            orderItems[i].style.display = '';
        } else {
            orderItems[i].style.display = 'none';
        }
    }
}

function updatePagination() {
    const totalItems = filteredOrders.length > 0 ? filteredOrders.length : document.getElementsByClassName('order-item').length;
    const totalPages = Math.ceil(totalItems / itemsPerPage);
    const paginationElement = document.querySelector('.pagination');
    paginationElement.innerHTML = '';

    // 이전 버튼
    const prevButton = document.createElement('button');
    prevButton.textContent = '< 이전';
    prevButton.onclick = () => {
        if (currentPage > 1) {
            currentPage--;
            displayPage(currentPage);
            updatePagination();
        }
    };
    paginationElement.appendChild(prevButton);

    // 페이지 숫자
    for (let i = 1; i <= totalPages; i++) {
        const pageButton = document.createElement('button');
        pageButton.textContent = i;
        pageButton.onclick = () => {
            currentPage = i;
            displayPage(currentPage);
            updatePagination();
        };
        if (i === currentPage) {
            pageButton.classList.add('active');
        }
        paginationElement.appendChild(pageButton);
    }

    // 다음 버튼
    const nextButton = document.createElement('button');
    nextButton.textContent = '다음 >';
    nextButton.onclick = () => {
        if (currentPage < totalPages) {
            currentPage++;
            displayPage(currentPage);
            updatePagination();
        }
    };
    paginationElement.appendChild(nextButton);
}

function initEventListeners() {

    // 주문 취소 버튼에 이벤트 리스너 추가
    document.querySelectorAll('.cancel-button').forEach(button => {
        button.addEventListener('click', function() {
            const paymentNo = this.getAttribute('data-payment-no');
            const imPortId = this.getAttribute('data-imp-uid');
            const amount = this.getAttribute('data-amount');
            cancelOrder(paymentNo, imPortId, amount);
        });
    });
}

document.addEventListener('DOMContentLoaded', function() {
    initEventListeners();
    updateTotalItemsCount();
    displayPage(currentPage);
    updatePagination();
});