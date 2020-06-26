function reply(type, replyTo) {
    var parentId;
    var comment;
    if (type === 1) {
        parentId = $("#parentId").val();
        comment = $("#comment").val();
    } else {
        parentId = $("#parentIdOf"+replyTo).val();
        comment = $("#reply"+replyTo).val();
    }

    if (comment === "") {
        alert("Message Must Not Be Empty!");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        data: JSON.stringify({
            "parentId": parentId,
            "description": comment,
            "parentType": type,
            "replyTo": replyTo
        }),
        success: function(response) {
            if (response.code === 200) {
                if (type === 1) {
                    $("#comment").removeClass("show").val('');
                } else {
                    $("#reply"+replyTo).removeClass("show").val('');
                }
                window.location.reload();
            } else if (response.code === 204) {
                let accept = confirm(response.message);
                if (accept) {
                    window.open("https://github.com/login/oauth/authorize?client_id=2b20d5da0f96da6781fa&redirect_uri=http://localhost:8888/callback&state=1");
                    window.localStorage.setItem("closePage", "true");
                }
            } else {
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json",
        contentType: "application/json"
    })
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var old = $("#tag").val();
    var oldSplit = old.split(",");
    if (!oldSplit.includes(value)) {
        if (old !== "") {
            $("#tag").val(old + "," + value);
        } else {
            $("#tag").val(value);
        }
    }
}