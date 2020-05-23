package util;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.awt.image.BufferedImage;

/**
 * !!!!!!! openCV中不能出现中文字符，加载照片不能出现，加载dll不能出现。。。（无力吐槽）
 *
 * @author xiaohuiduan
 */
public class OpenCVUtil {

    public static Mat getFace(Mat img) {
        //	加载模型（模型在Opencv的安装目录）
        String xmlPath = "C:\\opencv\\opencv\\build\\etc\\haarcascades\\haarcascade_frontalface_default.xml";
        // 级联分类器
        CascadeClassifier faceDetector = new CascadeClassifier();
        // 加载级联分类器
        faceDetector.load(xmlPath);
        //矩形向量组
        MatOfRect faceDetections = new MatOfRect();
        try {
            //检测出人脸，用矩阵保存
            faceDetector.detectMultiScale(img, faceDetections);
        } catch (Exception e) {
            System.out.println("It has some error:" + faceDetections);
        }
        for (Rect rect : faceDetections.toArray()) {
            // 设置识别方块的颜色
            Imgproc.rectangle(img, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }
        return img;
    }
    /**
     * 将Mat转化为BufferedImage
     *
     * @param mat
     * @return
     */
    public static BufferedImage mat2BI(Mat mat) {
        int dataSize = mat.cols() * mat.rows() * (int) mat.elemSize();
        byte[] data = new byte[dataSize];
        mat.get(0, 0, data);
        int type = mat.channels() == 1 ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_3BYTE_BGR;
        if (type == BufferedImage.TYPE_3BYTE_BGR) {
            for (int i = 0; i < dataSize; i += 3) {
                byte blue = data[i];
                data[i] = data[i + 2];
                data[i + 2] = blue;
            }
        }
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);
        return image;
    }



}
