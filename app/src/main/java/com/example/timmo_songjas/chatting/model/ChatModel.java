package com.example.timmo_songjas.chatting.model;
import java.util.HashMap;
import java.util.Map;

//채팅방 리스트, 그 채팅방의 대화 내용, 유저
public class ChatModel {
    public boolean isTeamChat; // 팀협업 채팅인가 ㅇ아닌가? 팀빌딩은 false 협업은 true

    //채팅방의 유저들, destination과 userid 둘 다 가짐
    //해당 채팅방의 본인 포함 전체 유저들
    //userid 임!! uid 아니고!!
    public Map<String,Boolean> users = new HashMap<>();

    //채팅방의 대화내용
    public Map<String,Comment> comments = new HashMap<>();
    public String teamchatuserid;

    public static class Comment {
        public boolean isBtn; // 버튼 기능을 하는가? 기본 false
        public String chatroomkey; //버튼 기능할때 초대하는 방의 roomkey값 저장하는 곳
        public String userid; // 일단 사용자 식별 위한
        public String message;
        public int projectId; // 프로젝트아이디, 팀원들이 등록할 프로젝트의 아이디
        public Object timestamp;
    }
}
