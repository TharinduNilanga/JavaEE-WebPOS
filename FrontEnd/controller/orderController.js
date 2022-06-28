/*
var customerId=$('#txtcusId');
var customerName=$('#txtOrderCusName');
var customerAddress=$('#txtOrderCusAddress');
var customerContact=$('#txtOrderCusContact');

var ItemId=$('#txtitemId');
var ItemName=$('#txtOrderItemName');
var ItemPrice=$('#txtOrderItemPrice');
var ItemQuantity=$('#txtOrderItemQuantity')

var orderId=$('#txtorderId');
var orderDiscount=$('#txtdis');
var orderQuantity=$('#txtOrderQuantity');
var totalPrice=$('#txtttlPrice');

var labelTotal=$('#lblTotal');
var btnAddToCart=$('#btnAddToCart');
var btnPlaceOrder=$('#btnPlaceOrder');
var btnClear=$('#btnClear');

generateOrderId();
function generateOrderId() {
    $.ajax({
        url:"http://localhost:8080/BackEnd_Web_exploded/order?option=GENERATEID",
        method:"GET",
        success:function (resp) {
            if (resp.status==200){
                orderId.val(resp.id);

            }else {
                console.log("error")
            }

        }
    });
}

customerId.keydown(function (event) {
    if (event.key=="Enter"){
        let id = customerId.val();
        $.ajax({
            url:"http://localhost:8080/BackEnd_Web_exploded/customer?option=SEARCH&cusId="+id,
            method:"GET",
            success: function (resp) {
                if (resp.status==200){
                    customerId.val(resp.cusId);
                    customerName.val(resp.cusName);
                    customerAddress.val(resp.cusAddress);
                    customerContact.val(resp.cusContact)
                }else {
                    resp.data;
                }

            }
        });
    }

});
$("#txtitemId").keydown(function (event) {
    if (event.key=="Enter"){
        let id=ItemId.val();
        $.ajax({
            url:"http://localhost:8080/BackEnd_Web_exploded/item?option=SEARCH&itemId="+id,
            method:"GET",
            success: function (resp) {
                if (resp.status==200){
                    ItemId.val(resp.itemId);
                    ItemName.val(resp.itemName);
                    ItemPrice.val(resp.itemPrice);
                    ItemQuantity.val(resp.itemQuantity)
                }else {
                    resp.data;
                }
            }
        });

    }
});
/!*let cusId=$('input[name="txtcusId"]').val();
      let itemId=$('input[name="txtitemId"]').val();
      let orderId=$('input[name="txtorderId"]').val();
      let discount=$('input[name="txtdis"]').val();
      let quantity=$('input[name="txtOrderQuantity"]').val();
      let total=$('input[name="txtttlPrice"]').val();
      var orderObject={
          orderId:orderId,
          customerId:cusId,
          itemId:itemId,
          discount:discount,
          quantity:quantity,
          totalPrice:total
      }
      orderDB.push(orderObject);*!/
var orderOBJ=new orderDTO(orderId.val(),customerId.val(),parseFloat(orderDiscount.val()),parseFloat(labelTotal.val()),date,time);
orderDB.push(orderOBJ);
for (let i=0;i<orderDB.length;i++){
    if (orderDB[i].getOrderId()==orderId.val()){
        for (let j=0;j<AddToCartTm.length;j++){
            orderDB[i].getOrderDetails().push(new OrderDetails(AddToCartTm[j].getOOderId(),
                AddToCartTm[j].getOOrderCustomerId(),AddToCartTm[j].getOOrderItemId(),
                AddToCartTm[j].getOOrderDiscount(),AddToCartTm[j].getOOrderQuantity(),
                AddToCartTm[j].getOOrderTotalPrice()));

        };
*/
