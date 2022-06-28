var btnSaveCustomer=$('#btnSaveCustomer');
var btnUpdateCustomer=$('#btnUpdateCustomer');
var btnDeleteCustomer=$('#btnDeleteCustomer');
var btnSearchCustomer=$('#btnSearchCustomer');

var txtCusId=$('#txtCustomerId');
var txtCusName=$('#txtCustomerName');
var txtCusAddress=$('#txtCustomerAddress');
var txtCusContact=$('#txtCustomerContact');
var txtSearchCustomerId=$('#txtSearchCustomerId');
var customerTable=$('#tblCustomerTable')


txtCusId.val(generateCustomerId());



    function generateCustomerId() {
      $.ajax({
          url:"http://localhost:8080/BackEnd_Web_exploded/customer?option=GENERATEID",
          method:"GET",
          success:function (resp) {

              if (resp.status==200){
                  console.log(resp.cusId)
                  txtCusId.val(resp.cusId)
              }else {
                  alert(resp.data)
              }

          }
      })
    }
    btnSaveCustomer.click(function () {
       // var data=$('#customerForm').serialize();
        var detail={
            id:txtCusId.val(),
            name:txtCusName.val(),
            address:txtCusAddress.val(),
            contact:txtCusContact.val()
        }
        $.ajax({
            url: "http://localhost:8080/BackEnd_Web_exploded/customer",
            method: "POST",
            data:JSON.stringify(detail),
            content:"application/json",
            dataType:"json",
            success:function (res) {
                if (res.status==200){

                    alert(res.message);
                    setDataToTable();
                    clearTextFields();
                }else {
                    alert(res.data)
                }

            }
        })
    });

    function  clearTextFields(){
        $('#txtCustomerId').val("");
        $('#txtCustomerName').val("");
        $('#txtCustomerAddress').val("");
        $('#txtCustomerContact').val("");
    }
    setDataToTable();
    function setDataToTable(){
        $('#tblCustomer').empty();
        $.ajax({
            url: "http://localhost:8080/BackEnd_Web_exploded/customer?option=GETALL",
            method: "GET",
            // dataType:"json", // please convert the response into JSON
            success: function (resp) {

                console.log(typeof resp);

                for (const customer of resp.data) {
                    let row='<tr><td>'+customer.id+'</td><td>'+customer.name+'</td><td>'+customer.address+'</td><td>'+customer.contact+'</td></tr>';
                    $("#tblCustomer").append(row);
                }
            bindClickEvents();
            }
        });
    }
        function bindClickEvents() {
            $("#tblCustomer>tr").click(function () {
                //Get values from the selected row
                let id = $(this).children().eq(0).text();
                let name = $(this).children().eq(1).text();
                let address = $(this).children().eq(2).text();
                let contact = $(this).children().eq(3).text();

                //Set values to the text-fields

                $('#txtCustomerId').val(id);
                $('#txtCustomerName').val(name);
                $('#txtCustomerAddress').val(address);
                $('#txtCustomerContact').val(contact);
            });
        }


    $("#txtCustomerId").focus();
    $("#txtCustomerId").keydown(function (event) {
        if (event.key=="Enter"){
            $("#txtCustomerName").focus();
            sucess();
        }
    });
    $("#txtCustomerName").keydown(function (event) {
        if (event.key=="Enter"){
            $("#txtCustomerAddress").focus();
            sucess();
        }
    });
    $("#txtCustomerAddress").keydown(function (event) {
        if (event.key=="Enter"){
            $("#txtCustomerContact").focus();
            sucess();
        }
    });
    $("#txtCustomerContact").keydown(function (event) {
        if (event.key=="Enter"){

            sucess();
        }
    });
    var regExCusID=/^(C00-)[0-9]{3,4}$/;

    $("#txtCustomerId").keyup(function () {

        let input= $("#txtCustomerId").val();

        if (regExCusID.test(input)){
            $("#txtCustomerId").css('border','2px solid green');
            $("#error").text("");

        }else{
            $("#txtCustomerId").css('border','2px solid red');
            $("#error").text("Wrong format : C00-001");
        }
    });

    var regExCusName=/^[a-z,A-Z ]*$/;
    $("#txtCustomerName").keyup(function () {

        let input= $("#txtCustomerName").val();

        if (regExCusName.test(input)){
            $("#txtCustomerName").css('border','2px solid green');
            $("#error1").text("");

        }else{
            $("#txtCustomerName").css('border','2px solid red');
            $("#error1").text("Wrong format : Kumara");
        }
    });

    var regExCusAddress=/[a-z,A-Z ]+[0-9 | a-z,A_Z]*$/;
    $("#txtCustomerAddress").keyup(function () {

        let input= $("#txtCustomerAddress").val();

        if (regExCusAddress.test(input)){
            $("#txtCustomerAddress").css('border','2px solid green');
            $("#error2").text("");

        }else{
            $("#txtCustomerAddress").css('border','2px solid red');
            $("#error2").text("Wrong format : Horana");
        }
    });

    var regExCusContact=/^[0-9]{10}$/;
    $("#txtCustomerContact").keyup(function () {

        let input= $("#txtCustomerContact").val();

        if (regExCusContact.test(input)){
            $("#txtCustomerContact").css('border','2px solid green');
            $("#error3").text("");

        }else{
            $("#txtCustomerContact").css('border','2px solid red');
            $("#error3").text("Wrong format : 0712345678");
        }
    });
    function sucess() {
        let cuID = $("#txtCustomerId").val();

        if (cuID.length>0) {
            let cuName = $("#txtCustomerName").val();
            if (cuName.length>0){
                let cuAddress = $("#txtCustomerAddress").val();
                if (cuAddress.length>0){
                    let cuContact = $("#txtCustomerContact").val();
                    if (cuContact.length>0){
                        $('#btnSaveCustomer').prop('disabled', false);
                    }else{
                        $('#btnSaveCustomer').prop('disabled', true);
                    }
                }else{
                    $('#btnSaveCustomer').prop('disabled', true);
                }
            }else{
                $('#btnSaveCustomer').prop('disabled', true);
            }
        }else {
            $('#btnSaveCustomer').prop('disabled', true);
        }
    }
    btnSearchCustomer.click(function () {
        let searchCustomerId=txtSearchCustomerId.val();

        let customerDetails=searchCustomer(searchCustomerId);
        if (customerDetails){
            $('#txtCustomerId').val(customerDetails.getCustomerId());
            $('#txtCustomerName').val(customerDetails.getCustomerName());
            $('#txtCustomerAddress').val(customerDetails.getCustomerAddress());
            $('#txtCustomerContact').val(customerDetails.getCustomerContact());

        }else{
            txtSearchCustomerId.val("");
            alert("empty result")
        }
    });
    txtSearchCustomerId.keyup(function() {
        let val = $.trim($(this).val()).replace(/ +/g, ' ').toLowerCase();

        customerTable.children('tbody').children('tr').show().filter(function() {
            let text = $(this).text().replace(/\s+/g, ' ').toLowerCase();
            return !~text.indexOf(val);
        }).hide();

    })
    function searchCustomer(id) {
        $.ajax({
            url:"http://localhost:8080/BackEnd_Web_exploded/customer?option=SEARCH?cusId="+id,
            method:"SEARCH",
            success: function (resp) {
                var details=resp.cusId;
                return details;
            }
        });

    }
    btnUpdateCustomer.click(function () {
        var cusOb={
            id:txtCusId.val(),
            name:txtCusName.val(),
            address:txtCusAddress.val(),
            contact:txtCusContact.val()
        }
        $.ajax({
            url:"http://localhost:8080/BackEnd_Web_exploded/customer",
            method:"PUT",
            contentType:"application/json",
            data:JSON.stringify(cusOb),
            success:function (resp) {
                    if (resp.status==200){
                        alert(resp.message);
                        setDataToTable();

                    }else if (resp.status==400){
                        alert(resp.data)
                    }else {
                        resp.data()
                    }
            }
        })

    });
   btnDeleteCustomer.click(function () {
            let cusId = txtCusId.val();

            $.ajax({
                url:"http://localhost:8080/BackEnd_Web_exploded/customer?cusId="+cusId,
                method:"DELETE",
                success:function (resp) {
                    if (resp.status==200){
                        console.log(resp.message)
                        alert(resp.message)
                        setDataToTable();
                    }else{
                        console.log(resp.message)
                        alert(resp.data)
                    }

                }
            })


    });