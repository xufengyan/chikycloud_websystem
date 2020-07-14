function draw (points) {

    //坐标区域长或宽，最大为300px。判断标准：坐标经度差与纬度差，大的那个为300px。再根据经度差与纬度差的比例计算出短的一边有多少px。
    var canvas = document.getElementById("myCanvas");

    //获取当前canvas外div的宽和高-----主要是为了适应各种电脑分辨率下，图像的显示问题
    var g_width =document.getElementById("graphic").offsetWidth;
    var g_height =document.getElementById("graphic").offsetHeight;
    //将当前div的宽高设置给canvas
    canvas.setAttribute("width",g_width);
    canvas.setAttribute("height",g_height);
    // alert(document.getElementById("myCanvas").offsetWidth - document.getElementById("myCanvas").offsetWidth*0.1);
    var MAXSIZE = g_width*0.83;
    var HSIZE = g_height*0.9;//图形大小
    var maxL = points[0]['L'];
    var maxB = points[0]['B'];
    var minL = points[0]['L'];
    var minB = points[0]['B'];

    var value;

    //获取最大最小经纬度差
    for(var i=0,pointsLen=points.length;i<pointsLen;i++){
        value = points[i];
        maxL = maxL <value['L']?value['L'] :maxL;
        maxB = maxB <value['B']?value['B'] :maxB;
        minL = minL >value['L']?value['L'] :minL;
        minB = minB >value['B']?value['B'] :minB;
    }

    var diffL = maxL - minL;//经度差
    var diffB = maxB - minB;//纬度差
    var width,height,Rate,diff;
    //计算坐标区域height width;

    // if(diffL == 0){
    //     width =MAXSIZE;
    //     height = MAXSIZE;
    //     Rate = MAXSIZE/parseFloat(diffB);
    // }
    // else if (diffB == 0) {
    //     width =MAXSIZE;
    //     height = MAXSIZE;
    //     Rate = MAXSIZE/parseFloat(diffL);
    // }else if(diffL >= diffB){
    //     diff = diffL;
    //     width = MAXSIZE;
    //     Rate = MAXSIZE/parseFloat(diffL);//单位坐标的有多少个px值。
    //     height = diffB/diffL*MAXSIZE;
    // }else {
    //     diff = diffB;
    //     height = MAXSIZE;
    //     Rate = MAXSIZE/parseFloat(diffB);//单位坐标的有多少个px值。
    //     width = diffL/diffB*MAXSIZE;
    // }
    if(diffL == 0){
        width =MAXSIZE;
        height = HSIZE;
        Rate = HSIZE/parseFloat(diffB);
    }
    else if (diffB == 0) {
        width =MAXSIZE;
        height = HSIZE;
        Rate = MAXSIZE/parseFloat(diffL);
    }else if(diffL >= diffB){
        diff = diffL;
        width = MAXSIZE;
        Rate = MAXSIZE/parseFloat(diffL);//单位坐标的有多少个px值。
        height = diffB/diffL*HSIZE;
    }else {
        diff = diffB;
        height = HSIZE;
        Rate = HSIZE/parseFloat(diffB);//单位坐标的有多少个px值。
        width = diffL/diffB*MAXSIZE;
    }
    var ctx = document.getElementById("myCanvas").getContext("2d");
    // var bodyWidth = document.body.offsetWidth;
    // if (2300>bodyWidth&&bodyWidth>1600){
    //     ctx.translate(35,30);
    // }else if (1024<bodyWidth&&bodyWidth<1600){
    //     ctx.translate(20,10);
    // }
    // alert(bodyWidth);
    //ctx.translate(40,30);//原点往右下方分别移动20px,多出来的是用来防止名字，以及点上的圆点显示不下

    // 根据B,L计算像素位置。计算应该有px。
    for(var k=0,pointsLen=points.length;k<pointsLen;k++){
        value = points[k];
        if(diffL == 0){
            points[k]['Lpx']  =MAXSIZE/2;
            points[k]['Bpx']  = parseInt(height - (value['B'] - minB)*Rate);
        }
        else if (diffB == 0) {
            points[k]['Lpx'] =parseInt((value['L'] - minL)* Rate);
            points[k]['Bpx'] = HSIZE/2;
        } else {
            points[k]['Lpx'] =  parseInt((value['L'] - minL)* Rate);
            points[k]['Bpx'] = parseInt(height - (value['B'] - minB)*Rate);
        }
    }

    maxL = parseInt((maxL - minL)* Rate);
    maxB = parseInt(height - (maxB - minB)*Rate);
    minL = parseInt((minL - minL)* Rate);
    minB = parseInt(height - (minB - minB)*Rate);
    // if ((maxL-minL)>0){
        //将图形居中显示
        ctx.translate((g_width - Math.abs(maxL-minL))/2,(g_height-Math.abs(maxB-minB))/2-Math.abs(maxB));
    // }


    ctx.font = "bold 14px Arial";
    ctx.textAlign = "left";
    ctx.textBaseline = "middle";

    ctx.lineWidth=2;
    for (var i=0;i<points.length-1;i++) {
        ctx.strokeStyle='rgb(29,143,254)';
        ctx.beginPath();
        // alert(points[i]['Lpx']);
        ctx.moveTo(points[i]['Lpx'] , points[i]['Bpx']);
        ctx.lineTo(points[i+1]['Lpx'],  points[i+1]['Bpx']);
        ctx.stroke();
    }


    // for(var x=0,pointsLen=points.length;x<pointsLen;x++){
    //     value = points[x];
    // i = x +1;
    // ctx.strokeStyle='red';
    //两两相连的处理
    //   while (i<pointsLen) {
    //     ctx.beginPath();
    //     ctx.moveTo(value['Lpx'] , value['Bpx']);
    //     ctx.lineTo(points[i]['Lpx'],  points[i]['Bpx']);
    //     ctx.stroke();
    //     i++;
    // }
    //画点名
    //   ctx.beginPath();
    // ctx.fillStyle ='black';
    // ctx.fillText(value.Name, value['Lpx']+5, value['Bpx']);
    //画点
    // ctx.fillStyle = 'rgb(29,143,254)';
    // ctx.beginPath();
    // ctx.arc(value['Lpx'] , value['Bpx'] , 1, 0, Math.PI*2, false);
    // ctx.fill();
    // }

    //起点和终点连线
    ctx.setLineDash([1,3]);
    ctx.lineWidth = 1;
    ctx.strokeStyle = 'red';
    ctx.beginPath();
    ctx.moveTo(points[0]['Lpx'] , points[0]['Bpx']);
    ctx.lineTo(points[points.length-1]['Lpx'],  points[points.length-1]['Bpx']);
    ctx.stroke();

    //起点和终点设置红色的点
    ctx.fillStyle = 'rgb(255,255,0)';
    ctx.beginPath();
    ctx.arc(points[0]['Lpx'] , points[0]['Bpx'] , 3, 0, Math.PI*2, false);
    ctx.fill();

    ctx.fillStyle = 'rgb(0,255,0)';
    ctx.beginPath();
    ctx.arc(points[points.length-1]['Lpx'] , points[points.length-1]['Bpx'] , 3, 0, Math.PI*2, false);
    ctx.fill();

}