window.onload=function (){
    $.ajax({
        type:"GET",
        url:"/GetOrders?email="+localStorage.getItem('email'),
        contentType:"charset=utf-8",
        success:function (data) {
            showMessage(data)
        },
        error:function () {
            alert("连接服务器超时")
        }
    })
};

function showMessage(data) {
    debugger;

    for(var i=1,len=data.length;i<len;i++){
        $(".orders-container").append($(".order-box:last").clone());
    }

    $(".order-date").each(function (i) {
        $(this).text(data[i]["Odate"]);
    });

    $(".order-id").each(function (i) {
        $(this).text("订单编号："+data[i]["Ono"]);
    });

    $(".poster img").each(function (i) {
        $(this).attr('src','/image?path='+data[i]["MposterPath"]);
    });

    $(".movie-name").each(function (i) {
        $(this).text("《"+data[i]["Mname"]+"》");
    });

    $(".cinema-name").each(function (i) {
        $(this).text(data[i]["Tname"]);
    });

    $(".hall-ticket").each(function (i) {
        $(this).find('span').each(function (k) {
            if(k===0){
                $(this).text(data[i]["roomName"])
            }else if(k===1){
                $(this).text(data[i]["seat"])
            }
        });
    });

    $(".show-time").each(function (i) {
        $(this).text(data[i]["beginDate"]+" "+data[i]["beginTime"]);
    });

    $(".order-price").each(function (i) {
        $(this).text("￥"+data[i]["price"]);
    });

    $(".order-detail").each(function (i) {
        $(this).click(function () {
            localStorage.setItem('Ono',data[i]["Ono"])
        })
    });

}