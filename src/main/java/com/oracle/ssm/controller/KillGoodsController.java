package com.oracle.ssm.controller;

import java.awt.List;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.oracle.ssm.model.Goods;
import com.oracle.ssm.model.Orders;
import com.oracle.ssm.model.User;
import com.oracle.ssm.service.GoodsPriceService;
import com.oracle.ssm.service.GoodsService;
import com.oracle.ssm.service.KillGoodsVoService;
import com.oracle.ssm.service.OrdersService;
import com.oracle.ssm.vo.KillGoodsVo;
import com.oracle.ssm.vo.SecKillGoodsVo;

@Controller
public class KillGoodsController {
	
	@Autowired
	private KillGoodsVoService kgvs;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private GoodsPriceService priceService;
	@RequestMapping("toKillList.do")
	public ModelAndView toKillList(HttpServletRequest request){
		//������ɱ
		//��ɱ��Ʒ��Ϣ
		KillGoodsVo kg=kgvs.setKillGoodsInfo(2);
		
		//�û���Ϣ
		String host=request.getRemoteAddr();
		String key="session:"+host;
		byte[] bytes=(byte[]) redisTemplate.opsForValue().get(key);
		User user=(User) redisTemplate.getHashValueSerializer().deserialize(bytes);
		
		//����ʣ������
		String goodsId="seckillgoods:"+kg.getGoods().getId();
		Long num=redisTemplate.boundListOps(goodsId).size();
		
		ModelAndView mv=new ModelAndView();
		mv.setViewName("killList");
		mv.addObject("user",user);
		mv.addObject("killGoods",kg);
		mv.addObject("num",num);
		
		return mv;
	}
	
	@RequestMapping("setKillInfo.do")
	public void setKillInfo(){
		//���� ������
		String key="seckillgoods:2";
		for(int i=1;i<=101;i++){
			redisTemplate.boundListOps(key).rightPush(i);
		}
		
		
	}
	
	@RequestMapping("killGoods.do")
	public  void kill(int id1,int id2,HttpServletResponse resp) throws Exception{
		
		// ������ɱ ��Ʒid1,�û�id2
		String goodsId="seckillgoods:"+id1;//��ɱ��Ʒlist���е� key
		String userKey="seccess:kill:"+id1;//��ɱ�ɹ��û�set���� key
		String userid=id2+"";
		
		if(redisTemplate.boundListOps(goodsId).size()!=0){//�ж���ɱ��Ʒ�Ƿ���
			if(!redisTemplate.opsForSet().isMember(userKey, userid)){//�ж��Ƿ��Ѿ�����
				String index= redisTemplate.boundListOps(goodsId).leftPop()+"";
				if(index!=null){
					
					redisTemplate.boundSetOps(userKey).add(userid);//��������set����û�id
					resp.getWriter().write("1");//System.out.println("��ɱ�ɹ���"+index);
				}else{
					resp.getWriter().write("2");//System.out.println("�´�������");
				}
			}else{
				resp.getWriter().write("3");//System.out.println("��������,��ѻ����������ˣ�");
			}
			
			
		}else{
			resp.getWriter().write("2");//System.out.println("�´�������");
		}
		
	}
	
	@RequestMapping("/goPay.do")
	public ModelAndView goPay(int id1,int id2,Double money,HttpServletRequest req){//id1��Ʒid,id2�û�id
		
		String userKey="seccess:kill:"+id1;//��ɱ�ɹ��û�set���� key
		String userid=id2+"";
		if(redisTemplate.opsForSet().isMember(userKey, userid)){//���μ����û�id
			
			Orders orders=new Orders();
			orders.setGoodsId(id1);
			orders.setUserId(id2);
			orders.setUpdateTime(new Date());
			orders.setOrdersMoney(money+"");
			orders.setShopCount(1+"");//��������Ĭ��Ϊ1
			orders.setStaus(0);//����״̬Ĭ��0��δ����
			ordersService.addOrders(orders);
			
			
			ModelAndView mv=new ModelAndView();
			mv.setViewName("killPay");
			mv.addObject("orders", orders);
			return mv;
		}
		
		return null;
		
	}
	
	@RequestMapping("/makeDate.do")
	public void toKillPay() throws Exception{
		//�������Ʒ�ļ�����
	//key1 kill:goods:��������:id:��Ʒid ��Ʒ��Ϣ��װ GoodsVo��key
		
	//key2 kill:goods:��������:list:��Ʒid ��Ʒ�Ŀ����� list��key
		
	//key3 kill:users:��������:set:�û�id �����ɹ��û�id set���ϵ�key
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date=sdf.format(new Date());
		
		for(int i=1;i<5;i++){//i����goods��id
			//��һ��key value string����
			String key1="kill:goods:"+date+":id:"+i;
			SecKillGoodsVo vo=new SecKillGoodsVo();
			vo.setGoods(goodsService.findGoodsById(i));
			vo.setStartTime(sdf2.parse("2019-08-18 8:00:00")); 
			vo.setEndTime(sdf2.parse("2019-08-18 23:00:00"));
			vo.setPrice(priceService.findGoodsPriceByGoodsId(i).getDiscountPrice()+"");
			vo.setInitCount(100);
			
			//���л���ɱ��Ʒ�ķ�װ��Ϣ
			byte[] b=redisTemplate.getHashValueSerializer().serialize(vo);
			redisTemplate.boundValueOps(key1).set(b);
			
			//�ڶ���key list list����
			String key2="kill:goods:"+date+":list:"+i;
			for(int j=1;j<=100;j++){
				redisTemplate.boundListOps(key2).leftPush(j);
			}
			
		}
	
	}
	@RequestMapping("/toManyKillList.do")
	public String manyKillList(HttpServletRequest req){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String date=sdf.format(new Date());
		String goods_key_prefix="kill:goods:"+date+":id:*";
		ArrayList<SecKillGoodsVo> list=new ArrayList<>();
		Set<String> set=redisTemplate.keys(goods_key_prefix);
		
		for(String key:set){
			//�����л���ȡ��Ʒ��Ϣ �浽list
			byte[] bs=(byte[]) redisTemplate.boundValueOps(key).get();
			SecKillGoodsVo vo=(SecKillGoodsVo) redisTemplate.getHashValueSerializer().deserialize(bs);
			
			//��ɱ����ҳ����Ҫʱʱ������Ʒ�������
			//���ݴ洢��Ʒ���� list�Ĵ�С 
			vo.setInitCount((redisTemplate.boundListOps("kill:goods:"+date+":list:"+vo.getGoods().getId()).size()).intValue());
			list.add(vo);
			
		}
		
		req.setAttribute("goodsList", list);
		
		return "manyKillList";
	}
	
	@RequestMapping("/manyKillGoods.do")
	public void manyKillGoods(int id,HttpServletRequest req,HttpServletResponse resp) throws Exception{
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String date=sdf.format(new Date());
		
		String key1="kill:goods:"+date+":id:"+id;//�洢��Ʒid��vo�� value��key
		String key2="kill:goods:"+date+":list:"+id;//�洢���� list��key
		String key3="kill:users:"+date+":set:"+id;//�洢�����û���id set��key
		//�����жϵ�¼��� session
		//�ж���Ʒ������� list�Ĵ�С
		//�ж�����û�� set���Ƿ���userId
		//��ϲ����
		
		String userKey="session:"+req.getRemoteAddr();
		
		if(redisTemplate.hasKey(userKey)){
			//�ѵ�¼,�ж��Ƿ�������ʱ��
			SecKillGoodsVo vo=(SecKillGoodsVo)redisTemplate.getHashValueSerializer().deserialize((byte[])(redisTemplate.boundValueOps(key1).get()));
			if(vo.getStartTime().before(new Date())){
				// �����ж���Ʒ�������
				// ��redisTemplate���ȡ�û���Ϣ����Ҫ���û�id
				byte[] bs = (byte[]) redisTemplate.boundValueOps(userKey).get();
				User user = (User) redisTemplate.getHashValueSerializer().deserialize(bs);
				String userId = user.getId() + "";
				if (redisTemplate.boundListOps(key2).size() > 0) {
					// �п�棬�����ж�����û��
					if (!redisTemplate.boundSetOps(key3).isMember(userId)) {
						// û��������
						redisTemplate.boundListOps(key2).rightPop();// ���ٿ�棬����list
						redisTemplate.boundSetOps(key3).add(userId);// ��¼�������û�id
						resp.getWriter().write("5");//��ϲ����
					} else {
						// ��ʾ�Ѿ�����
						resp.getWriter().write("4");
					}
				} else {
					// �ѱ�������
					resp.getWriter().write("3");
				}
			}else{
				//û�е�����ʱ��
				resp.getWriter().write("2");
			}
		}else{
			//δ��¼
			resp.getWriter().write("1");
		}
		
	}
	
	
	

}
