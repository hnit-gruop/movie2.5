window.onload=function (){
    $.ajax({
        type:"GET",
        url:"/GetAnOrder?Ono="+localStorage.getItem('Ono'),
        contentType:"charset=utf-8",
        success:function (data) {
            showDetailOrderMessage(data)
        },
        error:function () {
            alert("连接服务器超时")
        }
    })
};

function showDetailOrderMessage(data) {
    $(".order-id").text("订单编号："+data["ono"]);

    $(".movie-name").text("《"+data["mname"]+"》");

    $(".cinema-name").text(data["Tname"]);

    $(".hall").text(data["roomName"]);

    $(".seats span i").text(data["seat"]);

    $(".cinema-info .name").text(data["tname"]);

    $(".cinema-info .address").text("地址："+data["taddress"]);

    $(".cinema-info .phone").text("电话："+data["ttel"]);

    $(".showtime").text(data["beginDate"]+" "+data["beginTime"]);

    $(".price").text(data["price"]);

}