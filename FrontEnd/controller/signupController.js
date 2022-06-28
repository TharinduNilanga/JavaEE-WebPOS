var Email=$('#signUpMail');
var userName=$('#Name');
var password=$('#exampleInputPassword');

var confirm=$('#Confirm');

    confirm.click(function () {
        let details={

            userName:userName.val(),
            email:Email.val(),
            password:password.val()
        }
        $.ajax({
            url:"http://localhost:8080/BackEnd_Web_exploded/signUp",
            method:"POST",
            dataType:"json",
            contentType:"application/json",
            data:JSON.stringify(details),
            success:function (resp) {
                if (resp.status==200){
                    alert(resp.message)

                }else {
                    alert(resp.data)
                }
            }
        })

    });

    var regExUserName=/^(?=.{4,20}$)(?:[a-zA-Z\d]+(?:(?:\.|-|_)[a-zA-Z\d])*)+$/;
    userName.keyup(function () {

        let input=userName.val();

        if (regExUserName.test(input)){
            userName.css('border','2px solid green');

        }else{
            userName.css('border','2px solid red');

        }
    });

    var regExEmail=/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/;
    Email.keyup(function () {

        let input=Email.val();

        if (regExEmail.test(input)){
            Email.css('border','2px solid green');

        }else{
            Email.css('border','2px solid red');

        }
    });