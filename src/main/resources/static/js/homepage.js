$(function(){
    $(".carousel-content").carousel({
        carousel : ".carousel",//轮播图容器
        indexContainer : ".img-index",//下标容器
        prev : ".carousel-prev",//左按钮
        next : ".carousel-next",//右按钮
        timing : 3000,//自动播放间隔
        animateTime : 700,//动画时间
        autoPlay : true,//是否自动播放 true/false
        direction : "left"//滚动方向 right/left
    });

    $(".carousel-content").hover(function(){
        $(".carousel-prev,.carousel-next").fadeIn(300);
    },function(){
        $(".carousel-prev,.carousel-next").fadeOut(300);
    });

    $(".carousel-prev").hover(function(){
        $(this).find("img").attr("src","../img/left2.png");
    },function(){
        $(this).find("img").attr("src","../img/left1.png");
    });
    $(".carousel-next").hover(function(){
        $(this).find("img").attr("src","../img/right2.png");
    },function(){
        $(this).find("img").attr("src","../img/right1.png");
    });
});

$(document).ready(function(){
    $.ajax({
        type:"GET",
        contentType:"charset=utf-8",
        url: "/homepage",
        success: function (data) {
             MovieList(data)
        },
        error: function () {
            alert("连接服务器超时");
        }
    })
});

function MovieList(data) {
    debugger;
    for(var i=0, len=data.length;i<len;i++){
        //添加多余的文件
        if(i>=1){
            $("#movie-list-hot-play").append($("#movie-item-hot-play1").clone());
            // $("#movie-list-hot-play li:last").attr('id','#movie-item-hot-play'+(i+1));
        }
    }
    $("#movie-item-hot-play1 #movie-rate").each(function (i) {
        var rate = data[i]["Mrating"];
        $(this).text(rate);
    });
    $("#movie-item-hot-play1 #movie-name").each(function (i) {
        var name = data[i]["Mname"];
        $(this).text(name);
    });

    $("#movie-item-hot-play1 img").each(function (i) {
        var pic = data[i]["MposterPath"];
        $(this).attr({'src':"/image?path="+pic,'name':data[i]["Mno"]});
    });

    $("#movie-item-hot-play1 .movie-sale a").each(function (i) {
        $(this).attr('name',data[i]["Mno"]) ;
        $(this).click(function () {
            var temp = $(this).attr('name');
            localStorage.setItem('key',temp);
        })
    });

    $("#movie-item-img-my img").click(function (event) {
        debugger;
        var ID = $(event.target).attr("name");
        localStorage.setItem('key',ID);
        window.open("MovieDetail.html")
    });
}