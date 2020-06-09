window.onload = function () {
    var locationItem = window.localStorage.getItem("closeable");
    if(locationItem == "true"){
        window.close();
    }

    getTags();
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


