package com.movieticket.controller.payment;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class PaymentQRService {

	public static String generateQR(String data) throws Exception {

		BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 300, 300);

		BufferedImage image = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < 300; x++) {

			for (int y = 0; y < 300; y++) {

				image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
			}
		}

		ByteArrayOutputStream output = new ByteArrayOutputStream();

		ImageIO.write(image, "PNG", output);

		return Base64.getEncoder().encodeToString(output.toByteArray());
	}

}
