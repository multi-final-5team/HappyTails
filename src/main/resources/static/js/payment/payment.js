// 전역 변수 선언
let username, productname, productinfo, productprice;

// DOM이 로드된 후 실행될 함수
document.addEventListener('DOMContentLoaded', function() {
    // Thymeleaf에서 전달된 데이터를 JavaScript 변수에 할당
    username = document.getElementById('name').value;
    productname = document.querySelector('h3').textContent;
    productinfo = document.querySelector('.right-section p').textContent;

    const amountElement = document.getElementById('amount');
    if (amountElement) {
        productprice = parseFloat(amountElement.textContent.replace(/[^0-9.-]+/g,""));
        if (isNaN(productprice) || productprice <= 0) {
            console.error('유효하지 않은 제품 가격:', amountElement.textContent);
            productprice = 0;
        }
    } else {
        console.error('amount 요소를 찾을 수 없습니다.');
        productprice = 0;
    }

    console.log('초기 제품 가격:', productprice);

    // 초기 총 결제금액 설정
    updateTotalPrice();
});

function updateTotalPrice() {
    const amountDisplay = document.getElementById('amount');
    if (!amountDisplay) {
        console.error('amount 표시 요소를 찾을 수 없습니다.');
        return;
    }

    let totalPrice = productprice;

    // 총 결제금액이 100원 미만이 되지 않도록 제한
    if (totalPrice < 100) {
        console.warn("총 결제금액이 100원보다 작습니다. 100원으로 설정합니다.");
        totalPrice = 100;
    }

    amountDisplay.textContent = totalPrice + '원';
    console.log('업데이트된 총 가격:', totalPrice);
}

function mypayment() {
    const amountDisplay = document.getElementById('amount');
    if (!amountDisplay) {
        alert("결제 금액을 표시하는 요소를 찾을 수 없습니다.");
        return;
    }

    const myAmount = parseFloat(amountDisplay.textContent.replace(/[^0-9.-]+/g,""));

    if (isNaN(myAmount) || myAmount <= 0) {
        alert("유효하지 않은 결제 금액입니다: " + amountDisplay.textContent);
        return;
    }

    console.log('결제 요청 금액:', myAmount);

    const IMP = window.IMP;
    IMP.init("imp30336517");

    IMP.request_pay(
        {
            pg: "html5_inicis",
            pay_method: "card",
            name: productname,
            amount: myAmount,
            buyer_email: "gildong@gmail.com",
            buyer_name: username,
            buyer_tel: "010-4242-4242",
            buyer_addr: "서울특별시 강남구 신사동",
            buyer_postcode: "01181",
            m_redirect_url: "",
        },
        async (rsp) => {
            if (rsp.success) {
                try {
                    const { data } = await axios.post('/payment/verifyPayment', {
                        imPortId: rsp.imp_uid,
                        amount: myAmount
                    });

                    if (data.success) {
                        alert("결제 및 검증 성공");
                        window.location.href = '/payment/orderlist';
                    } else {
                        // 검증 실패 시 결제 취소 로직 추가
                        await cancelPayment(rsp.imp_uid, myAmount);
                        alert("결제 검증 실패로 인해 결제가 취소되었습니다.");
                    }
                } catch (error) {
                    // 검증 요청 자체가 실패한 경우 결제 취소
                    await cancelPayment(rsp.imp_uid, myAmount);
                    console.log("Sending amount to server:", myAmount);
                    alert("검증 실패로 인해 결제가 취소되었습니다: " + (error.response ? error.response.data : error.message));
                }
            } else {
                alert("결제 실패: " + rsp.error_msg);
            }
        }
    );
}

async function cancelPayment(imPortId, amount) {
    try {
        await axios.post('/payment/cancel', {
            imPortId: imPortId,
            amount: amount
        });
        console.log("결제 취소 성공");
    } catch (error) {
        console.error("결제 취소 실패:", error);
    }
}