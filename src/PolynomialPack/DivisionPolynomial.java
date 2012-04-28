package PolynomialPack;

// Réalise une division polynomiale entre un générateur polynomial et un message polynomial
public class DivisionPolynomial {
	
	private GeneratorPolynomial m_genP;
	private MessagePolynomial m_msgP;
	private AlphaPolynom m_generatedPolynomial;
	private IntegerPolynom m_messagePolynomial;
	private GaloisField m_galoisF;
	
	public DivisionPolynomial(String msgBinaire, int nbOctetsDeCorrection)
	{
		m_genP = new GeneratorPolynomial();
		try {
			// Crée le générateur polynomial
			m_generatedPolynomial = m_genP.createGeneratorPolynomial(nbOctetsDeCorrection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		m_msgP = new MessagePolynomial();
		m_messagePolynomial  = m_msgP.createMessagePolynomial(msgBinaire, nbOctetsDeCorrection);
		m_galoisF = GaloisField.getInstance();
	}
	
	// Multiplie un polynome (alpha ou entier) par x^exposant
	private void multiplyPolynom(Polynom p, int exposant)
	{
		for (int i=0; i<p.getNbTermes(); i++)
		{
			// p.exposant[i] = p.exposant[i] + exposant
			p.getTermeAt(i).setExposant(p.getTermeAt(i).getExposant() + exposant);
		}
	}
	
	// Divise un polynome (alpha ou entier) par x^exposant
	private void divisePolynom(Polynom p, int exposant)
	{
		for (int i=0; i<p.getNbTermes(); i++)
		{
			// p.exposant[i] = p.exposant[i] - exposant
			p.getTermeAt(i).setExposant(p.getTermeAt(i).getExposant() - exposant);
		}
	}
	
	// Ramène le polynome p1 au même degré que le polnyome p2 soit par division, soit par multiplication
	private void uniformisePolynoms(Polynom p1, Polynom p2)
	{
		// Les deux polynomes sont instanciés
		if (p1 != null && p2 != null)
		{
			// Les deux polynomes ont au moins un terme
			if (p1.getNbTermes() > 0 && p2.getNbTermes() > 0)
			{
				// Tri des deux polynomes par degré décroissant
				p1.sortByExposants();
				p2.sortByExposants();
				
				// p1 est de degré inférieur à p2.
				// On multiplie p1 par la différence entre les deux exposants de plus haut degré
				//
				if (p1.getTermeAt(0).getExposant() < p2.getTermeAt(0).getExposant())
				{
					multiplyPolynom(p1, p2.getTermeAt(0).getExposant() - p1.getTermeAt(0).getExposant());
				}
				// p1 est de degré supérieur à p2.
				// On divise p1 par la différence entre les deux exposants de plus haut degré
				//
				else if (p1.getTermeAt(0).getExposant() > p2.getTermeAt(0).getExposant())
				{
					divisePolynom(p1, p1.getTermeAt(0).getExposant() - p2.getTermeAt(0).getExposant());
				}
			}
		}
	}
	
	// Retourne un nouveau polynome résultant du produit du polynome p1 et du premier coefficient du polynome p2
	private AlphaPolynom mulPolynomByFirstCoeff(AlphaPolynom p1, AlphaPolynom p2)
	{
		AlphaPolynom result = new AlphaPolynom();
		TermeAlpha ta_1;
		TermeAlpha ta_2 = (TermeAlpha)p2.getTermeAt(0);	// Premier terme du second polynome
		TermeAlpha ta_product;
		
		for (int i=0; i<p1.getNbTermes(); i++)	// Pour tous les termes du premier polynome
		{
			// Multiplier avec le premier terme du second polynome
			ta_1 = (TermeAlpha)p1.getTermeAt(i);	// Terme i du premier polynome
			ta_product = m_genP.multiplyTerms(ta_1,ta_2);	// Terme résultant du produit de ta_1 et ta_2
			result.addNewTerme(ta_product.getExposantAlpha(), ta_1.getExposant());	// Ajout du produit comme nouveau terme dans le polynome résultat
		}
		return result;
	}
	
	// Retourne un polynome entier sans les termes nuls (= 0)
	private IntegerPolynom removeFirstZerosTerms(IntegerPolynom p)
	{
		IntegerPolynom result = (IntegerPolynom) p.clone();
		int resultSize = result.getNbTermes();
		Boolean isCurrentTermNull = false;
		
		int i=0;
		do
		{
			if (((TermeEntier)result.getTermeAt(0)).getCoefficient() == 0)
			{
				result.removeTermeAt(0);
				isCurrentTermNull = true;
			}
			else
			{
				isCurrentTermNull = false;
			}
			i++;
		} while(isCurrentTermNull && i < resultSize);
		
		/*
		for (int i=0; i<p.getNbTermes(); i++)	// Pour tous les termes du polynome
		{
			// Le coefficient entier du terme est différent de zéro
			if (((TermeEntier)p.getTermeAt(i)).getCoefficient() != 0)
				// Ajout du terme dans le polynome résultat
				result.addNewTerme(((TermeEntier)p.getTermeAt(i)).getCoefficient(), p.getTermeAt(i).getExposant());
		}
		*/
		return result;
	}
	
	// Retourne un polynome entier résultant d'un OU-EXCLUSIF entre deux polynomes entiers p1 et p2
	private IntegerPolynom combinePolynoms(IntegerPolynom p1, IntegerPolynom p2)
	{
		IntegerPolynom result = new IntegerPolynom();
		Boolean isP1greaterP2 = false;	// P1 a plus de termes que P2
		Boolean isP1equalP2 = false;	// P1 a le même nombre de termes que P2
		int lessGreater;	// Nombre de termes ayant un degré commun sur les deux polynomes
		
		// Détermination du polynome ayant le moins de termes
		// p1 a un nombre de termes supérieur à p2
		if(p1.getNbTermes() > p2.getNbTermes())
		{
			lessGreater = p2.getNbTermes();
			isP1greaterP2 = true;
		}
		// p1 a un nombre de termes inférieur à p2
		else if (p1.getNbTermes() < p2.getNbTermes())
		{
			lessGreater = p1.getNbTermes();
			isP1greaterP2 = false;
		}
		// p1 a le même nb de termes que p2
		else
		{
			lessGreater = p1.getNbTermes();
			isP1equalP2 = true;
		}
		
		int coeff1; // Premier coefficient
		int coeff2;	// Second coefficient
		
		// Effectue un XOR sur les termes de mêmes degrés
		for (int i=0; i<lessGreater; i++)
		{
			coeff1 = ((TermeEntier)p1.getTermeAt(i)).getCoefficient();	// récupère le premier coefficient
			coeff2 = ((TermeEntier)p2.getTermeAt(i)).getCoefficient();	// récupère le second coefficient
			// Ajoute un nouveau terme au résultat avec pour coefficient le XOR de coeff1 et coeff2
			result.addNewTerme(m_galoisF.XOR(coeff1, coeff2), p1.getTermeAt(i).getExposant()) ;
		}
		
		if (!isP1equalP2)	// Un des deux polynomes a plus de termes que l'autre
		{
			if (isP1greaterP2)	// Le polynome ayant le plus de termes est P1
			{
				// Ajout les termes supplémentaires de p1 au résultat
				for (int i=lessGreater; i<p1.getNbTermes(); i++)
					result.addNewTerme(((TermeEntier)p1.getTermeAt(i)).getCoefficient(), p1.getTermeAt(i).getExposant());
			}
			else	// Le polynome ayant le plus de termes est P2
			{
				// Ajout les termes supplémentaires de p2 au résultat
				for (int i=lessGreater; i<p2.getNbTermes(); i++)
					result.addNewTerme(((TermeEntier)p2.getTermeAt(i)).getCoefficient(), p2.getTermeAt(i).getExposant());
			}
		}
		
		return result;
	}
	
	// Retourne le polynome correcteur d'erreur pour cette instance de division polynomiale
	public IntegerPolynom createErrorCorrectorPolynom()
	{
		// Initialisation
		AlphaPolynom myGeneratorPolynomialAlpha = (AlphaPolynom) m_generatedPolynomial.clone();	// Copie locale du générateur polynomial
		IntegerPolynom myMessagePolynomialInteger = (IntegerPolynom) m_messagePolynomial.clone();	// Copie locale du message polynomial
		AlphaPolynom resultAlpha;
		IntegerPolynom previousResultInteger;
		
		// Uniformisation au même degré du générateur polynomial par rapport au message polynomial
		uniformisePolynoms(myGeneratorPolynomialAlpha,myMessagePolynomialInteger);
		
		// Conversion du message polynomial avec coefficients entiers en un polynome avec coefficients alphas
		AlphaPolynom myMessagePolynomialAlpha = myMessagePolynomialInteger.toAlphaPolynom();
		
		// Multiplication du Générateur polynomial par le coefficient du premier terme du message polynomial
		AlphaPolynom product = mulPolynomByFirstCoeff(myGeneratorPolynomialAlpha,myMessagePolynomialAlpha);
		
		// Conversion du résultat en un polynome entier
		IntegerPolynom productInteger = product.toIntegerPolynom();
		
		// Application d'un OU-EXCLUSIF entre le polynome entier résultant et le message polynomial entier
		IntegerPolynom result = combinePolynoms(productInteger,myMessagePolynomialInteger);
		
		// On retire les termes nuls du résultat
		result = removeFirstZerosTerms(result);
		
		// On répète l'opération tant que le dernier terme du polynome résultat n'a pas un terme de degré 1 (exposant à 0)
		while (result.getTermeAt(result.getNbTermes()-1).getExposant() != 0)
		{
			// Sauvegarde du résultat précédent pour le calcul de ce résultat
			previousResultInteger = (IntegerPolynom) result.clone();
			
			// Conversion du polynome résultat avec coefficients entiers en polynome avec coefficients alpha
			resultAlpha = result.toAlphaPolynom();
			
			// Uniformisation au même degré du générateur polynomial par rapport au resultat polynomial de l'étape précédente
			uniformisePolynoms(myGeneratorPolynomialAlpha,resultAlpha);
			
			// Multiplication du Générateur polynomial par le coefficient du premier terme du resultat polynomial de l'étape précédente
			// Cette multiplication donne le résultat de cette étape
			product = mulPolynomByFirstCoeff(myGeneratorPolynomialAlpha,resultAlpha);
			
			// Conversion du résultat de cette étape en un polynome entier
			productInteger = product.toIntegerPolynom();
			
			// Application d'un OU-EXCLUSIF entre le polynome entier résultat de cette étape
			// et le polynome résultat de l'étape précédente
			result = combinePolynoms(productInteger,previousResultInteger);
			
			// On retire les termes nuls du résultat
			result = removeFirstZerosTerms(result);
		}
		
		// Le résultat contient un dernier terme de degré 1, le résultat est prêt
		return result;
	}
	
	/*
	 * GETTERS
	 */
	public GeneratorPolynomial getGeneratorPolynomial()
	{
		return m_genP;
	}
	public MessagePolynomial getMessagePolynomial()
	{
		return m_msgP;
	}
	public AlphaPolynom getGeneratedPolynomial()
	{
		return m_generatedPolynomial;
	}
}
