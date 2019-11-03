package com.oracle.ssm.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.config.AlipayConfig;
import com.oracle.ssm.mapper.OrdersMapper;
import com.oracle.ssm.model.Orders;
import com.oracle.ssm.service.OrdersService;

@Controller
public class AlipayController  {
	
	@Autowired
	private OrdersMapper ordersMapper;
	
	@RequestMapping("/goAlipay.do")
	public void goAlipay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, AlipayApiException {
		//OrderInfoService ifs=new OrderInfoServiceImpl();
		//OrderInfo order=ifs.getOrderInfoById(req.getParameter("WIDout_trade_no"));
		
		//�̻������ţ��̻���վ����ϵͳ��Ψһ�����ţ�����
		String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//���������
		String total_amount = new String(request.getParameter("WIDtotal_amount").getBytes("ISO-8859-1"),"UTF-8").trim();
		//�������ƣ�����
		String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
		//��Ʒ�������ɿ�
		String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
		


		//Product product = productService.getProductById(order.getProductId());

		//��ó�ʼ����AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

		//�����������
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);


		// �ñʶ��������������ʱ�䣬���ڽ��رս��ס�ȡֵ��Χ��1m��15d��m-���ӣ�h-Сʱ��d-�죬1c-���죨1c-���������£����۽��׺�ʱ����������0��رգ��� �ò�����ֵ������С���㣬 �� 1.5h����ת��Ϊ 90m��
    	String timeout_express = "15m";

		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
				+ "\"total_amount\":\""+ total_amount +"\","
				+ "\"subject\":\""+ subject +"\","
				+ "\"body\":\""+ body +"\","
				+ "\"timeout_express\":\""+ timeout_express +"\","
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

		//����
		String result = alipayClient.pageExecute(alipayRequest).getBody();

		String head="<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> <head>";
		String foot="<body></body></html>";
		response.getWriter().write(head+result+foot);
		
	}
	@RequestMapping("/returnNotice.do")
	public String alipayReturnNotice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, AlipayApiException {
		/* *
		 * ���ܣ�֧����������ͬ��֪ͨҳ��
		 * ���ڣ�2017-03-30
		 * ˵����
		 * ���´���ֻ��Ϊ�˷����̻����Զ��ṩ���������룬�̻����Ը����Լ���վ����Ҫ�����ռ����ĵ���д,����һ��Ҫʹ�øô��롣
		 * �ô������ѧϰ���о�֧�����ӿ�ʹ�ã�ֻ���ṩһ���ο���


		 *************************ҳ�湦��˵��*************************
		 * ��ҳ�����ҳ��չʾ��ҵ���߼����������ڸ�ҳ��ִ��
		 */
		 
			//��ȡ֧����GET����������Ϣ
			Map<String,String> params = new HashMap<String,String>();
			Map<String,String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//����������δ����ڳ�������ʱʹ��
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}
			
			boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //����SDK��֤ǩ��

			//�������������д���ĳ������´�������ο�������
			if(signVerified) {
				//�̻�������
				String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			
				//֧�������׺�
				String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			
				//������
				String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
				
				
				
				//response.getWriter().write("trade_no:"+trade_no+"<br/>out_trade_no:"+out_trade_no+"<br/>total_amount:"+total_amount);
				request.setAttribute("price", total_amount);
				return "buyprodend";
			
			}else {
				response.getWriter().write("��ǩʧ��");
				return "";
			}
			//�������������д���ĳ������ϴ�������ο�������
	}
	
	public void alipayNotifyNotice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, AlipayApiException {
		
		/* *
		 * ���ܣ�֧�����������첽֪ͨҳ��
		 * ���ڣ�2017-03-30
		 * ˵����
		 * ���´���ֻ��Ϊ�˷����̻����Զ��ṩ���������룬�̻����Ը����Լ���վ����Ҫ�����ռ����ĵ���д,����һ��Ҫʹ�øô��롣
		 * �ô������ѧϰ���о�֧�����ӿ�ʹ�ã�ֻ���ṩһ���ο���


		 *************************ҳ�湦��˵��*************************
		 * ������ҳ���ļ�ʱ�������ĸ�ҳ���ļ������κ�HTML���뼰�ո�
		 * ��ҳ�治���ڱ������Բ��ԣ��뵽�������������ԡ���ȷ���ⲿ���Է��ʸ�ҳ�档
		 * ���û���յ���ҳ�淵�ص� success 
		 * �����ҳ��ֻ��֧���ɹ���ҵ���߼������˿�Ĵ������Ե����˿��ѯ�ӿڵĽ��Ϊ׼��
		 */
		 
			//��ȡ֧����POST����������Ϣ
			Map<String,String> params = new HashMap<String,String>();
			Map<String,String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//����������δ����ڳ�������ʱʹ��
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}
			
			boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //����SDK��֤ǩ��

			//�������������д���ĳ������´�������ο�������
			
			/* ʵ����֤���̽����̻�����������У�飺
			1����Ҫ��֤��֪ͨ�����е�out_trade_no�Ƿ�Ϊ�̻�ϵͳ�д����Ķ����ţ�
			2���ж�total_amount�Ƿ�ȷʵΪ�ö�����ʵ�ʽ����̻���������ʱ�Ľ���
			3��У��֪ͨ�е�seller_id������seller_email) �Ƿ�Ϊout_trade_no��ʵ��ݵĶ�Ӧ�Ĳ��������е�ʱ��һ���̻������ж��seller_id/seller_email��
			4����֤app_id�Ƿ�Ϊ���̻�����
			*/
			if(signVerified) {//��֤�ɹ�
				//�̻�������
				String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			
				//֧�������׺�
				String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			
				//����״̬
				String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
				
				if(trade_status.equals("TRADE_FINISHED")){
					//�жϸñʶ����Ƿ����̻���վ���Ѿ���������
					//���û�������������ݶ����ţ�out_trade_no�����̻���վ�Ķ���ϵͳ�в鵽�ñʶ�������ϸ����ִ���̻���ҵ�����
					//���������������ִ���̻���ҵ�����
					
					Orders orders=new Orders();
					orders.setOrderId(Integer.parseInt(out_trade_no));
					orders.setStaus(2);//2��ʾ�Ѹ���
					ordersMapper.updateByPrimaryKey(orders);
						
					//ע�⣺
					//�˿����ڳ������˿����޺��������¿��˿��֧����ϵͳ���͸ý���״̬֪ͨ
				}else if (trade_status.equals("TRADE_SUCCESS")){
					//�жϸñʶ����Ƿ����̻���վ���Ѿ���������
					//���û�������������ݶ����ţ�out_trade_no�����̻���վ�Ķ���ϵͳ�в鵽�ñʶ�������ϸ����ִ���̻���ҵ�����
					//���������������ִ���̻���ҵ�����
					Orders orders=new Orders();
					orders.setOrderId(Integer.parseInt(out_trade_no));
					orders.setStaus(2);//2��ʾ�Ѹ���
					ordersMapper.updateByPrimaryKey(orders);
					//ע�⣺
					//������ɺ�֧����ϵͳ���͸ý���״̬֪ͨ
				}
				
				//out.println("success");
				
			}else {//��֤ʧ��
				//out.println("fail");
			
				//�����ã�д�ı�������¼������������Ƿ�����
				//String sWord = AlipaySignature.getSignCheckContentV1(params);
				//AlipayConfig.logResult(sWord);
			}
			
			//�������������д���ĳ������ϴ�������ο�������
	}

}
