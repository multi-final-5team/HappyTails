function getHelpCategoryTag() {
    $.ajax({
        type: "GET",
        url: "/help/getCategory",
        dataType: "json",
        success: function (result) {
            for (var i = 0; i < result.length; i++) {
                let htmlStr = `
                            <button class='categoryBtns' value='` + result[i].helpCategoryCode + `'>` + result[i].categoryName + `</button>
                        `;
                $('#categoryTag').append(htmlStr);
            }
        }
    });
}