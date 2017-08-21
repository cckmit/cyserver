package com.cy.common.utils.request;

/**
 * Created by liuzhen on 16/5/23.
 */
public class RequestUtil {
    private RequestContent rc;//请求主体
    public RequestUtil(RequestContent rc){
        this.rc=rc;
    }

    public void run() throws InterruptedException { //开始请求
        final Thread t=new Thread(new Runnable(){
            public void run(){
                try{
                    rc.doSomeThing();//响应请求
                }catch (Exception e) {
                    e.printStackTrace();
                    rc.onFailure(); //如果执行失败
                }
                rc.onSuccess();//如果执行成功
            }}
        );
        t.start();
    }
}
