package Model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.StringBuilder;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import java.awt.image.BufferedImage;

public class ImageParser {

	// Liste des valeurs binaires des octets placés au tout début d'un fichier et qui identifient son format.
	// Aussi appelés nombres magiques ou header de format
	private static String[][] MAGIC_NUMBERS_TABLE = 
		new String[][] {
		{"jpg","11111111110110001111111111100000"},	// 0xFFD8FFE0
		{"png","10001001010100000100111001000111"},		// 0x89504E47
		{"bmp","0100001001001101"},						// 0x424D
		{"gif","01000111010010010100011000111000"}		// 0x47494638
	};

	private static String IMG_MODE_INDICATOR = "1111";

	// Constructeur privé: l'ensemble des méthodes sera statique
	private ImageParser() {}

	public static String getImageModeIndicator()
	{
		return IMG_MODE_INDICATOR;
	}

	// Retourne un tableau d'octets depuis l'image pointée par le chemin
	private static byte[] getBytesFromImage(String imgPath) throws IOException
	{
		// Création d'un fichier
		File filePath = new File(imgPath);

		// Création d'un flux de lecture ouvert sur le fichier
		FileInputStream fileInStream = new FileInputStream(filePath);

		// Création d'un flux en écriture dans un tableau d'octets,
		// dans lequel seront stockés les octets lus par le flux de lecture
		ByteArrayOutputStream bytesOutStream = new ByteArrayOutputStream();

		// Création d'un tableau d'octets qui servira de mémoire tampon
		byte[] buffer = new byte[1024];

		// Tant que nous recevons encore des octets en provenance du flux de lecture (c.à.d que nous ne sommes pas à la fin du fichier)
		// On lit une nouvelle série d'octets qui est stockée dans le buffer
		// On récupère le nombre d'octets lus en provenance du flux d'entrée
		for (int nbrOctetsLus; (nbrOctetsLus = fileInStream.read(buffer)) != -1;)
		{
			// On écrit sur le flux de sortie le contenu du buffer,
			// en partant de l'indice 0 jusqu'au nombre d'octets lus
			// (1023 au maximum, car notre buffer a une taille de 1024 octets)
			bytesOutStream.write(buffer, 0, nbrOctetsLus); 
		}

		// On retourne le tableau d'octets issu du flux de sortie et qui représente notre fichier
		return bytesOutStream.toByteArray();
	}

	private static StringBuilder m_stringBuilder = new StringBuilder();

	// Convertit un byte en chaine binaire de 8 bits
	public static String byteToBinary(byte b){
		m_stringBuilder.delete(0, m_stringBuilder.length());
		int i = b & 0xFF;
		m_stringBuilder.append(Integer.toBinaryString(i));
		while (m_stringBuilder.length()<8) m_stringBuilder.insert(0,'0');
		return m_stringBuilder.toString();
	}

	// Retourne la chaine binaire correspondant à l'image passée en paramètre
	// Lève une exception si l'image n'existe pas
	public static String getBinaryString(String imagePath) throws IOException
	{
		byte[] octets = null;
		octets = ImageParser.getBytesFromImage(imagePath);

		// Construction d'un nouveau string builder pour optimiser la concaténation
		StringBuilder sb = new StringBuilder();

		// Pour tous les octets contenus dans le tableau
		int taille = octets.length;
		for (int i=0; i<taille; i++)
		{
			// Concaténation de leur chaine binaire équivalente en version non-signée
			//sb.append(getBinaryByte(octets,i));
			sb.append(byteToBinary(octets[i]));
		}

		// Retour de la chaine binaire globale
		return sb.toString();
	}

	// Retourne le format de l'image encodée dans la chaine binaire
	// Retourne null si le format n'est pas supporté
	public static String getImageExtension(String binaryString)
	{
		for (int i=0; i<MAGIC_NUMBERS_TABLE.length; i++)
			if (binaryString.startsWith(MAGIC_NUMBERS_TABLE[i][1]))
				return MAGIC_NUMBERS_TABLE[i][0];	// Le format a été identifié, on le retourne

		return null;
	}

	// Retourne un tableau d'octets depuis une chaine de caractères binaires
	private static byte[] getBytesFromString(String binaryString)
	{
		// Création d'un nouveau tableau d'octets
		int arrayLength;
		if (binaryString.length()%8 == 0)
			arrayLength = (int)binaryString.length()/8;
		else
			arrayLength = (int)(binaryString.length()/8) +1;
		byte[] byteArray = new byte[arrayLength];

		for (int i=0; i<arrayLength; i++)
			byteArray[i] = (byte)(intFromBinaryString(binaryString.substring(i*8, i*8+8)) & 0xff);

		return byteArray;
	}

	// Convertit une chaine binaire (base 2) en entier (base 10)
	public static Integer intFromBinaryString(String msgBinaire)
	{
		int value;
		try{
			value = Integer.parseInt(msgBinaire, 2);
		}
		catch(Exception e)
		{
			System.err.println("ErrorCorrector -> msgBinaire(String msgBinaire) : 'msgBinaire' is not a binary string.");
			return null;
		}
		return value;
	}

	// Retourne une image à partir d'une chaine de données binaires
	public static Image getImage(String binaryString) throws Exception
	{
		String extension = getImageExtension(binaryString);

		if (extension != null)
		{
			byte[] donnees = getBytesFromString(binaryString);

			ByteArrayInputStream bytesInStream = new ByteArrayInputStream(donnees);
			Iterator<?> readers = ImageIO.getImageReadersByFormatName(extension);

			ImageReader reader = (ImageReader) readers.next();
			Object source = bytesInStream;

			ImageInputStream imgInputStream = ImageIO.createImageInputStream(source);

			reader.setInput(imgInputStream, true);
			ImageReadParam param = reader.getDefaultReadParam();

			Image image = reader.read(0, param);
			return image;
		}
		else
			throw new Exception("Unsupported Format Exception");
	}

	public static boolean isUnderMaximum(File fichier, int maximumSizeInOctets)
	{
		return fichier.length() <= maximumSizeInOctets;
	}

	public static boolean isSupportedImageFormat(File fichier)
	{
		// Création d'un flux de lecture ouvert sur le fichier
		FileInputStream fileInStream;
		try {
			fileInStream = new FileInputStream(fichier);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return false;
		}

		// Création d'un flux en écriture dans un tableau d'octets,
		// dans lequel seront stockés les octets lus par le flux de lecture
		ByteArrayOutputStream bytesOutStream = new ByteArrayOutputStream();

		// Création d'un tableau d'octets qui servira de mémoire tampon
		byte[] buffer = new byte[4];

		// Tant que nous recevons encore des octets en provenance du flux de lecture (c.à.d que nous ne sommes pas à la fin du fichier)
		// On lit une nouvelle série d'octets qui est stockée dans le buffer
		// On récupère le nombre d'octets lus en provenance du flux d'entrée
		try {
			for (int nbrOctetsLus; (nbrOctetsLus = fileInStream.read(buffer)) != -1;)
			{
				// On écrit sur le flux de sortie le contenu du buffer,
				// en partant de l'indice 0 jusqu'au nombre d'octets lus
				// (4 au maximum, car notre buffer a une taille de 4 octets)
				bytesOutStream.write(buffer, 0, nbrOctetsLus); 
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		// On retourne les 4 premiers octets que l'on stocke dans notre buffer (pour gagner en simplicité et performances)
		buffer = bytesOutStream.toByteArray();

		// Création de la chaine binaire
		StringBuilder binary = new StringBuilder(32);
		for (int i=0; i<4; i++)
			binary.append(byteToBinary(buffer[i]));

		// Comparaison avec les headers connus
		for (int i=0; i<MAGIC_NUMBERS_TABLE.length; i++)
			if (binary.toString().startsWith(MAGIC_NUMBERS_TABLE[i][1]))
				return true;	// Le format est connu
		return false;	// Le format est inconnu
	}
	
	// Retourne le nombre de caractères codés selon la version du QRcode dans laquelle seront codés les caractères
	public static String getCountIndicator(String binaryString, int version)
	{
		int length;
		if (binaryString.length()%8 == 0) length = (int)(binaryString.length()/8);
		else length = (int)(binaryString.length()/8) + 1;

		StringBuilder sb = new StringBuilder();
		sb.append(Integer.toBinaryString(length));

		if(version>=1 && version <=9)
		{
			while (sb.length() < 8)
				sb.insert(0, '0');
		}
		else if(version>=10 && version <= 40)
		{
			while (sb.length() < 16)
				sb.insert(0, '0');
		}

		return sb.toString();
	}

	// Ecrit l'image dans un fichier
	public static void saveImage(Image img, String pathFile, String format) throws IOException
	{
		// Création d'un buffer d'image pour y dessiner notre image
		BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		
		// Récupération de l'objet Graphics associé au buffer pour peindre l'image
		Graphics2D g2 = bufferedImage.createGraphics();
		g2.drawImage(img, null, null);	// Peinture de l'image dans le buffer
		
		File imageFile = new File(pathFile);	// Création d'un nouveau fichier à l'emplacement désigné
		ImageIO.write(bufferedImage, format, imageFile);	// Ecriture du buffer dans le fichier
	}

	/*
	public static void main(String[] args) throws IOException
	{
		String path = "/home/etudiant/test.jpg";
		String binaryString = getBinaryString(path);

		byte[] b1 = getBytesFromImage(path);
		byte[] b2 = getBytesFromString(binaryString);

		if (b1.length == b2.length)
			for (int i=0; i<b1.length; i++)
			{
				System.out.print("b1["+i+"] = "+b1[i] + "\tb2["+i+"] = "+b2[i]);
				if (b1[i] == b2[i]) System.out.print("\t IDENTIQUES");
				else System.out.print("\t DIFFERENTS");
				System.out.println();
			}
	}
	 */
}
