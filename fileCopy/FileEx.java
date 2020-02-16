package fileCopy;

import java.io.File;
import java.net.URI;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileEx {
	FileEx() throws Exception{
		File dir = new File("C:/Temp/Dir");
		File file1 = new File("C:/Temp/file1.txt");
		File file2 = new File("C:/Temp/file2.txt");
		File file3 = new File(new URI("file:///C:/Temp/file3.txt"));
		
		//경로가 없으면 생성
		if(!dir.exists()) {dir.mkdirs();}
		//파일이 없으면 생성
		if(!file1.exists()) {file1.createNewFile();}
		if(!file2.exists()) {file2.createNewFile();}
		if(!file3.exists()) {file3.createNewFile();}
		
		//목록들을 생성일자와 함께 확인
		File temp = new File("C:/Temp");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd a HH:mm");
		File[] contents = temp.listFiles();
		System.out.println("날짜              시간                        형태                       크기          이름");
		System.out.println("-----------------------");
		
		for(File file : contents) {
			System.out.print(sdf.format(new Date(file.lastModified())));
			
			DecimalFormat df = new DecimalFormat("#,###.#(kb)");
			
			double len = file.length();
			String t = "";
			if(len>=1024.0) {
				len = len/1024.0;
				t = df.format(len);
			}else {
				t = df.format(len);
			}
			
			if(file.isDirectory()) {
				System.out.print("\t<DIR>\t\t" + file.getName());
			}else {
				System.out.print("\t\t\t" + t + "\t" + file.getName());
			}
			System.out.println();
		}
	}
	public static void main(String[] args) {
		try {
			new FileEx();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

