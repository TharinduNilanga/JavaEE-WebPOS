var Email=$('#signUpMail');
var userName=$('#Name');
var password=$('#exampleInputPassword');

var confirm=$('#Confirm');

    confirm.click(function () {
        for (let i=0;i<signUpDB.length;i++){
            if (signUpDB[0].getEmail()==Email.val()){
                alert("This EMail Address is Exists.Enter Another...");
                return;
            }
        }
        alert("yes")
        console.log(password.val())

        if (signUpDB.push(new signUp(Email.val(),userName.val(),password.val()))){
            alert("User has been added...")
            Email.val("");
            userName.val("");
            password.val("");
        }else{
            alert("Error...");
        };

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