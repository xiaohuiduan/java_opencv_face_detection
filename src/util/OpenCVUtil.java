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
 * !!!!!!! openCV�в��ܳ��������ַ���������Ƭ���ܳ��֣�����dll���ܳ��֡������������²ۣ�
 *
 * @author xiaohuiduan
 */
public class OpenCVUtil {

    public static Mat getFace(Mat img) {
        //	����ģ�ͣ�ģ����Opencv�İ�װĿ¼��
        String xmlPath = "C:\\opencv\\opencv\\build\\etc\\haarcascades\\haarcascade_frontalface_default.xml";
        // ����������
        CascadeClassifier faceDetector = new CascadeClassifier();
        // ���ؼ���������
        faceDetector.load(xmlPath);
        //����������
        MatOfRect faceDetections = new MatOfRect();
        try {
            //�����������þ��󱣴�
            faceDetector.detectMultiScale(img, faceDetections);
        } catch (Exception e) {
            System.out.println("It has some error:" + faceDetections);
        }
        for (Rect rect : faceDetections.toArray()) {
            // ����ʶ�𷽿����ɫ
            Imgproc.rectangle(img, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }
        return img;
    }
    /**
     * ��Matת��ΪBufferedImage
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
