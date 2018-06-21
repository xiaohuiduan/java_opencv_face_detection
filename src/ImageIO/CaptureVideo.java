package ImageIO;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class CaptureVideo extends JPanel {

	public static BufferedImage mImg;

	public static void main(String[] args) {
		System.loadLibrary("opencv_java320");// 加载dll文件
		VideoCapture capture = new VideoCapture(0);
		int height = (int) capture.get(Videoio.CAP_PROP_FRAME_HEIGHT);
		int width = (int) capture.get(Videoio.CAP_PROP_FRAME_WIDTH);
		if (height == 0 || width == 0)
			System.out.println("摄像头未找到");
		JFrame frame = new JFrame("摄像头");

		frame.setDefaultCloseOperation(2);
		CaptureVideo panel = new CaptureVideo();
		frame.add(panel);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setSize(width + frame.getInsets().left + frame.getInsets().right,
				height + frame.getInsets().top + frame.getInsets().bottom);
		frame.setLocationRelativeTo(null);
		Mat temp = new Mat();
		Mat capImg = new Mat();
		while (frame.isShowing()) {
			capture.read(capImg);
			Imgproc.cvtColor(capImg, temp, Imgproc.COLOR_RGB2GRAY);// temp的图片是灰色
			// Imgcodecs.imwrite("G:\\" + n + ".png", capImg);
			// 将图片进行识别
			mImg = mat2BI(Main.run(capImg));
			panel.repaint();
		}
		capture.release();
		frame.dispose();
	}

	// 将Mat转化为BufferedImage。
	public static BufferedImage mat2BI(Mat mat) {
		int dataSize = mat.cols() * mat.rows() * (int) mat.elemSize();
		byte[] data = new byte[dataSize];
		mat.get(0, 0, data);
		int type = mat.channels() == 1 ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_3BYTE_BGR;
		if (type == BufferedImage.TYPE_3BYTE_BGR) {
			for (int i = 0; i < dataSize; i += 3) {
				byte blue = data[i + 0];
				data[i + 0] = data[i + 2];
				data[i + 2] = blue;
			}
		}
		BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
		image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);
		return image;
	}

	public void paintComponent(Graphics g) {
		super.repaint();
		if (mImg != null) {
			g.drawImage(mImg, 0, 0, mImg.getWidth(), mImg.getHeight(), this);
		}
	}
}