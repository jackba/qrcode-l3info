package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import Vue.Fenetre;

public class BdecoderController extends AbstractController implements ActionListener {

	public BdecoderController(Fenetre f) {
		super(f);
	}

	public boolean isValid() {
		return false;
	}

	public void actionPerformed(ActionEvent event)
	{
		
	}

	// Retourne le texte résultant du décodage de l'image
	private static String getDecodeText(String pathName) {
		File file = new File(pathName);
		BufferedImage image;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			return e.toString();
		}
		if (image == null) {
			return "Impossible de décoder l'image";
		}
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Result result;
		try {
			result = new MultiFormatReader().decode(bitmap);
		} catch (ReaderException e) {
			return e.toString();
		}
		boolean isAnImage = (Boolean)result.getResultMetadata().get(ResultMetadataType.OTHER);
		// Le qr code décodé contient une image
		if (isAnImage)
		{
			
		}
		// Le qr code décodé contient du texte
		else
		{
			
		}
		return String.valueOf(result.getText());
	}
}
