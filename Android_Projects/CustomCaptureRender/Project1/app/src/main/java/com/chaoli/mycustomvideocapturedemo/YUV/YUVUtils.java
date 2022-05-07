package com.chaoli.mycustomvideocapturedemo.YUV;

import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.Image;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.ByteBuffer;

import androidx.annotation.RequiresApi;

public class YUVUtils {

    //Re:
    // 1.https://cloud.tencent.com/developer/article/1756532
    // 2.https://cloud.tencent.com/developer/article/1747685?from=10680
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static String isYUV420PorYUV420SP(Image image) {
        Image.Plane[] planes = image.getPlanes();
        int pixelStride = planes[1].getPixelStride();
        String imageDataType = pixelStride == 1 ? "I420" : "NV21";
        return imageDataType;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean isImageFormatSupported(Image image) {
        int format = image.getFormat();
        switch (format) {
            case ImageFormat.YUV_420_888:
            case ImageFormat.NV21:
            case ImageFormat.YV12:
                return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static byte[] getNV21DataFromImage(Image image) {
        Rect crop = image.getCropRect();
        int width = crop.width();
        int height = crop.height();
        Image.Plane[] planes = image.getPlanes();
        byte[] data = new byte[width * height * 3 / 2];

        //dumpFile & OutputData
        // Y-buffer
        ByteBuffer yBuffer = planes[0].getBuffer();
        int ySize = yBuffer.remaining();
        byte[] yBytes = new byte[ySize];
        yBuffer.get(yBytes);

        // U-buffer
        ByteBuffer uBuffer = planes[1].getBuffer();
        int uSize = uBuffer.remaining();
        byte[] uBytes = new byte[uSize];
        uBuffer.get(uBytes);

        // V-buffer
        ByteBuffer vBuffer = planes[2].getBuffer();
        int vSize = vBuffer.remaining();
        byte[] vBytes = new byte[vSize];
        vBuffer.get(vBytes);

        dumpFile(ySize, yBytes, uSize, uBytes, vSize, vBytes);

        //Output to data
        yBuffer.get(data, 0, ySize);
        vBuffer.get(data, ySize, vSize);
        uBuffer.get(data, ySize + vSize, uSize);

        return data;
    }

    private static void dumpFile(int ySize, byte[] yBytes, int uSize, byte[] uBytes, int vSize, byte[] vBytes) {
        String yFileName = "Y";
        String uFileName = "U";
        String vFileName = "V";

        String mRootDir = "D:/CodeRepository/Android_Projects/CustomCaptureRender/Project1/OutputFile";

        // 保存目录
        File dir = new File(mRootDir + File.separator + "YUV_0");

        if (!dir.exists()) {
            dir.mkdir();
        }

        // 文件名
        File yFile = new File(dir.getAbsolutePath() + File.separator + yFileName + ".yuv");
        File uFile = new File(dir.getAbsolutePath() + File.separator + uFileName + ".yuv");
        File vFile = new File(dir.getAbsolutePath() + File.separator + vFileName + ".yuv");

        try {
            // 以字符方式书写
            Writer yW = new FileWriter(yFile);
            Writer uW = new FileWriter(uFile);
            Writer vW = new FileWriter(vFile);

            for (int i = 0; i < ySize; i++) {
                String preValue = Integer.toHexString(yBytes[i]); // 转为16进制
                // 因为byte[] 元素是一个字节，这里只取16进制的最后一个字节
                String lastValue = preValue.length() > 2 ? preValue.substring(preValue.length() - 2) : preValue;
                yW.write(" " + lastValue + " "); // 写入文件
                if ((i + 1) % 20 == 0) {  // 每行20个
                    yW.write("\n");
                }
            }
            yW.close();
            for (int i = 0; i < uSize; i++) {
                String preValue = Integer.toHexString(uBytes[i]);
                String lastValue = preValue.length() > 2 ? preValue.substring(preValue.length() - 2) : preValue;
                uW.write(" " + lastValue + " ");
                if ((i + 1) % 20 == 0) {
                    uW.write("\n");
                }
            }
            uW.close();
            for (int i = 0; i < vSize; i++) {
                String preValue = Integer.toHexString(vBytes[i]);
                String lastValue = preValue.length() > 2 ? preValue.substring(preValue.length() - 2) : preValue;
                vW.write(" " + lastValue + " ");
                if ((i + 1) % 20 == 0) {
                    vW.write("\n");
                }
            }
            vW.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static byte[] getI420DataFromImage(Image image) {
        Image.Plane[] planes = image.getPlanes();
        int width = image.getWidth();
        int height = image.getHeight();
        byte[] yBytes = new byte[width * height];
        byte[] uBytes = new byte[width * height / 4];
        byte[] vBytes = new byte[width * height / 4];
        byte[] i420 = new byte[width * height * 3 / 2];
        for (int i = 0; i < planes.length; i++) {
            int dstIndex = 0;
            int uIndex = 0;
            int vIndex = 0;
            int pixelStride = planes[i].getPixelStride();
            int rowStride = planes[i].getRowStride();
            ByteBuffer buffer = planes[i].getBuffer();
            byte[] bytes = new byte[buffer.capacity()];
            buffer.get(bytes);
            int srcIndex = 0;
            if (i == 0) {
                for (int j = 0; j < height; j++) {
                    System.arraycopy(bytes, srcIndex, yBytes, dstIndex, width);
                    srcIndex += rowStride;
                    dstIndex += width;
                }
            } else if (i == 1) {
                for (int j = 0; j < height / 2; j++) {
//                    逐行逐个像素间隔输入U分量
                    for (int k = 0; k < width / 2; k++) {
                        uBytes[dstIndex++] = bytes[srcIndex];
                        srcIndex += pixelStride;
                    }
//                    下一行U分量输入的开始序号
//                    但是为啥当pixelStride = 1/2之时，srcIndex对应的计算公式如下？
                    if (pixelStride == 2) {
                        srcIndex += rowStride - width;
                    } else if (pixelStride == 1) {
                        srcIndex += rowStride - width / 2;
                    }
                }
            } else if (i == 2) {
                for (int j = 0; j < height / 2; j++) {
//                    逐行逐个像素间隔输入V分量
                    for (int k = 0; k < width / 2; k++) {
                        vBytes[dstIndex++] = bytes[srcIndex];
                        srcIndex += pixelStride;
                    }
//                    下一行V分量输入的开始序号
                    if (pixelStride == 2) {
                        srcIndex += rowStride - width;
                    } else if (pixelStride == 1) {
                        srcIndex += rowStride - width / 2;
                    }
                }
            }
            System.arraycopy(yBytes, 0, i420, 0, yBytes.length);
            System.arraycopy(uBytes, 0, i420, yBytes.length, uBytes.length);
            System.arraycopy(vBytes, 0, i420, yBytes.length + uBytes.length, vBytes.length);
        }
        File mRootDir = Environment.getExternalStorageDirectory();
        // 保存目录
        File dir = new File(mRootDir + File.separator + "01_File_getI420DataFromImage");
//        Log.i("dir", dir.getAbsolutePath());
        if (!dir.exists()) {
            dir.mkdir();
            File file = new File(dir.getPath(), "01_File_getI420DataFromImage");
            dumpFile(file, i420);
        }
        return i420;
    }

    //Re: https://www.polarxiong.com/archives/Android-YUV_420_888%E7%BC%96%E7%A0%81Image%E8%BD%AC%E6%8D%A2%E4%B8%BAI420%E5%92%8CNV21%E6%A0%BC%E5%BC%8Fbyte%E6%95%B0%E7%BB%84.html
    private static final int COLOR_FormatI420 = 1;
    private static final int COLOR_FormatNV21 = 2;
    private static final String TAG = "YUVUtils";

    /**
     * 将采集到的YUV数据放到byte[]中
     *
     * @param image
     * @param colorFormat
     * @return
     */
    public static byte[] getDataFromImage(Image image, int colorFormat) {
        if (colorFormat != COLOR_FormatI420 && colorFormat != COLOR_FormatNV21) {
            throw new IllegalArgumentException("only support COLOR_FormatI420 " + "and COLOR_FormatNV21");
        }
        if (!isImageFormatSupported(image)) {
            throw new RuntimeException("can't convert Image to byte array, format " + image.getFormat());
        }
        Rect crop = image.getCropRect();
        int format = image.getFormat();
        int width = crop.width();
        int height = crop.height();
        Image.Plane[] planes = image.getPlanes();
        byte[] data = new byte[width * height * ImageFormat.getBitsPerPixel(format) / 8];
        byte[] rowData = new byte[planes[0].getRowStride()];
        Log.v(TAG, "get data from " + planes.length + " planes");
        int channelOffset = 0;
        int outputStride = 1;
        for (int i = 0; i < planes.length; i++) {
            switch (i) {
                case 0:
                    channelOffset = 0;
                    outputStride = 1;
                    break;
                case 1:
                    if (colorFormat == COLOR_FormatI420) {
                        channelOffset = width * height;
                        outputStride = 1;
                    } else if (colorFormat == COLOR_FormatNV21) {
                        channelOffset = width * height + 1;
                        outputStride = 2;
                    }
                    break;
                case 2:
                    if (colorFormat == COLOR_FormatI420) {
                        channelOffset = (int) (width * height * 1.25);
                        outputStride = 1;
                    } else if (colorFormat == COLOR_FormatNV21) {
                        channelOffset = width * height;
                        outputStride = 2;
                    }
                    break;
            }
            ByteBuffer buffer = planes[i].getBuffer();
            int rowStride = planes[i].getRowStride();
            int pixelStride = planes[i].getPixelStride();

//            批量打印自定义采集到的视频数据
            Log.v(TAG, "pixelStride " + pixelStride);
            Log.v(TAG, "rowStride " + rowStride);
            Log.v(TAG, "width " + width);
            Log.v(TAG, "height " + height);
            Log.v(TAG, "buffer size " + buffer.remaining());

            int shift = (i == 0) ? 0 : 1;
            int w = width >> shift;
            int h = height >> shift;
//            为啥buffer的开始位置是rowStride * (crop.top >> shift) + pixelStride * (crop.left >> shift)？
            buffer.position(rowStride * (crop.top >> shift) + pixelStride * (crop.left >> shift));
            for (int row = 0; row < h; row++) {
                int length;
                if (pixelStride == 1 && outputStride == 1) {
                    length = w;
//                    将buffer当中从position位置开始的长度为length的字节数据写入到data中从位置channelOffset到位置channelOffset + length
                    buffer.get(data, channelOffset, length);
                    channelOffset += length;
                } else {
//                   因为此处的pixelStride只能为2/1；那么length为2w - 1 或者 w
//                    那么length为pixelStride * w，那么length为2w 或者 w
                    length = (w - 1) * pixelStride + 1;
//                    length = w * pixelStride;
                    buffer.get(rowData, 0, length);
                    for (int col = 0; col < w; col++) {
                        data[channelOffset] = rowData[col * pixelStride];
                        channelOffset += outputStride;
                    }
                }
                if (row < h - 1) {
                    buffer.position(buffer.position() + rowStride - length);
                }
            }
            Log.v(TAG, "Finished reading data from plane " + i);
        }
        File mRootDir = Environment.getRootDirectory();
        File mDataDir = Environment.getDataDirectory();
        File mExternalDir = Environment.getExternalStorageDirectory();
        Log.i("Root_dir", mRootDir.getAbsolutePath());
        Log.i("Data_dir", mDataDir.getAbsolutePath());
        Log.i("External_dir", mExternalDir.getAbsolutePath());
// 保存目录
        File dir = new File(mExternalDir + File.separator + "3_File_getDataFromImage");
//        只需向指定的文件夹中写入一次数据
        if (!dir.exists()) {
            dir.mkdir();
            File file = new File(dir, "01_File_getDataFromImage");

            file = new File(dir.getPath(), "01_File_getDataFromImage");
            dumpFile(file, data);
        }
        return data;
    }

    /**
     * 将采集到的byte[]数据放到文件当中
     *
     * @param file
     * @param data
     */
    private static void dumpFile(File file, byte[] data) {
        FileOutputStream outStream;
        try {
            outStream = new FileOutputStream(file);
        } catch (IOException ioe) {
            throw new RuntimeException("Unable to create output file " + file, ioe);
        }
        try {
            outStream.write(data);
            outStream.close();
        } catch (IOException ioe) {
            throw new RuntimeException("failed writing data to file " + file, ioe);
        }
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    private static String byteArrayToHexString(byte[] data) {
        char[] out = new char[data.length << 1];
        for (int i = 0, j = 0; i < data.length; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & data[i]];
        }
        return new String(out);
    }

    private static final char[] DIGITS_LOWER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

//    private static void dumpFile(String fileName, byte[] data) {
//        FileOutputStream outStream;
//        try {
//            outStream = new FileOutputStream(fileName);
//        } catch (IOException ioe) {
//            throw new RuntimeException("Unable to create output file " + fileName, ioe);
//        }
//        try {
//            outStream.write(data);
//            outStream.close();
//        } catch (IOException ioe) {
//            throw new RuntimeException("failed writing data to file " + fileName, ioe);
//        }
//    }

    /**
     * 将含有YUV数据的文件转换成JPG的格式
     *
     * @param fileName
     * @param image
     */
    private void compressToJpeg(String fileName, Image image) {
        FileOutputStream outStream;
        try {
            outStream = new FileOutputStream(fileName);
        } catch (IOException ioe) {
            throw new RuntimeException("Unable to create output file " + fileName, ioe);
        }
        Rect rect = image.getCropRect();
        YuvImage yuvImage = new YuvImage(getDataFromImage(image, COLOR_FormatNV21), ImageFormat.NV21, rect.width(), rect.height(), null);
        yuvImage.compressToJpeg(rect, 100, outStream);
    }

}