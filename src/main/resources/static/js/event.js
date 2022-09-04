/**
 * packingOrder.html
 * 현금영수증 - 미신청, 신청
 */
function cashReceipt() {
    if ($('input:radio[id=unApply]').is(':checked')) {
        $('#cashReceiptForm').hide();
    } else {
        $('#cashReceiptForm').show();
    }
}

/**
 * packingOrder.html
 * 현금영수증 신청 - 개인, 사업자
 */
function cashReceiptDetail() {
    if ($('input:radio[id=individual]').is(':checked')) {
        $('#individualForm').show();
        $('#businessForm').hide();
    } else {
        $('#businessForm').show();
        $('#individualForm').hide();
    }
}

function toRiderWrite(value) {
    if (value === "write") {
        $("#toRiderForm").show();
    } else {
        $("#toRiderForm").hide();
    }
}

function beforeService() {
    alert('서비스 준비중입니다');
}

function scrollToTop() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}

function changeReceiptType(type) {
    if (type === "delivery") {
        $("#deliveryTipInfo").show();
        $("#deliveryOrder").show();
        $("#packingOrder").hide()
        let price = 0;
        $("input[name*='data']").each(function () {
            if (!isNaN($(this).val())) {
                price += parseInt($(this).val());
            }
        });
        let totalPrice = price + 3000;
        price = price.toLocaleString('ko-KR');
        $("input[name=totalOrderPrice]").val(price + '원');
        totalPrice = totalPrice.toLocaleString('ko-KR');
        $("input[name=totalPrice]").val(totalPrice + '원');
    } else {
        $("#deliveryTipInfo").hide();
        $("#deliveryOrder").hide();
        $("#packingOrder").show()
        let price = 0;
        $("input[name*='data']").each(function () {
            if (!isNaN($(this).val())) {
                price += parseInt($(this).val());
            }
        });
        price = price.toLocaleString('ko-KR');
        $("input[name=totalOrderPrice]").val(price + '원');
        $("input[name=totalPrice]").val(price + '원');
    }
}