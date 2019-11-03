		$(function(){
				$(".jian").click(function(){
					var $objjian=$(this).next().val();
					if($objjian==1){
						alert("不能再减了，再减就没有了");
						jisuan()
						return;
					}
					$objjian=parseInt($objjian)-1;
					$(this).next().val($objjian);
					var xianjia=$(this).parents("tr").children("td:eq(2)").children("span:eq(0)").html();
					xianjia=parseFloat(xianjia.substr(1));
					var xiaoji=parseInt($(this).next().val())*xianjia;
					xiaoji=parseFloat(xiaoji).toFixed(2);
					$(this).parents("td").next().html("￥"+xiaoji)
					jisuan();				
				});
				
				$(".jia").click(function(){
					var $obj=$(this).parents("td");
					var $objjian=$obj.children("input").val();
					$objjian=parseInt($objjian)+1;
					$obj.children("input").val($objjian);
					var xianjia=$(this).parents("tr").children("td:eq(2)").children("span:eq(0)").html();
					xianjia=parseFloat(xianjia.substr(1));
					var xiaoji=parseInt($obj.children("input").val())*xianjia;
					xiaoji=parseFloat(xiaoji).toFixed(2);
					$(this).parents("td").next().html("￥"+xiaoji)
					jisuan();
				});
				$(".gouwunum").blur(function(){
					if(isNaN($(this).val())||parseInt($(this).val())<0){
						alert("请输入大于零的数字");
						$(this).val("1");
						var yuanjia= $(this).parents("tr").children("td:eq(2)").children("span:eq(1)").html();
						yuanjia=yuanjia.substr(1);
						var xiaoji=parseInt(yuanjia)*1;
						xiaoji=parseFloat(xiaoji).toFixed(2);
						$(this).parents("td").next().html("￥"+xiaoji);	
						jisuan();
						pid=$(this).parents("tr").children("td:eq(0)").children("input:eq(0)").val();
						location.href="CarItemServlet?method=xiu&&pid="+pid+"&&jianshu=1";
						location.submit();
						return;
					}
					var xianjia=$(this).parents("tr").children("td:eq(2)").children("span:eq(1)").html();
					xianjia=parseFloat(xianjia.substr(1));
					var xiaoji=parseInt($(this).val())*xianjia;
					$(this).parents("td").next().html("￥"+xiaoji)
					jisuan();
					pid=$(this).parents("tr").children("td:eq(0)").children("input:eq(0)").val();
					location.href="CarItemServlet?method=xiu&&pid="+pid+"&&jianshu="+$(this).val();
					location.submit();
				});
				$(".prodcol6 a").click(function(){
					$(this).parents("tr").remove();
					jisuan();
					pid=$(this).parents("tr").children("td:eq(0)").children("input:eq(0)").val();
					location.href="CarItemServlet?method=shan&&pid="+pid;
					location.submit();
				});
				$(".prodBuy a").click(function(){
					var name=$(this).parents("ul").children("li:eq(0)").text();
					var yuanjia=$(this).parents("ul").children("li:eq(1)").text();
					var xianjia=$(this).parents("ul").children("li:eq(2)").text();	
					var gouwutable=document.getElementsByClassName("gouwutable")[0];
					var rowCount=gouwutable.rows.length;
					for (var i=2;i<rowCount;i++) {
						if(name==gouwutable.rows[i].cells[1].getElementsByTagName("a")[0].innerHTML){
							gouwutable.rows[i].cells[3].getElementsByTagName("input")[0].value=parseInt(gouwutable.rows[i].cells[3].getElementsByTagName("input")[0].value)+1;
							jisuan()
							return;
						}
					}
					var new_row=gouwutable.insertRow(rowCount);
					new_row.className="prodRow";
					var cell1=new_row.insertCell(0);
					cell1.className="prodcol1";
					cell1.innerHTML="<input type='checkbox' class='checkprod'/><img class='prodImg' src='img/632.jpg'/>";
					var cell2=new_row.insertCell(1);
					cell2.className="prodcol2";
					cell2.innerHTML="<a href='javascript:'>"+name+"</a><br/><img src='img/creditcard.png'/><img src='img/7day.png'/><img src='img/promise.png'/>";
					var cell3=new_row.insertCell(2);
					cell3.className="prodcol3";
					cell3.innerHTML="<span class='yuanjia'>"+yuanjia+"</span><br/><span class='xianjia'>"+xianjia+"</span>";
					var cell4=new_row.insertCell(3);
					cell4.className="prodcol4";
					cell4.innerHTML="<span class='gouwufuhao jian'>-</span><input class='gouwunum' type='text' value='1'/><span class='gouwufuhao jia'>+</span>";
					var cell5=new_row.insertCell(4);
					cell5.className="prodcol5";
					cell5.innerHTML=xianjia;
					var cell6=new_row.insertCell(5);
					cell6.className="prodcol6";
					cell6.innerHTML="<a href='javascript:'>删除</a>";
					$(".prodcol6 a").on("click",function(){
						$(this).parents("tr").remove();
						jisuan();
						
					});
					$(".jian").last().on("click",function(){
						var $objjian=$(this).next().val();
						if($objjian==1){
							alert("不能再减了，再减就没有了");
							jisuan()
							return;
						}
						$objjian=parseInt($objjian)-1;
						$(this).next().val($objjian);
						var xianjia=$(this).parents("tr").children("td:eq(2)").children("span:eq(1)").html();
						xianjia=parseFloat(xianjia.substr(1));
						var xiaoji=parseInt($(this).next().val())*xianjia;
						xiaoji=parseFloat(xiaoji).toFixed(2);
						$(this).parents("td").next().html("￥"+xiaoji)		
						jisuan()
					});
					$(".jia").last().on("click",function(){
						var $obj=$(this).parents("td");
						var $objjian=$obj.children("input").val();
						$objjian=parseInt($objjian)+1;
						$obj.children("input").val($objjian);
						var xianjia=$(this).parents("tr").children("td:eq(2)").children("span:eq(1)").html();
						xianjia=parseFloat(xianjia.substr(1));
						var xiaoji=parseInt($obj.children("input").val())*xianjia;
						xiaoji=parseFloat(xiaoji).toFixed(2);
						$(this).parents("td").next().html("￥"+xiaoji)
						jisuan()
					});
					$(".gouwunum").on("blur",function(){
						if(isNaN($(this).val())||parseInt($(this).val())<0){
							alert("请输入大于零的数字")
							$(this).val("1");
							var yuanjia= $(this).parents("tr").children("td:eq(2)").children("span:eq(1)").html();
							yuanjia=yuanjia.substr(1);
							var xiaoji=parseInt(yuanjia)*1;
							xiaoji=parseFloat(xiaoji).toFixed(2);
							$(this).parents("td").next().html("￥"+xiaoji);	
							jisuan()
							return;
						}
						var xianjia=$(this).parents("tr").children("td:eq(2)").children("span:eq(1)").html();
						xianjia=parseFloat(xianjia.substr(1));
						var xiaoji=parseInt($(this).val())*xianjia;
						$(this).parents("td").next().html("￥"+xiaoji)	
						jisuan()
					});
					$(".checkprod").on("click",function(){
						jisuan()
					})
					jisuan()
				});
				$(".checkprod").click(function(){
					jisuan()
				});
				$("#tuijianTItle").click(function(){
					if($("#tuijianInfo").css("display")=="block"){
						$("#tuijianInfo").css("display","none");
						$(this).css("border-radius","15px");
					}else{
						$("#tuijianInfo").css("display","block");
						$(this).css({"border-bottom-left-radius":"0px","border-bottom-right-radius":"0px"});
					}
				});
				
			});
			function quanxuan(op){
				var check=document.getElementsByClassName("checkprod");
				var checkall=document.getElementsByClassName("checkAll");
				var quanxuan=document.getElementsByClassName("qunxuan");
				for(var i=0;i<check.length;i++){
					check[i].checked=op;
				}
				for(var i=0;i<checkall.length;i++){
					checkall[i].checked=op;
				}
				for(var i=0;i<quanxuan.length;i++){
					quanxuan[i].checked=op;
				}
				jisuan()
			}
			function jisuan(){
				var checknum=0;
				var zongjia=0
				var checkprod=document.getElementsByClassName("checkprod");
				for(var i=0;i<checkprod.length;i++){
					if(checkprod[i].checked){
						checknum++;
						var xiaoji=document.getElementsByClassName("prodRow")[i].getElementsByClassName("prodcol5")[0].innerHTML;
						xiaoji=parseFloat(xiaoji.substr(1));
						zongjia=zongjia+xiaoji;
					}
				}
				if (checknum==0) {
					document.getElementsByClassName("jiesuanButton")[0].style.background="#DDDDDD";
					document.getElementsByClassName("deleteall")[0].style.background="#999999";
				} else{
					document.getElementsByClassName("jiesuanButton")[0].style.background="#C40000";
					document.getElementsByClassName("deleteall")[0].style.background="#C40000";
				}
				zongjia=parseFloat(zongjia).toFixed(2);
				document.getElementsByClassName("prodzong")[0].innerHTML=checknum;
				document.getElementsByClassName("prodPrice")[0].innerHTML="￥"+zongjia;
			}
			function deleteAll1(){
				var checkprod=document.getElementsByClassName("checkprod");
				for(var i=checkprod.length;i>0;i--){
					if(checkprod[i-1].checked){
						var fu=document.getElementsByClassName("gouwutable")[0];
						fu.deleteRow(i+1);
					}
				}
				jisuan();	
			}
			function danxuan(){
				var count=document.getElementsByName("checkProdId").length;
				var checkids=document.getElementsByName("checkProdId");
				var testcount=0;
				for(var i=0;i<count;i++){
					if(checkids[i].checked){
						testcount=++testcount;	
					}
				}
				if(testcount==count){
					document.getElementsByClassName("checkAll")[0].checked=true;
					document.getElementsByClassName("qunxuan")[0].checked=true;
				}else{
					document.getElementsByClassName("checkAll")[0].checked=false;
					document.getElementsByClassName("qunxuan")[0].checked=false;
				}
			}
