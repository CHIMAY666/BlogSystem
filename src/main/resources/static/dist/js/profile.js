$(function () {
    let updateUserNameButton = $('#updateUserNameButton');
    let updatePasswordButton = $('#updatePasswordButton');
    //修改个人信息
    updateUserNameButton.click(function () {
        updateUserNameButton.attr("disabled",true);
        let userName = $('#loginUserName').val();
        let nickName = $('#nickName').val();
        if (validUserNameForUpdate(userName, nickName)) {
            //ajax提交数据
            let params = $("#userNameForm").serialize();
            $.ajax({
                type: "POST",
                url: "/admin/profile/name",
                data: params,
                success: function (r) {
                    if (r === 'success') {
                        alert('修改成功');
                    } else {
                        alert('修改失败');
                        $("#updateUserNameButton").prop("disabled",false);
                    }
                }
            });
        } else {
            $("#updateUserNameButton").prop("disabled",false);
        }
    });
    //修改密码
    updatePasswordButton.click(function () {
        updatePasswordButton.attr("disabled",true);
        let originalPassword = $('#originalPassword').val();
        let newPassword = $('#newPassword').val();
        if (validPasswordForUpdate(originalPassword, newPassword)) {
            let params = $("#userPasswordForm").serialize();
            $.ajax({
                type: "POST",
                url: "/admin/profile/password",
                data: params,
                success: function (r) {
                    console.log(r);
                    if (r === 'success') {
                        alert('修改成功');
                        window.location.href = '/admin/login';
                    } else {
                        alert('修改失败，请检查原密码是否正确！');
                        $("#updatePasswordButton").attr("disabled",false);
                    }
                }
            });
        } else {
            $("#updatePasswordButton").attr("disabled",false);
        }
    });
})

/**
 * 名称验证
 */
function validUserNameForUpdate(userName, nickName) {
    let updateUserNameInfo = $('#updateUserName-info');
    if (isNull(userName) || userName.trim().length < 1) {
        updateUserNameInfo.css("display", "block");
        updateUserNameInfo.html("请输入登陆名称！");
        return false;
    }
    if (isNull(nickName) || nickName.trim().length < 1) {
        updateUserNameInfo.css("display", "block");
        updateUserNameInfo.html("昵称不能为空！");
        return false;
    }
    if (!validUserName(userName)) {
        updateUserNameInfo.css("display", "block");
        updateUserNameInfo.html("请输入符合规范的登录名！");
        return false;
    }
    if (!validCN_ENString2_18(nickName)) {
        updateUserNameInfo.css("display", "block");
        updateUserNameInfo.html("请输入符合规范的昵称！");
        return false;
    }
    return true;
}

/**
 * 密码验证
 */
function validPasswordForUpdate(originalPassword, newPassword) {
    let updatePasswordInfo = $('#updatePassword-info');
    if (isNull(originalPassword) || originalPassword.trim().length < 1) {
        updatePasswordInfo.css("display", "block");
        updatePasswordInfo.html("请输入原密码！");
        return false;
    }
    if (isNull(newPassword) || newPassword.trim().length < 1) {
        updatePasswordInfo.css("display", "block");
        updatePasswordInfo.html("新密码不能为空！");
        return false;
    }
    return true;
}
