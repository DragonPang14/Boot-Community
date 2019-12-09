function editQuestion() {
    var id = $("#question_id").val();
    window.location.href = "/publish/" + id;
}