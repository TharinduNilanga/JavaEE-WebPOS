var homeSection=document.getElementById("homeContainer");
var itemSection=document.getElementById("itemcontainer");
var customerSection=document.getElementById("CustomerContainer");
var orderSection=document.getElementById("orderContainer");
var loginSection=document.getElementById("loginContainer");
var signUpSection=document.getElementById("signUpContainer");
var OrderDetailsAllSection=document.getElementById("orderDetails");


var loginEmail=$('#loginexampleInputEmail1');
var loginPassword=$('#loginexampleInputPassword1');

var btnLog=$('#btnLogin');

    btnLog.click(function () {
        var userName=loginEmail.val();
        var password=loginPassword.val();

        $.ajax({
            url:"http://localhost:8080/BackEnd_Web_exploded/signUp?userName="+userName,
            method:"GET",
            success:function (resp) {


                    if (resp.password==password){
                        alert(resp.message)
                        homeSection.style.display = "block";
                        itemSection.style.display = "none";
                        customerSection.style.display = "none";
                        orderSection.style.display = "none";
                        loginSection.style.display = "none";
                        signUpSection.style.display = "none";
                        headerNav.style.display = "block";
                        OrderDetailsAllSection.style.display = "none";

                        loginEmail.val("");
                        loginPassword.val("");

                    }else {
                        alert(resp.data)
                    }


            }
        })





        /*for (let i=0;i<signUpDB.length;i++){
            if (signUpDB[i].getEmail()==loginEmail.val()){
                        if (signUpDB[i].getPassword()==loginPassword.val()) {
                                homeSection.style.display = "block";
                                itemSection.style.display = "none";
                                customerSection.style.display = "none";
                                orderSection.style.display = "none";
                                loginSection.style.display = "none";
                                signUpSection.style.display = "none";
                                headerNav.style.display = "block";
                                OrderDetailsAllSection.style.display = "none";

                                loginEmail.val("");
                                loginPassword.val("");


                        }

            }else{
                alert("incorrect login details");
            }
        }*/
    })
