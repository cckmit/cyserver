package com.cy.smscloud.exception;

/**
 * 网络服务异常类
 * @author Kent
 * @date  2015-08-25.
 *
 */
public class NetServiceException extends Exception{
    public NetServiceException(){
        super();
    }
    public NetServiceException(String msg){
        super(msg);
    }
    public NetServiceException(Throwable cause){
        this.initCause(cause);
    }
    public NetServiceException(String msg,Throwable cause){
        super(msg);
        this.initCause(cause);
    }

}
