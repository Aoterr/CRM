<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.zendaimoney.crm.modification.entity.Field"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglibs.jsp"%>
<%@ page import="com.zendaimoney.crm.fieldform.helper.formUtilHelper"%>

<input type="hidden" id="existFlag" name="existFlag" value="${existFlag}">

<c:if test="${existFlag!=''&&existFlag!=null}">&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;该变更项所属对象（联系人、地址、电话）不存在，如需更改请新建该对象！</c:if>
 <c:forEach var="bank" items="${items }">
 	 <c:forEach var="field" items="${fields }" varStatus="sta">
 	 <script type="text/javascript">
 	 	if ('${field.fdFieldOption}' == 'text') {
			$('#inputId${sta.count}').html(
					'<input name="${field.fdFieldEn }" id="${field.fdFieldEn }" type="text" value="<%=formUtilHelper.getValue(pageContext.getAttribute("bank"), pageContext.getAttribute("field"))%>"/>');
		}
 	 	if ('${field.fdFieldOption}' == 'numberbox') {
			$('#inputId${sta.count}').html(
					'<input name="${field.fdFieldEn }" id="${field.fdFieldEn }" type="text" value="<%=formUtilHelper.getValue(pageContext.getAttribute("bank"), pageContext.getAttribute("field"))%>"/>');
			$('#${field.fdFieldEn }').numberbox({ min:0,precision:2}); 
		}
		if ('${field.fdFieldOption}' == 'select') {
			$('#inputId${sta.count}').html('<select name="${field.fdFieldEn }" id="${field.fdFieldEn}"  name="${field.fdFieldEn }" ></select>');
			if('${field.fdReserve}' == 'product') {
				comboboxUtil.setComboboxByUrl('${field.fdFieldEn }',"${ctx}/${field.fdUrl}", 'id', 'ptProductName', '150', false);
				if(dataOrigin=='1'){
					comboboxUtil.setComboboxByUrl('${field.fdFieldEn }',"${ctx}/crm/visit/find/finance/product", 'id', 'ptProductName', '150', false);
				}
			}else if('${field.fdReserve}' == 'nations'){
				comboboxUtil.setComboboxByUrlWithpanelHeight('${field.fdFieldEn }',"${ctx}/${field.fdUrl}", 'id', 'name', '150', false);

			}else if('${field.fdReserve}' == 'payment'){
				comboboxUtil.setComboboxByUrl('${field.fdFieldEn }',"${ctx}/${field.fdUrl}", 'prValue', 'prName', '150',true,function(rec){loadPayMentWay(rec.prValue)});
				//comboboxUtil.setValue('${field.fdFieldEn }',null); //设置默认选择自行转账（目的是默认加载不是选中委托划扣）
			}
			else{
				comboboxUtil.setComboboxByUrl('${field.fdFieldEn }',"${ctx}/${field.fdUrl}", 'prValue', 'prName', '150', false);
			}
			var  fieldValue='<%=formUtilHelper.getValue(pageContext.getAttribute("bank"), pageContext.getAttribute("field"))%>';
			if(fieldValue=='1.0')fieldValue='1';
			if(fieldValue=='0.0')fieldValue='0';
			$('#${field.fdFieldEn }').combobox('setValue',fieldValue);
			//if('${field.fdReserve}' == 'payment'){$('#${field.fdFieldEn }').combobox('setValue','')}
		}
		if ('${field.fdFieldOption}' == 'date') {
			<%  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String date = formUtilHelper.getValue(pageContext.getAttribute("bank"), pageContext.getAttribute("field"));
			Field fieldnew =(Field) pageContext.getAttribute("field");
			if(fieldnew.getFdFieldOption().equalsIgnoreCase("date")){
				if(!date.isEmpty()){date = formatter.format(formatter.parse(date));}
			}
			%>
			if('${field.fdReserve}' == 'forever'){
				$('#inputId${sta.count}').html(
				'<input name="${field.fdFieldEn }" id="${field.fdFieldEn }" type="text" class="Wdate" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\'})" value="<%= date%>"/>'
				+'<input type="checkbox" id = "forever_crExpiryDate" name = "forever_crExpiryDate" onclick="foreverCrExpiryDate(this);" value="0">长期'
						);
				if(<%= date.equals(formatter.format(formatter.parse("2099-12-31")))%>){
					$('#forever_crExpiryDate').attr("checked","true");
					$('#${field.fdFieldEn }').hide();
					}
				}else{
				$('#inputId${sta.count}').html(
				'<input name="${field.fdFieldEn }" id="${field.fdFieldEn }" type="text" class="Wdate" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\'})" value="<%= date%>"/>');
					}
				}
		if ('${field.fdFieldOption}' == 'select1') {
			$('#inputId${sta.count}')
			.html(
					'<select name="${field.fdFieldEn }"  id="${field.fdFieldEn}"  name="${field.fdFieldEn }" ></select>');
			comboboxUtil.setComboboxByUrl('${field.fdFieldEn }',"${ctx}/${field.fdUrl}${field.fdReserve == 'select1' ? bank.customerid : null}", 'id', 'baAccount', '200', false);
			comboboxUtil.formatter('${field.fdFieldEn }',function(row){return row.baBankName + "  " + row.baAccount});
			$('#${field.fdFieldEn }').combobox('setValue','<%=formUtilHelper.getValue(pageContext.getAttribute("bank"), pageContext.getAttribute("field"))%>'); 
		} 
		if ('${field.fdFieldOption}' == 'checkbox') {
			if('${field.fdReserve}' == 'sex') {
				$('#inputId${sta.count}').html('<input type="checkbox" name="${field.fdFieldEn }" id="${field.fdFieldEn }" onclick="checkedThis($(this))" value="1" disp="男">&nbsp;男<input type="checkbox" name="${field.fdFieldEn }" id="${field.fdFieldEn }" onclick="checkedThis($(this))" value="0" disp="女">&nbsp;女');
				$("input:checkbox[name='${field.fdFieldEn }']").each(function(){
					if($(this).val()=='<%=formUtilHelper.getValue(pageContext.getAttribute("bank"), pageContext.getAttribute("field"))%>')
						 $(this).attr("checked",'true');
				})
			} 
			else if ('${field.fdFieldEn}' == 'feDeductCompany') {
				var boxStr='';
				postAjax(false, "${ctx}/${field.fdUrl}", function(data1) {
					 $(data1).each(function(index) {
						var data = data1[index]; 
						boxStr+='<input type="checkbox" onchange="deductCompanyChange(this)" name="${field.fdFieldEn }" id="${field.fdFieldEn }' + index + '" value="'+data.prValue+'" disp="'+data.prName+'">&nbsp;'+data.prName;
						$('#inputId${sta.count}').html(boxStr);
						$("input:checkbox[name='${field.fdFieldEn }']").each(function(){
							if($(this).val()==($(this).val() & '<%=formUtilHelper.getValue(pageContext.getAttribute("bank"), pageContext.getAttribute("field"))%>'))
								 $(this).attr("checked",'true');
						});
					 })
				}, 'json');
			}
			else{
				var boxStr='';
				postAjax(false, "${ctx}/${field.fdUrl}", function(data1) {
					 $(data1).each(function(index) {
						 var data = data1[index]; 
						boxStr+='<input type="checkbox" name="${field.fdFieldEn }" id="${field.fdFieldEn }" onclick="checkedThis($(this))" value="'+data.prValue+'" disp="'+data.prName+'">&nbsp;'+data.prName;
						$('#inputId${sta.count}').html(boxStr);
						$("input:checkbox[name='${field.fdFieldEn }']").each(function(){
							if($(this).val()=='<%=formUtilHelper.getValue(pageContext.getAttribute("bank"), pageContext.getAttribute("field"))%>')
								 $(this).attr("checked",'true');
						})
					 })
				}, 'json');
			}
		} 

		if ('${field.fdFieldOption}' == 'radio') {
			var boxStr='';
			boxStr+='<input type="radio" name="${field.fdFieldEn }" id="${field.fdFieldEn }" onclick="checkedThis($(this))" value="1" disp="是">&nbsp;' + "是";
			boxStr+='<input type="radio" name="${field.fdFieldEn }" id="${field.fdFieldEn }" onclick="checkedThis($(this))" value="0" disp="否">&nbsp;' + "否";
			$('#inputId${sta.count}').html(boxStr);
			$("input:radio[name='${field.fdFieldEn }']").each(function(){
				if($(this).val()=='<%=formUtilHelper.getValue(pageContext.getAttribute("bank"), pageContext.getAttribute("field"))%>'){
					$(this).attr("checked",'true');
				}
			})
		} 	 	
		
		if ('${field.fdFieldOption}' == 'tel') {
			if('${field.fdReserve}' == '1'||'${field.fdReserve}' == '2' ||'${field.fdReserve}' == '4'||'${field.fdReserve}' == '5'){
				$('#inputId${sta.count}').html("<%=formUtilHelper.getTel(pageContext.getAttribute("bank"), pageContext.getAttribute("field"))%>");
				$('#tlAreaCode').validatebox({ validType:'numberL[3,4]'}); 
				$('#tlTelNum').validatebox({ validType:'numberL[6,8]'}); 
				$('#tlExtCode').validatebox({ validType:'numberL[0,6]'}); 
				}
			if('${field.fdReserve}' == '3'){
				$('#inputId${sta.count}').html(
				'<input name="${field.fdFieldEn }" id="${field.fdFieldEn }" class="easyui-validatebox"  type="text" value="<%=formUtilHelper.getValue(pageContext.getAttribute("bank"), pageContext.getAttribute("field"))%>"/>');
				$('#${field.fdFieldEn }').validatebox({ validType:'mobile'});  
				}
		}
		if ('${field.fdFieldOption}' == 'addr') {
			if('${field.fdReserve}' == 'house'){
	  			$('#inputId${sta.count}').html("<%=formUtilHelper.getHouseAddr(pageContext.getAttribute("bank"), pageContext.getAttribute("field"))%>");
				$('#heZipCode').validatebox({ validType:'ZIP'});  
				addrProvice('heProvince','heCity','heCounty','','heAddrDetail');//通讯地址
				if($('#heProvince').val()!=null){
					addrCity('heProvince','heCity','heCounty','','heAddrDetail',$('#heProvince').combobox('getValue'));//单位地址
				}
				if($('#heCity').val()!=null){
					addrCounty('heProvince','heCity','heCounty','','heAddrDetail',$('#heCity').combobox('getValue'));//单位地址  
				} 
			}
			else{
		     		$('#inputId${sta.count}').html("<%=formUtilHelper.getAddr(pageContext.getAttribute("bank"), pageContext.getAttribute("field"))%>");
					$('#arZipCode').validatebox({ validType:'ZIP'});  
					addrProvice('arProvince','arCity','arCounty','arStreet','arAddrDetail');//通讯地址
					if($('#arProvince').val()!=null){
						addrCity('arProvince','arCity','arCounty','arStreet','arAddrDetail',$('#arProvince').combobox('getValue'));//单位地址
					}
					if($('#arCity').val()!=null){
						addrCounty('arProvince','arCity','arCounty','arStreet','arAddrDetail',$('#arCity').combobox('getValue'));//单位地址
					}
				}	
		}
		if ('${field.fdFieldOption}' == 'tlPriority') {
			$('#inputId${sta.count}').html('<select name="${field.fdFieldEn }" id="${field.fdFieldEn}"  name="${field.fdFieldEn }" ></select>');
			comboboxUtil.setComboboxByUrl('${field.fdFieldEn }',"${ctx}/crm/customer/findByCustomeridAndTlCustTypeAndTlTelType?obId=${field.fdFieldOption == 'tlPriority' ? bank.customerid : null}&cusType=${field.fdMemo }&fdReserve=${field.fdReserve}", 'obId', 'obString', '250', false);
			if(null!=<%=formUtilHelper.getTelValue(pageContext.getAttribute("bank"), pageContext.getAttribute("field"))%>){
				$('#${field.fdFieldEn }').combobox('setValue','<%=formUtilHelper.getTelValue(pageContext.getAttribute("bank"), pageContext.getAttribute("field"))%>');
			} 
		}
		if ('${field.fdFieldOption}' == 'arPriority') {
			$('#inputId${sta.count}').html('<select name="${field.fdFieldEn }" id="${field.fdFieldEn}"  name="${field.fdFieldEn }" ></select>');
			comboboxUtil.setComboboxByUrl('${field.fdFieldEn }',"${ctx}/crm/customer/findByCustomeridAndArCustTypeAndArTelType?obId=${field.fdFieldOption == 'arPriority' ? bank.customerid : null}&cusType=${field.fdMemo }&fdReserve=${field.fdReserve}", 'obId', 'obString', '250', false);
			if(null!=<%=formUtilHelper.getAddrValue(pageContext.getAttribute("bank"), pageContext.getAttribute("field"))%>){
				$('#${field.fdFieldEn }').combobox('setValue','<%=formUtilHelper.getAddrValue(pageContext.getAttribute("bank"), pageContext.getAttribute("field"))%>');
			} 
		}
		function foreverCrExpiryDate(obj){
			if(obj.checked==true){
		    	$('#customer_crExpiryDate').attr('disabled',true);
		    	$('#${field.fdFieldEn }').val('');
		    	$('#${field.fdFieldEn }').hide();
		    }else{
		    	$('#customer_crExpiryDate').attr('disabled',false);
		    	$('#${field.fdFieldEn }').val('');
		    	$('#${field.fdFieldEn }').show();
		    	
		    }
		}
		//划扣公司单选
		function deductCompanyChange(obj) {
			var check=obj.checked;
			$("input[name=\"feDeductCompanyBox\"]").each(function()
					{
					   this.checked=false;
					});
			obj.checked=check;
		} 
		
		</script>
		&nbsp; &nbsp; &nbsp;&nbsp;&nbsp; ${field.fdFieldCn } &nbsp; &nbsp; <span id='inputId${sta.count }'></span><br>
		&nbsp; &nbsp; &nbsp;&nbsp;&nbsp; <span id='deductId'></span> 
 	 </c:forEach>
 </c:forEach>
 