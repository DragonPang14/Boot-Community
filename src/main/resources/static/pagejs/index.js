window.onload = function () {
    var locationItem = window.localStorage.getItem("closeable");
    if(locationItem == "true"){
        window.close();
    }

    getTags();

    getHotRank();
}


function getHotRank() {
    $.ajax({
        url:"/getHotRank",
        type:"post",
        dataType:"json",
        success:function (data) {
            if(data.code != 100){
                alert(data.msg);
            }else {
                var html = '';
                $.each(data.obj,function (index,question) {
                    html += '<div class="cell media">';
                    html += '<img class="mr-3 avatar-side rounded" src="'+question.user.avatarUrl+'">';
                    html += '<div class="media-body">';
                    html += '<span class="hot-rank-title ">';
                    html += '<a href="javascript:;">'+question.title+'</a>';
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


