package com.oracle.ssm.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oracle.ssm.model.Goods;
import com.oracle.ssm.service.GoodsService;
import com.oracle.ssm.vo.SeachGoodsVo;


@Controller
public class GoodsController {
	
	@Autowired
	
	private GoodsService goodsService;
	
	/**
	 * ��ȡ������Ʒ��Ϣ
	 * @return
	 */
	@RequestMapping("/getAllGoods.do")
	public ModelAndView getAllGoods(SeachGoodsVo seachGoodsVo,Integer pageNo,Integer pageSize){
		//��ʼ����ҳ
		pageNo=pageNo==null?1:pageNo;
		pageSize=pageSize==null?4:pageSize;	
		//pageHelper��ҳ����
		PageHelper.startPage(pageNo, pageSize);
		List<Goods> goodsList=goodsService.findGoodsBySeachVo(seachGoodsVo);
		PageInfo<Goods>  pageInfo=new PageInfo<Goods>(goodsList);
		
		ModelAndView mv=new ModelAndView();
		mv.addObject("pageInfo", pageInfo);
		mv.addObject("serchPrice", seachGoodsVo);
		mv.setViewName("allGoods");
		return mv;
		
	}
	
	/**
	 * ǰ��������Ʒҳ��18610679287
	 * @return
	 */
	@RequestMapping("toAddGoods.do")
	public String toAddGoods(){
		
		return "addGoods";
	}
	
	    /**
	     * ������Ʒ
	     * @param goods
	     * @param file
	     * @return
	     * @throws IllegalStateException
	     * @throws IOException
	     */
		@RequestMapping("addGoods.do")
		public String addGoods(Goods goods,MultipartFile file) throws IllegalStateException, IOException{
			
			String fileName=file.getOriginalFilename();
			String suffixName=fileName.substring(fileName.lastIndexOf("."));
			String newName=UUID.randomUUID().toString()+suffixName;
			
			String diskPath="f:/pic/";
			file.transferTo(new File(diskPath+newName));
			
			goods.setImgUrl("/pic/"+newName);
			goodsService.addGoods(goods);
			return "redirect:getAllGoods.do";
		}
		
		/**
		 * ɾ����Ʒ
		 * @param req
		 * @return
		 */
		@RequestMapping("deleteGoodsById.do")
		public String delete(HttpServletRequest req){
			String id=req.getParameter("id");
			goodsService.deleteGoodsById(Integer.parseInt(id));
			
			return "redirect:getAllGoods.do";	
		}
		
		/**
		 * ǰ���޸���Ʒҳ��
		 * @param id
		 * @return
		 */
		@RequestMapping("findGoodsById.do")
		public ModelAndView findGoodsById(int id){
			Goods goods=goodsService.findGoodsById(id);
			ModelAndView mv=new ModelAndView();
			mv.addObject("goods", goods);
			mv.setViewName("findGoodsById");
			return mv;
		}
		
		/**
		 * �޸���Ʒ
		 * @param goods
		 * @param file
		 * @return
		 * @throws IllegalStateException
		 * @throws IOException
		 */
		@RequestMapping("updateGoodsById.do")
		public String updateGoods(Goods goods,MultipartFile file) throws IllegalStateException, IOException{
			
		
			if(file.getSize()!=0){
			String fileName=file.getOriginalFilename();
			String suffixName=fileName.substring(fileName.lastIndexOf("."));
			String newName=UUID.randomUUID().toString()+suffixName;
			
			String diskPath="f:/pic/";
			file.transferTo(new File(diskPath+newName));
			
			goods.setImgUrl("/pic/"+newName);
			}

			goodsService.updateGoodsById(goods);
			return "redirect:getAllGoods.do";
		}
		
		/**
		 * ��Ʒ���� �۸� ����������
		 * @param serchPrice
		 * @return
		 */
		@RequestMapping("findGoodsBySeachVo.do")
		public ModelAndView findGoodsBySeachVo(SeachGoodsVo seachGoodsVo){
			System.out.println(seachGoodsVo);
			List<Goods> goodsList=goodsService.findGoodsBySeachVo(seachGoodsVo);
			ModelAndView mv=new ModelAndView();
			mv.addObject("goodsList", goodsList);
			mv.addObject("serchPrice",seachGoodsVo);
			mv.setViewName("allGoods");
			return mv;
		}
		
		/**
		 * ����ɾ����Ʒ
		 * @param ids
		 * @return
		 */
		@RequestMapping("deleteGoodsByIds.do")
		public String deleteGoodsByIds(String ids){
			String[] array=ids.split("-");
			goodsService.deleteGoodsByIds(array);
			
			return "redirect:getAllGoods.do";
			
		}

}
