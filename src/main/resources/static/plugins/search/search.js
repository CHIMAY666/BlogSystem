// 搜索栏回车搜索
$(function () {
    $('#searchbox').keypress(function (e) {
        let key = e.which; //e.which是按键的值
        if (key === 13) {
            let q = $(this).val();
            if (q && q !== '') {
                window.location.href = '/search/' + q;
            }
        }
    });
});

function search() {
    let q = $('#searchbox').val();
    if (q && q !== '') {
        window.location.href = '/search/' + q;
    }
}