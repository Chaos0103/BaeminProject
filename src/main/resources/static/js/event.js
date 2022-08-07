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