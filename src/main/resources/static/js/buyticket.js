window.onload=function (){
    //对应方法2
    var key=localStorage.getItem('key');

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

    $.ajax({
        type:"GET",
        url:"/options?Mno="+key,
        contentType: "charset=utf-8",
        success:function (datalist) {
            showTheaterOptioins(datalist)
        },
        error:function () {
            alert("连接服务器超时")
        }
    })
};

function showMovieDetail(data) {
    $(".avatar").attr('src','/image?path='+data["MposterPath"]);
    $(".movie-brief-container h3").text(data["Mname"]);
    $(".movie-brief-container .ename").text(data["MEnglishName"]);
    $(".movie-brief-container #type").text(data["Mtype"]);
    var times = data["Mduration"].split(":");
    var time = parseInt(times[0])*60+parseInt(times[1]);
    if(data["Mlanguage"]==="English"){
        $(".movie-brief-container #origin").text(time+"分钟/英文");
    }else {
        $(".movie-brief-container #origin").text(time+"分钟/中文");
    }

    $(".movie-brief-container #time").text(data["Mdate"]+"大陆上映");

    $("#my-score").text(data["Mrating"]);
    var num = data["MscoreNumber"];
    if(num>=10000){
        $("#score-num-my").text( Math.ceil(num/10000)+"万")
    }else
        $("#score-num-my").text(num);

    $("#my-box-office").text(data["MboxOffice"]);
}

var time=0,pinpai=-1,xingzhengqu=-1,teshuting=-1,optionsData,Tno;
function showTheaterOptioins(data) {
    optionsData=data;

    showTime(data[0]);
    showPinPai(data[1]);
    showXingZhengQu(data[2]);
    showTeShuTing(data[3]);

    setActive(0,0,0,0);

    /*
    -1代表全部，其他的代表在data中的位置
     */
    sendTheaterListAjax(time,pinpai,xingzhengqu,teshuting,optionsData)
}

function sendTheaterListAjax(time,pinpai,xingzhengqu,teshuting,data) {

    debugger;
    var url="/theaters?Mno="+localStorage.getItem('key')+"&Sdate="+data[0][time];
    if(pinpai===-1){
        url+="&Tbrand=";
    }else {
        url+="&Tbrand="+data[1][pinpai];
    }
    if(xingzhengqu===-1){
        url+="&location=";
    }else {
        url+="&location="+data[2][xingzhengqu];
    }
    if(teshuting===-1){
        url+="&roomType=";
    }else {
        url+="&roomType="+data[3][teshuting];
    }
    url+="&Tno=";
    $.ajax({
        type:"GET",
        url:url,
        contentType:"charset=utf-8",
        success:function (data) {
            console.log(data);
            showTheaterList(data)
        },
        error:function () {
            alert("连接服务器超时")
        }
    })
}

function showTheaterList(theaterlist) {
    $(".my-cinema-infos").each(function (i) {
        if(i!==0){
            $(this).remove();
        }
    });
    for(var i=1,len=theaterlist.length;i<len;i++){
        $("#my-cinema-cell").append($("#my-cinema-info").clone());
    }
    $(".cinema-info a").each(function (i) {
        $(this).text(theaterlist[i]["tname"]);
    });
    $(".cinema-info p").each(function (i) {
        $(this).text(theaterlist[i]["taddress"]);
    });
    $(".buy-btn a").each(function (i) {
        $(this).attr('name',theaterlist[i]["tno"]);

        //设置点击之后的跳转页面的逻辑
        $(this).click(function (event) {
            Tno = $(event.target).attr("name");
            debugger;
            localStorage.setItem('Sdate',optionsData[0][parseInt(time)]);
            if(parseInt(pinpai)===-1){
                localStorage.setItem('Tbrand',"全部");
            }else {
                localStorage.setItem('Tbrand',optionsData[1][parseInt(pinpai)]);
            }
            if(parseInt(xingzhengqu)===-1){
                localStorage.setItem('location',"全部");
            }else {
                localStorage.setItem('location',optionsData[2][parseInt(xingzhengqu)]);
            }
            if(parseInt(teshuting)===-1){
                localStorage.setItem('roomType',"全部");
            }else {
                localStorage.setItem('roomType',optionsData[3][parseInt(teshuting)]);
            }
            localStorage.setItem('Tno',Tno);
        })
    })
}

function setActive(a,b,c,d) {
    debugger;
    console.log(a,b,c,d);
    $("#my-ul-time li").each(function (i) {
        if(i===a){
            $(this).attr("class","active")
        }else {
            $(this).removeAttr("class")
        }
    });
    $("#my-ul-pinpai li").each(function (i) {
        if(i===b){
            $(this).attr("class","active")
        }else {
            $(this).removeAttr("class")
        }
    });
    $("#my-ul-xingzhengqu li").each(function (i) {
        if(i===c){
            $(this).attr("class","active")
        }else {
            $(this).removeAttr("class")
        }
    });
    $("#my-ul-teshuting li").each(function (i) {
        console.log(i);
        if(i===d){
            console.log('equal');
            $(this).attr("class","active")
        }else {
            $(this).removeAttr("class")
        }
    });
}

function showTime(data) {
    for(var i=1;i<data.length;i++){
        $("#my-ul-time").append($("#my-time").clone())
    }
    $("#my-time a").each(function (i) {
        var date = data[i].split("-");
        $(this).text(date[0]+"年"+date[1]+"月"+date[2]+"日");
        $(this).attr('my-val',i);

        $(this).click(function (event) {
            time = $(event.target).attr("my-val");
            setActive(parseInt(time),parseInt(pinpai)+1,parseInt(xingzhengqu)+1,parseInt(teshuting)+1);
            sendTheaterListAjax(parseInt(time),parseInt(pinpai),parseInt(xingzhengqu),parseInt(teshuting),optionsData)
        })
    })
}


function showPinPai(data) {

    for(var i=0;i<data.length;i++){
        $("#my-ul-pinpai").append($("#my-pinpai").clone())
    }
    $("#my-pinpai a").each(function (i) {
        if(i>0){
            $(this).text(data[i-1])
        }
        $(this).attr('my-val',i-1);
        $(this).click(function (event) {
            pinpai = $(event.target).attr("my-val");
            setActive(parseInt(time),parseInt(pinpai)+1,parseInt(xingzhengqu)+1,parseInt(teshuting)+1);
            sendTheaterListAjax(parseInt(time),parseInt(pinpai),parseInt(xingzhengqu),parseInt(teshuting),optionsData)
        })
    })
}

function showXingZhengQu(data) {
    for(var i=0;i<data.length;i++){
        $("#my-ul-xingzhengqu").append($("#my-xingzhengqu").clone())
    }
    $("#my-xingzhengqu a").each(function (i) {
        if(i>0){
            $(this).text(data[i-1])
        }
        $(this).attr('my-val',i-1);
        $(this).click(function (event) {
            xingzhengqu = $(event.target).attr("my-val");
            setActive(parseInt(time),parseInt(pinpai)+1,parseInt(xingzhengqu)+1,parseInt(teshuting)+1);
            sendTheaterListAjax(parseInt(time),parseInt(pinpai),parseInt(xingzhengqu),parseInt(teshuting),optionsData)
        })
    })
}

function showTeShuTing(data) {
    for(var i=0;i<data.length;i++){
        $("#my-ul-teshuting").append($("#my-teshuting").clone())
    }
    $("#my-teshuting a").each(function (i) {
        if(i>0){
            $(this).text(data[i-1])
        }
        $(this).attr('my-val',i-1);
        $(this).click(function (event) {
            teshuting = $(event.target).attr("my-val");
            setActive(parseInt(time),parseInt(pinpai)+1,parseInt(xingzhengqu)+1,parseInt(teshuting)+1);
            sendTheaterListAjax(parseInt(time),parseInt(pinpai),parseInt(xingzhengqu),parseInt(teshuting),optionsData)
        })
    })
}