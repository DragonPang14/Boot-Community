
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

function saveTag() {
    var flag = true;
    $("#tag-modal").each(function () {
        console.info("each");
        console.info("input" + $(this).find("input").val());
        if (!verifyInput($(this).find("input").val())){
            $(this).find("input").addClass("is-invalid").attr("placeholder","必填！");
            flag = false;
        }
    });
    var name = $("#tagName").val();
    var remarks = $("#remarks").val();
    if (flag){
        $.ajax({
            url:"/saveTag",
            type:"post",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({"tagName":name,"remarks":remarks}),
            success:function (data) {
                if (data.code == 100){
                    alert("创建成功！");
                    $("#tag-modal").modal('close');
                }
            }
        })
    } 
}

function getTags() {
    $("#tag-content").empty();
    $.ajax({
        url:"/getTags",
        type:"post",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({"tagName":name,"remarks":remarks}),
        success:function (data) {
            if (data.code == 100){
                var tagHtml = '';
                $.each(data.obj,function (index,tag) {
                    tagHtml += '<a class="unselect-tag m-1" href="javascript:;" >'+tag.tagName+'</a>\n'
                })
                $("#tag-content").append(tagHtml);
                bind_search_event();
                bind_tag_click_event();
            }
        }
    })
}

function verifyInput(value) {
    console.info("name" + name);
    if(value == null||value == ""){
        return false;
    }else {
        return true;
    }
}

var bind_search_event = function () {
    var search_input = document.querySelector('#search-tag')
    search_input.addEventListener('input', function (evt) {
        var input = evt.target.value
        var search_input_trim = input.trim()
        if (search_input_trim && search_input_trim != '') {
            $('.unselect-tag').hide()
        }
        $('.unselect-tag').each(function () {
            var tag_text = this.text.toLowerCase()
            if (tag_text.includes(search_input_trim.toLowerCase())) {
                this.style.display = ''
            }
        })
    })
}

var template_tag = function (tag_text) {
    var tag = `
        <a class="btn btn-light btn-sm tag-input mr-2" data-tag-id="${tag_text}" href="javascript:;">
            ${tag_text}
            <span class="ml-2">✗</span>
        </a>
    `
    return tag
}

var bind_tag_click_event = function() {
    $('#tag-content').click(function (e) {
        if (e.target.classList.contains('unselect-tag')){
            var add_tag_btn = document.querySelector('#dropdown-button')
            var tag_text = e.target.text
            var tag = template_tag(tag_text)
            add_tag_btn.insertAdjacentHTML('beforeBegin', tag)
            bind_tag_close_event()
        }
    })
}

var bind_tag_close_event = function(){
    $('.ml-2').click(function (e) {
        e.target.parentNode.remove()
    })
}


bind_search_event()
bind_tag_click_event()
