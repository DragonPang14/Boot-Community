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
            } else {
                alert(data.msg);
            }

        }
    })

}