function reply() {
    var parentId = $("#parentId").val();
    var comment = $("#comment").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        data: JSON.stringify({
            "parentId": parentId,
            "description": comment,
            "parentType": 1
        }),
        success: function(response) {
            console.log(response);
        },
        dataType: "json",
        contentType: "application/json"
    })
}