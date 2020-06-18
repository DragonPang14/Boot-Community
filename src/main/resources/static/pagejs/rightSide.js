window.onload = function () {
    unReadNotifications();

    getTags();

    getHotRank();

    getActiveUser();
}

function unReadNotifications() {
    $.post({
        url:"/unReadNotifications",
        dataType:"json",
        success:function (data) {
            if (data.code == 100 && data.obj > 0){
                $("#notifications").find("a").text("有" + data.obj + "条未读消息");
            }else {
                $("#notifications").find("a").text("无未读消息");
            }
        }
    })
}



function getActiveUser() {
    $.post({
        url:"/getActiveUser",
        dataType:"json",
        success:function (data) {
            if(data.code == 100){
                var html = '';
                html +=  data.obj;
                html += "人";
                $("#active-user").prepend(html);
            }
        }
    })
}

function getHotRank() {
    $.ajax({
        url:"/getHotRank",
        type:"post",
        dataType:"json",
        success:function (data) {
            if(data.code == 100){
                var html = '';
                $.each(data.obj,function (index,question) {
                    html += '<div class="cell media">';
                    html += '<img class="mr-3 avatar-side rounded" src="'+question.user.avatarUrl+'">';
                    html += '<div class="media-body">';
                    html += '<span class="hot-rank-title ">';
                    html += '<a href="/question/'+question.id+'">'+question.title+'</a>';
                    html += '</span>';
                    html += '</div>';
                    html += '</div>';
                });
                $("#hot-rank").append(html);
            }
        }
    })

}

function getTags() {
    $("#tag-content").empty();
    $.ajax({
        url:"/getTags",
        type:"post",
        contentType: "application/json",
        dataType: "json",
        success:function (data) {
            if (data.code == 100){
                var tagHtml = '';
                $.each(data.obj,function (index,tag) {
                    tagHtml += '<a class="unselect-tag m-1 gray" href="/?tag='+tag.id+'" id="'+tag.id+'">'+tag.tagName+'</a>\n'
                })
                $("#tag-content").append(tagHtml);
            }
        }
    })
}
