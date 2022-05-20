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
    btnSaveCustomer.click(function () {

    });

    function  clearTextFields(){
        $('#txtCustomerId').val("");
        $('#txtCustomerName').val("");
        $('#txtCustomerAddress').val("");
        $('#txtCustomerContact').val("");
    }
    function setDataToTable(){
        $("#tblCustomerTable tbody tr").remove();
        for (let i=0;i<customerDB.length;i++){
            var tr='<tr><td>'+customerDB[i].getCustomerId()+'</td> <td>'+customerDB[i].getCustomerName()+'</td> <td>'+customerDB[i].getCustomerAddress()+'</td> <td>'+customerDB[i].getCustomerContact()+'</td></tr>';
            $("#tblCustomer").append(tr);
        }
    }

/*    $(document).ready(function () {
        $(document).on('click','#tblCustomerTable tbody tr',function () {
            var cusId=$(this).find('td:eq(0)').text();
            var name=$(this).find('td:eq(1)').text();
            var  address=$(this).find('td:eq(2)').text();
            var  contact=$(this).find('td:eq(3)').text();


            $('#txtCustomerId').val(cusId);
            $('#txtCustomerName').val(name);
            $('#txtCustomerAddress').val(address);
            $('#txtCustomerContact').val(contact);

        });
        $(document).on('dblclick','#tblCustomerTable tbody tr',function () {
            $(this).remove();
        });
    });*/

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
        for (let i=0;i<customerDB.length;i++){
            if (customerDB[i].getCustomerId()==id){
                return customerDB[i];
            }
        }

    }
    btnUpdateCustomer.click(function () {
        /*let id=txtCusId.val();*/

        for (let i=0;i<customerDB.length;i++){
            if (customerDB[i].getCustomerId()==txtCusId.val()){
                customerDB[i].setCustomerName(txtCusName.val());
                customerDB[i].setCustomerAddress(txtCusAddress.val());
                customerDB[i].setCustomerContact(txtCusContact.val());
                alert("Customer Have been Updated")
                setDataToTable();
                clearTextFields();
                txtCusId.val(generateCustomerId());/* $("#tblCustomerTable tbody tr").filter(function() {

                    if ($(this).children("td:nth-child(1)").text() == customerDB[i].getCustomerId()) {
                        $(this).replaceWith('<tr><td>'+customerDB[i].getCustomerId()+'</td> <td>'+customerDB[i].getCustomerName()+'</td> <td>'+customerDB[i].getCustomerAddress()+'</td> <td>'+customerDB[i].getCustomerContact()+'</td></tr>');
                    }
                })*/
            }else {
                alert("error")

            }
        }


/*         let obj=customerDB.findIndex((obj =>obj.getCustomerId()==id))
        customerDB[obj].setCustomerName(txtCusName.val());
        customerDB[obj].setCustomerAddress(txtCusAddress.val());
        customerDB[obj].setCustomerContact(txtCusContact.val());*/
        /*alert("Customer Have been Updated")*/
           /* $("#tblCustomerTable tbody tr").filter(function() {
                rowNoToUpdate = $(this).children("td:nth-child(1)").text();
                if ($(this).children("td:nth-child(2)").text() == customerDB[obj].getCustomerId()) {
                    $(this).replaceWith('<tr><td>'+customerDB[obj].getCustomerId()+'</td> <td>'+customerDB[obj].getCustomerName()+'</td> <td>'+customerDB[obj].getCustomerAddress()+'</td> <td>'+customerDB[obj].getCustomerContact()+'</td></tr>');
                }
            })*/
       /* }else{
            alert("error")
        }
*/

    });
   btnDeleteCustomer.click(function () {

        alert("do you want to delete this customer");
        let id=$('#txtCustomerId').val();
        let obj=customerDB.findIndex((obj =>obj.id==id));
        customerDB.splice(obj,1);
        setDataToTable();
        alert("Succesfully Deleted!.");
        clearTextFields();
        let i = generateCustomerId();
        txtCusId.val(i);
        console.log(obj);
        console.log(customerDB);

    });