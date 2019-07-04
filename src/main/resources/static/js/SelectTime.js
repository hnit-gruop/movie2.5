window.onload=function (){
    debugger;
    //对应方法2
    var key=localStorage.getItem('key');
    var time=localStorage.getItem('Sdate');
    var pinpai=localStorage.getItem('Tbrand');
    var xingzhengqu=localStorage.getItem('location');
    var teshuting=localStorage.getItem('roomType');
    var tno=localStorage.getItem('Tno');

    $.ajax({
        type:"GET",
        url:"/TheaterDetail?Tno="+key,
        contentType:"charset=utf-8",
        success:function (data) {
            showTheater(data)
        },
        error:function () {
            alert("连接服务器出错")
        }
    });

    $.ajax({
        type:"GET",
        contentType:"charset=utf-8",
        url: "/MovieDetails?"+"Mno="+key,
        success: function (data) {
            showMovieDetail(data)
        },
        error: function () {
            alert("连接服务器超时");
        }
    });
    var url="/Scenes?Mno="+key+"&Sdate="+time;
    if(pinpai==="全部"){
        url+="&Tbrand=";
    }else {
        url+="&Tbrand="+pinpai;
    }
    if(xingzhengqu==="全部"){
        url+="&location=";
    }else {
        url+="&location="+xingzhengqu;
    }
    if(teshuting==="全部"){
        url+="&roomType=";
    }else {
        url+="&roomType="+teshuting;
    }
    url+="&Tno="+tno;
    $.ajax({
        type:"GET",
        contentType:"charset=utf-8",
        url:url,
        success: function (data) {
            showTimeList(data)
        },
        error: function () {
            alert("连接服务器超时");
        }
    });
};

function showMovieDetail(data) {
    $(".movie-name").text(data["Mname"]);
    $(".score").text(data["Mrating"]);
    var times = data["Mduration"].split(":");
    var time = parseInt(times[0])*60+parseInt(times[1]);
    $("#my-movie-time-value").text(time+"分钟");
    $("#my-movie-type-value").text(data["Mtype"]);
}


function showTheater(data) {
    $(".cinema-brief-container h3").text(data["tname"]);
    $(".address").text(data["taddress"]);
    $(".telphone span").text(data["ttel"]);
}

function showTimeList(data) {
    console.log(data);
    for(var i=1,len=data.length;i<len;i++){
        $("#my-time-body").append($(".my-time-row:last").clone());
    }
    $("#my-time-body tr").each(function (i) {
        $(this).find('span').each(function (k) {
            if(k===0){
                $(this).text(data[i]["beginTime"]);
            } else if(k===1){
                $(this).text(data[i]["language"]);
            } else if(k===2){
                $(this).text(data[i]["roomName"]);
            } else if(k===4){
                $(this).text(data[i]["price"]);
            }
        });
        $(this).find('a').each(function () {
            //只有一个a标签
            $(this).click(function () {
                debugger;
                localStorage.setItem('Sno',data[i]["sno"]);

                //判断是否登录了
                var isLogin = localStorage.getItem('isLogin');
                if(isLogin==null||isLogin!=="true"){
                    window.location.href = "../pages/Login.html";
                    localStorage.setItem('oldPage',location.href);
                }else if(isLogin==="true"){
                    window.location.href = "../pages/SelectSeat.html";
                }
            })
        })
    })

}
