package com.cy.common.utils.wechat.utils;

import com.cy.common.utils.JsonUtils;
import com.cy.common.utils.wechat.entity.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: MenuUtils</p>
 * <p>Description: </p>
 *
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-12-23 10:13
 */
public class MenuParser {

    public static void main(String[] args) throws Exception {
        String str = "{\n" +
                "    \"button\": [\n" +
                "        {\n" +
                "            \"name\": \"进入理财1\",\n" +
                "            \"url\": \"http://m.bajie8.com/bajie/enter\",\n" +
                "            \"type\": \"view\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"安全保障1\",\n" +
                "            \"key\": \"122\",\n" +
                "            \"type\": \"click\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"使用帮助1\",\n" +
                "    	     \"url\": \"http://m.bajie8.com/footer/cjwt\",\n" +
                "    	     \"type\": \"view\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        String str1 = "{\n" +
                "        \"button\": [\n" +
                "            {\n" +
                "                \"name\": \"开源项目\",\n" +
                "                \"sub_button\": [\n" +
                "                    {\n" +
                "                        \"type\": \"click\",\n" +
                "                        \"name\": \"Jeewx微信管家\",\n" +
                "                        \"key\": \"jeewx\",\n" +
                "                        \"sub_button\": []\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"type\": \"view\",\n" +
                "                        \"name\": \"Jeecg快速开发平台\",\n" +
                "                        \"url\": \"http://mp.weixin.qq.com/s?__biz=MjM5NjA2OTkxMg==&mid=200275454&idx=2&sn=a4a4bd237aff404b8044e59105b74c69&scene=18#rd\",\n" +
                "                        \"sub_button\": []\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"微活动\",\n" +
                "                \"sub_button\": [\n" +
                "                    {\n" +
                "                        \"type\": \"click\",\n" +
                "                        \"name\": \"营销工具\",\n" +
                "                        \"key\": \"c0201\",\n" +
                "                        \"sub_button\": []\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"type\": \"click\",\n" +
                "                        \"name\": \"微商城\",\n" +
                "                        \"key\": \"c0202\",\n" +
                "                        \"sub_button\": []\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"type\": \"click\",\n" +
                "                        \"name\": \"微网站\",\n" +
                "                        \"key\": \"c0203\",\n" +
                "                        \"sub_button\": []\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"type\": \"click\",\n" +
                "                        \"name\": \"刮刮乐\",\n" +
                "                        \"key\": \"c0204\",\n" +
                "                        \"sub_button\": []\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"关于我们\",\n" +
                "                \"sub_button\": [\n" +
                "                    {\n" +
                "                        \"type\": \"view\",\n" +
                "                        \"name\": \"社区培训\",\n" +
                "                        \"url\": \"http://mp.weixin.qq.com/s?__biz=MjM5NjA2OTkxMg==&mid=200211928&idx=1&sn=18baf4ad4076f2f42c7db938c1277b08&scene=18#rd\",\n" +
                "                        \"sub_button\": []\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"type\": \"click\",\n" +
                "                        \"name\": \"联系我们\",\n" +
                "                        \"key\": \"c0302\",\n" +
                "                        \"sub_button\": []\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ]\n" +
                "    }" ;

        String str2 = "{\n" +
                "    \"menu\": {\n" +
                "        \"button\": [\n" +
                "            {\n" +
                "                \"name\": \"开源项目\",\n" +
                "                \"sub_button\": [\n" +
                "                    {\n" +
                "                        \"type\": \"click\",\n" +
                "                        \"name\": \"Jeewx微信管家\",\n" +
                "                        \"key\": \"jeewx\",\n" +
                "                        \"sub_button\": []\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"type\": \"view\",\n" +
                "                        \"name\": \"Jeecg快速开发平台\",\n" +
                "                        \"url\": \"http://mp.weixin.qq.com/s?__biz=MjM5NjA2OTkxMg==&mid=200275454&idx=2&sn=a4a4bd237aff404b8044e59105b74c69&scene=18#rd\",\n" +
                "                        \"sub_button\": []\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"微活动\",\n" +
                "                \"sub_button\": [\n" +
                "                    {\n" +
                "                        \"type\": \"click\",\n" +
                "                        \"name\": \"营销工具\",\n" +
                "                        \"key\": \"c0201\",\n" +
                "                        \"sub_button\": []\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"type\": \"click\",\n" +
                "                        \"name\": \"微商城\",\n" +
                "                        \"key\": \"c0202\",\n" +
                "                        \"sub_button\": []\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"type\": \"click\",\n" +
                "                        \"name\": \"微网站\",\n" +
                "                        \"key\": \"c0203\",\n" +
                "                        \"sub_button\": []\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"type\": \"click\",\n" +
                "                        \"name\": \"刮刮乐\",\n" +
                "                        \"key\": \"c0204\",\n" +
                "                        \"sub_button\": []\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"关于我们\",\n" +
                "                \"sub_button\": [\n" +
                "                    {\n" +
                "                        \"type\": \"view\",\n" +
                "                        \"name\": \"社区培训\",\n" +
                "                        \"url\": \"http://mp.weixin.qq.com/s?__biz=MjM5NjA2OTkxMg==&mid=200211928&idx=1&sn=18baf4ad4076f2f42c7db938c1277b08&scene=18#rd\",\n" +
                "                        \"sub_button\": []\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"type\": \"click\",\n" +
                "                        \"name\": \"联系我们\",\n" +
                "                        \"key\": \"c0302\",\n" +
                "                        \"sub_button\": []\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}" ;
        System.out.println(str2);
//        Map<String,Object> map = JsonUtils.json2map(str) ;
		Menu menu = parse(str2);
        System.out.println(menu);
    }
    /**
     * 将菜单格式的字符串转换成菜单对象
     * @param jsonStr
     * @return
     */
    public static Menu parse(String jsonStr) throws Exception {
        Menu menu = new Menu() ;
        Map<String,Object> map = JsonUtils.json2map(jsonStr) ;
        List<Map<String,Object>> firstButtons = null;
        if(map.get("menu") != null) {
            firstButtons = (List<Map<String,Object>>)((Map<String,Object>)map.get("menu")).get("button") ;
        } else {
            firstButtons = (List<Map<String,Object>>)map.get("button") ;
        }

        if(firstButtons != null && !firstButtons.isEmpty()) {
            Button[] firBtns = new Button[firstButtons.size()] ;
            for (int i = 0 ; i < firstButtons.size() ; i++) {
                Button firBtn = parseButton(firstButtons.get(i)) ;
                if(firBtn != null) {
                   firBtns[i] = firBtn ;
                }
            }
            menu.setButton(firBtns);
        }
        return menu ;
    }

    /**
     * 将按钮Map数据转换成按钮对象
     * @param map
     * @return
     */
    public static Button parseButton(Map<String,Object> map) {
        Button btn = null ;
        if(map == null) {
            return btn ;
        }
        if(map.get("sub_button") != null) {
            List<Map<String,Object>> list = (List<Map<String,Object>>) map.get("sub_button") ;
            ComplexButton complexButton = new ComplexButton() ;
            if(list != null && !list.isEmpty()) {
                Button[] btns = new Button[list.size()] ;
                for(int i = 0 ; i < list.size() ; i++) {
                    Button tempBtn = parseButton(list.get(i)) ;
                    if(tempBtn != null) {
                        btns[i] = tempBtn ;
                    }
                }
                complexButton.setName((String) map.get("name"));
                complexButton.setSub_button(btns) ;
                btn = complexButton ;
            }
        }
        if(StringUtils.isNotBlank((String)map.get("type"))) {
            String type = (String)map.get("type") ;
            if("view".equals(type)) {
                btn = JsonUtils.map2pojo(map, ViewButton.class) ;
            } else{
                btn = JsonUtils.map2pojo(map, CommonButton.class) ;
            }
        }
//        btn.setName((String) map.get("name"));
        return btn ;
    }
}
