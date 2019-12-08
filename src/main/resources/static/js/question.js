function editQuestion() {
    var id = $("#question_id").val();
    $.get("/publish/" + id);
}