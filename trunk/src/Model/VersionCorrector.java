package Model;

import PolynomialPack.DivisionPolynomial;
import PolynomialPack.IntegerPolynom;

// Crée une instance d'un générateur de chaine binaire pour encoder la version du QRcode
public class VersionCorrector {

	private DivisionPolynomial m_division;
	private IntegerPolynom m_diviseur;
	private IntegerPolynom m_dividende;
	
	public VersionCorrector()
	{
		m_division = new DivisionPolynomial();

		m_diviseur = new IntegerPolynom();
		m_diviseur.addNewTerme(1, 12);
		m_diviseur.addNewTerme(1, 11);
		m_diviseur.addNewTerme(1, 10);
		m_diviseur.addNewTerme(1, 9);
		m_diviseur.addNewTerme(1, 8);
		m_diviseur.addNewTerme(0, 7);
		m_diviseur.addNewTerme(0, 6);
		m_diviseur.addNewTerme(1, 5);
		m_diviseur.addNewTerme(0, 4);
		m_diviseur.addNewTerme(0, 3);
		m_diviseur.addNewTerme(1, 2);
		m_diviseur.addNewTerme(0, 1);
		m_diviseur.addNewTerme(1, 0);
	}
	
	public String getVersionBinaryString(int version)
	{
		if (version > 40) return null;
		return null;
	}
	
	
	
	public IntegerPolynom integerToPolynom(int entier)
	{
		try{
			// Conversion de la version en binaire
			String value = Integer.toBinaryString(entier);
			
			// Ajout de zéro à gauche pour combler si la chaine n'a pas 6 caractères
			if (value.length() < 6)
			{
				int diff = 6 - value.length();
				String zeros = "";
				for (int i=0; i<diff; i++) zeros += "0";
				value = zeros + value;
			}
			
			// Création du polynome
			IntegerPolynom p = new IntegerPolynom();
			for (int i=0; i<value.length(); i++)
			{
				if (value.charAt(i) == '1')
					p.addNewTerme(1, value.length()-1-i);
				else
					p.addNewTerme(0, value.length()-1-i);
			}
			return p;
		}
		catch(Exception e)
		{
			System.err.println("ErrorCorrector -> BinaryStringFromInteger(int entier) : ");
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		DivisionPolynomial div = new DivisionPolynomial();

		IntegerPolynom diviseur = new IntegerPolynom();
		diviseur.addNewTerme(1, 12);
		diviseur.addNewTerme(1, 11);
		diviseur.addNewTerme(1, 10);
		diviseur.addNewTerme(1, 9);
		diviseur.addNewTerme(1, 8);
		diviseur.addNewTerme(0, 7);
		diviseur.addNewTerme(0, 6);
		diviseur.addNewTerme(1, 5);
		diviseur.addNewTerme(0, 4);
		diviseur.addNewTerme(0, 3);
		diviseur.addNewTerme(1, 2);
		diviseur.addNewTerme(0, 1);
		diviseur.addNewTerme(1, 0);

		IntegerPolynom dividende = new IntegerPolynom();


		// 7
		/*
		dividende.addNewTerme(1, 14);
		dividende.addNewTerme(1, 13);
		dividende.addNewTerme(1, 12);
		dividende.addNewTerme(0, 11);
		dividende.addNewTerme(0, 10);
		dividende.addNewTerme(0, 9);
		dividende.addNewTerme(0, 8);
		dividende.addNewTerme(0, 7);
		dividende.addNewTerme(0, 6);
		dividende.addNewTerme(0, 5);
		dividende.addNewTerme(0, 4);
		dividende.addNewTerme(0, 3);
		dividende.addNewTerme(0, 2);
		dividende.addNewTerme(0, 1);
		dividende.addNewTerme(0, 0);
		 */


		// 40
		dividende.addNewTerme(1, 17);
		dividende.addNewTerme(0, 16);
		dividende.addNewTerme(1, 15);
		dividende.addNewTerme(0, 14);
		dividende.addNewTerme(0, 13);
		dividende.addNewTerme(0, 12);
		dividende.addNewTerme(0, 11);
		dividende.addNewTerme(0, 10);
		dividende.addNewTerme(0, 9);
		dividende.addNewTerme(0, 8);
		dividende.addNewTerme(0, 7);
		dividende.addNewTerme(0, 6);
		dividende.addNewTerme(0, 5);
		dividende.addNewTerme(0, 4);
		dividende.addNewTerme(0, 3);
		dividende.addNewTerme(0, 2);
		dividende.addNewTerme(0, 1);
		dividende.addNewTerme(0, 0);

		System.out.println(div.createRemainderPolynom(diviseur, dividende));
	}
}
