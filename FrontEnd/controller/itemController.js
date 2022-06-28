var btnSaveItem=$('#btnItemSave');
var btnUpdateItem=$('#btnUpdateItem');
var btnDeleteItem=$('#btnDeleteItem');
var btnSearchItem=$('#btnSearchItem');

var txtItemId=$('#txtitemidnew');
var txtItemName=$('#txtItemName');
var txtItemPrice=$('#txtItemPrice');
var txtItemQuantity=$('#txtItemQuantity');
var txtSearchItemId=$('#txtSearchItemId');
var itemTable=$('#tblItemTable')
txtItemId.val(generateItemId());



    //generateItemId();
    function generateItemId() {
        $.ajax({
            url:"http://localhost:8080/BackEnd_Web_exploded/item?option=GENERATEID",
            method:"GET",
            success:function (resp) {

                if (resp.status==200){
                    console.log(resp.itemId)
                   txtItemId.val(resp.itemId)
                    clearItemTextFields()
                }else {
                    alert(resp.data)
                }

            }
        })
    }
  btnSaveItem.click(function () {
    let itemDetails={
        id:txtItemId.val(),
        name:txtItemName.val(),
        price:txtItemPrice.val(),
        qty:txtItemQuantity.val()
    }
        $.ajax({
            url:"http://localhost:8080/BackEnd_Web_exploded/item",
            method: "POST",
            data:JSON.stringify(itemDetails),
            content:"application/json",
            dataType:"json",
            success:function (resp) {
                if (resp.status==200){
                    alert(resp.message);
                    setItemDataToTable();
                    generateItemId();

                }else{
                    alert(resp.data)
                }

            }
        })


        });
    function clearItemTextFields(){
       // $('#txtitemidnew').val("");
        $('#txtItemName').val("");
        $('#txtItemPrice').val("");
        $('#txtItemQuantity').val("");
    };
    setItemDataToTable();
     function setItemDataToTable(){
         $("#tblItem tbody tr").remove();
         $.ajax({
             url: "http://localhost:8080/BackEnd_Web_exploded/item?option=GETALL",
             method: "GET",
             // dataType:"json", // please convert the response into JSON
             success: function (resp) {

                 console.log(typeof resp);

                 for (const item of resp.data) {
                     let row='<tr><td>'+item.id+'</td><td>'+item.name+'</td><td>'+item.price+'</td><td>'+item.qty+'</td></tr>';
                     $("#tblItem").append(row);
                 }
                 bindClickEvents();
             }
         });
     }

        function bindClickEvents() {
            $("#tblItem>tr").click(function () {
                //Get values from the selected row
                let id = $(this).children().eq(0).text();
                let name = $(this).children().eq(1).text();
                let address = $(this).children().eq(2).text();
                let contact = $(this).children().eq(3).text();

                //Set values to the text-fields

               txtItemId.val(id);
                txtItemName.val(name);
                txtItemPrice.val(address);
                txtItemQuantity.val(contact);
            });
        }
/*        $(document).ready(function () {
            $(document).on('click','#tblItemTable tbody tr',function () {
                var itemId=$(this).find('td:eq(0)').text();
                var name=$(this).find('td:eq(1)').text();
                var  price=$(this).find('td:eq(2)').text();
                var  quantity=$(this).find('td:eq(3)').text();


                $('#txtitemidnew').val(itemId);
                $('#txtItemName').val(name);
                $('#txtItemPrice').val(price);
                $('#txtItemQuantity').val(quantity);

            });
        });*/
            $("#txtitemidnew").focus();
            $("#txtitemidnew").keydown(function (event) {
                if (event.key=="Enter"){
                    $("#txtItemName").focus();
                    sucessItem();
                }
            });
            $("#txtItemName").keydown(function (event) {
                if (event.key=="Enter"){
                    $("#txtItemPrice").focus();
                    sucessItem();
                }
            });
            $("#txtItemPrice").keydown(function (event) {
                if (event.key=="Enter"){
                    $("#txtItemQuantity").focus();
                    sucessItem();
                }
            });
            $("#txtItemQuantity").keydown(function (event) {
                if (event.key=="Enter"){
                    sucessItem();
                }
            });
        var regExitemID=/^(I00-)[0-9]{3,4}$/;

        $("#txtitemidnew").keyup(function () {

            let input= $("#txtitemidnew").val();

            if (regExitemID.test(input)){
                $("#txtitemidnew").css('border','2px solid green');
                $("#errorI").text("");

            }else{
                $("#txtitemidnew").css('border','2px solid red');
                $("#errorI").text("Wrong format : I00-001");
            }
        });

        var regExItemName=/^[a-z,A-Z ]*$/;
        $("#txtItemName").keyup(function () {

            let input= $("#txtItemName").val();

            if (regExItemName.test(input)){
                $("#txtItemName").css('border','2px solid green');
                $("#errorI1").text("");

            }else{
                $("#txtItemName").css('border','2px solid red');
                $("#errorI1").text("Wrong format : ***");
            }
        });

        var regExItemPrice=/^[0-9]{0,10}$/;
        $("#txtItemPrice").keyup(function () {

            let input= $("#txtItemPrice").val();

            if (regExItemPrice.test(input)){
                $("#txtItemPrice").css('border','2px solid green');
                $("#errorI2").text("");

            }else{
                $("#txtItemPrice").css('border','2px solid red');
                $("#errorI2").text("Wrong format : 1200");
            }
        });

        var regExItemQuantity=/^[0-9]{0,5}$/;
        $("#txtItemQuantity").keyup(function () {

            let input= $("#txtItemQuantity").val();

            if (regExItemQuantity.test(input)){
                $("#txtItemQuantity").css('border','2px solid green');
                $("#errorI3").text("");

            }else{
                $("#txtItemQuantity").css('border','2px solid red');
                $("#errorI3").text("Wrong format : 12");
            }
        });
        function sucessItem() {
            let iId = $("#txtitemidnew").val();

            if (iId.length>0) {
                let iName = $("#txtItemName").val();
                if (iName.length>0){
                    let iPrice = $("#txtItemPrice").val();
                    if (iPrice.length>0){
                        let iQuantity = $("#txtItemQuantity").val();
                        if (iQuantity.length>0){
                            $('#btnItemSave').prop('disabled', false);
                        }else{
                            $('#btnItemSave').prop('disabled', true);
                        }
                    }else{
                        $('#btnItemSave').prop('disabled', true);
                    }
                }else{
                    $('#btnItemSave').prop('disabled', true);
                }
            }else {
                $('#btnItemSave').prop('disabled', true);
            }
        }
        txtSearchItemId.keyup(function() {
            let val = $.trim($(this).val()).replace(/ +/g, ' ').toLowerCase();

            itemTable.children('tbody').children('tr').show().filter(function() {
                let text = $(this).text().replace(/\s+/g, ' ').toLowerCase();
                return !~text.indexOf(val);
            }).hide();

        });
        btnSearchItem.click(function () {
            let searchItemId=txtSearchItemId.val();
            let itemDetails=searchItem(searchItemId);
            if (itemDetails){
                $('#txtitemidnew').val(itemDetails.getItemId());
                $('#txtItemName').val(itemDetails.getItemName());
                $('#txtItemPrice').val(itemDetails.getItemPrice());
                $('#txtItemQuantity').val(itemDetails.getItemQuantity());
            }else{
                txtSearchItemId.val("");
                alert("empty result")
            }
        })
        function searchItem(id) {
            $.ajax({
                url:"http://localhost:8080/BackEnd_Web_exploded/item?option=SEARCH?cusId="+id,
                method:"SEARCH",
                success: function (resp) {
                    var details=resp.cusId;
                    return details;
                }
            });

        }
        $("#btnUpdateItem").click(function () {
         let itemOb={
             id:txtItemId.val(),
             name:txtItemName.val(),
             price: txtItemPrice.val(),
             qty:txtItemQuantity.val()
         }
         $.ajax({
             url:"http://localhost:8080/BackEnd_Web_exploded/item",
             method:"PUT",
             contentType:"application/json",
             data: JSON.stringify(itemOb),
             dataType: "json",
             success:function (resp) {
                if (resp.status==200){
                    alert(resp.message)
                    setItemDataToTable()
                    clearItemTextFields()
                    generateItemId()
                }else if (resp.status==400){
                    alert(resp.data)
                }else {
                    resp.data()
                }
             }
         })
        });
        $("#btnDeleteItem").click(function () {
            let itemId=txtItemId.val();
            $.ajax({
                url:"http://localhost:8080/BackEnd_Web_exploded/item?itemId="+itemId,
                method:"DELETE",
                success:function (resp) {
                    if (resp.status==200){
                        alert(resp.message)
                    }else {
                        alert(resp.data)
                    }
                }
            })
        });