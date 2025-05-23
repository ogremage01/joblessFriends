<<<<<<< HEAD
package com.joblessfriend.jobfinder.chat.service;

import java.util.List;

import org.springframework.web.socket.WebSocketSession;

import com.joblessfriend.jobfinder.chat.domain.ChatRoomVo;

public interface ChatService {
	
	
	
	public List<ChatRoomVo> findAllRoom();
	
	public ChatRoomVo findRoomById(String roomId);
	
	public ChatRoomVo createRoom(String name);
	
	public <T> void sendMessage(WebSocketSession session, T Message);
	
}
=======
//package com.joblessfriend.jobfinder.chat.service;
//
//import java.util.List;
//
//import org.springframework.web.socket.WebSocketSession;
//
//import com.joblessfriend.jobfinder.chat.domain.ChatMessageVo;
//import com.joblessfriend.jobfinder.chat.domain.ChatRoomVo;
//
//public interface ChatService {
//
//
//
//	public List<ChatRoomVo> findAllRoom();
//
//	public ChatRoomVo findRoomById(String roomId);
//
//	public ChatRoomVo createRoom(String name);
//
//	public <T> void sendMessage(WebSocketSession session, T Message);
//
//	ChatRoomVo companyCreateRoom(int companyId);
//
//	List<ChatRoomVo> findMemberRooms();
//
//	List<ChatRoomVo> findCompanyRooms();
//
//	List<ChatMessageVo> findMessagesByRoomId(String roomId);
//}
>>>>>>> origin/jhs
