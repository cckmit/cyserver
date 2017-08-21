package com.cy.common.utils.jpush;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.ClientConfig;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.cy.common.utils.request.RequestContent;
import com.cy.common.utils.request.RequestUtil;
import com.cy.system.Global;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class JpushClientUtil {

    protected static final Logger LOG = LoggerFactory.getLogger(JpushClientUtil.class);
    private static String appKey = "e7d2e330ff16a1aff9be2b48";
    private static String masterSecret = "563cb8e79cdd83055a80c835";
//    private static String appKey = "ff8671b0cfc8d9aaf34228f0";
//    private static String masterSecret = "a8f2d2b964c04efe459463b1";
    public static final String TITLE = "窗友消息";
    public static final String ALERT = "窗友通知内容";
    public static final String MSG_CONTENT = "窗友消息内容";
    public static final String REGISTRATION_ID = "0900e8d85ef";
    public static final String TAG = "liuzhen";

    private static final boolean IOS_APNS_FALSE = false; //IOS 生产环境 true :生产环境；false :开发环境
    private static final boolean IOS_APNS_TRUE = true; //IOS 生产环境 true :生产环境；false :开发环境
    private static boolean ios_apns = false ; //IOS 生产环境 true :生产环境；false :开发环境

    public JpushClientUtil() {
//        appKey = Global.JPUSH_APP_KEY ;
//        masterSecret = Global.JPUSH_MASTER_SECRET ;
//        ios_apns = Global.JPUSH_IOS_APNS ;
    }
    public JpushClientUtil(String appKey ,String masterSecret ,Boolean ios_apns) {
        this.appKey = appKey ;
        this.masterSecret = masterSecret ;
        this.ios_apns = ios_apns ;
    }

    public static void main(String[] args) throws InterruptedException {
//        testSendPush();

//        Map map = new HashMap() ;
//        map.put("notifyId","窗友");
//        String msg = pushMessageByTags("窗友推送测试", "窗友-后台推送测试", new String[]{"liuzhen"}, null, map);
//        System.out.println(msg);

        Map map = new HashMap() ;
        map.put("notifyId","窗友");
        map.put("sign","chuangyou");
        JpushClientUtil jpushClientUtil = new JpushClientUtil("ba41fa2e3585f95238303409","e6e70ace92a10a4579e163ee",false) ;
        String msg = jpushClientUtil.pushMessageByAlias("5 窗友推送测试", "5 窗友-后台推送测试", new String[]{"527"}, map);
//        String msg = jpushClientUtil.pushMessageByTags("窗友推送测试", "窗友-后台推送测试", new String[]{"class_0019010010201601"}, map);
        System.out.println(msg);
    }

    public void testSendPush() {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
//        PushPayload payload = buildPushObject_ios_audienceMore_messageWithExtras1();
//        PushPayload payload = buildPushObject_android_tag_alertWithTitle();
//        PushPayload payload = buildPushObject_ios_audienceMore_messageWithExtras1() ;
        Map map = new HashMap() ;
        map.put("notifyId","窗友");
//        PushPayload payload = buildPushObject_ios_tagAnd_alertWithExtrasAndMessage("窗友推送测试","窗友-后台推送测试",new String[]{"liuzhen"},map) ;
//        PushPayload payload = buildPushObject_android_tag_alertWithTitle("窗友推送测试","窗友-后台推送测试",new String[]{"liuzhen"},map) ;
//        PushPayload payload = buildPushObject_ios_audienceMore_messageWithExtras() ;
//        PushPayload payload = buildPushObject_android_tag_alertWithTitle("系统更新","口腔圈版本1.5更新啦",new String[]{"869310011295352X"}) ;
//        PushPayload payload = buildPushObject_all_alias_alert("窗友推送测试","4  窗友-后台推送测试11111",new String[]{"liuz"},map) ;
        PushPayload payload =
                buildPushObject_with_extra("窗友推送测试","6  窗友-后台推送测试11111",new String[]{"liuz"},map) ;
//        PushPayload payload = buildPushObject_ios_audienceMore_messageWithExtras1() ;
//        PushPayload payload = buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() ;
        try {
            PushResult e = jpushClient.sendPush(payload);

//            PushResult e = jpushClient.sendNotificationAll("11111111") ;
            LOG.info("Got result - " + e);
        } catch (APIConnectionException var3) {
            LOG.error("Connection error. Should retry later. ", var3);
        } catch (APIRequestException var4) {
            LOG.error("Error response from JPush server. Should review and fix it. ", var4);
            LOG.info("HTTP Status: " + var4.getStatus());
            LOG.info("Error Code: " + var4.getErrorCode());
            LOG.info("Error Message: " + var4.getErrorMessage());
            LOG.info("Msg ID: " + var4.getMsgId());
        }

    }

    /**
     * 推送消息 (通过tag 方式推送)
     * @param title
     * @param content
     * @param androidTags
     * @param iosTags
     * @param extras
     * @return
     * @throws InterruptedException
     */
    public String pushMessageByTags(final String title ,final String content , final String[] androidTags ,final String[] iosTags , final Map extras) throws InterruptedException {
        String message = null ;

        new RequestUtil(new RequestContent(){
            public void doSomeThing() throws InterruptedException {

                if(androidTags != null && androidTags.length > 0 ) {
                    // 推送Android 客户端
                    sendPushByTag(title, content, androidTags, "1", extras);
                }
                if(iosTags != null && iosTags.length > 0 ) {
                    // IOS 客户端
                    sendPushByTag(title, content, iosTags, "2", extras);
                }

            }
            public void onSuccess(){
                System.out.println("消息推送成功【极光】！");
            }
        }).run();

        return message ;
    }
    /**
     * 推送消息 (通过tag 方式推送)
     * @param title
     * @param content
     * @param tags
     * @param extras
     * @return
     * @throws InterruptedException
     */
    public String pushMessageByTags(final String title ,final String content , final String[] tags, final Map extras) throws InterruptedException {
        String message = null ;

        new RequestUtil(new RequestContent(){
            public void doSomeThing() throws InterruptedException {

                sendPushByTag(title, content, tags, extras) ;

            }
            public void onSuccess(){
                System.out.println("消息推送成功【极光】！");
            }
        }).run();

        return message ;
    }
    /**
     * 推送消息 (通过aliax 方式推送)
     * @param title
     * @param content
     * @param alias
     * @param extras
     * @return
     * @throws InterruptedException
     */
    public String pushMessageByAlias(final String title ,final String content , final String[] alias, final Map extras) throws InterruptedException {
        String message = null ;

        new RequestUtil(new RequestContent(){
            public void doSomeThing() throws InterruptedException {
                sendPushByAlias(title, content, alias, extras);
            }
            public void onSuccess(){
                System.out.println("消息推送成功【极光】！");
            }
        }).run();

        return message ;
    }

    /**
     * 发送消息到Android 客户端
     * @param title
     * @param content
     * @param tags
     * @return
     */
    public PushPayload buildPushObject_android_tag_alertWithTitle(String title ,String content ,String[] tags ,Map extras) {
        return PushPayload.newBuilder().setPlatform(Platform.android())
                .setAudience(Audience.tag(tags))
                .setNotification(
                        Notification.android(content, title, extras)
                ).build();
    }

    /**
     * 发消息到IOS 客户端上
     * @return
     */
    public PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(String title ,String content ,String[] tags ,Map extras) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.tag(tags))
                .setNotification(
                        Notification.newBuilder()
                                .addPlatformNotification(
                                        IosNotification.newBuilder()
                                                .setAlert(content).addExtras(extras)
//                                                .setBadge(5)
                                                .setSound("defualt")
//                                                .addExtra("from", "JPush")
                                                .build())
                                .build())
                .setMessage(
                        Message.content(title)
                )
                .setOptions(Options.newBuilder().setApnsProduction(ios_apns).build()).build();
    }

    /**
     * 通过tag 发送消息到APP 中
     * @param title
     * @param content
     * @param tags
     * @param type  1：表示Android ；2：表示IOS
     * @return
     */
    public boolean sendPushByTag(String title ,String content ,String[] tags ,String type ,Map extras) {
        boolean success = false ;
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
        PushPayload payload = null ;
        if(StringUtils.isBlank(title)) {
            title = TITLE ;
        }
        if(StringUtils.isNotBlank(type)) {
            switch(type.trim()) {
                case "1":
                    payload = buildPushObject_android_tag_alertWithTitle(title,content,tags,extras) ;
                    break ;
                case "2":
                    payload = buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(title,content,tags,extras) ;
                    break ;
            }
        }
        try {
            if(payload != null) {
                PushResult e = jpushClient.sendPush(payload);
                LOG.info("Got result - " + e);
                success = true ;
            }
        } catch (APIConnectionException var3) {
            LOG.error("Connection error. Should retry later. ", var3);
            success = false ;
        } catch (APIRequestException var4) {
            LOG.error("Error response from JPush server. Should review and fix it. ", var4);
            LOG.info("HTTP Status: " + var4.getStatus());
            LOG.info("Error Code: " + var4.getErrorCode());
            LOG.info("Error Message: " + var4.getErrorMessage());
            LOG.info("Msg ID: " + var4.getMsgId());
            success = false ;
        }

        return success ;
    }
    /**
     * 通过tag 发送消息到APP 中
     * @param title
     * @param content
     * @param tags
     * @return
     */
    public boolean sendPushByTag(String title ,String content ,String[] tags ,Map extras) {
        boolean success = false ;
        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);
        PushPayload payload = null ;
        if(StringUtils.isBlank(title)) {
            title = TITLE ;
        }
        payload = buildPushObject_with_extra(title,content,tags,extras) ;
        try {
            if(payload != null) {
                PushResult e = jpushClient.sendPush(payload);
                LOG.info("Got result - " + e);
                success = true ;
            }
        } catch (APIConnectionException var3) {
            LOG.error("Connection error. Should retry later. ", var3);
            success = false ;
        } catch (APIRequestException var4) {
            LOG.error("Error response from JPush server. Should review and fix it. ", var4);
            LOG.info("HTTP Status: " + var4.getStatus());
            LOG.info("Error Code: " + var4.getErrorCode());
            LOG.info("Error Message: " + var4.getErrorMessage());
            LOG.info("Msg ID: " + var4.getMsgId());
            success = false ;
        }

        return success ;
    }

    /**
     * 通过alias 发送消息到APP 中
     * @param title
     * @param content
     * @param alias
     * @return
     */
    public boolean sendPushByAlias(String title ,String content ,String[] alias ,Map extras) {
        boolean success = false ;
        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);
        PushPayload payload = null ;
        if(StringUtils.isBlank(title)) {
            title = TITLE ;
        }
        payload = buildPushObject_all_alias_alert(title,content,alias,extras) ;
        try {
            if(payload != null) {
                PushResult e = jpushClient.sendPush(payload);
                LOG.info("Got result - " + e);
                success = true ;
            }
        } catch (APIConnectionException var3) {
            LOG.error("Connection error. Should retry later. ", var3);
            success = false ;
        } catch (APIRequestException var4) {
            LOG.error("Error response from JPush server. Should review and fix it. ", var4);
            LOG.info("HTTP Status: " + var4.getStatus());
            LOG.info("Error Code: " + var4.getErrorCode());
            LOG.info("Error Message: " + var4.getErrorMessage());
            LOG.info("Msg ID: " + var4.getMsgId());
            success = false ;
        }

        return success ;
    }

    /**
     * 通过tag 推送消息给Android、IOS
     * @param title
     * @param content
     * @param tags
     * @param extras
     * @return
     */
    public PushPayload buildPushObject_with_extra(String title ,String content ,String[] tags ,Map extras) {

        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.tag(tags))
                .setNotification(Notification.newBuilder()
                        .setAlert(content)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(title)
                                .addExtras(extras)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .setSound("default")
                                .addExtras(extras).build())
                        .build())

                .setOptions(Options.newBuilder().setApnsProduction(ios_apns).build()).build();;

        System.out.println(payload.toJSON());
        return payload ;
    }

    public PushPayload buildPushObject_all_alias_alert(String title ,String content ,String[] alias ,Map extras) {
        return PushPayload.newBuilder().setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .setAlert(content)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(title)
                                .addExtras(extras)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .setSound("defualt")
                                .addExtras(extras).build())
                        .build())

                .setOptions(Options.newBuilder().setApnsProduction(ios_apns).build()).build();
    }


    public PushPayload buildPushObject_all_all_alert() {
        return PushPayload.alertAll("Test from API Example - alert1111111111");
    }





    public PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
        return PushPayload.newBuilder().setPlatform(Platform.android_ios())
                .setAudience(
                        Audience.newBuilder()
                                .addAudienceTarget(
                                        AudienceTarget.tag(new String[]{"F483521B_8661_4781_A4DA_DCFA147DDEA9"})
                                )
//                                .addAudienceTarget(
//                                        AudienceTarget.alias(new String[]{"alias1", "alias2"}))
                                .build()
                ).setMessage(Message.newBuilder().setMsgContent("Test from API Example - msgContent -- 王志收到了么？").addExtra("from", "JPush").build()).build();
    }
    public PushPayload buildPushObject_ios_audienceMore_messageWithExtras1() {
        return PushPayload.newBuilder().setPlatform(Platform.android_ios()).setAudience(Audience.newBuilder()
                .addAudienceTarget(AudienceTarget.alias(new String[]{"liuz"})).build())
                .setMessage(Message.newBuilder().setMsgContent("发送").addExtra("name", "于洋洋").build()).build();
    }

}