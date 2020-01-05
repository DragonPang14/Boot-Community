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
            if(data.code == 1001){
                if(confirm("未登录不能进行回复，是否进行登陆？")){
                    window.open("https://github.com/login/oauth/authorize?client_id=dc364cf81cbdc28a0e43&redirect_uri=http://localhost:8887/callback&scope=user&state=123");
                    window.localStorage.setItem("closeable","true");
                }
            }
        }
    });
}