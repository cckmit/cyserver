package com.cy.smscloud.entity;

import java.util.Map;

/**
 * Created by Kentun on 2015-08-28.
 */
public class Body {
    private Object data;//数据实体
    private Result result;//响应结果对象

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Body(Object data, Result result) {
        this.data = data;
        this.result = result;
    }

    public Body() {
    }
    public static Body getBody(Object data,Result result){
        return new Body(data,result);
    }
    public static Body getBody(Result result){
        return new Body(null,result);
    }
    public static Body getBody(Object data){
        return new Body(data,null);
    }

    public static Object getDataObject(Object map){
        Map<String,Object> body = (Map<String, Object>) map;
        if(body!=null){
        	return body.get("data");
        }else{
        	return null;
        }
    }

	@Override
	public String toString() {
		return "Body [data=" + data + ", result=" + result + "]";
	}
}
