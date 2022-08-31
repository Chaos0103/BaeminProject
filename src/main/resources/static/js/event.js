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

function test() {
    const value = $('#toRider option:selected').val();
    if (value == 6) {
        $('#toRiderForm').show();
    } else {
        $('#toRiderForm').hide();
    }
}

function beforeService() {
    alert('서비스 준비중입니다');
}

function scrollToTop() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}