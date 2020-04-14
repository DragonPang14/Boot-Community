function uploadAvatar() {
    var formData = new formData($("#avatar-form")[0]);
    $.ajax({
        url:"/uploadAvatar",

    })

}