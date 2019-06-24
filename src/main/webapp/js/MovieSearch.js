window.onload=function (){
    debugger;
    check();

    //设置新的搜索按钮点击事件
    $("#movieSearchSubmit").click(function () {
        debugger;
        var str = $(".kw").val();
        if(str===""){
            alert("请在搜索框中输入搜索内容")
        }else {
            localStorage.setItem('searchMovie',"searchMovie="+str);
            sendQuest(str);
        }
    });
};

function check() {
    var form_data = localStorage.getItem('searchMovie');
    if(form_data==null) {
        $(".search-result-box dd").css('display', 'none');
        $(".navbar a").text("影视剧（0）");
        alert("请在搜索框中输入搜索内容")
    }else {
        var data=form_data.split("=")[1];

        //显示搜索框里面的内容
        $(".kw").val(data);

        sendQuest(data);
    }
}

function sendQuest(data) {
    $.ajax({
        type: "GET",
        contentType: "charset=utf-8",
        url: "/SearchMovie?Mname="+data,
        success: function (data) {
            showSearchMovieList(data)
        },
        error: function () {
            alert("连接服务器失败！");
        }
    });
}

function showSearchMovieList(data) {
    console.log(data);
    $("dd").each(function (i) {
        if(i!==0){
            $(this).remove();
        }
    });


    for(var i=1;i<data.length;i++){
        $("dl").append($("dd:last").clone());
    }

    $(".navbar a").text("影视剧（"+data.length+"）");

    $(".movie-item a .movie-poster").each(function (i) {
        $(this).attr('name',data[i]["Mno"]);
        $(this).click(function (event) {
            debugger;
            var mno = $(event.target).attr("name");
            localStorage.setItem('key',mno);
        })
    });

    $(".movie-item img").each(function (i) {
        $(this).attr('src','/image?path='+data[i]["MposterPath"]);
    });

    $(".movie-item-title a").each(function (i) {
       $(this).text(data[i]["Mname"]);

        $(this).attr('name',data[i]["Mno"]);
        $(this).click(function (event) {
            debugger;
            var mno = $(event.target).attr("name");
            localStorage.setItem('key',mno);
        });
    });

    $(".movie-item-subtitle").each(function (i) {
        $(this).text(data[i]["mEnglishName"])
    });

    $(".channel-detail-orange i").each(function (i) {
        $(this).text(data[i]["Mrating"])
    });

    $(".movie-item-cat").each(function (i) {
        $(this).text(data[i]["mtype"])
    });

    $(".movie-item-pub").each(function (i) {
        $(this).text(data[i]["mdate"])
    });
}
