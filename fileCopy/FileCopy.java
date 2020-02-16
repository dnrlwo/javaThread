package fileCopy;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;

public class FileCopy extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JLabel lblNewLabel;
	private JLabel label;
	private JTextField sourceFile;
	private JTextField targetFile;
	private JButton sourceBtn;
	private JButton targetBtn;
	private JButton copyBtn;
	private JPanel mainPanel;
	PanelProgressBar bar;
	String sFile;
	String tFile;
	ArrayList<PanelProgressBar> list = new ArrayList<PanelProgressBar>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileCopy frame = new FileCopy();
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
	public FileCopy() {
		setTitle("FileCopy");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 590, 534);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanel(), BorderLayout.NORTH);
		contentPane.add(getButton_1_1(), BorderLayout.SOUTH);
		contentPane.add(getMainPanel(), BorderLayout.CENTER);

		// test
//		PanelProgressBar bar = new PanelProgressBar("abc.jpg", 10000);
//		mainPanel.add(bar);
//		PanelProgressBar bar1 = new PanelProgressBar("picture.png", 5000);
//		mainPanel.add(bar1);
	}

	protected JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setPreferredSize(new Dimension(10, 90));
			panel.setLayout(null);
			panel.add(getLblNewLabel());
			panel.add(getLabel());
			panel.add(getSourceFile());
			panel.add(getTargetFile());
			panel.add(getSourceBtn());
			panel.add(getButton_1());
		}
		return panel;
	}

	protected JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("원본");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
			lblNewLabel.setBounds(12, 10, 57, 26);
		}
		return lblNewLabel;
	}

	protected JLabel getLabel() {
		if (label == null) {
			label = new JLabel("대상");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
			label.setBounds(12, 46, 57, 26);
		}
		return label;
	}

	protected JTextField getSourceFile() {
		if (sourceFile == null) {
			sourceFile = new JTextField();
			sourceFile.setBounds(81, 15, 355, 21);
			sourceFile.setColumns(10);
		}
		return sourceFile;
	}

	protected JTextField getTargetFile() {
		if (targetFile == null) {
			targetFile = new JTextField();
			targetFile.setColumns(10);
			targetFile.setBounds(81, 51, 355, 21);
		}
		return targetFile;
	}

	protected JButton getSourceBtn() {
		if (sourceBtn == null) {
			sourceBtn = new JButton("원본선택");
			sourceBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// 원본파일 선택
					JFileChooser fc = new JFileChooser();
					int yn = fc.showOpenDialog(mainPanel);
					if (yn == JFileChooser.APPROVE_OPTION) {
						sourceFile.setText(fc.getSelectedFile().toString());
						sFile = fc.getSelectedFile().toString();
					}
				}
			});
			sourceBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			sourceBtn.setBounds(448, 10, 97, 28);
		}
		return sourceBtn;
	}

	protected JButton getButton_1() {
		if (targetBtn == null) {
			targetBtn = new JButton("대상선택");
			targetBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// 대상파일 선택
					JFileChooser fc = new JFileChooser();
					int yn = fc.showSaveDialog(mainPanel);
					
					if (yn == JFileChooser.APPROVE_OPTION) {
						tFile = fc.getSelectedFile().toString();
						targetFile.setText(tFile);
						File temp = new File(sFile);
						long len = temp.length();
						bar = new PanelProgressBar(sFile, tFile, len);
						
						list.add(bar);
						
//						bar.thread.setDaemon(true);
//						bar.thread.start();
						
						mainPanel.add(bar);
						mainPanel.updateUI();
					}
				}
			});
			targetBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
			targetBtn.setBounds(448, 46, 97, 28);
		}
		return targetBtn;
	}

	protected JButton getButton_1_1() {
		if (copyBtn == null) {
			copyBtn = new JButton("복사하기");
			copyBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if(list != null) {
						for(int i =0;i<list.size();i++) {
						list.get(i).thread.setDaemon(true);
						list.get(i).thread.start();
						}
					}
				}
			});
			copyBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 19));
		}
		return copyBtn;
	}

	protected JPanel getMainPanel() {
		if (mainPanel == null) {
			mainPanel = new JPanel();
		}
		return mainPanel;
	}
}
