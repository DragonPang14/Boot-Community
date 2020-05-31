function editQuestion() {
    var id = $("#question-id").val();
    window.location.href = "/publish/" + id;
}

function sendComment(questionId,targetId,type) {
    var content;
    if (type == 1){
        content = $("#content").val();
    } else {
        content = $("#comments-reply-input-" + targetId).val();
    }
    console.info(content);
    $.ajax({
        url: "/sendComment",
        type: "post",
        dataType: "json",
        contentType:"application/json",
        data: JSON.stringify({"questionId":questionId,"parentId": targetId, "type": type, "content": content}),
        success: function (data) {
            console.info(data);
            if(data.code == 1001){
                if(confirm("未登录不能进行回复，是否进行登陆？")){
                    window.open("https://github.com/login/oauth/authorize?client_id=dc364cf81cbdc28a0e43&redirect_uri=http://localhost:8887/callback&scope=user&state=123");
                    window.localStorage.setItem("closeable","true");
                    // window.location.reload();
                }
            }else if(data.code == 100 && type == 1){
                window.location.reload();
            }else if(data.code == 100 && type == 2){
                var htmlStr = buildHtmlStr(data.obj);
                $("#comments-" + targetId).append(htmlStr);
            }
            else {
                alert(data.msg);
            }
        }
    });
}

/**
 * @desc 构建回复html
 * @param data
 * @returns {string}
 */
function buildHtmlStr(comment) {
    var htmlStr = '' +
        '                               <div class="media">\n' +
        '                                    <img class="mr-3 avatar rounded" src="'+comment.user.avatarUrl+'"\n' +
        '                                         alt="avater image">\n' +
        '                                    <div class="media-body">\n' +
        '                                        <div class="fr thank-area ">\n' +
        '                                            <span class="gray">赞&nbsp;&nbsp;&nbsp;\n' +
        '                                                <strong>|</strong>&nbsp;&nbsp;&nbsp;\n' +
        '                                                回复\n' +
        '                                            </span>\n' +
        '                                        </div>\n' +
        '                                        <strong class="gray">'+comment.user.name+'</strong>\n' +
        '                                        <div class="sep5"></div>\n' +
        '                                        <span class="reply-content" >'+comment.content+'</span>\n' +
        '                                    </div>\n' +
        '                                </div>' +
        '                                <div class="sep5"></div>';
    return htmlStr;
}

//评论回复
function replyComment(commentId,replyName) {
    if($("#comments-" + commentId).css('display') === 'none'){
        $("#comments-" + commentId).empty();
        $.get({
            url: "/getCommentsReply/"+commentId,
            async:false,
            success:function (data) {
                console.info(data);
                if(data.code == 100){
                    var htmlStr = '';
                    $.each(data.obj,function (index,comment) {
                        htmlStr += buildHtmlStr(comment);
                    });
                    $("#comments-" + commentId).append(htmlStr);
                } else {
                    alert(data.msg);
                }
            }
        });
    }
    $("#comments-" + commentId).collapse('toggle');
    $("#comments-reply-" + commentId).collapse('toggle');
    $("#comments-reply-input-" + commentId).attr("placeholder","回复" + replyName);
}