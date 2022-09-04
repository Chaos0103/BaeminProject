function removeBasket(basketId) {
    $.ajax({
        url: "/stores/basket/" + basketId + "/remove",
        type: "POST",
        cache: false,
        success: function () {
            location.reload();
        }
    });
}
