var txtOrderDetailsSearch=$('#txtOrderDetailsIdSearch');
var btnSearchOrderDetails=$('#btnOrderDetailsSerach')


  function setOrderDetailsToDetailsTable() {
    $("#tableOrderDetails tbody tr").remove();
    for (let i=0;i<orderDB.length;i++){
      var tr='<tr><td>'+orderDB[i].getOrderId()+'</td> <td>'+orderDB[i].getOrderCustomerId()+'</td> <td>'+orderDB[i].getOrderDiscount()+'</td> <td>'+orderDB[i].getOrderTotalPrice()+'</td><td>'+orderDB[i].getOrderDate()+'</td><td>'+orderDB[i].getOrderTime()+'</td></tr>';
      $("#tblOrderDetails").append(tr);
    }


  }
  function setAllOrders(){
     for (let i=0;i<orderDB.length;i++){
       if (orderDB[i].getOrderId()==txtOrderDetailsSearch.val()){
          for (let j=0;j<orderDB[i].getOrderDetails().length;j++){
            var tr='<tr><td>'+orderDB[i].getOrderDetails()[j].getOOrderItemId()+'</td> <td>'+orderDB[i].getOrderDetails()[j].getOOrderCustomerId()+'</td> <td>'+orderDB[i].getOrderDetails()[j].getOOrderItemId()+'</td> <td>'+orderDB[i].getOrderDetails()[j].getOOrderDiscount()+'</td><td>'+orderDB[i].getOrderDetails()[j].getOOrderQuantity()+'</td><td>'+orderDB[i].getOrderDetails()[j].getOOrderTotalPrice()+'</td></tr>';
            $("#tblOrderDetailsAll").append(tr);

          }
       }
     }
  }