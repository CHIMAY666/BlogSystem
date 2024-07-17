/**
 * 判空
 *
 * @param obj
 * @returns {boolean}
 */
function isNull(obj) {
    return obj == null || obj.trim() === "";
}

/**
 * 参数长度验证
 *
 * @param obj
 * @param length
 * @returns {boolean}
 */
function validLength(obj, length) {
    return obj.trim().length < length;
}

/**
 * url验证
 *
 * @returns {boolean}
 * @param str_url
 */
function isURL(str_url) {
    let strRegex = "^((https|http|ftp|rtsp|mms)?://)"
        + "(([0-9]{1,3}\.){3}[0-9]{1,3}"
        + "|"
        + "([0-9a-zA-Z_!~*'()-]+\.)*"
        + "([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\."
        + "[a-zA-Z]{2,6})"
        + "(:[0-9]{1,4})?"
        + "((/?)|"
        + "(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";
    let re = new RegExp(strRegex);
    return re.test(str_url);
}

/**
 * 用户名称验证 4到16位（字母，数字，下划线，减号）
 *
 * @param userName
 * @returns {boolean}
 */
function validUserName(userName) {
    let pattern = /^[a-zA-Z0-9_-]{4,16}$/;
    return pattern.test(userName.trim());
}

/**
 * 正则匹配2-18位的中英文字符串
 *
 * @param str
 * @returns {boolean}
 */
function validCN_ENString2_18(str) {
    let pattern = /^[a-zA-Z0-9-\u4E00-\u9FA5_,， ]{2,18}$/;
    return pattern.test(str.trim());
}

/**
 * 正则匹配2-100位的中英文字符串
 *
 * @param str
 * @returns {boolean}
 */
function validCN_ENString2_100(str) {
    let pattern = /^[a-zA-Z0-9-\u4E00-\u9FA5_,， ]{2,100}$/;
    return pattern.test(str.trim());
}

/**
 * 用户密码验证 最少6位，最多20位字母或数字、特殊字符的组合
 *
 * @param password
 * @returns {boolean}
 */
function validPassword(password) {
    let pattern = /^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)])+$)^.{6,20}$/;
    return pattern.test(password.trim());
}

<!-- 正则验证 end-->

/**
 * 获取jqGrid选中的一条记录
 * @returns {*}
 */
function getSelectedRow() {
    let grid = $("#jqGrid");
    let rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        swal("请选择一条记录", {
            icon: "warning",
        });
        return;
    }
    var selectedIDs = grid.getGridParam("selarrrow");
    if (selectedIDs.length > 1) {
        swal("只能选择一条记录", {
            icon: "warning",
        });
        return;
    }
    return selectedIDs[0];
}

/**
 * 获取jqGrid选中的一条记录(不出现弹框)
 * @returns {*}
 */
function getSelectedRowWithoutAlert() {
    let grid = $("#jqGrid");
    let rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        return;
    }
    let selectedIDs = grid.getGridParam("selarrrow");
    if (selectedIDs.length > 1) {
        return;
    }
    return selectedIDs[0];
}

/**
 * 获取jqGrid选中的多条记录
 * @returns {*}
 */
function getSelectedRows() {
    let grid = $("#jqGrid");
    let rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        swal("请选择一条记录", {
            icon: "warning",
        });
        return;
    }
    return grid.getGridParam("selarrrow");
}