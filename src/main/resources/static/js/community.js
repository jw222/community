function reply(type) {
    let parentId = $("#parentId").val();
    let comment = $("#comment").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        data: JSON.stringify({
            "parentId": parentId,
            "description": comment,
            "parentType": type
        }),
        success: function(response) {
            if (response.code === 200) {
                $("#replyForm").removeClass("show");
            } else {
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json",
        contentType: "application/json"
    })
}