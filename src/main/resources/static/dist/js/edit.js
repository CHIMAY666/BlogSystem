let blogEditor;
// Tags Input
$('#blogTags').tagsInput({
    width: '100%',
    height: '38px',
    defaultText: '文章标签'
});

//Initialize Select2 Elements
$('.select2').select2()

$(function () {
    blogEditor = editormd("blog-editormd", {
        width: "100%",
        height: 640,
        syncScrolling: "single",
        path: "/plugins/editormd/lib/",
        toolbarModes: 'full',
    });
});

$('#confirmButton').click(function () {
    let blogTitle = $('#blogName').val();
    let blogCategoryId = $('#blogCategoryId').val();
    let blogTags = $('#blogTags').val();
    let blogContent = blogEditor.getMarkdown();
    if (isNull(blogTitle)) {
        swal("请输入文章标题", {
            icon: "error",
        });
        return;
    }
    if (!validLength(blogTitle, 150)) {
        swal("标题过长", {
            icon: "error",
        });
        return;
    }
    if (isNull(blogCategoryId)) {
        swal("请选择文章分类", {
            icon: "error",
        });
        return;
    }
    if (isNull(blogTags)) {
        swal("请输入文章标签", {
            icon: "error",
        });
        return;
    }
    if (!validLength(blogTags, 150)) {
        swal("标签过长", {
            icon: "error",
        });
        return;
    }
    if (isNull(blogContent)) {
        swal("请输入文章内容", {
            icon: "error",
        });
        return;
    }
    if (!validLength(blogTags, 100000)) {
        swal("文章内容过长", {
            icon: "error",
        });
        return;
    }
    $('#articleModal').modal('show');
});

$('#saveButton').click(function () {
    let blogId = $('#blogId').val();
    let blogTitle = $('#blogName').val();
    let blogCategoryId = $('#blogCategoryId').val();
    let blogTags = $('#blogTags').val();
    let blogContent = blogEditor.getMarkdown();
    let blogCoverImage = $('#blogCoverImage')[0].src;
    let blogStatus = $("input[name='blogStatus']:checked").val();
    let enableComment = $("input[name='enableComment']:checked").val();
    if (isNull(blogCoverImage) || blogCoverImage.indexOf('img-upload') !== -1) {
        swal("封面图片不能为空", {
            icon: "error",
        });
        return;
    }
    let url = '/admin/blogs/save';
    let swlMessage = '保存成功';
    let data = {
        "blogTitle": blogTitle, "blogCategoryId": blogCategoryId,
        "blogTags": blogTags, "blogContent": blogContent, "blogCoverImage": blogCoverImage, "blogStatus": blogStatus,
        "enableComment": enableComment
    };
    if (blogId > 0) {
        url = '/admin/blogs/update';
        swlMessage = '修改成功';
        data = {
            "blogId": blogId,
            "blogTitle": blogTitle,
            "blogCategoryId": blogCategoryId,
            "blogTags": blogTags,
            "blogContent": blogContent,
            "blogCoverImage": blogCoverImage,
            "blogStatus": blogStatus,
            "enableComment": enableComment
        };
    }
    console.log(data);
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        data: data,
        success: function (result) {
            if (result.resultCode == 200) {
                $('#articleModal').modal('hide');
                swal({
                    title: swlMessage,
                    type: 'success',
                    showCancelButton: false,
                    confirmButtonColor: '#3085d6',
                    confirmButtonText: '返回博客列表',
                    confirmButtonClass: 'btn btn-success',
                    buttonsStyling: false
                }).then(function () {
                    window.location.href = "/admin/blogs";
                })
            }
            else {
                $('#articleModal').modal('hide');
                swal(result.message, {
                    icon: "error",
                });
            }
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });
});

$('#cancelButton').click(function () {
    window.location.href = "/admin/blogs";
});

/**
 * 随机封面功能
 */
$('#randomCoverImage').click(function () {
    let rand = parseInt((Math.random() * 40 + 1).toString());
    let cover_image = $("#blogCoverImage");
    cover_image.attr("src", '/dist/img/rand/' + rand + ".jpg");
    cover_image.attr("style", "width:160px ;height: 120px;display:block;");
});
