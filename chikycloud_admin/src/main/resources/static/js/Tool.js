/**
 * 成功提示并关闭所有弹窗
 * @param msg
 */
function toolMsgAndClose(msg) {
    if(isEmpty(msg))
    {
        msg = "成功";
    }
    parent.layer.closeAll();
    layer.msg(msg, {icon: 1});
}

function toolMsgAndCloseNocpm(msg) {
    if(isEmpty(msg))
    {
        msg = "成功";
    }
    layer.msg(msg, {icon: 1});
}

/**
 * 失败提示弹框
 * @param msg
 */
function toolMsgAndDefeatedClose(msg) {
    if(isEmpty(msg))
    {
        msg = "失败";
    }
    layer.msg(msg, {icon: 2});
}



/**
 * 判断控空字符串
 * @param obj
 * @returns {boolean}
 */
function isEmpty(obj){

    if(obj == null ||  obj == undefined )
    {
        return true;
    }
    //当传入为字符串时使用此方法
    if(Object.prototype.toString.call(obj) === "[object String]")
    {
        if(obj == '' || obj == 'undefined' || obj == 'null')
        {
            return true;
        }
    }

    return false;
}

/**
 * 有返回值有参数ajax请求
 * @param url
 * @param data
 * @param callback
 */
function ajaxResult(url,data,callback) {
    $.ajax({
        url : ctx+"/"+url,
        type : "POST",
        // contentType : 'application/json',
        async: true,
        dataType:'json',
        data :data,
        success : function(res) {
            callback(res)
        },
        error:function () {
            alert("数据传递错误，请刷新页面");
        }
    });

}

/**
 * 有返回值无参数ajax请求
 * @param url
 * @param data
 * @param callback
 */
function ajaxNOParam(url,callback) {
    $.ajax({
        url:ctx+"/"+url,
        type:'post',
        async: true,
        dataType:'json',
        contentType: 'application/json;charset=UTF-8',
        success : function(res) {
            callback(res)
        },
        error:function () {
            alert("数据传递错误，请刷新页面");
        }
    });

}



/**
 * 参数无大小写ajax请求
 * @param url
 * @param data
 * @param callback
 */
function ajaxLayuiFrom(url,data,callback) {
    $.ajax({
        url: ctx+ "/" + url,
        type: 'post',
        // dataType: 'html',
        data: JSON.stringify(data.field),
        dataType: 'json',
        contentType: 'application/json',
        // dataType : 'json',
        // contentType: 'application/json;charset=UTF-8',
        success: function (data) {
            //关闭弹框
            toolMsgAndClose();
            // window.parent.brush();
        },
        error: function () {
            alert("数据传递错误，请刷新页面");
        }
    });
}

/**
 * 当传递的参数有大写字母的时候用ajaxLayuiFrom()方法后台无法接收到参数
 * 使用这个方法解决这个问题
 */
function ajaxLayuiFromParamMax(url,data,callback) {
    $.ajax({
        url:ctx+"/"+url,
        type:'post',
        data:data.field,
        dataType:'json',
        success:function (data) {
            //关闭弹框
            callback(data)
        },
        error:function () {
            alert("数据传递错误，请刷新页面");
        }
    })
}

/**
 * 通用弹出层，需要传递参数的直接拼接在地址后面
 * @param url
 */
// function layuiWindowsOpen(title,url,miniPage) {
//     var content = miniPage.getHrefContent(url);
//     var openWH = miniPage.getOpenWidthHeight();
//     var index = layer.open({
//         title: title,
//         type: 1,
//         shade: 0.2,
//         maxmin:true,
//         shadeClose: true,
//         area: [openWH[0] + 'px', openWH[1] + 'px'],
//         offset: [openWH[2] + 'px', openWH[3] + 'px'],
//         content: content,
//     });
//     $(window).on("resize", function () {
//         layer.full(index);
//     });
// }
/**
 * 当前页面
 * 通用弹出层，需要传递参数的直接拼接在地址后面
 * @param url
 */
function layuiWindowsOpenSublevel(title,url) {
    var index = layer.open({
        type: 2,        //iframe窗
        title: title,
        shadeClose: false,
        shade: 0.3,
        offset: 'auto',
        maxmin: true, //开启最大化最小化按钮
        area: ['80%', '80%'],
        content: ctx+"/"+url,
    });
    $(window).on("resize", function () {
        layer.full(index);
    });
}

/**
 * 在父级页面打开弹框
 *
 */
function layuiWindowsParentOpensl(title,url) {
    var index = parent.layer.open({
        type: 2,        //iframe窗
        title: title,
        shadeClose: true,
        shade: 0.3,
        offset:  'auto',
        maxmin: true, //开启最大化最小化按钮
        area: ['100%', '100%'],
        content: ctx+"/"+url,
    });
    $(window).on("resize", function () {
        layer.full(index);
    });
}


/**
 * 转换时间
 * @param data
 * @returns {string}
 */
function date(data) {
    var date = new Date(data)
    var Y = date.getFullYear() + '-'
    var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-'
    var D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' '
    var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':'
    var m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':'
    var s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds())
    return Y + M + D + h + m + s
}

/**
 * 随机数
 * @returns {number}
 * @param num 随机范围
 */
function randomNum(max,min) {
    return Math.floor(Math.random()*(max-min+1)+min);
}