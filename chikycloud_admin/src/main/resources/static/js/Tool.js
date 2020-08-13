/**
 * 成功提示并关闭所有弹窗
 * @param msg
 */
function toolMsgAndClose(msg) {
    if(isEmpty(msg))
    {
        msg = "成功";
    }
    layer.closeAll();
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
        msg = "成功";
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
        url : "../"+url,
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
 * 有返回值有参数ajax请求
 * @param url
 * @param data
 * @param callback
 */
function ajaxNOParam(url,callback) {
    $.ajax({
        url:"../"+url,
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
        url: "../" + url,
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
        url:"../"+url,
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
function layuiWindowsOpen(title,url,miniPage) {
    var content = miniPage.getHrefContent(url);
    var openWH = miniPage.getOpenWidthHeight();
    var index = layer.open({
        title: title,
        type: 1,
        shade: 0.2,
        maxmin:true,
        shadeClose: true,
        area: [openWH[0] + 'px', openWH[1] + 'px'],
        offset: [openWH[2] + 'px', openWH[3] + 'px'],
        content: content,
    });
    $(window).on("resize", function () {
        layer.full(index);
    });
}