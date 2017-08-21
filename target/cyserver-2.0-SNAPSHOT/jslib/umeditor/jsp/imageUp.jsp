    <%@ page language="java" contentType="text/html; charset=utf-8"
             pageEncoding="utf-8"%>
    <%@ page import="com.cy.system.Global" %>
    <%@ page import="com.cy.common.utils.UploaderUmeditor" %>

    <%
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        UploaderUmeditor up = new UploaderUmeditor(request);
        String savePath = Global.DISK_PATH;
        if(!"/".equals(savePath.substring(savePath.length()-1,savePath.length()))){
            savePath += "/";
        }
        savePath += "image";
        up.setSavePath(savePath);
        up.setRelative(false) ;

//      up.setSavePath("./../../../userfiles/upload");
        String[] fileType = {".gif" , ".png" , ".jpg" , ".jpeg" , ".bmp"};
        up.setAllowFiles(fileType);
        up.setMaxSize(10000); //单位KB
        up.upload();


        String callback = request.getParameter("callback");
        String url = up.getUrl() ;
        System.out.println("-----------------> url1 : "+url);
        if(url != null && url != "") {
            url = url.replace(Global.DISK_PATH,"") ;
            if(!"/".equals(url.substring(0,1))){
                url = "/"+url;
            }
        }
        System.out.println("-----------------> url2 : "+url);


        String result = "{\"name\":\""+ up.getFileName() +"\", \"originalName\": \""+ up.getOriginalName()
                +"\", \"size\": "+ up.getSize() +", \"state\": \""+ up.getState()
                +"\", \"type\": \""+ up.getType() +"\", \"url\": \""+ url +"\"}";

        result = result.replaceAll( "\\\\", "\\\\" );
        result = result.replaceAll( "//", "/" );
        System.out.println("-----------------> result : "+result);
        if( callback == null ){
            response.getWriter().print( result );
        }else{
            response.getWriter().print("<script>"+ callback +"(" + result + ")</script>");
        }
    %>
