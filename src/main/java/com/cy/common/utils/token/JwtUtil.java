package com.cy.common.utils.token;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.cy.core.user.entity.UserAuth;
import com.cy.system.Global;
import com.cy.util.WebUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	//@Value("${spring.profiles.active}")
   // private String profiles;
	
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
	/**
	 * 由字符串生成加密key
	 * @return
	 */
	public SecretKey generalKey(){
		//String stringKey = profiles+Constant.JWT_SECRET;
		String stringKey = Constant.JWT_SECRET; //密钥
		
		if (!WebUtil.isEmpty(Global.jwt_secret))
			stringKey=Global.jwt_secret;
			
			
		byte[] encodedKey = Base64.decodeBase64(stringKey);
	    SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
	    return key;
	}

	/**
	 * 创建jwt
	 * @param id
	 * @param subject
	 * @param ttlMillis
	 * @return
	 * @throws Exception
	 */
	public Map<String, String>  createJWT(String subject, String type) throws Exception {
		
	   ScriptEngineManager manager = new ScriptEngineManager();  
	   ScriptEngine engine = manager.getEngineByName("js");  
	   String str="";
	       
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;  //加密方式
		long nowMillis = System.currentTimeMillis(); //当前时间
		Date now = new Date(nowMillis); //当前日期
		
		String id=Constant.JWT_ID;
		long ttlMillis=Constant.JWT_TTL;
		
		  System.out.println("type="+type);
		  
		if (type.equalsIgnoreCase("token"))
		{			
		    if (!WebUtil.isEmpty(Global.jwt_ttl))
		         ttlMillis= Long.valueOf(Global.jwt_ttl);	
		     System.out.println("token-ttlMillis="+ttlMillis);
		}		
		
		if (type.equalsIgnoreCase("refreshtoken"))
		{			  
			ttlMillis=Constant.JWT_REFRESH_TTL;							 
			if (!WebUtil.isEmpty(Global.jwt_refresh_ttl))
					 ttlMillis= Long.valueOf(Global.jwt_refresh_ttl);				
		    System.out.println("refreshtoken-ttlMillis="+ttlMillis);
		}
		
		
		SecretKey key = generalKey();//加密字符		
		Map<String, String> TokenMap = new HashMap<String, String>();	
		
		
		JwtBuilder builder = Jwts.builder()
			.setId(id)
			.setIssuedAt(now)
			.setSubject(subject)
		    .signWith(signatureAlgorithm, key);
		
		if (ttlMillis >= 0) {
			ttlMillis=ttlMillis*60000; //分钟转毫秒	
		    long expMillis = nowMillis + ttlMillis;
		    Date exp = new Date(expMillis);  //过期时间 
		    
		    System.out.println("exp="+sdf.format(exp));
		    
		    builder.setExpiration(exp);  //设置过期时间
		    
		    TokenMap.put("exp", sdf.format(exp)); //有效期
		   }
		 
		
		 TokenMap.put("token",  builder.compact());  //token
	
		
		//return builder.compact(); //生成TOKEN
		 return TokenMap; //生成TOKEN
	}
	
	/**
	 * 解密jwt
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public Claims parseJWT(String jwt) throws Exception{
		SecretKey key = generalKey();
		Claims claims = Jwts.parser()         
		   .setSigningKey(key)
		   .parseClaimsJws(jwt).getBody();
		return claims;
	}
	
	/**
	 * 生成subject信息
	 * @param user
	 * @return
	 */
	public static String generalSubject(UserAuth userAuth){
		JSONObject jo = new JSONObject();
		
		jo.put("userid", userAuth.getUserid()); //用户ID
		jo.put("clientMeid", userAuth.getClientMeid());//设备ID
		jo.put("clientMacid", userAuth.getClientMacid());//网卡地址
		jo.put("clientSys", userAuth.getClientSys());//操作系统
		jo.put("stype", userAuth.getStype());//来源		
		
		return jo.toJSONString();
	}
}
