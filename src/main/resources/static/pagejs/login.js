function registered() {
    var name = $("#registered-name").val();
    var userName = $("#registered-user-name").val();
    var password = $("#registered-password").val();
    var mobile = $("#registered-mobile").val();
    var mail = $("#registered-mail").val();
    $.ajax({
        url:"/registered",
        dataType:"json",
        type:"post",
        contentType:"application/json",
        data:JSON.stringify({"name":name,"userName":userName,"password":password,"mobile":mobile,"mail":mail}),
        success:function (data) {
            if (data.code == 100){
                alert("注册成功！登陆后可设置个人信息");
                $("#signup_modal").modal("hide");
            }else {
                alert(data.msg);
            }
        }
    });
}

function userLogin() {
    var userName = $("#username").val();
    var password = $("#password").val();
    $.ajax({
       url:"/userLogin",
        dataType:"json",
        type:"post",
        contentType:"application/json",
        data:JSON.stringify({"userName":userName,"password":password}),
        success:function (data) {
            if (data.code == 100){
                window.location.href = "/";
            }else {
                alert(data.msg);
            }
        }
    });
}

$(function () {
    $(document).keydown(function(event){
        if(event.keyCode==13){
            userLogin();
        }
    });
})