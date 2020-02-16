package chap18_chatt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Vector;

import javax.print.attribute.standard.Severity;
import javax.swing.DefaultListModel;

public class ServerThread extends Thread {

	ChattServer server;
	Socket socket;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	boolean threadFlag = true;
	String userName;

	public ServerThread(ChattServer server, Socket socket) {
		this.server = server;
		this.socket = socket;

		start();
	}

	@Override
	public void run() {

		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());

			while (threadFlag) {
				ChattData receiveData = (ChattData) ois.readObject();
				switch (receiveData.getCommand()) {

				case ChattData.LOG_IN:
					login(receiveData);
					break;

				case ChattData.LOG_OUT:
					logout(receiveData);
					break;

				case ChattData.WHISPER:
					whisper(receiveData);
					break;

				case ChattData.SERVER_STOP:
					serverStop();

//				case ChattData.USERS:
//					users(receiveData);

				}
				server.viewMessage(receiveData.getUserName() + ":" + receiveData.getMessage());

			}

		} catch (Exception e) {
			server.viewMessage("ServerThread에서 오류발생" + e.toString());
		} finally { //예외발생과 상관없이 무조건.
			try {
				ois.close();
				oos.close();
				socket.close();
			} catch (Exception ex) {
			}
		}
	}

	public void login(ChattData data) throws Exception {
		this.userName = data.getUserName();

		for (int i = 0; i < server.userList.size(); i++) {
			ChattData cd = server.userList.get(i);
			if (cd.getUserName().equals(data.getUserName())) {
				cd.setUserFlag(true);

				String temp = String.format("%-5s%2s", cd.getUserName(), "(접속중)");
				DefaultListModel<String> model = (DefaultListModel<String>) server.getList().getModel();
				model.set(i, temp);

				// 접속된 모든 클라이언트에게 현재 유저의 LOGIN을 전달

				for (ServerThread st : server.users) {
					st.oos.writeObject(data);
					st.oos.flush();
				}
				break;
			}
		}
	}

	public void logout(ChattData data) throws Exception {
		for (int i = 0; i < server.userList.size(); i++) {
			ChattData cd = server.userList.get(i);
			if (cd.getUserName().equals(data.getUserName())) {
				cd.setUserFlag(false);

				String temp = String.format("%-5s%2s", cd.getUserName(), "(부재중)");
				DefaultListModel<String> model = (DefaultListModel<String>) server.getList().getModel();
				model.set(i, temp);
				break;
			}
		}
		this.threadFlag = false;
		server.users.remove(this);

		for (ServerThread st : server.users) {
			st.oos.writeObject(data);
			st.oos.flush();
		}
	}

	public void whisper(ChattData data) throws Exception {
		for(ChattData cd : data.users) {
			for(ServerThread temp : server.users) {
				if(cd.getUserName().equals(temp.getUserName())) {
					temp.oos.writeObject(data);
					temp.oos.flush();
					break;
				}
			}
		}
	}


	public void serverStop() throws Exception {
		ChattData data = new ChattData();
		data.setCommand(ChattData.SERVER_STOP);
		data.setMessage("서버가 종료되었습니다.");
		data.setUserName("관리자");
		
		for (ServerThread st : server.users) {
			st.oos.writeObject(data);
			st.oos.flush();
		}
	}

	public void users(ChattData data) throws Exception {
		//추가 가입자가 있는 경우 TODO
	}
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
