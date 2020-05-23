package util;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author XiaoHui
 */
public class ShowImgUtil extends JPanel {

    public static BufferedImage mImg;
    private VideoCapture capture;
    private JFrame frame;

    /**
     * 运行窗体并进行显示
     */
    public void run() {
        this.init();

        Mat temp = new Mat();
        Mat capImg = new Mat();

        while (this.frame.isShowing()) {
            this.capture.read(capImg);
            // temp的图片是灰色
            Imgproc.cvtColor(capImg, temp, Imgproc.COLOR_RGB2GRAY);
            // 将图片进行识别
            mImg = OpenCVUtil.mat2BI(OpenCVUtil.getFace(capImg));
            this.repaint();
        }
        capture.release();
        frame.dispose();
    }

    /**
     * 初始化摄像头和lib
     */
    private void init() {
        // 加载dll文件
        System.loadLibrary("opencv_java430");

        capture = new VideoCapture();
        capture.open(0);
        int height = (int) capture.get(Videoio.CAP_PROP_FRAME_HEIGHT);
        int width = (int) capture.get(Videoio.CAP_PROP_FRAME_WIDTH);

        if (height == 0 || width == 0) {
            System.out.println("摄像头打开失败");
        }

        // 创建页面
        frame = new JFrame("I Can Capture Face");
        frame.setDefaultCloseOperation(2);
        frame.add(this);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setSize(width + frame.getInsets().left + frame.getInsets().right,
                height + frame.getInsets().top + frame.getInsets().bottom);
        frame.setLocationRelativeTo(null);
    }

    /**
     * 重绘Panel
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.repaint();
        if (mImg != null) {
            g.drawImage(mImg, 0, 0, mImg.getWidth(), mImg.getHeight(), this);
        }
    }

}
