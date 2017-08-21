/**
 * Created by cha0res on 3/8/17.
 */
document.write("<script language=javascript src='http://res.wx.qq.com/open/js/jweixin-1.0.0.js'></script>");



/*************************************************************************************************/
function appShareInfo(accountType,shareStr) {
    B.ready(function() {
            $.ajax({
                type: "post",
                url: B.serverUrl + '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
                data: {
                    jsonStr: '{"command": "277","content": {"accountType": "' + accountType + '" ,"url":"' + shareStr.link + '"} }'
                },
                dataType: "json",
                success: function (data) {
                    if (data && data.success && data.obj) {
                        if (!shareStr.img || shareStr.img == '' || shareStr.img == 'null') {
                            shareStr.imgUrl = B.imageServiceHttp + data.obj.logo;
                        }
                        shareStr.link = data.obj.url ? data.obj.url : '';
                    }
                }
            });
    });
}
/*****************************************************************************************************/

function mkShareInfo(url,shareData, hideMenu) {
    B.ready(function() {
        var appId = localStorage.getItem("appId");
        if (appId && appId != 'null') {
            if(shareData.link.indexOf('?') >= 0){
                shareData.link += '&'
            }else{
                shareData.link += '?'
            }
            shareData.link += ('appId='+appId+'&wechatFlag=1');

            $.ajax({
                type: "post",
                url: B.serverUrl + '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
                data: {
                    jsonStr: '{"command": "277","content": {"appId": "' + appId + '" ,"url":"' + shareData.link + '"} }'
                },
                dataType: "json",
                success: function (data) {
                    if (data && data.success && data.obj) {
                        if (!shareData.imgUrl || shareData.imgUrl == '' || shareData.imgUrl == 'null') {
                            shareData.imgUrl = B.imageServiceHttp + data.obj.logo;
                        }
                        // alert(shareData.imgUrl);
                        // shareData.link = data.obj.url ? data.obj.url : '';
                        if (!shareData.desc || shareData.desc == '' || shareData.desc == 'null') {
                            shareData.desc = data.obj.sign ? data.obj.sign : '';
                        }
                        getWeiXinSign(appId, url, shareData, hideMenu);
                    }
                }
            });
        }
    });
}

function getWeiXinSign (appId,url,shareData, hideMenu) {
    $.ajax({
        type: "get",
        url: B.weChatService +'api/getJssdkConfig/'+ appId+'?url='+BASE64.encoder(url)+'&longUrl='+BASE64.encoder(shareData.link),
        dataType: "json",
        success: function(data){
            if(data && data != 'null' && data != ''){
                // shareData.link = data.shortUrl;
                setWeiXinConfig(data,shareData, hideMenu);
            }
        }
    });
}

function getAuthUrl(appId, url, serverUrl) {
    var resultUrl = "";
    if(appId != ''){
        resultUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri="+serverUrl+"bsweb/wechat/api/openMenu/"+appId+"&response_type=code&scope=snsapi_userinfo&state="+url+"#wechat_redirect";
    }
    return resultUrl;
}

function setWeiXinConfig (obj, shareData, hideMenu) {
    wx.config({
        debug: false,
        appId: obj.appId,
        timestamp: obj.timestamp,
        nonceStr: obj.nonceStr,
        signature: obj.signature,
        jsApiList: [
            'checkJsApi',
            'onMenuShareTimeline',
            'onMenuShareAppMessage',
            'onMenuShareQQ',
            'onMenuShareWeibo',
            'onMenuShareQZone',
            'hideMenuItems',
            'showMenuItems',
            'hideAllNonBaseMenuItem',
            'showAllNonBaseMenuItem',
            'translateVoice',
            'startRecord',
            'stopRecord',
            'onVoiceRecordEnd',
            'playVoice',
            'onVoicePlayEnd',
            'pauseVoice',
            'stopVoice',
            'uploadVoice',
            'downloadVoice',
            'chooseImage',
            'previewImage',
            'uploadImage',
            'downloadImage',
            'getNetworkType',
            'openLocation',
            'getLocation',
            'hideOptionMenu',
            'showOptionMenu',
            'closeWindow',
            'scanQRCode',
            'chooseWXPay',
            'openProductSpecificView',
            'addCard',
            'chooseCard',
            'openCard'
        ]
    });
    wx.ready(function () {
        if(hideMenu == 1){
            wx.hideOptionMenu();
        }else{
            wx.onMenuShareAppMessage(shareData);
            wx.onMenuShareTimeline(shareData);
        }
    });
    wx.error(function (res) {
        //alert(res.errMsg);
    });
}

function wechatUpdatePic(appId, openId,accountNum, picCount, domNode, fromWhere){
    var images = {
        localId: "",
            serverId: []
    };
    wx.ready(function () {
        wx.chooseImage({
            count: picCount, // 默认9
            sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
            success: function (res) {
                images.localId = res.localIds;
                if (images.localId.length == 0) {
                    alert('请先选择图片');
                    return;
                }
                var i = 0, length = images.localId.length;
                images.serverId = "";

                function upload() {
                    wx.uploadImage({
                        localId: images.localId[i],
                        isShowProgressTips: 1, // 默认为1，显示进度提示
                        success: function (res) {
                            i++;
                            if (i > 1) {
                                images.serverId += ",";
                            }
                            images.serverId += res.serverId;
                            if (i < length) {
                                upload();
                            } else if (i == length) {
                                B.ready(function () {
                                    $.ajax({
                                        type: "post",
                                        url: B.serverUrl + '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
                                        data: {
                                            jsonStr: '{"command": "281","content": {"appId": "' + appId + '" ,"openId":"' + openId + '", "ids":"' + images.serverId + '", "accountNum":"'+accountNum+'", "fromWhere": "'+fromWhere+'"} }'
                                        },
                                        dataType: "json",
                                        success: function (data) {
                                            if (data && data.success && data.obj && data.obj.length > 0) {
                                                //alert('上传成功');

                                                if (fromWhere==1) {             // 1:上传头像
                                                    $(domNode).attr('src', B.getImageAbsoluteUrl(data.obj));
                                                }else if(fromWhere == 2){       // 2: 活动花絮
                                                    for(var key in data.obj){
                                                        var $li = $("<li class=\"preimg\"></li>");
                                                        $li.css("background","url(" + B.getImageAbsoluteUrl(data.obj[key]) + ")");
                                                        $li.css("background-size","80px 80px");
                                                        $li.append("<input type=\"hidden\" class=\"pic\" value=\"" + B.getImageAbsoluteUrl(data.obj[key]) + "\">");
                                                        $("#addImg").before($li);
                                                    }
                                                }else if(fromWhere == 3){       // 3: 值年返校
                                                    $("#picPreview").attr("src", B.getImageAbsoluteUrl(data.obj)).show();
                                                    $("#uploadImg").hide();
                                                    $(domNode).val(data.obj);
                                                }
                                            } else {
                                                alert(data.msg);
                                            }
                                        }
                                    });
                                });
                                //alert(wechat.images.serverId);
                            }
                        },
                        fail: function (res) {
                            alert(JSON.stringify(res));
                        }
                    });
                }
                upload();
            }
        });
    });

}

