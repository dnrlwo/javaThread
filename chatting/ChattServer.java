package chap18_chatt;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.ietf.jgss.Oid;

import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ChattServer extends JFrame {

	// 현재 유저들의 이름과 상태값을 저장하도록
	Vector<ChattData> userList = new Vector<ChattData>();
	Vector<ServerThread> users = new Vector<ServerThread>();

	ServerSocket serverSocket;
	AcceptThread acceptThread;

	boolean acceptThreadFlag = true;

	class AcceptThread extends Thread {

		@Override
		public void run() {
			try {
				serverSocket = new ServerSocket(Integer.parseInt(tfPort.getText()));
				viewMessage("서버가 시작되었습니다.");
				
				while (acceptThreadFlag) {
					Socket socket = serverSocket.accept();
					ServerThread st = new ServerThread(ChattServer.this, socket);
					users.add(st);
					viewMessage(socket.getInetAddress().getHostName() + "님이 접속하셨습니다.");
					
				}//end of while
			} catch (Exception ex) {

			}
		}
	}

	private JPanel contentPane;
	private JPanel panel;
	private JPanel panel_1;
	private JTextField tfMessage;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JTextField tfIp;
	private JTextField tfPort;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JScrollPane scrollPane;
	private JList list;
	private JLabel lblNewLabel_3;
	private JScrollPane scrollPane_1;
	private JTextArea textArea;
	private JLabel lblNewLabel_4;
	private JButton button;

	private JButton btnNewButton_2;
	private JButton button_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChattServer frame = new ChattServer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ChattServer() {

		setTitle("서버관리용");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 821, 617);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanel(), BorderLayout.NORTH);
		contentPane.add(getPanel_1(), BorderLayout.SOUTH);
		contentPane.add(getScrollPane(), BorderLayout.WEST);
		contentPane.add(getScrollPane_1(), BorderLayout.CENTER);
		try {
			InetAddress local = InetAddress.getLocalHost();
			String where = local.getHostAddress();
			tfIp.setText(where);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void serverStart() {
		try {
			// 가입된 유저명단을 userList에 추가
			userList.add(new ChattData("조수연", false));
			userList.add(new ChattData("정재욱", false));
			userList.add(new ChattData("임솔빈", false));
			userList.add(new ChattData("신성찬", false));
			userList.add(new ChattData("김승현", false));
			userList.add(new ChattData("한보겸", false));
			userList.add(new ChattData("이동현", false));

			// 현재 유저들의 이름과 상태값을 JList에 출력

			DefaultListModel<String> showinglist = new DefaultListModel<String>();
			for (ChattData cd : userList) {
				String flag = (cd.isUserFlag()) ? "(접속중)" : "(부재중)";
				showinglist.addElement(String.format("%-5s%2s", cd.getUserName(), flag));
				// stringformat에서 -5s에서 -는 왼쪽정렬
			}
			list.setModel(showinglist);

			acceptThread = new AcceptThread();
			acceptThread.start();
			btnNewButton_1.setEnabled(false);
			button.setEnabled(true);
			
		} catch (Exception ex) {
		}
	}

	public void serverStop() {

	}

	public void viewMessage(String str) {

	}

	public void send() {

	}

	public void getOut() {

	}

	public void whisper() {

	}

	protected JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setPreferredSize(new Dimension(10, 70));
			panel.setLayout(null);
			panel.add(getBtnNewButton_1());
			panel.add(getTfIp());
			panel.add(getTfPort());
			panel.add(getLblNewLabel());
			panel.add(getLblNewLabel_1());
			panel.add(getButton());
		}
		return panel;
	}

	protected JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setPreferredSize(new Dimension(10, 50));
			panel_1.setLayout(null);
			panel_1.add(getTfMessage());
			panel_1.add(getBtnNewButton());
			panel_1.add(getButton_1());
		}
		return panel_1;
	}

	protected JTextField getTfMessage() {
		if (tfMessage == null) {
			tfMessage = new JTextField();
			tfMessage.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						send();
						tfMessage.setText("");
						tfMessage.requestFocus();
					}
				}
			});
			tfMessage.setBounds(109, 11, 565, 30);
			tfMessage.setColumns(10);
		}
		return tfMessage;
	}

	protected JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("전송");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					send();
					tfMessage.setText("");
					tfMessage.requestFocus();
				}
			});
			btnNewButton.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			btnNewButton.setBounds(686, 8, 97, 30);
		}
		return btnNewButton;
	}

	protected JButton getBtnNewButton_1() {
		if (btnNewButton_1 == null) {
			btnNewButton_1 = new JButton("서버 시작");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					serverStart();
				}
			});
			btnNewButton_1.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
			btnNewButton_1.setBounds(582, 10, 97, 51);
		}
		return btnNewButton_1;
	}

	protected JTextField getTfIp() {
		if (tfIp == null) {

			tfIp = new JTextField();
			tfIp.setBounds(12, 40, 270, 21);
			tfIp.setColumns(10);
		}
		return tfIp;
	}

	protected JTextField getTfPort() {
		if (tfPort == null) {
			tfPort = new JTextField();
			tfPort.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent a) {
					if(a.getKeyCode() == KeyEvent.VK_ENTER) {
						serverStart();
					}
				}
			});
			tfPort.setBounds(312, 40, 159, 21);
			tfPort.setColumns(10);
		}
		return tfPort;
	}

	protected JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("IP");
			lblNewLabel.setBounds(12, 15, 57, 15);
		}
		return lblNewLabel;
	}

	protected JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("PORT");
			lblNewLabel_1.setBounds(312, 15, 57, 15);
		}
		return lblNewLabel_1;
	}

	protected JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setPreferredSize(new Dimension(220, 2));
			scrollPane.setViewportView(getList());
			scrollPane.setColumnHeaderView(getLblNewLabel_3());
			scrollPane.setRowHeaderView(getBtnNewButton_2());
		}
		return scrollPane;
	}

	protected JList getList() {
		if (list == null) {
			list = new JList();

		}
		return list;
	}

	protected JLabel getLblNewLabel_3() {
		if (lblNewLabel_3 == null) {
			lblNewLabel_3 = new JLabel("접속자 목록");
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_3.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		}
		return lblNewLabel_3;
	}

	protected JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setViewportView(getTextArea());
			scrollPane_1.setColumnHeaderView(getLblNewLabel_4());
		}
		return scrollPane_1;
	}

	protected JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
		}
		return textArea;
	}

	protected JLabel getLblNewLabel_4() {
		if (lblNewLabel_4 == null) {
			lblNewLabel_4 = new JLabel("대화 내용");
			lblNewLabel_4.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
			lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblNewLabel_4;
	}

	protected JButton getButton() {
		if (button == null) {
			button = new JButton("서버 종료");
			button.setEnabled(false);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					serverStop();
				}
			});
			button.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
			button.setBounds(691, 10, 97, 51);
		}
		return button;
	}

	protected JButton getBtnNewButton_2() {
		if (btnNewButton_2 == null) {
			btnNewButton_2 = new JButton("강퇴");
			btnNewButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					getOut();
				}
			});
			btnNewButton_2.setPreferredSize(new Dimension(65, 10));
		}
		return btnNewButton_2;
	}

	protected JButton getButton_1() {
		if (button_1 == null) {
			button_1 = new JButton("귓속말");
			button_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					whisper();
				}
			});
			button_1.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			button_1.setBounds(0, 8, 97, 30);
		}
		return button_1;
	}
}
