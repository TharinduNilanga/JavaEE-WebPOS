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



    function generateItemId() {
        if (itemDB[0] != undefined) {
            for (let i = 0; i < itemDB.length; i++) {
                if (i == (itemDB.length - 1)) {
                    let temp = parseInt(itemDB[i].getItemId().split('-')[1]);
                    temp = temp + 1;
                    if (temp <= 9) {
                        return "I00-00" + temp;
                    } else if (temp <= 99) {
                        return "I00-0" + temp;
                    } else {
                        return "I00-" + temp;
                    }
                }
            }
        } else {
            return "I00-001";
        }
    }
    $("#btnItemSave").click(function () {
        for (let i = 0; i < itemDB.length; i++) {
            if (itemDB[i].getItemId() == txtItemId.val()) {
                alert('This Id is Exists.Enter Different..');
                clearItemTextFields();
                txtItemId.val(generateItemId());
                return;
            }
        }
           if (itemDB.push(new itemDTO(txtItemId.val(),txtItemName.val(),txtItemPrice.val(),txtItemQuantity.val()))){
               alert("Item has been added succesfully");
               clearItemTextFields();
               txtItemId.val(generateItemId());
               setItemDataToTable();
               $('#btnItemSave').prop('disabled', true);
           }else{
               alert("Item doesn't added!..")
           }
        });
    function clearItemTextFields(){
        $('#txtitemidnew').val("");
        $('#txtItemName').val("");
        $('#txtItemPrice').val("");
        $('#txtItemQuantity').val("");
    };
     function setItemDataToTable(){
         $("#tblItemTable tbody tr").remove();
         for (let i=0;i<itemDB.length;i++){
             var tr='<tr><td>'+itemDB[i].getItemId()+'</td> <td>'+itemDB[i].getItemName()+'</td> <td>'+itemDB[i].getItemPrice()+'</td> <td>'+itemDB[i].getItemQuantity()+'</td></tr>';
             $("#tblItemTable").append(tr);
         }
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
            for (let i=0;i<itemDB.length;i++){
                if (itemDB[i].getItemId()==id){
                    return itemDB[i];
                }
            }

        }
        $("#btnUpdateItem").click(function () {
           /* let id=$("#txtitemidnew").val();
            let iName=$('input[name="txtItemName"]').val();
            let iPrice=$('input[name="txtItemPrice"]').val();
            let iQuantity=$('input[name="txtItemQuantity"]').val();

            let objItem=itemDB.findIndex((obj =>obj.id==id));
            itemDB[objItem].name=iName;
            itemDB[objItem].price=iPrice;
            itemDB[objItem].quantity=iQuantity;*/
                for (let i=0;i<itemDB.length;i++){
                    if (itemDB[i].getItemId()==txtItemId.val()){
                        itemDB[i].setItemName(txtItemName.val());
                        itemDB[i].setItemPrice(txtItemPrice.val());
                        itemDB[i].setItemQuantity(txtItemQuantity.val());

                        alert("Item deails has been added succesfully!..")
                        setItemDataToTable();
                        clearItemTextFields();
                        txtItemId.val(generateItemId());
                    }else{
                        alert("Error");
                    }
                }
        });
        $("#btnDeleteItem").click(function () {
            alert("do you want to delete this customer");
            let id=$('#txtitemidnew').val();
            let obj=itemDB.findIndex((obj =>obj.getItemId()==id));
            itemDB.splice(obj,1);
            setItemDataToTable();
            alert("Succesfully deleted!..");
            clearItemTextFields();
            txtItemId.val(generateItemId());
            itemDB.log(obj);
            itemDB.log(itemDB);
        });