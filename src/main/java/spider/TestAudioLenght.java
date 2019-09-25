package spider;

import java.io.File;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

public class TestAudioLenght {

	public static void main(String[] args) {
		 File source = new File("C:\\Users\\Administrator\\Desktop\\test\\dam9.wav");
	        Encoder encoder = new Encoder();
	        try {
	            MultimediaInfo m = encoder.getInfo(source);
	            long ls = m.getDuration();
	            System.out.println("此视频时长为:" + ls / 60000 + "分" + ls / 1000 + "秒！");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
}
