package com.joblessfriend.jobfinder.chat.domain;

import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 채팅 메시지의 상세 정보를 담는 Value Object(VO) 클래스입니다.
 * 메시지 타입, 채팅방 ID, 발신자, 내용, 전송 시간 등의 데이터를 관리합니다.
 */
@Getter
@Setter
@Data // @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor 포함
@NoArgsConstructor // 파라미터 없는 기본 생성자를 생성합니다.
public class ChatMessageVo {

    /**
     * 모든 필드를 인자로 받는 생성자입니다.
     *
     * @param type 메시지 타입 (ENTER, TALK 등)
     * @param roomId 메시지가 속한 채팅방의 ID
     * @param sender 메시지 발신자의 식별자 (예: 사용자 이메일, 관리자 닉네임)
     * @param message 메시지의 실제 내용
     * @param sendTime 메시지가 발신된 시간
     */
    public ChatMessageVo(MessageType type, String roomId, String sender, String message, Date sendTime) {
		super(); // 부모 클래스(Object)의 생성자를 호출합니다.
		this.type = type;
		this.roomId = roomId;
		this.sender = sender;
		this.message = message;
		this.sendTime = sendTime;
		// this.saved 필드는 DB 저장 여부를 나타내며, 기본값은 false (저장 안됨)입니다.
	}

	// 이 생성자는 필드 초기화가 불완전하여 사용에 주의가 필요하거나, 특정 용도로만 사용될 수 있습니다.
	// TODO: 이 생성자의 사용 목적을 명확히 하거나, 필요 없다면 제거하는 것을 고려해야 합니다.
	public ChatMessageVo(String roomId2, String sender2, String message2, Date sendTime2) {
		// 현재 이 생성자는 필드를 제대로 초기화하지 않고 있습니다.
		// 예를 들어 type 필드가 누락되었습니다.
		this.roomId = roomId2;
		this.sender = sender2;
		this.message = message2;
		this.sendTime = sendTime2;
	}

	/**
     * 채팅 메시지의 종류를 정의하는 열거형(Enum)입니다.
     * ENTER: 사용자가 채팅방에 입장했음을 나타내는 타입입니다.
     * TALK: 일반적인 대화 메시지를 나타내는 타입입니다.
     */
    public enum MessageType {
        ENTER,  // 사용자가 채팅방에 처음 입장했을 때의 메시지 타입
        TALK    // 사용자가 일반적인 대화 내용을 전송할 때의 메시지 타입
    }

    // 메시지 타입 (예: ENTER, TALK)
    private MessageType type;
    // 메시지가 속한 채팅방의 고유 ID
    private String roomId;
    // 메시지를 보낸 사람의 식별자 (사용자 이메일, 기업명, "관리자" 등)
    private String sender;
    // 실제 메시지 내용
    private String message;
    // 메시지가 보내진 시간 (서버 시간 기준)
	private Date sendTime;
	// 해당 메시지가 데이터베이스에 저장되었는지 여부를 나타내는 플래그
	// true이면 저장됨, false이면 아직 저장되지 않음 (또는 저장 대상 아님)
	private boolean saved = false;
	
	private String senderDisplayName;
}
