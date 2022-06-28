var homeSection=document.getElementById("homeContainer");
var itemSection=document.getElementById("itemcontainer");
var customerSection=document.getElementById("CustomerContainer");
var orderSection=document.getElementById("orderContainer");
var loginSection=document.getElementById("loginContainer");
var signUpSection=document.getElementById("signUpContainer");
var OrderDetailsAllSection=document.getElementById("orderDetails");

var homeNav=document.getElementById("homenav");
var itemNav=document.getElementById("itemnav");
var customerNav=document.getElementById("customernav");
var orderNav=document.getElementById("ordernav");
var headerNav=document.getElementById("navbarheader");
var loginNav=document.getElementById("loginNav");
var SignUpNav=document.getElementById("SignUpNav");
var loginbtn=document.getElementById("btnLogin");
var SignUpbtn=document.getElementById("btnSignUp");
var SignUpbtn1=document.getElementById("SignUpbtn1");
var btnSearchOrderDetails=$('#btnOrderDetailsSearch');

loginbtn.addEventListener("click",function () {
    homeSection.style.display="block";
    itemSection.style.display="none";
    customerSection.style.display="none";
    orderSection.style.display="none";
    loginSection.style.display="none";
    signUpSection.style.display="none";
    headerNav.style.display="block";
    OrderDetailsAllSection.style.display="none";

})
loginNav.addEventListener("click",function () {
    homeSection.style.display="none";
    itemSection.style.display="none";
    customerSection.style.display="none";
    orderSection.style.display="none";
    loginSection.style.display="block";
    signUpSection.style.display="none";
    headerNav.style.display="none";
    OrderDetailsAllSection.style.display="none";

})
SignUpbtn.addEventListener("click",function () {
    homeSection.style.display="none";
    itemSection.style.display="none";
    customerSection.style.display="none";
    orderSection.style.display="none";
    loginSection.style.display="none";
    signUpSection.style.display="block";
    headerNav.style.display="none";
    OrderDetailsAllSection.style.display="none";
})
SignUpNav.addEventListener("click",function () {
    homeSection.style.display="none";
    itemSection.style.display="none";
    customerSection.style.display="none";
    orderSection.style.display="none";
    loginSection.style.display="none";
    signUpSection.style.display="block";
    headerNav.style.display="none";
    OrderDetailsAllSection.style.display="none";
})
SignUpbtn1.addEventListener("click",function () {
    homeSection.style.display="none";
    itemSection.style.display="none";
    customerSection.style.display="none";
    orderSection.style.display="none";
    loginSection.style.display="block";
    signUpSection.style.display="none";
    headerNav.style.display="none";
    OrderDetailsAllSection.style.display="none";
})
homeNav.addEventListener("click",function () {
    homeSection.style.display="block";
    itemSection.style.display="none";
    customerSection.style.display="none";
    orderSection.style.display="none";
    loginSection.style.display="none";
    signUpSection.style.display="none";
    headerNav.style.display="block";
    OrderDetailsAllSection.style.display="none";

})
itemNav.addEventListener("click",function () {
    homeSection.style.display="none";
    itemSection.style.display="block";
    customerSection.style.display="none";
    orderSection.style.display="none";
    loginSection.style.display="none";
    signUpSection.style.display="none";
    headerNav.style.display="block";
    OrderDetailsAllSection.style.display="none";
    setItemDataToTable();

})
customerNav.addEventListener("click",function () {
    homeSection.style.display="none";
    itemSection.style.display="none";
    customerSection.style.display="block";
    orderSection.style.display="none";
    loginSection.style.display="none";
    signUpSection.style.display="none";
    headerNav.style.display="block";
    OrderDetailsAllSection.style.display="none";
})
orderNav.addEventListener("click",function () {
    homeSection.style.display="none";
    itemSection.style.display="none";
    customerSection.style.display="none";
    orderSection.style.display="block";
    loginSection.style.display="none";
    signUpSection.style.display="none";
    headerNav.style.display="block";
    OrderDetailsAllSection.style.display="none";
    setOrderDetailsToDetailsTable();
})
btnSearchOrderDetails.off('click');
btnSearchOrderDetails.click(function () {

    homeSection.style.display="none";
    itemSection.style.display="none";
    customerSection.style.display="none";
    orderSection.style.display="none";
    loginSection.style.display="none";
    signUpSection.style.display="none";
    headerNav.style.display="block";
    OrderDetailsAllSection.style.display="block";
    setAllOrders();
});

