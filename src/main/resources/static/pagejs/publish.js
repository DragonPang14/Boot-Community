
function publish() {
    var title = $("#title").val();
    var description = $("#description").val();
    var questionId = $("#question-id").val();
    if (title == null || title == "") {
        alert("请输入标题！");
        return;
    }
    if (description == null || description == "") {
        alert("请输入描述！");
        return;
    }
    $.ajax({
        url: "/doPublish",
        dataType: "json",
        type: "post",
        contentType: "application/json",
        data: JSON.stringify({"id":questionId,"title": title, "description": description}),
        success: function (data) {
            if (data.code == 100) {
                window.location.href = "/";
            } else {
                alert(data.msg);
            }
        }
    });
}

$(function () {

    $(".unselect-tag").on('click',function () {
        var tag = $.this.text();
        console.info(tag);
    });
});