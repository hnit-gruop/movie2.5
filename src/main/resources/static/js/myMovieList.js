window.onload=function (){
    $.ajax({
        type:"GET",
        url:"/MovieOptions",
        contentType:"charset=utf-8",
        success:function (data) {
            debugger;
            console.log(data);
            showMovieListOptions(data)
        },
        error:function () {
            alert("连接服务器超时")
        }
    })
};

var MovieType=0,MovieLocation=0,MovieTime=0,MovieOptionData;
function showMovieListOptions(data) {
    MovieOptionData=data;
    
    showMovieType(data[0]);
    showMovieLocation(data[1]);
    showMovieTime(data[2]);

    //0,0,0代表将“全部”设置为active
    setMovieListOptionActive(0,0,0);

    sendMovieListByOptionAjax(0,0,0,data);
}

function showMovieType(data) {
    for(var i=0;i<data.length;i++){
        $(".tags-lines li[data-val='类型'] ul").append($(".tags-lines li[data-val='类型'] ul li:last").clone());
    }
    $(".tags-lines li[data-val='类型'] ul li a").each(function (i) {
        if(i!==0){
            $(this).text(data[i-1]);
            $(this).attr('data-val',data[i-1])
        }
        $(this).attr('my-val',i);
        $(this).click(function (event) {
            MovieType = $(event.target).attr("my-val");
            setMovieListOptionActive(parseInt(MovieType),parseInt(MovieLocation),parseInt(MovieTime));
            sendMovieListByOptionAjax(parseInt(MovieType)-1,parseInt(MovieLocation)-1,parseInt(MovieTime)-1)
        })
    });

}

function showMovieLocation(data) {
    for(var i=0;i<data.length;i++){
        $(".tags-lines li[data-val='区域'] ul").append($(".tags-lines li[data-val='区域'] ul li:last").clone());
    }
    $(".tags-lines li[data-val='区域'] ul li a").each(function (i) {
        if(i!==0){
            $(this).text(data[i-1]);
            $(this).attr('data-val',data[i-1])
        }
        $(this).attr('my-val',i);
        $(this).click(function (event) {
            MovieLocation = $(event.target).attr("my-val");
            setMovieListOptionActive(parseInt(MovieType),parseInt(MovieLocation),parseInt(MovieTime));
            sendMovieListByOptionAjax(parseInt(MovieType)-1,parseInt(MovieLocation)-1,parseInt(MovieTime)-1)
        })
    });
}

function showMovieTime(data) {
    for(var i=0;i<data.length;i++){
        $(".tags-lines li[data-val='语言'] ul").append($(".tags-lines li[data-val='语言'] ul li:last").clone());
    }
    $(".tags-lines li[data-val='语言'] ul li a").each(function (i) {
        if(i!==0){
            $(this).text(data[i-1]);
            $(this).attr('data-val',data[i-1])
        }
        $(this).attr('my-val',i);
        $(this).click(function (event) {
            MovieTime = $(event.target).attr("my-val");
            setMovieListOptionActive(parseInt(MovieType),parseInt(MovieLocation),parseInt(MovieTime));
            sendMovieListByOptionAjax(parseInt(MovieType)-1,parseInt(MovieLocation)-1,parseInt(MovieTime)-1)
        })
    });
}

function setMovieListOptionActive(a,b,c) {
    //这里的abc代表的是将option每一行当中的哪一个置为active
    $(".tags-lines li[data-val='类型'] ul li").each(function (i) {
        if(i===a){
            $(this).attr("class","active")
        }else {
            $(this).removeAttr("class")
        }
    });
    $(".tags-lines li[data-val='区域'] ul li").each(function (i) {
        if(i===b){
            $(this).attr("class","active")
        }else {
            $(this).removeAttr("class")
        }
    });
    $(".tags-lines li[data-val='语言'] ul li").each(function (i) {
        if(i===c){
            $(this).attr("class","active")
        }else {
            $(this).removeAttr("class")
        }
    });
}

function sendMovieListByOptionAjax(MovieType, MovieLocation, MovieTime) {
    var url = "/SelectMovies?";

    if(MovieType===-1){
        url+="&Mtype=";
    }else {
        url+="&Mtype="+MovieOptionData[0][MovieType];
    }
    if(MovieLocation===-1){
        url+="&Mlocation=";
    }else {
        url+="&Mlocation="+MovieOptionData[1][MovieLocation];
    }
    if(MovieTime===-1){
        url+="&Mlanguage=";
    }else {
        url+="&Mlanguage="+MovieOptionData[2][MovieTime];
    }
    $.ajax({
        type:"GET",
        url:url,
        contentType:"charset=utf-8",
        success:function (data) {
            console.log(data);
            showMovieList(data)
        },
        error:function () {
            alert("连接服务器超时")
        }
    })
}

function showMovieList(data) {
    $(".movie-list li").each(function (i) {
        if(i!==0){
            $(this).remove();
        }
    });


    for(var i=1;i<data.length;i++){
        $(".movie-list").append($(".movie-list li:last").clone());
    }
    $(".movie-list li .movie-item img").each(function (i) {
        $(this).attr('src',"/image?path="+data[i]["MposterPath"]);
    });
    $(".movie-list li .movie-item-title a").each(function (i) {
        $(this).text(data[i]["Mname"]);
        $(this).click(function () {
            localStorage.setItem('key',data[i]["Mno"])
        })
    });
    $(".movie-list li .channel-detail-orange i").each(function (i) {
        $(this).text(data[i]["Mrating"]);
    });

    $(".movie-list li .movie-item a").each(function (i) {
        $(this).click(function () {
            localStorage.setItem('key',data[i]["Mno"])
        })
    });
}