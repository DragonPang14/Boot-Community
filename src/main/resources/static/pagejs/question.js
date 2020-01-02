function editQuestion() {
    var id = $("#question_id").val();
    window.location.href = "/publish/" + id;
}

function sendComment() {
    var content = $("#content").val();
    var questionId = $("#question_id").val();
    console.info(content);
    $.ajax({
        url: "/comment",
        type: "post",
        dataType: "json",
        contentType:"application/json",
        data: JSON.stringify({"parentId": questionId, "type": 1, "content": content}),
        success: function (data) {
            console.info(data);
        }
    });
}