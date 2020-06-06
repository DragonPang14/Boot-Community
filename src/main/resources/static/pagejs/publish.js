$(function () {
    getTags();
});

function publish() {
    var title = $("#title").val();
    var description = $("#description").val();
    var questionId = $("#question-id").val();
    var tag = '';
    $(".selected-tag").each(function () {
        tag += $(this).data("tag-id");
        tag += ',';
    });
    if (title == null || title == "") {
        alert("请输入标题！");
        return;
    }
    if (description == null || description == "") {
        alert("请输入描述！");
        return;
    }
    if (tag == ''){
        alert("请选择标签！");
        return;
    }
    $.ajax({
        url: "/doPublish",
        dataType: "json",
        type: "post",
        contentType: "application/json",
        data: JSON.stringify({"id":questionId,"title": title, "description": description,"tag":tag}),
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
    $("#tag-modal .form-control").each(function () {
        if (!verifyInput($(this).val())){
            $(this).addClass("is-invalid").attr("placeholder","必填！");
            flag = false;
        }
    });
    console.info(flag);
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
                    alert("创建成功，请重新进入页面使用新标签");
                    $("#tag-modal").modal('hide');
                }else {
                    alert(data.msg);
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
        success:function (data) {
            if (data.code == 100){
                var tagHtml = '';
                $.each(data.obj,function (index,tag) {
                    tagHtml += '<a class="unselect-tag m-1" href="javascript:;" id="'+tag.id+'">'+tag.tagName+'</a>\n'
                })
                $("#tag-content").append(tagHtml);
            }
        }
    })
}

function verifyInput(value) {
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

var template_tag = function (tag_text,tag_id) {
    var tag = `
        <a class="btn btn-light btn-sm selected-tag mr-2" data-tag-id="${tag_id}" href="javascript:;">
            ${tag_text}
            <span class="ml-2">✗</span>
        </a>
    `
    return tag
}

var check_tag_limit = function () {
    var add_tag_btn = document.querySelector('#dropdown-button')
    var p = add_tag_btn.parentNode
    ++p.dataset.tagNums
    var max = p.dataset.tagMaxNum
    var tag_nums = p.dataset.tagNums
    if (tag_nums >= max) {
        add_tag_btn.disabled = true
    }
}

var hide_tag = function (tag) {
    tag.hidden = true
    tag.classList.remove('unselect-tag')
    tag.classList.add('hidden-tag')
}

var show_tag = function (id) {
    $('.hidden-tag').each(function () {
        if (this.id == id) {
            this.classList.add('unselect-tag')
            this.classList.remove('hidden-tag')
            this.hidden = false
        }
    })
}

var bind_tag_click_event = function() {
    $('#tag-content').click(function (e) {
        if (e.target.classList.contains('unselect-tag')){
            var tag_text = e.target.text
            var tag_id = e.target.id
            var tag = template_tag(tag_text,tag_id)
            var add_tag_btn = document.querySelector('#dropdown-button')
            add_tag_btn.insertAdjacentHTML('beforeBegin', tag)
            bind_tag_close_event()
            check_tag_limit()
            hide_tag(e.target)
        }
    })
}

var bind_tag_close_event = function(){
    $('.ml-2').click(function (e) {
        var p = e.target.parentNode
        --p.parentNode.dataset.tagNums
        var add_tag_btn = document.querySelector('#dropdown-button')
        add_tag_btn.disabled = false
        var id = p.dataset.tagId
        show_tag(id)
        p.remove()
    })
}


bind_search_event()
bind_tag_click_event()
