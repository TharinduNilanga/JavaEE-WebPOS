function AddToCart(oId,cusId,itemId,discount,quantity,totalPrice) {
    var __oId=oId;
    var __cusId=cusId;
    var __itemId=itemId;
    var __discount=discount;
    var __quantity=quantity;
    var __totalPrice=totalPrice;


    this.getOOderId=function () {
        return __oId;
    }
    this.getOOrderCustomerId=function () {
        return __cusId;
    }
    this.getOOrderItemId=function () {
        return __itemId;
    }
    this.getOOrderDiscount=function () {
        return __discount;
    }
    this.getOOrderQuantity=function () {
        return __quantity;
    }
    this.getOOrderTotalPrice=function () {
        return __totalPrice;
    }

    this.setOOrderId=function (v) {
        __oId=v;
    }
    this.setOOderCustomerId=function (v) {
        __cusId=v;
    }
    this.setOOrderItemId=function (v) {
        __itemId=v;
    }
    this.setOOrderDiscount=function (v) {
        __discount=v;
    }
    this.setOOrderQuantity=function (v) {
        __quantity=v;
    }
    this.setOOrderTotalPrice=function (v) {
        __totalPrice=v;
    }

}