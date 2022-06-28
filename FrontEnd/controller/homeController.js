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
var btnClear=$('#btnClear')

var orderCost=0;


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

    var AddToCartTm=new Array();
    $("#btnAddToCart").off("click")
    $("#btnAddToCart").click(function () {
        let iquantity=parseInt(ItemQuantity.val());
        let customerQuantity=parseInt(orderQuantity.val());
        let discount=parseFloat(orderDiscount.val());
        let calcPrice=parseFloat(ItemPrice.val())*customerQuantity;
        let calcDiscount=(calcPrice/100)*discount;
        let calcTotal=calcPrice-calcDiscount;

        console.log(isExists(ItemId));
        totalPrice.val(calcTotal)
        if ( iquantity<customerQuantity){
            alert("Quantity is not sufficient.Please enter less quantity or select another Item..")
        }
       /* var cartTm=new CartDTO(
            orderId.val(),
            customerId.val(),
            ItemId.val(),
            orderDiscount.val(),
            orderQuantity.val(),
            calcTotal
        );
        console.log(cartTm)*/
        /*let rowNumber=isExists(cartTm);*/
        if (isExists(ItemId.val())==false){
            AddToCartTm.push(new AddToCart( orderId.val(), customerId.val(), ItemId.val(), discount,customerQuantity,calcTotal));

            setOrderDataToTable();
            alert("added");
        }else{
            for (let i=0;i<AddToCartTm.length;i++){
                if (AddToCartTm[i].getOOrderItemId() === ItemId.val()){
                    AddToCartTm[i].setOOrderQuantity(AddToCartTm[i].getOOrderQuantity()+customerQuantity);
                    AddToCartTm[i].setOOrderTotalPrice(AddToCartTm[i].getOOrderTotalPrice()+calcTotal);
                };
            };
            setOrderDataToTable();
            orderQuantity.val("");
            totalPrice.val("");
            alert("Updated");
        };
        orderQuantity.val("");
        totalPrice.val("");
        calculateTotal();
        labelTotal.val(orderCost)
        btnPlaceOrder.prop('disabled', false);
    // /*    console.log(calcPrice);
    //     console.log(calcDiscount);
    //     console.log(calcTotal)
    //     console.log(iquantity)
    //     console.log(customerQuantity)*/
    //    /* totalPrice.val(calcTotal);*/
    //
    //
    //    /* let cusId=$('input[name="txtcusId"]').val();
    //     let itemId=$('input[name="txtitemId"]').val();
    //     let orderId=$('input[name="txtorderId"]').val();
    //     let discount=$('input[name="txtdis"]').val();
    //     let quantity=$('input[name="txtOrderQuantity"]').val();
    //     let total=$('input[name="txtttlPrice"]').val();
    //
    //     var tr='<tr><td>'+orderId+'</td> <td>'+cusId+'</td> <td>'+itemId+'</td> <td>'+discount+'</td> <td>'+quantity+'</td> <td>'+total+'</td></tr>';
    //
    //     $("#tblAddOrder").append(tr);*/
    });
    btnClear.click(function () {

    })
    function calculateTotal() {
        let total=0;
        for (let i=0;i<AddToCartTm.length;i++){

            total+=AddToCartTm[i].getOOrderTotalPrice();
        }
        orderCost=total;
    }
    function isExists(code){
        for (let i=0;i<AddToCartTm.length;i++){
            if (AddToCartTm[i].getOOrderItemId()==code){
                return true;
            };
        };
        return false;
    };
    let dt = new Date();
    let date = dt.getDate() + "." + (dt.getMonth() + 1) + "." + dt.getFullYear();
    let time = dt.getHours() + "." + dt.getMinutes() + "." + dt.getSeconds();
    $('#btnPlaceOrder').off("click");
    $('#btnPlaceOrder').click(function () {

        let order={
            oId:orderId.val(),
            cusId:customerId.val(),
            discount:parseFloat(orderDiscount.val()),
            totalPrice:parseFloat(labelTotal.val()),
            date:date,
            time:time,
            orderDetails:AddToCartTm

        }
        $.ajax({
            url:"http://localhost:8080/BackEnd_Web_exploded/order",
            method: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(order),
            success:function (resp) {
                if (resp.status==200){
                   alert(resp.message)
                    customerId.val("");
                    customerName.val("");
                    customerAddress.val("");
                    customerContact.val("");

                    ItemId.val("");
                    ItemName.val("");
                    ItemPrice.val("");
                    ItemQuantity.val("");

                    orderDiscount.val("");
                    orderQuantity.val("");
                    totalPrice.val("");



                    $("#tablehome tbody tr").remove();
                    orderCost=0;
                    labelTotal.val(orderCost);

                }else {
                    alert(resp.data)
                }

            }

        })







    });
   function setOrderDataToTable(){
    $("#tablehome tbody tr").remove();
    for (let i=0;i<AddToCartTm.length;i++){
        var tr='<tr><td>'+AddToCartTm[i].getOOderId()+'</td> <td>'+AddToCartTm[i].getOOrderCustomerId()+'</td> <td>'+AddToCartTm[i].getOOrderItemId()+'</td> <td>'+AddToCartTm[i].getOOrderDiscount()+'</td> <td>'+AddToCartTm[i].getOOrderQuantity()+'</td> <td>'+AddToCartTm[i].getOOrderTotalPrice()+'</td></tr>';
        $("#tblAddOrder").append(tr);
    }
}


    orderQuantity.keydown(function (event) {
        if (event.key=="Enter"){
            let customerQuantity=parseInt(orderQuantity.val());
            let discount=parseFloat(orderDiscount.val());
            let calcPrice=parseFloat(ItemPrice.val())*customerQuantity;
            let calcDiscount=(calcPrice/100)*discount;
            let calcTotal=calcPrice-calcDiscount;

            totalPrice.val(calcTotal)
            }


    });
$(document).ready(function () {
    $(document).on('dblclick','#tblhome tbody tr',function () {
        var itemId=$(this).find('td:eq(2)').text();
        console.log(itemId)
      /*  var name=$(this).find('td:eq(1)').text();
        var  price=$(this).find('td:eq(2)').text();
        var  quantity=$(this).find('td:eq(3)').text();

        $('#txtitemidnew').val(itemId);
        $('#txtItemName').val(name);
        $('#txtItemPrice').val(price);
        $('#txtItemQuantity').val(quantity);
        let obj=customerDB.findIndex((obj =>obj.id==id));
        customerDB.splice(obj,1);*/
        for (let i=0;i<AddToCartTm.length;i++){
            if (AddToCartTm[i].getOOrderItemId()==itemId){
               AddToCartTm.splice(i,1)
                $(this).remove();
            }
        }
       /*setOrderDataToTable();*/
    });
});

    /*function itemQuantity() {
        for (let i=0;i<orderDB.length;i++){
            for (let j=0;j<orderDB[i].getOrderDetails().length;j++){
                let itemId=orderDB[i].getOrderDetails()[j].getOOrderItemId();
                console.log(itemId);
                let itemqty=parseInt(orderDB[i].getOrderDetails()[j].getOOrderQuantity());
                console.log(itemqty);
                 if (itemDB[j].getItemId()==itemId){
                     let newqty=parseInt(itemDB[j].getItemQuantity());
                     let calcqty=newqty-itemqty;
                     itemDB[j].setItemQuantity(calcqty);
                 }
            }
        }
    }*/
    var regExCusID=/^(C00-)[0-9]{3,4}$/;
    customerId.keyup(function () {

        let input= customerId.val();

        if (regExCusID.test(input)){
            customerId.css('border','2px solid green');

        }else{
            customerId.css('border','2px solid red');

        }
    });

    var regExItemID=/^(I00-)[0-9]{3,4}$/;
    ItemId.keyup(function () {

        let input= ItemId.val();

        if (regExItemID.test(input)){
            ItemId.css('border','2px solid green');

        }else{
            ItemId.css('border','2px solid red');

        }
    });
    var regExDiscount=/^(\+|-)?(\d+(\.\d*)?|\.\d+)%?$/;
    orderDiscount.keyup(function () {

        let input= orderDiscount.val();

        if (regExDiscount.test(input)){
            orderDiscount.css('border','2px solid green');

        }else{
            orderDiscount.css('border','2px solid red');

        }
    });
    var regExQuantity=/^[0-9]{0,10}$/;
    orderQuantity.keyup(function () {

        let input= orderQuantity.val();

        if (regExQuantity.test(input)){
            orderQuantity.css('border','2px solid green');

        }else{
            orderQuantity.css('border','2px solid red');

        }
    });
