function post(){
    var questionId = $("#question_id").val();
    var commentContent = $("#comment-content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parentId":questionId,
            "content":commentContent,
            "type":1
        }),
        success: function (response){
            if(response.code == 200){
                $("#comment_section").hide();
            }else {
                if(response.code == 2003){
                    var isAccepted = confirm(response.message);
                    if(isAccepted){
                        window.open("/login");
                        window.localStorage.setItem("closable","true");
                    }
                }else {
                    alert(response.message);
                }
            }
            console.log(response);
        },
        dataType: "json"
    });
    console.log(questionId);
    console.log(commentContent);
}