package models.util;

import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;
import java.awt.image.WritableRaster;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import javax.swing.JButton;//
import java.awt.FlowLayout;//
import java.io.FileWriter;//
import java.io.IOException;//
import java.io.PrintWriter;//


import javax.imageio.ImageIO;

import play.Play;

public class ImageUtil {

	public static byte[] getImageBytesFromBuffered(BufferedImage image,
            String imageFormat) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BufferedOutputStream os = new BufferedOutputStream(bos);
        image.flush();
        ImageIO.write(image, imageFormat, os);
        os.flush();
        os.close();
        return bos.toByteArray();
    }

	public static BufferedImage getImageBufferedFromBytes(byte[] bytes) throws IOException{
		ByteArrayInputStream baos = new ByteArrayInputStream(bytes);
		BufferedImage img = ImageIO.read(baos);
		return img;
	}

	public static String createImageStringFromPath(String inputImagePath){

		BufferedImage imageBuffer = null;
		byte[] inputImage = new byte[1];
		String imagePath = "";
		try{
			imagePath = Play.application().path() + inputImagePath;
			imageBuffer = ImageIO.read(new File(imagePath));
			inputImage = getImageBytesFromBuffered(imageBuffer, "jpg");
		}catch(Exception ex){
			ex.printStackTrace();
		}

		String imageStr = Base64.getEncoder()
                .encodeToString(inputImage);

		return imageStr;
	}

	public static String createImageStringFromFile(File file){

		BufferedImage imageBuffer = null;
		byte[] inputImage = new byte[1];
		try{

			imageBuffer = ImageIO.read(file);
			inputImage = getImageBytesFromBuffered(imageBuffer, "jpg");
		}catch(Exception ex){
			ex.printStackTrace();
		}

		String imageStr = Base64.getEncoder()
                .encodeToString(inputImage);

		return imageStr;
	}


	public static String convertGrayScale(String inputStr){

    	String outputStr;
    	byte[] convertBin = new byte[1];
    	byte[] inputBin = Base64.getDecoder().decode(inputStr);
    	try{

    		BufferedImage inputBuffered = getImageBufferedFromBytes(inputBin);
    		toGrayScale(inputBuffered);
    		convertBin = getImageBytesFromBuffered(inputBuffered, "jpg");

    	}catch(Exception e){
    		e.printStackTrace();
    	}

    	outputStr = Base64.getEncoder().encodeToString(convertBin);

    	return outputStr;
    }

	public static void toGrayScale(BufferedImage image) {

		WritableRaster raster = image.getRaster();

		int[] pixelBuffer = new int[raster.getNumDataElements()];

		for (int y = 0; y < raster.getHeight(); y++) {
			for (int x = 0; x < raster.getWidth(); x++) {
				// ピクセルごとに処理

				raster.getPixel(x, y, pixelBuffer);

				// 単純平均法((R+G+B)/3)でグレースケール化したときの輝度取得
				int pixelAvg = (pixelBuffer[0] + pixelBuffer[1] + pixelBuffer[2]) / 3;
				// RGBをすべてに同値を設定することでグレースケール化する
				pixelBuffer[0] = pixelAvg;
				pixelBuffer[1] = pixelAvg;
				pixelBuffer[2] = pixelAvg;

				raster.setPixel(x, y, pixelBuffer);
			}
		}
	}


	public static String binarize(String inputStr, double threshold){

		// 一旦グレースケール化する
		inputStr = convertGrayScale(inputStr);

		LookupOp lookUpTable = getLookUpTable(threshold);

		String outputStr;
    	byte[] convertBin = new byte[1];
    	byte[] inputBin = Base64.getDecoder().decode(inputStr);
    	try{
    		BufferedImage inputBuffered = getImageBufferedFromBytes(inputBin);
    		BufferedImage outputBuffered = lookUpTable.filter(inputBuffered, null);
    		convertBin = getImageBytesFromBuffered(outputBuffered, "jpg");

    	}catch(Exception e){
    		e.printStackTrace();
    	}

    	outputStr = Base64.getEncoder().encodeToString(convertBin);

		return outputStr;
	}

	public static LookupOp getLookUpTable(double threshold) {

		byte[] lookUpTable = new byte[256];

		for (int i = 0; i < 256; i++) {
			// 閾値により、白・黒どちらを返すか決定
			if (i > threshold) {
				lookUpTable[i] = (byte) 255;
			} else {
				lookUpTable[i] = (byte) 0;
			}
		}

		return new LookupOp(new ByteLookupTable(0, lookUpTable), null);
	}

	public static String calcAverageBrightness(String inputStr){

		Double average = 0.0;
    	byte[] inputBin = Base64.getDecoder().decode(inputStr);
    	try{

    		BufferedImage inputBuffered = getImageBufferedFromBytes(inputBin);
    		average = calcAverage(inputBuffered);


    	}catch(Exception e){
    		e.printStackTrace();
    	}

		return average.toString();
	}

	public static Double calcAverage(BufferedImage image) {


		WritableRaster raster = image.getRaster();

		//int[] brightnessList = new int[raster.getNumDataElements()];
		int[] brightnessList = new int[raster.getHeight() * raster.getWidth()];
		int[] pixelBuffer = new int[raster.getNumDataElements()];
		int index = 0;

		for (int y = 0; y < raster.getHeight(); y++) {
			for (int x = 0; x < raster.getWidth(); x++) {
				// ピクセルごとに処理

				raster.getPixel(x, y, pixelBuffer);

				// 単純平均法((R+G+B)/3)でグレースケール化したときの輝度取得
				int pixelAvg = (pixelBuffer[0] + pixelBuffer[1] + pixelBuffer[2]) / 3;

				brightnessList[index++] = pixelAvg;
			}
		}

		return Arrays.stream(brightnessList).average().getAsDouble();
	}

	public static String printmake(Double a,Double b,Double c,Double d,Double e,Double f) {
			/*try {
					FileWriter fw = new FileWriter("/tmp/test.txt");
					fw.write("テスト2");//本番では変数(printwriter?)pwformatで#1ごといけるかも？
					fw.close();
			} catch (IOException ex) {
					ex.printStackTrace();
			}*/
			try {
            PrintWriter pw = new PrintWriter("/tmp/test.txt");
            //int a = 3,b = 5 ;
            pw.format("%d %d %d %d %d %d", a, b, c,d,e,f);
            pw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
      }
			return "テスト";
	}
	//サーバにファイル生成、書き込み

}
