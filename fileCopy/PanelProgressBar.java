package fileCopy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

public class PanelProgressBar extends JPanel {
	private JLabel fileName;
	private JProgressBar progressBar;
	private JLabel quantity;
	String filename;
	long length;
	String targetFile;
	
	Thread thread = new Thread(new Runnable() {
		@Override
		public void run() {
			long pos = 0; //수신된 파일의 크기
			progressBar.setMaximum(100); //0~100%
			
			FileInputStream fis = null;
			FileOutputStream fos = null;
			byte[] data = new byte[4096]; // 4kb
			
			try {
				fis = new FileInputStream(new File(filename));
				fos = new FileOutputStream(new File(targetFile));
				
				while(pos<length) {
					int len = fis.read(data);
					fos.write(data, 0, len);
					pos += len;
					progressBar.setValue((int)((pos/(double)length)*100)); //백분율로 계산
					quantity.setText(pos + "/" + length); //복사된 양 표시
				
				}
				fos.close();
				fis.close();
				JOptionPane.showMessageDialog(PanelProgressBar.this, "파일복사가 완료되었습니다.");
			}catch(Exception ex) {}
		}
	});
	
	/**
	 * Create the panel.
	 */
	public PanelProgressBar(String filename, String targetFile, long length) {
		this.filename = filename;
		this.length = length;
		this.targetFile = targetFile;
		
		setPreferredSize(new Dimension(500, 80));
		setLayout(null);
		add(getFileName());
		add(getProgressBar());
		add(getQuantity());
//		fileName.setText(filename);
//		quantity.setText("0/" + length);
		

	}
	protected JLabel getFileName() {
		if (fileName == null) {
			fileName = new JLabel(filename);
			fileName.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
			fileName.setBounds(12, 0, 426, 35);
		}
		return fileName;
	}
	protected JProgressBar getProgressBar() {
		if (progressBar == null) {
			progressBar = new JProgressBar();
			progressBar.setForeground(Color.GREEN);
			progressBar.setBounds(12, 35, 476, 21);
		}
		return progressBar;
	}
	protected JLabel getQuantity() {
		if (quantity == null) {
			quantity = new JLabel("0/" + length);
			quantity.setHorizontalAlignment(SwingConstants.RIGHT);
			quantity.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
			quantity.setBounds(344, 55, 144, 15);
		}
		return quantity;
	}
}
