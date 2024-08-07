function getHelpCategoryTag() {
    $.ajax({
        type: "GET",
        url: "/help/getCategory",
        dataType: "json",
        success: function (result) {
            for (var i = 0; i < result.length; i++) {
                let htmlStr = `
                            <button type="button" class="btn btn-outline-primary categoryBtns"value='` + result[i].helpCategoryCode + `'>` + result[i].categoryName + `</button>
                        `;
                $('#categoryTag').append(htmlStr);
            }
        }
    });//
}

function dataFormat1(date) {
    date = new Date(date);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1 필요
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');

    return `${year}.${month}.${day} ${hours}:${minutes}`;
}