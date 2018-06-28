package org.andy.shop.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.andy.shop.service.CartAndPayService;
import org.andy.shop.service.GoodsManagerService;
import org.andy.shop.service.GroupMapService;
import org.andy.shop.service.UserInfoService;
import org.andy.shop.service.UserPowerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
public class GoodsManagerController {



	private static final Logger LOGGER = Logger
			.getLogger(CartAndPayController.class);
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserPowerService userPowerService;
	@Autowired
	private GroupMapService groupMapService;
	@Autowired
	private GoodsManagerService goodsManagerService;
	
	@Autowired
	private CartAndPayService cartAndPayService;

//	/*根据用户提交的预支付信息，申请支付编码
//	 * 参数:
//	 * */
//	@RequestMapping(value="/getPrePayIdByOrderInfo.do",method = {RequestMethod.GET })
//	@ResponseBody
//	public Map<String,String> getPrePayIdByOrderInfo(@RequestParam Map<String,String> map) {
//		LOGGER.info("根据用户提交的预支付信息，申请支付编码");
//		LOGGER.info("dataXml:"+map.get("dataXml"));
//		
//		Map<String, String> reStr = null;
//		try {
//			reStr = cartAndPayService.getPrePayIdByOrderInfo(map);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return  reStr;
//	}
	/*
     *采用spring提供的上传文件的方法
     */
	
	@RequestMapping(value = "/addGoodsRecord.do",method = {RequestMethod.POST })
	@ResponseBody
	public String addGoodsRecord(@RequestParam Map<String,String> map,HttpServletRequest request) {
 
    	String reStr="";
    	//产生uuid
		String goodsId = UUID.randomUUID().toString();
		map.put("goodsId", goodsId);
    	//1、上传图片
    	try {
			this.springUpload( request,goodsId);
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		}
    	
    	//2、新增记录
    	try {
			reStr = goodsManagerService.addGoodsRecord(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return reStr;
    }
	
	
	
	/*
     *采用spring提供的上传文件的方法
     */
//    @RequestMapping(value="/springUpload.do",method = {RequestMethod.POST })
//	@ResponseBody
    public String  springUpload(HttpServletRequest request,String goodsId) 
    {
         long  startTime=System.currentTimeMillis();
       //获取当前路径
		 File currentDirectory = new File("");//设定为当前文件夹 
		 try{ 
		     System.out.println("当前路径:"+currentDirectory.getCanonicalPath());//获取标准的路径 
		     System.out.println(currentDirectory.getAbsolutePath());//获取绝对路径 
		 }catch(Exception e){
			 e.printStackTrace();
		 } 
         
         
         
         //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request))
        {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
           //获取multiRequest 中所有的文件名
            Iterator iter=multiRequest.getFileNames();
            String path;
			try {
				path = currentDirectory.getCanonicalPath().replace("\\","/");
			
	            String desPath =path+"/../webapps/YLXcxMallBack/images/goodsImages/";
	            
	            LOGGER.info("desPath:"+desPath);
	            int i=1;
	            while(iter.hasNext())
	            {
	                //一次遍历所有文件
	                MultipartFile file=multiRequest.getFile(iter.next().toString());
	                if(file!=null)
	                {
	                	
	//                	String uploadFilePath = desPath+file.getOriginalFilename();
	                	String uploadFilePath = desPath+"goodsimage00"+String.valueOf(i);
	                	i++;
	                    //如果上传的文件已存在，则先删除掉
	                	File uploadFile = new File(uploadFilePath);
	                	if(!uploadFile.exists())  
	                    {  
	                		uploadFile.delete();
	                    } 
	              
	                    //上传
	                    file.transferTo(new File(uploadFilePath));
	                    try {
							goodsManagerService.addGoodsImage(uploadFilePath, goodsId);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	                 
	            }
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        }
        long  endTime=System.currentTimeMillis();
        System.out.println("方法三的运行时间："+String.valueOf(endTime-startTime)+"ms");
    return "/success"; 
    }
    
    @RequestMapping(value = "/deleteGoodsRecord.do",method = {RequestMethod.POST })
	@ResponseBody
	public String deleteGoodsRecord(@RequestParam Map<String,String> map) {
  
    	String reStr =null;
    	map.put("id", map.get("ids"));
    	
    	try {
			  reStr =goodsManagerService.deleteGoodsImage(map);


			  
		} catch (Exception e) {
			e.printStackTrace();
		}
		  try {
			reStr =goodsManagerService.deleteGoodsInfo(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		  try {
			reStr =goodsManagerService.deleteGoodsMap(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reStr;
    	
    }
    @RequestMapping(value = "/getGoodsRecordList.do",method = {RequestMethod.POST })
	@ResponseBody
	public List<Map<String, Object>> getGoodsRecordList(@RequestParam Map<String,String> map) {
    	int indexSize=10;
    	int pageIndex =(Integer.parseInt(map.get("page"))-1)*indexSize;
    	if(pageIndex<0){
    		pageIndex=0;
    	}
    	String startIndex=String.valueOf(pageIndex);
    	
    	List<Map<String, Object>>  reList =null;
    	try {
			  reList =goodsManagerService.getGoodsRecordList(startIndex, String.valueOf(indexSize));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reList;
    	
    }
    
    @RequestMapping(value = "/getGoodsRecordDetail.do",method = {RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> getGoodsRecordDetail(@RequestParam Map<String,String> map) {
    	String goodsId = map.get("id");
    	Map<String, Object> reMap = new HashMap<String, Object>();
    	try {
    		reMap =goodsManagerService.getGoodsRecordDetail(goodsId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return reMap;
    	
    }
    
}

