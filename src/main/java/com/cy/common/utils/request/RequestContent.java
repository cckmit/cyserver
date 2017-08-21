package com.cy.common.utils.request;

/**
 * Created by liuzhen on 16/5/23.
 */
public abstract class RequestContent {
    public void onSuccess(){ //执行成功的动作。用户可以覆盖此方法
        System.out.println("onSuccess");
    }

    public void onFailure(){ //执行失败的动作。用户可以覆盖此方法
        System.out.println("onFailure");
        }

    public abstract void doSomeThing() throws Exception; //用户必须实现这个抽象方法，告诉子线程要做什么
}
