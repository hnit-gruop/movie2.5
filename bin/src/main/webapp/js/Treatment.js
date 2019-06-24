
//弹出
function ErroAlert(e) {
    var index = layer.alert(e, { icon: 5, time: 2000, offset: 't', closeBtn: 0, title: '错误信息', btn: [], anim: 2, shade: 0 });
    layer.style(index, {
        color: '#777'
    }); 
}