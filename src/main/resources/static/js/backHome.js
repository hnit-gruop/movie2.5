$(function(){
	//类型选择（近24小时、近30天、近一年）
	$(".type span").click(function(){
		$(this).siblings().find("font").removeClass("active");
		$(this).find("font").addClass("active");
	});
});

//全屏重绘echarts报表
function fullScreenRefreshEcharts(myChart1, maChart2, maChart3, maChart4, maChart5, maChart6, maChart7){
	myChart1.resize();
	maChart2.resize();
	maChart3.resize();
	maChart4.resize();
	maChart5.resize();
	maChart6.resize();
	maChart7.resize();
}

/***全屏按钮拖拽效果 - 开始***/
function fullScreenAllowDrop(ev){
	ev.preventDefault();
}

function fullScreenDrag(ev){
	ev.dataTransfer.setData("Text",ev.target.id);
}

function fullScreenDrop(ev,obj){
	var x = ev.clientX-obj.offsetLeft - 30;
    var y = ev.clientY-obj.offsetTop - 30;
    
    $(".fullScreenBut").css({"right": "auto","top": y+"px","left": x+"px"});
	ev.preventDefault();
}
/***全屏按钮拖拽效果 - 结束***/

//全屏按钮
function fullScreenClick(){
	var iframe = $(parent.document.body).find(".frameMain .con");
	var win_w = $(parent.document.body).width();
	var win_h = $(parent.document.body).find(".frameMenu").height();
	
	if(iframe.css("position") == "fixed"){
    	$(".fullScreenBut").css({"right": "10px","top": "60px","left": "auto"});
		$(".fullScreenBut").attr("class","iconfont icon-quanping fullScreenBut");
		iframe.css({
			"position":"static",
			"top":"0",
			"left":"0",
			"z-index":"1",
			"width":"100%",
			"height":(win_h - 92)+"px"
		});
	}else{
		$(".fullScreenBut").attr("class","iconfont icon-tuichuquanping fullScreenBut");
		iframe.css({
			"position":"fixed",
			"top":"0",
			"left":"0",
			"z-index":"1",
			"width":win_w+"px",
			"height":win_h+"px"
		});
	}
	//重新绘制报表
	fullScreenRefreshEcharts(totalAmountChart, weekActiveChart, payRatioChart, orderMapChart, merchantGraphChart, categorySingleChart, businessChart);
}

//千分位
function get_thousand_num(num) {
    return num.toString().replace(/\d+/, function (n) { // 先提取整数部分
        return n.replace(/(\d)(?=(\d{3})+$)/g, function ($1) { // 对整数部分添加分隔符
            return $1 + ",";
        });
    });
}

//初始化页面
function init(totalAmountData, weekActiveData, payRatioData, orderMapData, merchantGraphDate, categorySingleDate, businessData){
	totalAmount("totalAmount", totalAmountData);
	weekActive("weekActive", weekActiveData);
	payRatio("payRatio", payRatioData);
	orderMap("orderMap", orderMapData);
	merchantGraph("merchantGraph", merchantGraphDate);
	categorySingle("categorySingle", categorySingleDate);
	business("businessData", businessData);
}

//营业数据刷新
function businessDataRefresh(myChart, data){
    var xData = new Array();
    var yData = new Array();
    var orders = new Array();
    var option = myChart.getOption();

    if(!myChart) {
        return;
    }

    var colors = ['#61c7e9','#7b77bb','#25bcb6'];
    var tag = 0;

    for (var i = 0; i < data[0].length; i++) {
        for (x in data[0][i]){
            if(x == "订单数"){
                orders.push(data[0][i][x]);
            }else{
                xData.push(x);
                var temp = {
                    value: data[0][i][x],
                    name: x,
                    itemStyle : {	//区域填充样式
                        color: colors[i]
                    }
                };
                yData.push(temp);
            }
        }
    }

    option.series[0].data = yData;

    myChart.setOption(option);
}
//支付刷新
function payRatioDataRefresh(myChart, data){
    var xData = new Array();
    var yData = new Array();
    var option = myChart.getOption();

    if(!myChart) {
        return;
    }

    var colors = [
        {
            "color1":"#2fb6f0",
            "color2":"#3090f5"
        },
        {
            "color1":"#faa61a",
            "color2":"#f98339"
        },
        {
            "color1":"#1da683",
            "color2":"#2dc86d"
        }
    ];
    var tag = 0;

    for (var i = 0; i < data[0].length; i++) {
        for (x in data[0][i]){
            xData.push(x);
            var temp = {
                value: data[0][i][x],
                name: x,
                itemStyle : {	//区域填充样式
                    color: {
                        type: 'linear',
                        x: 0,
                        y: 0,
                        x2: 1,
                        y2: 0,
                        colorStops: [{
                            offset: 0, color: colors[i].color1 // 0% 处的颜色
                        }, {
                            offset: 1, color: colors[i].color2 // 100% 处的颜色
                        }],
                        globalCoord: false // 缺省为 false
                    }
                }
            };
            yData.push(temp);
        }
    }

    option.series[0].data = yData;
    option.legend[0].data = xData;

    myChart.setOption(option);
}
//数据刷新
function dataRefresh(myChart, data){
    var xData = new Array();
    var yData = new Array();
    var option = myChart.getOption();

    if(!myChart) {
        return;
    }

    if(option.xAxis != undefined){

        for(var i = 0; i < data.length; i++){
            if(i == 0){
                for (var j = 0; j < data[i].length; j++) {
                    for (x in data[i][j]){
                        xData.push(x);
                        yData.push(data[i][j][x]);
                    }
                }
            }else{
                for (var j = 0; j < data[i].length; j++) {
                    for (x in data[i][j]){
                        yData.push(data[i][j][x]);
                    }
                }
            }
            option.series[i].data = yData;
            yData = [];
        }
        option.xAxis[0].data = xData;
    }else{
        var colors = ['#6f71f0','#7eed80','#d47f4f','#f8696c','#1ca484','#fa9c24','#2977d5','#2cc6ed','#7e7c7c','#dd78e8'];
        var tag = 0;

        for (var i = 0; i < data[0].length; i++) {
            for (x in data[0][i]){
                xData.push(x);
                var temp = {
                    value: data[0][i][x],
                    name: x,
                    itemStyle : {	//区域填充样式
                        color: colors[i]
                    }
                };
                yData.push(temp);
            }
        }

        option.series[0].data = yData;
        option.legend[0].data = xData;
    }

    myChart.setOption(option);
}

//营业数据
var businessChart;
function business(id,data){
	var xData = new Array();
	var yData = new Array();
	var orders = new Array();
	var colors = ['#61c7e9','#7b77bb','#25bcb6'];
	
	var tag = 0;
	for (var i = 0; i < data.length; i++) {
	    for (x in data[i]){
	    	if(x == "订单数"){
		    	orders.push(data[i][x]);
	    	}else{
		    	xData.push(x);
		    	var temp = {
		        	value: data[i][x],
		        	name: x,
		            itemStyle : {	//区域填充样式
		            	color: colors[i]
		           	}
		        };
		    	yData.push(temp);
	    	}
		}
	}
	
    businessChart = echarts.init(document.getElementById(id));

    var option = {
	    tooltip: {
	    	show: true,
	    	textStyle: {
	    		color: '#FFF'
	    	},
			formatter: function (p) {
			    return p.name+'<span style="display: block; margin: 5px 0; width: 20px; height: 6px; border-radius: 3px; background-color: #fbb80b;"></span>'+get_thousand_num(p.value)+'<br/>订单数<br/>'+get_thousand_num(orders[p.dataIndex])+'单';
			}
	    },
	    legend: {
	    	top: 50,
	    	right: 0,
	    	textStyle: {
	    		color: '#FFF'
	    	},
	        icon: 'roundRect',
	        data: xData
	    },
	    series : [
	        {
	            name: '访问来源',
	            type: 'pie',
	            center: ['50%', '65%'],
	            radius: '50%',
	            label: {	//外部百分比
	                normal: {
	                    formatter: '{per|{d}%}',
	                    borderRadius: 1,
	                    rich: {
	                        per: {
	                            color: '#eee'
	                        }
	                    }
	                }
	            },
	            itemStyle: {
	                normal: {
	                    labelLine: {    //指示线状态
	                        show: true,
	                        smooth: 0.2,
	                        length: 0,
	                        length2: 10
	                    }
	                }
	            },
	            data: yData
	        }
	    ]
	};

    businessChart.setOption(option);
}

//技术栈与单排占比排行
var categorySingleChart;
function categorySingle(id,data){
	
	var xData = new Array();
	var yData = new Array();
	var colors = ['#6f71f0','#7eed80','#d47f4f','#f8696c','#1ca484','#fa9c24','#2977d5','#2cc6ed','#7e7c7c','#dd78e8'];
	
	var tag = 0;
	for (var i = 0; i < data.length; i++) {
	    for (x in data[i]){
	    	xData.push(x);
	    	var temp = {
	        	value: data[i][x],
	        	name: x,
	            itemStyle : {	//区域填充样式
	            	color: colors[i]
	           	}
	        };
	    	yData.push(temp);
		}
	}
	
    categorySingleChart = echarts.init(document.getElementById(id));

    var option = {
	    legend: {
	    	top: 0,
	    	right: 10,
	    	textStyle: {
	    		color: '#FFF'
	    	},
//	    	selectedMode:false,//取消图例上的点击事件
	        icon: 'roundRect',
	        orient: 'vertical',
	        data: xData
	    },
	    series: [
	        {
	            name:'访问来源',
	            type:'pie',
	            center: ['35%', '50%'],
	            radius: ['60%', '75%'],
	            avoidLabelOverlap: false,
//	            label: {	//外部百分比
//	                normal: {
//	                    formatter: '{per|{d}%}',
//	                    backgroundColor: '#eee',
//	                    borderColor: '#aaa',
//	                    borderWidth: 1,
//	                    borderRadius: 4,
//	                    rich: {
//	                        per: {
//	                            color: '#eee',
//	                            backgroundColor: '#334455',
//	                            padding: [2, 4],
//	                            borderRadius: 2
//	                        }
//	                    }
//	                }
//	            },
	            label: {
                    show: false,
                    position: 'center',
	            },
                emphasis: {
		            label: {
	                    show: true,
	                    position: 'center',
	                    formatter: '{b}\n{per|{d}%}',
	                	lineHeight: 50,
	                    fontSize: 20,
	                    fontWeight: 'bold',
	                    color: '#FFF',
	                    rich: {
	                        per: {
	                            color: '#eee',
	                            padding: [2, 4],
	                            borderRadius: 2
	                        }
	                    }
		            }
                },
	            data: yData
	        }
	    ]
	};
	categorySingleChart.on("click", categorySingleClick);
	
    categorySingleChart.setOption(option);
}

//前端增长图
var merchantGraphChart;
function merchantGraph(id,data){
	
	var xData = new Array();
	var yData = new Array();
	for (var i = 0; i < data.length; i++) {
	    for (x in data[i]){
	    	xData.push(x);
	    	yData.push(data[i][x]);
		}
	}
    
    merchantGraphChart = echarts.init(document.getElementById(id));

    var option = {
		//提示框组件
	    tooltip: {
	        position: function (pos, params, dom, rect, size) {
	        	var tipW = size.contentSize[0];
	        	var tipH = size.contentSize[1];
	        	var domW = size.viewSize[0];
	        	var domH = size.viewSize[1];
	        	
	        	if(pos[0] < tipW){
	        		if(pos[1] > domH - tipH){
			    		return [10, pos[1]-tipH];
	        		}else{
			    		return [10, pos[1]-tipH/2];
	        		}
	        	}
	        	if(pos[0] > domW - tipW){
	        		if(pos[1] < tipH){
			    		return [domW - tipW - 10, pos[1]];
	        		}else{
                        return [domW - tipW - 10, pos[1]-tipH-20];
	        		}
	        	}
			},
			formatter: function (p) {
			    return '<font style="font-size:16px">'+p.name+' '+get_thousand_num(p.value)+'</font><br/><span style="display: block; float: right; width: 40px; height:4px; border-radius: 2px; background-color: #5cb8b9;"></span>';
			}
	    },
	    //直角坐标系内绘图网格
	    grid: {
	    	top: 80,
	    	bottom: 0,
	    	left: 0,
	    	right: 0
	    },
	    xAxis:  {
	        type: 'category',
	        boundaryGap: false,
	        splitLine:{show: false},//去除网格线
	        data: xData
	    },
	    yAxis: {
	        type: 'value',
	        splitLine:{show: false},//去除网格线
	        axisPointer: {
	            snap: true
	        }
	    },
		//系列列表。每个系列通过 type 决定自己的图表类型
	    series: [
	        {
	            type:'line',
	            smooth: true,	//是否平滑曲线显示
	            data: yData,
	            symbolSize: 8,
	            itemStyle: {	//折线拐点标志的样式
	            	borderWidth: 2,
	            	borderColor: '#5271c5'
	            },
	            lineStyle: {	//线条样式
	            	color: '#5271c5',
	            	width: 3
	            },
	            areaStyle: {	//区域填充样式
	            	color: {
					    type: 'linear',
					    x: 0,
					    y: 0,
					    x2: 1,
					    y2: 0,
					    colorStops: [{
					        offset: 0, color: '#477f8c' // 0% 处的颜色
					    }, {
					        offset: 1, color: '#286194' // 100% 处的颜色
					    }],
					    globalCoord: false // 缺省为 false
					}
	            }
	        }
	    ]
	};

    merchantGraphChart.setOption(option);
}

//前端周期活跃度
var orderMapChart;
function orderMap(id,data){
	
	var xData = new Array();
	var yData1 = new Array();
	var yData2 = new Array();
	for (var i = 0; i < data[0].length; i++) {
	    for (x in data[0][i]){
	    	xData.push(x);
	    	yData1.push(data[0][i][x]);
		}
	}
	for (var i = 0; i < data[1].length; i++) {
	    for (x in data[1][i]){
	    	yData2.push(data[1][i][x]);
		}
	}
	
    orderMapChart = echarts.init(document.getElementById(id));

    var option = {
		//提示框组件
	    tooltip: {
	        position: function (pos, params, dom, rect, size) {
	        	var tipW = size.contentSize[0];
	        	var tipH = size.contentSize[1];
	        	var domW = size.viewSize[0];
	        	var domH = size.viewSize[1];
	        	
	        	if(pos[0] > domW - tipW){
	        		if(pos[1] < tipH){
			    		return [domW - tipW - 10, pos[1]];
	        		}else{
                        return [domW - tipW - 10, pos[1]-tipH-20];
	        		}
	        	}
			},
			formatter: function (p) {
			    return '<font style="font-size:18px">'+get_thousand_num(p.value)+'</font> <font style="font-size:12px">单</font><span style="display: block; width: 45px; height:4px; border-radius: 2px; background-color: #199d8b;"></span>'+p.name;
			}
	    },
	    color:['#3f555f','#2baaac'],	//调色盘颜色列表
	    legend: {
	    	top: 42,
	    	right: 0,
	    	textStyle: {
	    		color: '#FFF'
	    	},
	        icon: 'roundRect',
	        data: ['下单统计', '分拣接单统计']
	    },
	    //直角坐标系内绘图网格
	    grid: {
	    	top: 100,
	    	bottom: 30,
	    	left: 60,
	    	right: 30
	    },
	    xAxis : {
	    	axisLine : {
	    		show: true,
	    		lineStyle : {
	    			color: '#FFF'
	    		}
	    	},
            type : 'category',
            boundaryGap : false,	//坐标轴两边留白策略
            data : xData
		},
	    yAxis: {
	        type: 'value',
	    	axisLine : {
	    		show: true,
	    		lineStyle : {
	    			color: '#FFF'
	    		}
	    	},
	        axisLabel: {
	        	color: '#FFF'
	        },
	        axisPointer: {
	            snap: true
	        }
	    },
	    series : [
	        {
	            name:'下单统计',
	            type:'line',
	            smooth: true,	//是否平滑曲线显示
	            symbolSize: 5,
	            itemStyle: {	//折线拐点标志的样式
	            	borderWidth: 2,
	            	borderColor: '#6884f4'
	            },
	            lineStyle: {	//线条样式
	            	color: '#303030',
	            	width: 3
	            },
	            areaStyle: {	//区域填充样式
	            	color: {
					    type: 'linear',
					    x: 0,
					    y: 0,
					    x2: 0,
					    y2: 1,
					    colorStops: [{
					        offset: 0, color: '#3e555f' // 0% 处的颜色
					    }, {
					        offset: 1, color: '#4a586e' // 100% 处的颜色
					    }],
					    globalCoord: false // 缺省为 false
					}
	           	},
	            data: yData1
	        },
	        {
	            name:'分拣接单统计',
	            type:'line',
	            smooth: true,	//是否平滑曲线显示
	            symbolSize: 5,
	            itemStyle: {	//折线拐点标志的样式
	            	borderWidth: 2,
	            	borderColor: '#6884f4'
	            },
	            lineStyle: {	//线条样式
	            	color: '#4ca3a6',
	            	width: 3
	            },
	            areaStyle: {	//区域填充样式
	            	color: {
					    type: 'linear',
					    x: 0,
					    y: 0,
					    x2: 0,
					    y2: 1,
					    colorStops: [{
					        offset: 0, color: '#2d979a' // 0% 处的颜色
					    }, {
					        offset: 1, color: '#706ca6' // 100% 处的颜色
					    }],
					    globalCoord: false // 缺省为 false
					}
	           	},
	            data: yData2
	        }
	    ]
	};

    orderMapChart.setOption(option);
}

//支付占比
var payRatioChart;
function payRatio(id,data){
	var xData = new Array();
	var yData = new Array();
	for (var i = 0; i < data.length; i++) {
	    for (x in data[i]){
	    	xData.push(x);
	    	yData.push(data[i][x]);
		}
	}
	
    payRatioChart = echarts.init(document.getElementById(id));

    var option = {
	    legend: {
	    	top: 18,
	    	right: 0,
	    	textStyle: {
	    		color: '#FFF'
	    	},
	        icon: 'roundRect',
	        data: xData
	    },
	    series: [
	        {
	            name:'访问来源',
	            type:'pie',
	            center: ['50%', '60%'],
	            radius: ['50%', '75%'],
	            avoidLabelOverlap: false,
	            label: {
                    show: false,
                    position: 'center',
	            },
                emphasis: {
		            label: {
	                    show: true,
	                    position: 'center',
						formatter: function (p) {
						    return '总计\n\n'+get_thousand_num(p.value)+'\n\n万元';
						},
	                	lineHeight: 50,
	                    fontSize: 20,
	                    fontWeight: 'bold',
	                    color: '#FFF'
		            }
                },
	            data:[
	                {
	                	value: yData[0],
	                	name: xData[0],
			            itemStyle : {	//区域填充样式
			            	color: {
							    type: 'linear',
							    x: 0,
							    y: 0,
							    x2: 1,
							    y2: 0,
							    colorStops: [{
							        offset: 0, color: '#2fb6f0' // 0% 处的颜色
							    }, {
							        offset: 1, color: '#3090f5' // 100% 处的颜色
							    }],
							    globalCoord: false // 缺省为 false
							}
			           	}
	                },
	                {
	                	value: yData[1],
	                	name: xData[1],
			            itemStyle : {	//区域填充样式
			            	color: {
							    type: 'linear',
							    x: 0,
							    y: 0,
							    x2: 1,
							    y2: 0,
							    colorStops: [{
							        offset: 0, color: '#faa61a' // 0% 处的颜色
							    }, {
							        offset: 1, color: '#f98339' // 100% 处的颜色
							    }],
							    globalCoord: false // 缺省为 false
							}
			           	}
	                },
	                {
	                	value: yData[2],
	                	name: xData[2],
			            itemStyle : {	//区域填充样式
			            	color: {
							    type: 'linear',
							    x: 0,
							    y: 0,
							    x2: 1,
							    y2: 0,
							    colorStops: [{
							        offset: 0, color: '#1da683' // 0% 处的颜色
							    }, {
							        offset: 1, color: '#2dc86d' // 100% 处的颜色
							    }],
							    globalCoord: false // 缺省为 false
							}
			           	}
	                }
	            ]
	        }
	    ]
	};

    payRatioChart.setOption(option);
}

//前端周期活跃度
var weekActiveChart;
function weekActive(id,data){
	var xData = new Array();
	var yData1 = new Array();
	var yData2 = new Array();
	var yData3 = new Array();
	
	for (var i = 0; i < data[0].length; i++) {
	    for (x in data[0][i]){
	    	xData.push(x);
	    	yData1.push(data[0][i][x]);
		}
	}
	for (var i = 0; i < data[1].length; i++) {
	    for (x in data[1][i]){
	    	yData2.push(data[1][i][x]);
		}
	}
	for (var i = 0; i < data[2].length; i++) {
	    for (x in data[2][i]){
	    	yData3.push(data[2][i][x]);
		}
	}
	
    weekActiveChart = echarts.init(document.getElementById(id));

    var option = {
		//提示框组件
	    tooltip: {
	        position: function (pos, params, dom, rect, size) {
	        	var tipW = size.contentSize[0];
	        	var tipH = size.contentSize[1];
	        	var domW = size.viewSize[0];
	        	var domH = size.viewSize[1];
	        	
	        	if(pos[0] < tipW){
	        		if(pos[1] > domH - tipH){
			    		return [10, pos[1]-tipH];
	        		}else{
			    		return [10, pos[1]-tipH/2];
	        		}
	        	}
	        	if(pos[0] > domW - tipW){
	        		if(pos[1] < tipH){
			    		return [domW - tipW - 10, pos[1]];
	        		}else{
                        return [domW - tipW - 10, pos[1]-tipH-20];
	        		}
	        	}
			},
			formatter: function (p) {
				var name = "活跃商家";
				var color = "#424345";
				if(p.seriesIndex == 0){
					name = "总前端数";
					color = "#424345";
				}
				if(p.seriesIndex == 1){
					name = "活跃商家";
					color = "#5371c5";
				}
				if(p.seriesIndex == 2){
					name = "活跃商家";
					color = "#be6256";
				}
			    return '<font style="display: block;">'+name+'：'+get_thousand_num(p.value)+'</font><span style="width: 100%; display: block; overflow: hidden;"><span style="display: block; float: right; width: 45px; height:4px; border-radius: 2px; background-color: '+color+';"></span></span><font style="float:right;">'+p.name+'</font>';
			}
	    },
	    color:['#424345','#5371c5','#be6256'],	//调色盘颜色列表
	    legend: {
	    	top: 18,
	    	right: 0,
	    	textStyle: {
	    		color: '#FFF'
	    	},
	        icon: 'roundRect',
	        data: ['总前端数', '近7天跃度','24小时商户活跃度']
	    },
	    //直角坐标系内绘图网格
	    grid: {
	    	top: 60,
	    	bottom: 0,
	    	left: 0,
	    	right: 0
	    },
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : false,	//坐标轴两边留白策略
	            data : xData
	        }
	    ],
	    yAxis: {
	        type: 'value',
	        splitLine:{show: false},//去除网格线
	        axisPointer: {
	            snap: true
	        }
	    },
	    series : [
	        {
	            name:'总前端数',
	            type:'line',
	            smooth: true,	//是否平滑曲线显示
	            symbolSize: 5,
	            itemStyle: {	//折线拐点标志的样式
	            	borderWidth: 2,
	            	borderColor: '#424345'
	            },
	            lineStyle: {	//线条样式
	            	color: '#303030',
	            	width: 3
	            },
	            areaStyle: {	//区域填充样式
	            	color: {
					    type: 'linear',
					    x: 0,
					    y: 0,
					    x2: 1,
					    y2: 0,
					    colorStops: [{
					        offset: 0, color: '#424345' // 0% 处的颜色
					    }, {
					        offset: 1, color: '#424445' // 100% 处的颜色
					    }],
					    globalCoord: false // 缺省为 false
					}
	           	},
	            data: yData1
	        },
	        {
	            name:'近7天跃度',
	            type:'line',
	            smooth: true,	//是否平滑曲线显示
	            symbolSize: 5,
	            itemStyle: {	//折线拐点标志的样式
	            	borderWidth: 2,
	            	borderColor: '#3342c2'
	            },
	            lineStyle: {	//线条样式
	            	color: '#5068ac',
	            	width: 3
	            },
	            areaStyle: {	//区域填充样式
	            	color: {
					    type: 'linear',
					    x: 0,
					    y: 0,
					    x2: 0,
					    y2: 1,
					    colorStops: [{
					        offset: 0, color: '#545898' // 0% 处的颜色
					    }, {
					        offset: 1, color: '#2c7776' // 100% 处的颜色
					    }],
					    globalCoord: false // 缺省为 false
					}
	           	},
	            data: yData2
	        },
	        {
	            name:'24小时前端活跃度',
	            type:'line',
	            smooth: true,	//是否平滑曲线显示
	            symbolSize: 5,
	            itemStyle: {	//折线拐点标志的样式
	            	borderWidth: 2,
	            	borderColor: '#d7696b'
	            },
	            lineStyle: {	//线条样式
	            	color: '#d89169',
	            	width: 3
	            },
	            areaStyle: {	//区域填充样式
	            	color: {
					    type: 'linear',
					    x: 0,
					    y: 0,
					    x2: 0,
					    y2: 1,
					    colorStops: [{
					        offset: 0, color: '#b0786a' // 0% 处的颜色
					    }, {
					        offset: 1, color: '#a99a70' // 100% 处的颜色
					    }],
					    globalCoord: false // 缺省为 false
					}
	           	},
	            data: yData3
	        }
	    ]
	};

    weekActiveChart.setOption(option);
}

//总金额
var totalAmountChart;
var COMPANY_VALUE = "";	//总金额 - 默认单位
var SPECIFICATION_TAG = "";//总金额 - 横坐标默认单位
function totalAmount(id,data){
	COMPANY_VALUE = "元";
	SPECIFICATION_TAG = "小时";
	var xData = new Array();
	var yData = new Array();
	for (var i = 0; i < data.length; i++) {
	    for (x in data[i]){
	    	xData.push(x);
	    	yData.push(data[i][x]);
		}
	}
    
    totalAmountChart = echarts.init(document.getElementById(id));

    var option = {
		//提示框组件
	    tooltip: {
	        position: function (pos, params, dom, rect, size) {
	        	var tipW = size.contentSize[0];
	        	var tipH = size.contentSize[1];
	        	var domW = size.viewSize[0];
	        	var domH = size.viewSize[1];
	        	
	        	if(pos[0] < tipW){
	        		if(pos[1] > domH - tipH){
			    		return [10, pos[1]-tipH];
	        		}else{
			    		return [10, pos[1]-tipH/2];
	        		}
	        	}
	        	if(pos[0] > domW - tipW){
	        		if(pos[1] < tipH){
			    		return [domW - tipW - 10, pos[1]];
	        		}else{
			    		return [domW - tipW - 10, pos[1]-tipH-20];
	        		}
	        	}
			},
			formatter: function (p) {
			    return '<font style="font-size:18px">'+get_thousand_num(p.value)+'</font> <font style="font-size:12px">'+COMPANY_VALUE+'</font><span style="display: block; width: 45px; height:4px; border-radius: 2px; background-color: #199d8b;"></span>'+p.name + SPECIFICATION_TAG;
			}
	    },
	    //直角坐标系内绘图网格
	    grid: {
	    	top: 100,
	    	bottom: 0,
	    	left: 0,
	    	right: 0
	    },
	    xAxis:  {
	        type: 'category',
	        boundaryGap: false,
	        splitLine:{show: false},//去除网格线
	        data: xData
	    },
	    yAxis: {
	        type: 'value',
	        splitLine:{show: false},//去除网格线
	        axisPointer: {
	            snap: true
	        }
	    },
		//系列列表。每个系列通过 type 决定自己的图表类型
	    series: [
	        {
	            type:'line',
	            smooth: true,	//是否平滑曲线显示
	            data: yData,
	            symbolSize: 8,
	            itemStyle: {	//折线拐点标志的样式
	            	borderWidth: 2,
	            	borderColor: '#2e6e79'
	            },
	            lineStyle: {	//线条样式
	            	color: '#2e6e79',
	            	width: 3
	            },
	            areaStyle: {	//区域填充样式
	            	color: {
					    type: 'linear',
					    x: 0,
					    y: 0,
					    x2: 1,
					    y2: 0,
					    colorStops: [{
					        offset: 0, color: '#207262' // 0% 处的颜色
					    }, {
					        offset: 1, color: '#296d77' // 100% 处的颜色
					    }],
					    globalCoord: false // 缺省为 false
					}
	            }
	        }
	    ]
	};

    totalAmountChart.setOption(option);
}
