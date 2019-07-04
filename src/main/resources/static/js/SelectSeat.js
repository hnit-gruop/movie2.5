window.onload=function (){
    $.ajax({
        type:"GET",
        url:"/UsedSeat?Sno="+localStorage.getItem('Sno'),
        contentType:"charset=utf-8",
        success:function (selectSeats) {
            showSelectSeats(selectSeats)
        },
        error:function () {
            alert("连接服务器失败！")
        }
    })
};

function showSelectSeats(data){
    var seats = data.split(",");
    for(var num in seats){
        var Part = "#"+seats[num][0];
        $(Part+" div").each(function (i) {
            console.log(i);
            if($(this).data("tooltip")===seats[num]){
                $(this).attr('class','row__seat row__seat--reserved');
            }
        })
    }
}

$(".action--buy").click(function(){
    var s="";
    var len = $(".row__seat--selected").length;
    $(".row__seat--selected").each(function (i) {
        if(i!==len-1){
            s += $(this).data("tooltip")+",";
        }else {
            s += $(this).data("tooltip");
        }

    });
    $.ajax({
        type:"GET",
        url:"/InsertSeatOrder?Sno="+localStorage.getItem('Sno')+"&email="+localStorage.getItem('email')+"&seat="+s,
        contentType:"charset=utf-8",
        success:function (data) {
            if(data==="successful"){
                alert("购票成功");
                window.location.href = "../pages/MyOrder.html";
            }else if(data==="fail"){
                alert("购票失败，请重试")
            }
        },
        error:function () {
            alert("请求服务器出错！")
        }
    })
});