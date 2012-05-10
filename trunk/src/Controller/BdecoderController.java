package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import Model.ImageParser;
import Vue.Fenetre;

public class BdecoderController extends AbstractController implements ActionListener {

	private boolean m_isAnImage;
	private String m_decodedString;
	
	public BdecoderController(Fenetre f) {
		super(f);
		m_isAnImage = false;
		m_decodedString = null;
	}

	public boolean isValid() {
		return false;
	}

	public void actionPerformed(ActionEvent event)
	{
		getFenetre().hideShownResultBoxes();
		m_decodedString = getTextFromQRFile(getFenetre().getTF_decodeImgPath().getText());
		// Le qr code décodé contient une image
		if (m_isAnImage)
		{
			getFenetre().showResultImgBox();
			try {
				getFenetre().getImageComponent().setImage(ImageParser.getImage(m_decodedString));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// Le qr code décodé contient du texte
		else
		{
			getFenetre().showResultTextBox();
			getFenetre().getTA_result().setText(m_decodedString);
		}
	}

	// Retourne le texte résultant du décodage de l'image
	private String getTextFromQRFile(String pathName) {
		File file = new File(pathName);
		BufferedImage image;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
			m_isAnImage = false;
			return e.toString();
		}
		if (image == null) {
			m_isAnImage = false;
			return "Impossible de décoder l'image";
		}
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Result result;
		try {
			Map<DecodeHintType,Object> mp = new HashMap<DecodeHintType,Object>();
			mp.put(DecodeHintType.PURE_BARCODE, null);
			mp.put(DecodeHintType.TRY_HARDER, null);
			result = new MultiFormatReader().decode(bitmap,mp);	// DecodeHint
		} catch (ReaderException e) {
			m_isAnImage = false;
			return e.toString();
		}
		m_isAnImage = (Boolean)result.getResultMetadata().get(ResultMetadataType.OTHER);
		return String.valueOf(result.getText());
	}
}
