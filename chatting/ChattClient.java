package chap18_chatt;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ChattClient extends JFrame {
	Socket socket;
	ObjectOutputStream oos;
	ClientThread ct;
	private JPanel contentPane;
	private JPanel panel;
	private JPanel panel_1;
	private JTextField tfMessage;
	private JButton btnNewButton;
	private JButton btnConnect;
	private JTextField tfIp;
	private JTextField tfPort;
	private JTextField tfUserName;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JScrollPane scrollPane;
	private JList list;
	private JLabel lblNewLabel_3;
	private JScrollPane scrollPane_1;
	private JTextArea textArea;
	private JLabel lblNewLabel_4;
	private JButton btnDisConnect;

	private JButton btnWhisper;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChattClient frame = new ChattClient();
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
	public ChattClient() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				disConnect();
			}
		});
		setTitle("클라이언트");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 829, 617);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanel(), BorderLayout.NORTH);
		contentPane.add(getPanel_1(), BorderLayout.SOUTH);
		contentPane.add(getScrollPane(), BorderLayout.WEST);
		contentPane.add(getScrollPane_1(), BorderLayout.CENTER);

	}

	public void connect() {
		try {
		socket = new Socket(tfIp.getText(), Integer.parseInt(tfPort.getText()));
		viewMessage("서버에 연결되었습니다.");
		OutputStream os = socket.getOutputStream();
		oos = new ObjectOutputStream(os);
		
		ChattData data = new ChattData();
		data.setMessage("접속");
		data.setUserName(tfUserName.getText());
		data.setCommand(ChattData.LOG_IN);
		
		oos.writeObject(data);
		oos.flush();
		}catch(Exception ex) {}
		
	}

	public void disConnect() {

	}

	public void send() {

	}

	public void whisper() {

	}

	public void viewMessage(String str) {
		textArea.append(str + "\n");
		int leng = textArea.getText().length();
		textArea.setCaretPosition(leng);
	}

	protected JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setPreferredSize(new Dimension(10, 70));
			panel.setLayout(null);
			panel.add(getBtnConnect());
			panel.add(getTfIp());
			panel.add(getTfPort());
			panel.add(getTfUserName());
			panel.add(getLblNewLabel());
			panel.add(getLblNewLabel_1());
			panel.add(getLblNewLabel_2());
			panel.add(getBtnDisConnect());
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
			panel_1.add(getBtnWhisper());
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
			tfMessage.setBounds(121, 11, 571, 30);
			tfMessage.setColumns(10);
		}
		return tfMessage;
	}

	protected JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("전송");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					send();
					tfMessage.setText("");
					tfMessage.requestFocus();
				}
			});
			btnNewButton.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			btnNewButton.setBounds(694, 11, 97, 30);
		}
		return btnNewButton;
	}

	protected JButton getBtnConnect() {
		if (btnConnect == null) {
			btnConnect = new JButton("연결");
			btnConnect.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					connect();
				}
			});
			btnConnect.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
			btnConnect.setBounds(582, 10, 97, 51);
		}
		return btnConnect;
	}

	protected JTextField getTfIp() {
		if (tfIp == null) {
			tfIp = new JTextField();
			tfIp.setText("192.168.0.81");
			tfIp.setBounds(12, 40, 270, 21);
			tfIp.setColumns(10);
		}
		return tfIp;
	}

	protected JTextField getTfPort() {
		if (tfPort == null) {
			tfPort = new JTextField();
			tfPort.setBounds(294, 40, 132, 21);
			tfPort.setColumns(10);
		}
		return tfPort;
	}

	protected JTextField getTfUserName() {
		if (tfUserName == null) {
			tfUserName = new JTextField();
			tfUserName.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						connect();
					}
				}
			});
			tfUserName.setBounds(438, 40, 132, 21);
			tfUserName.setColumns(10);
		}
		return tfUserName;
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
			lblNewLabel_1.setBounds(294, 15, 57, 15);
		}
		return lblNewLabel_1;
	}

	protected JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("User Name");
			lblNewLabel_2.setBounds(438, 15, 97, 15);
		}
		return lblNewLabel_2;
	}

	protected JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setPreferredSize(new Dimension(200, 2));
			scrollPane.setViewportView(getList());
			scrollPane.setColumnHeaderView(getLblNewLabel_3());
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

	protected JButton getBtnDisConnect() {
		if (btnDisConnect == null) {
			btnDisConnect = new JButton("연결 해제");
			btnDisConnect.setEnabled(false);
			btnDisConnect.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					disConnect();
				}
			});
			btnDisConnect.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
			btnDisConnect.setBounds(691, 10, 97, 51);
		}
		return btnDisConnect;
	}

	protected JButton getBtnWhisper() {
		if (btnWhisper == null) {
			btnWhisper = new JButton("귓속말");
			btnWhisper.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					whisper();
					tfMessage.setText("");
					tfMessage.requestFocus();
				}
			});
			btnWhisper.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			btnWhisper.setBounds(12, 11, 97, 30);
		}
		return btnWhisper;
	}
}
