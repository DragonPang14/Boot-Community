function uploadAvatar() {
    var formData = new FormData($("#avatar-form")[0]);
    $.ajax({
        url:"/uploadAvatar",
        type: "post",
        data: formData,
        catch: false,
        contentType: false,
        processData: false,
        success:function (data) {
            if (data.code == 100){
                $("#current-avatar img").each(function () {
                    $(this).attr("src",data.obj.avatarUrl);
                });
                $("#modify-button").show();
            } else {
                alert(data.msg);
            }

        }
    })

}

function modifyAvatar() {
    var userId = $("input[name='userId']").val();
    var avatarUrl = $("#avatar-url").attr("src");
    $.ajax({
        url:"/modifyAvatar",
        data:JSON.stringify({"userId":userId,"avatarUrl":avatarUrl}),
        type:"post",
        contentType:"application/json",
        dataType:"json",
        success:function (data) {
            if (data.code == 100){
                if (confirm("修改头像成功")){
                    window.location.reload();
                }
            } else {
                alert(data.msg);
            }
        }
    })
}