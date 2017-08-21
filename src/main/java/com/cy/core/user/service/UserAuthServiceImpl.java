package com.cy.core.user.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cy.base.entity.Message;
import com.cy.common.utils.token.Constant;
import com.cy.common.utils.token.JwtUtil;
import com.cy.core.mobevent.entity.CyEvent;
import com.cy.core.mobevent.entity.CyEventComment;
import com.cy.core.user.dao.UserAuthMapper;
import com.cy.core.user.entity.UserAuth;
import com.cy.system.Global;
import com.cy.util.WebUtil;

import io.jsonwebtoken.Claims;

@Service("userAuthService")
public class UserAuthServiceImpl implements UserAuthService {
	@Autowired
	private UserAuthMapper userAuthMapper;

	@Autowired
	private JwtUtil jwt;
	
	//新增token记录
   public void insert (UserAuth userAuth)
   {    
	   userAuthMapper.insert(userAuth);	    
   }

   //删除旧记录 by userid
   public void delete (UserAuth userAuth)
   {    
	   //用户ID和来源类别不能为空
	   if(userAuth.getUserid().equals("")||userAuth.getStype().equals("")) return;
	   
	   userAuthMapper.deleteByuserid(userAuth);	    
   }

   //删除token
   public void deletebytoken (String token)
   {    
	if (token!=null&&token.length()>10)	   
	   userAuthMapper.deleteByToken(token);    
   }
   
   
   public String GetAccount(String token)
   {
	  String  AccountNum="0";
	  if (token==null) return AccountNum;
		   
	   String err="";
	   Claims claims;

       try {
								
				claims = jwt.parseJWT(token);
				
				String json = claims.getSubject();
		 		
		 		System.out.println("json="+json); 		
		 		
		 		UserAuth userAuth = JSONObject.parseObject(json, UserAuth.class);
		 		String meid=userAuth.getClientMeid();
		 		String macid=userAuth.getClientMacid();
		 		String userid=userAuth.getUserid();
		 		String stype=userAuth.getStype();  //0-APP,1-微信,2-网页
		 		
		 	
		 		
		 		System.out.println("meid="+meid); 
		 		System.out.println("macid="+macid); 
		 		System.out.println("userid="+userid); 
		 		System.out.println("stype="+stype);
	
		 		AccountNum=userid;	
		 		
			} catch (Exception e) {
	
			}
			finally
			{
				//AccountNum=userid;			
			}
			
			
		return	AccountNum;

	   
   }
   
   //检测token有效性
   public long CheckToken (String token)
   {    
        
	 //使用Date
	   long returnID=Constant.RESCODE_EXCEPTION;
	   
	   if (token==null) return 0L;
	   
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String err="";
	 	Claims claims;
		try {
			
			
			claims = jwt.parseJWT(token);
			
			String json = claims.getSubject();
	 		
	 		System.out.println("json="+json); 		
	 		
	 		UserAuth userAuth = JSONObject.parseObject(json, UserAuth.class);
	 		String meid=userAuth.getClientMeid();
	 		String macid=userAuth.getClientMacid();
	 		String userid=userAuth.getUserid();
	 		String stype=userAuth.getStype();  //0-APP,1-微信,2-网页
	 		
	 		Date expdt=claims.getExpiration();
	 		Date d = new Date();
	 		
	 		System.out.println("meid="+meid); 
	 		System.out.println("macid="+macid); 
	 		System.out.println("userid="+userid); 
	 		System.out.println("stype="+stype);
	 		System.out.println("sdf.format(expdt)="+sdf.format(expdt));
	 		System.out.println("sdf.format(d)="+sdf.format(d));
	 		
	 		int t=0;
	 		//int i= compare_date(sdf.format(expdt), sdf.format(d));
	 		
	 		
	 		//检测数据token是否存在，有效 			 		
	 		
	 		//  if (i==1)
	 			// {
	 			//    System.out.println("i="+i); 
	 			  //判断，用户ID,MAC地址,MEID 是否匹配,APP来源检测
	 			  //if (stype.equals("0")) 
	 			    userAuth.setToken(token); 	 			    
	 			    t=userAuthMapper.selectCheckToken(userAuth);	 
	 			    System.out.println("t=="+t);	 			  
	 		 			    
	 			    if (t==1)
	 			  	  returnID= Constant.RESCODE_SUCCESS; //有效 1000
	 			    else
	 				  returnID=  Constant.RESCODE_EXCEPTION;	//无效 1002
	 			    
	 		//	 } 
	 		//  else
	 		//	   if (i==-1)
	 		//		  {returnID= Constant.RESCODE_EXPTIME;} //过期 1006
	 		///	   else
	 		//		 if(i==0)
	 		//			{returnID=  Constant.RESCODE_EXCEPTION;	} //无效 	1002
	 		  
	 		  
		 } 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			err=e.toString();
			System.out.println("err="+err); 	
		}
		finally
		{
			if (err.indexOf("expired")>0)
				returnID=  Constant.RESCODE_EXPTIME; //过期异常 1006
			
		}
		
		
	return	returnID;
	
   }

   public void token_refresh(Message message, String content)
   {		
	   
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		
		
		String err="";
		Map<String, Object> map = JSON.parseObject(content, Map.class);
		
		String token = (String)map.get("token"); //实际对应数据库fhtoken 

		if (StringUtils.isBlank(token)) {
			message.setMsg("未传token ID");
			message.setSuccess(false);
			return;
		}
		
				
 		System.out.println("token="+token);
 		long nowMillis = System.currentTimeMillis(); //当前时间
		Date now = new Date(nowMillis); //当前日期
	 	Claims claims;
		try {
			claims = jwt.parseJWT(token);					

	 		String json = claims.getSubject();
	 		
	 		UserAuth userAuth = JSONObject.parseObject(json, UserAuth.class);
			 
	 		String meid=userAuth.getClientMeid();
	 		String macid=userAuth.getClientMacid();
	 		String userid=userAuth.getUserid();
	 		String stype=userAuth.getStype();  //0-APP,1-微信,2-网页
	 		
	 		System.out.println("meid="+meid); 
	 		System.out.println("macid="+macid); 
	 		System.out.println("userid="+userid); 
	 		System.out.println("stype="+stype);
			
	 		userAuth.setTokenFh(token);
	 		int t=userAuthMapper.selectCheckRFToken(userAuth);	 		 
	 		if (t!=1)
	 		{
	 			message.setMsg("refrech token 不存在");
	 			message.setSuccess(false);
	 			message.setReturnId(String.valueOf(Constant.RESCODE_EXCEPTION)); //刷新失败
	 			return; 
	 		}
	 		  
	 		 
	 		String subject = JwtUtil.generalSubject(userAuth); //基本信息
	 		
	 		Map<String, String> tokenMap=jwt.createJWT(subject,"token");
			String refreshToken=tokenMap.get("token");
			String tokenexp=tokenMap.get("exp");
			
	 		String retry=String.valueOf(Constant.JWT_REFRESH_INTERVAL); //重试时间
	 		if (!WebUtil.isEmpty(Global.jwt_refresh_interval))	 
	 		   retry=Global.jwt_refresh_interval;  //按分钟计算
		
	 	
	 		
	 		
			Map<String, String> rMap = new HashMap<String, String>();
			rMap.put("token", refreshToken); //新token值
	 		rMap.put("exp", tokenexp); //新token有效期
	 		rMap.put("retry",retry); //刷新间隔有效期
	 		       
	 		userAuth.setToken(refreshToken);
	 		userAuth.setTokenExp(tokenexp);
	 		userAuth.setTokenStatus("0");
	 		userAuth.setTokenFh(token); //原刷新token 		
			
	 		userAuth.setUpdateTime(sdf.format(d));  //更新时间
	 		
	 		userAuthMapper.updatetoken(userAuth); //更新数据库写入新TOKEN

			message.setMsg("refrech token success");
			message.setSuccess(true);
			message.setReturnId(String.valueOf(Constant.RESCODE_SUCCESS)); //刷新成功
			message.setObj(rMap);
			return;
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			err=e.toString();
			System.out.println("err="+err); 	
		}
		finally
		{
			
			if (err.indexOf("expired")>0)
			{
				UserAuth ua=new UserAuth();
				ua.setUpdateTime(sdf.format(d));  //更新时间
				ua.setTokenStatus("1");
				ua.setTokenFhStatus("1");
				
				ua.setTokenFh(token);				
				userAuthMapper.updatetokenfhstatus(ua); //刷新token过期，token记录作废
				
				
				message.setMsg("refrech token expired");
				message.setReturnId(String.valueOf(Constant.RESCODE_EXPTIME)); //过期
				message.setSuccess(false);
				return;						
			}			
			
		}	
		
		
		
	}
   
   
   //重载方法，header取token
   public void token_refresh(Message message, String content,String token)
   {		
	   /*
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		 */
	   
		if (WebUtil.isEmpty(token)) {
			message.setMsg("token未传入");
			message.setSuccess(false);
			return;
		}
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();		
		
		String err="";
	//	Map<String, Object> map = JSON.parseObject(content, Map.class);	
		//String reftoken = (String)map.get("token"); //实际对应数据库fhtoken 

		if (StringUtils.isBlank(token)) {
			message.setMsg("未传token ID");
			message.setSuccess(false);
			return;
		}
		
				
 		System.out.println("token="+token);
 		long nowMillis = System.currentTimeMillis(); //当前时间
		Date now = new Date(nowMillis); //当前日期
	 	Claims claims;
		try {
			claims = jwt.parseJWT(token);					

	 		String json = claims.getSubject();
	 		
	 		UserAuth userAuth = JSONObject.parseObject(json, UserAuth.class);
			 
	 		String meid=userAuth.getClientMeid();
	 		String macid=userAuth.getClientMacid();
	 		String userid=userAuth.getUserid();
	 		String stype=userAuth.getStype();  //0-APP,1-微信,2-网页
	 		
	 		System.out.println("meid="+meid); 
	 		System.out.println("macid="+macid); 
	 		System.out.println("userid="+userid); 
	 		System.out.println("stype="+stype);
			
	 		userAuth.setTokenFh(token);
	 		int t=userAuthMapper.selectCheckRFToken(userAuth);	 		 
	 		if (t!=1)
	 		{
	 			message.setMsg("refrech token 不存在");
	 			message.setSuccess(false);
	 			message.setReturnId(String.valueOf(Constant.RESCODE_EXCEPTION)); //刷新失败
	 			return; 
	 		}
	 		  
	 		 
	 		String subject = JwtUtil.generalSubject(userAuth); //基本信息
	 		
	 		Map<String, String> tokenMap=jwt.createJWT(subject,"token");
			String refreshToken=tokenMap.get("token");
			String tokenexp=tokenMap.get("exp");
			
	 		String retry=String.valueOf(Constant.JWT_REFRESH_INTERVAL); //重试时间
	 		if (!WebUtil.isEmpty(Global.jwt_refresh_interval))	 
	 		   retry=Global.jwt_refresh_interval;  //按分钟计算
		
	 	
	 		
	 		
			Map<String, String> rMap = new HashMap<String, String>();
			rMap.put("token", refreshToken); //新token值
	 		rMap.put("exp", tokenexp); //新token有效期
	 		rMap.put("retry",retry); //刷新间隔有效期
	 		       
	 		userAuth.setToken(refreshToken);
	 		userAuth.setTokenExp(tokenexp);
	 		userAuth.setTokenStatus("0");
	 		userAuth.setTokenFh(token); //原刷新token 		
			
	 		userAuth.setUpdateTime(sdf.format(d));  //更新时间
	 		
	 		userAuthMapper.updatetoken(userAuth); //更新数据库写入新TOKEN

			message.setMsg("refrech token success");
			message.setSuccess(true);
			message.setReturnId(String.valueOf(Constant.RESCODE_SUCCESS)); //刷新成功
			message.setObj(rMap);
			return;
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			err=e.toString();
			System.out.println("err="+err); 	
		}
		finally
		{
			
			if (err.indexOf("expired")>0)
			{
				UserAuth ua=new UserAuth();
				ua.setUpdateTime(sdf.format(d));  //更新时间
				ua.setTokenStatus("1");
				ua.setTokenFhStatus("1");
				
				ua.setTokenFh(token);				
				userAuthMapper.updatetokenfhstatus(ua); //刷新token过期，token记录作废
				
				
				message.setMsg("refrech token expired");
				message.setReturnId(String.valueOf(Constant.RESCODE_EXPTIME)); //过期
				message.setSuccess(false);
				return;						
			}			
			
		}	
		
		
		
	}
   
   public static int compare_date(String DATE1, String DATE2) {       
       
       DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
       try {
           Date dt1 = df.parse(DATE1);
           Date dt2 = df.parse(DATE2);
           if (dt1.getTime() > dt2.getTime()) {
               //System.out.println("dt1 在dt2前");
               return 1;
           } else if (dt1.getTime() < dt2.getTime()) {
              // System.out.println("dt1在dt2后");
               return -1;
           } else {
               return 0;
           }
       } catch (Exception exception) {
           exception.printStackTrace();
       }
       return 0;
   }
}