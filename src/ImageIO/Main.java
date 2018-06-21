package ImageIO;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/**
 * openCV中不能出现中文字符，加载照片不能出现，加载dll不能出现。。。（无力吐槽）
 * 
 * @author 段小辉
 *
 */
public class Main {

	public static  Mat run(Mat img) {
		// 因此去掉‘/’
		String xmlPath = "F:\\OpenVC\\opencv\\build\\etc\\haarcascades\\haarcascade_frontalface_default.xml";
		CascadeClassifier faceDetector = new CascadeClassifier();// 级联分类器
		faceDetector.load(xmlPath);// 加载级联分类器
//		Mat image = Imgcodecs.imread("G:\\ii.jpg");// Mat类的对象用于表示一个多维度的单通道或者多通道稠密数组
		MatOfRect faceDetections = new MatOfRect();//矩形向量组
		try {
		faceDetector.detectMultiScale(img, faceDetections);//检测出人脸，用矩阵保存
		}
		catch (Exception e) {
			System.out.println(""+faceDetections);
			System.out.println("读取文件出错出错");
		}
		for (Rect rect : faceDetections.toArray()) {
			Imgproc.rectangle(img, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
					new Scalar(0, 255, 0));//设置颜色
		}
//		String filename = "face_ta.png";
//		Imgcodecs.imwrite(filename, img);
//		System.out.println(filename);
		return img;
	}

}
