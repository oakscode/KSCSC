package com.supplyco.jsonqr.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCode {

	public static void main(String[] args) throws WriterException, IOException,
			NotFoundException {
		String qrCodeData = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjExNUY0NDI2NjE3QTc5MzhCRTFCQTA2REJFRTkxQTQyNzU4NEVEQUIiLCJ0eXAiOiJKV1QiLCJ4NXQiOiJFVjlFSm1GNmVUaS1HNkJ0dnVrYVFuV0U3YXMifQ.eyJkYXRhIjoie1wiU2VsbGVyR3N0aW5cIjpcIjMyQUFBQ0s2NzY3RkhaNVwiLFwiQnV5ZXJHc3RpblwiOlwiMzJBQUJDSzAzODVKMVpQXCIsXCJEb2NOb1wiOlwiMTIzNDVBQlwiLFwiRG9jVHlwXCI6XCJJTlZcIixcIkRvY0R0XCI6XCIxNi8wOS8yMDIwXCIsXCJUb3RJbnZWYWxcIjoxNzcwMCxcIkl0ZW1DbnRcIjoyLFwiTWFpbkhzbkNvZGVcIjpcIjM0MDFcIixcIklyblwiOlwiYjIyY2UyMmExZTMzNWYzMmIwNDFiMDY3YTFjYmQzNzc1NzdhOWJhNTJhZTQzZWMyMDU4ZTI0OGM5Y2I3NjUxNFwiLFwiSXJuRHRcIjpcIjIwMjAtMDktMTYgMTY6MzQ6MDBcIn0iLCJpc3MiOiJOSUMifQ.aF340-sNsxhkVIji0pWv6v3bnun0HeT8OUX-sHwJAnFMP4juBvlBx3GmWGNAngsWErzydnSE5ykArVCQSH_03kcd3Xhy-mVJJy4-X-lEAHAFooy21ByFJ4ZKVK1QdHK1haTNSWuSrfVtsLjfDKYfbtnslEMBqC4lA6G33YSQDNqe0ZBuPZadbRAAQYz0zkGOb3rLTryknNQn1Kw2z5CvXjIBGoT6rTT59ZMshFsJNKSFB3FXmeung635a8tlLNezwjMu9Q2hrvy35ZOuU5N7rH3d9ntVAixFMEwH13L5NrnDo5Ck4FU0c9akzt-Y3cDA7MYszEdqGIJQXwaMiUuHUg";
		String filePath = "E:\\QRCode.png";
		String charset = "UTF-8"; // or "ISO-8859-1"
		Map hintMap = new HashMap();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		createQRCode(qrCodeData, filePath, charset, hintMap, 400, 400);
		System.out.println("QR Code image created successfully!");

		System.out.println("Data read from QR Code: "
				+ readQRCode(filePath, charset, hintMap));

	}

	public static void createQRCode(String qrCodeData, String filePath,
			String charset, Map hintMap, int qrCodeheight, int qrCodewidth)
			throws WriterException, IOException {
		BitMatrix matrix = new MultiFormatWriter().encode(
				new String(qrCodeData.getBytes(charset), charset),
				BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
		MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
				.lastIndexOf('.') + 1), new File(filePath));
	}

	public static String readQRCode(String filePath, String charset, Map hintMap)
			throws FileNotFoundException, IOException, NotFoundException {
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
				new BufferedImageLuminanceSource(
						ImageIO.read(new FileInputStream(filePath)))));
		Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap,
				hintMap);
		return qrCodeResult.getText();
	}
}