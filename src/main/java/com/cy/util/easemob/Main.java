package com.cy.util.easemob;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.cy.util.easemob.api.ChatGroupAPI;
import com.cy.util.easemob.api.ChatMessageAPI;
import com.cy.util.easemob.api.ChatRoomAPI;
import com.cy.util.easemob.api.FileAPI;
import com.cy.util.easemob.api.IMUserAPI;
import com.cy.util.easemob.api.SendMessageAPI;
import com.cy.util.easemob.comm.ClientContext;
import com.cy.util.easemob.comm.EasemobRestAPIFactory;
import com.cy.util.easemob.comm.body.IMUserBody;
import com.cy.util.easemob.comm.body.ResetPasswordBody;
import com.cy.util.easemob.comm.utils.EasemobUtils;
import com.cy.util.easemob.comm.wrapper.BodyWrapper;
import com.cy.util.easemob.comm.wrapper.ResponseWrapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Main {

	public static void main(String[] args) throws Exception {
		EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
		
		IMUserAPI user = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
		ChatMessageAPI chat = (ChatMessageAPI)factory.newInstance(EasemobRestAPIFactory.MESSAGE_CLASS);
		FileAPI file = (FileAPI)factory.newInstance(EasemobRestAPIFactory.FILE_CLASS);
		SendMessageAPI message = (SendMessageAPI)factory.newInstance(EasemobRestAPIFactory.SEND_MESSAGE_CLASS);
		ChatGroupAPI chatgroup = (ChatGroupAPI)factory.newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
		ChatRoomAPI chatroom = (ChatRoomAPI)factory.newInstance(EasemobRestAPIFactory.CHATROOM_CLASS);

        /*ResponseWrapper fileResponse = (ResponseWrapper) file.uploadFile(new File("d:/logo.png"));
        String uuid = ((ObjectNode) fileResponse.getResponseBody()).get("entities").get(0).get("uuid").asText();
        String shareSecret = ((ObjectNode) fileResponse.getResponseBody()).get("entities").get(0).get("share-secret").asText();
        InputStream in = (InputStream) ((ResponseWrapper) file.downloadFile(uuid, shareSecret, false)).getResponseBody();
        FileOutputStream fos = new FileOutputStream("d:/logo1.png");
        byte[] buffer = new byte[1024];
        int len1 = 0;
        while ((len1 = in.read(buffer)) != -1) {
            fos.write(buffer, 0, len1);
        }
        fos.close();*/

        /*
		// Create a IM user
		BodyWrapper userBody = new IMUserBody("User101", "123456", "HelloWorld");
		user.createNewIMUserSingle(userBody);

		// Create some IM users
		List<IMUserBody> users = new ArrayList<IMUserBody>();
		users.add(new IMUserBody("User002", "123456", null));
		users.add(new IMUserBody("User003", "123456", null));
		BodyWrapper usersBody = new IMUsersBody(users);
		user.createNewIMUserBatch(usersBody);
		
		// Get a IM user
		user.getIMUsersByUserName("User001");
		
		// Get a fake user
		user.getIMUsersByUserName("FakeUser001");
		
		// Get 12 users
		user.getIMUsersBatch(null, null);
		*/
		// Create a IM user
		// 添加群组初始管理员
//		BodyWrapper userBody = new IMUserBody("init", "cy_111111", "初始人");
//		ResponseWrapper response = (ResponseWrapper) user.createNewIMUserSingle(userBody);
//		System.out.println("----------------------\n"+response.toString());
		// Get a IM user
//		ResponseWrapper response = (ResponseWrapper) user.getIMUsersBatch(null, null);
//		ResponseWrapper response = (ResponseWrapper) user.getIMUsersByUserName("User101") ;
		
//		ResetPasswordBody passwordBody = new ResetPasswordBody("123456") ;
//		ResponseWrapper response = (ResponseWrapper) user.modifyIMUserPasswordWithAdminToken("448", passwordBody) ;

//        ChatGroupBody groupBody = new ChatGroupBody("测试2组","这是后台测试二组", true, 100L, true, "User102", new String[] {"User101","User104"}) ;
//		ResponseWrapper response = (ResponseWrapper) chatgroup.createChatGroup(groupBody) ;
        
//        ModifyChatGroupBody modifyChatGroupBody = new ModifyChatGroupBody("测试1组_改","这是后台测试一组——改",50L) ;
//        ResponseWrapper response = (ResponseWrapper) chatgroup.modifyChatGroup("217886521632489920", modifyChatGroupBody) ;
//        String[] users;
//        ResponseWrapper response = (ResponseWrapper) chatgroup.addSingleUserToChatGroup("217886521632489920","User104") ;
//		System.out.println("----------------------\n"+response.toString());
//		response = (ResponseWrapper) user.getIMUsersByUserName("448") ;
		ResponseWrapper response = EasemobUtils.createNewIMUserSingle("liuzhenhaosuai","123456",null) ;
		System.out.println("----------------------\n"+response.toString());
	}

}
