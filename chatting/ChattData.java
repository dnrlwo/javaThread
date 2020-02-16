package chap18_chatt;

import java.io.Serializable;
import java.util.Vector;

public class ChattData implements Serializable {
	
	static final long serialVersionUID = 3L;
	private String userName;
	private boolean userFlag; //true == login, false == logout
	private int command;
	private String message;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	Vector<ChattData> users = new Vector<ChattData>(); //귓속말 보낼 명단
	
	static final int MESSAGE = 0;
	static final int LOG_IN = 1;
	static final int LOG_OUT = 2;
	static final int WHISPER = 3;
	static final int USERS = 4;
	static final int SERVER_STOP = 5;
	
	//기존 유저들의 상태 변화
	public ChattData() {}
	//초기유저 상태값
	public ChattData(String u, boolean b) { //서버가 가동이되면, 접속자목록에 뿌려줌
		this.userName = u;
		this.userFlag = b;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public boolean isUserFlag() {
		return userFlag;
	}
	public void setUserFlag(boolean userFlag) {
		this.userFlag = userFlag;
	}
	public int getCommand() {
		return command;
	}
	public void setCommand(int command) {
		this.command = command;
	}
	public Vector<ChattData> getUsers() {
		return users;
	}
	public void setUsers(Vector<ChattData> users) {
		//주소값이 전송됨. 실제 데이터가 넘어가는 것이 아님.
		for(ChattData cd : users) {
			this.users.add(cd);
		}
		
		
	}
}
