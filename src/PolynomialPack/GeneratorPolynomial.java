package PolynomialPack;

public class GeneratorPolynomial {
	
	private AlphaPolynom m_basePolynom;	// Polynome de départ pour la génération
	private GaloisField m_galoisField; // Pour les opérations sur le champs de Galois
	
	public GeneratorPolynomial()
	{
		m_galoisField = GaloisField.getInstance();
		initBasePolynom();
	}
	
	// initialise le polynome utilisé à la base de toute génération polynomiale basePolynom = a^0.x^1 + a^0.x^0
	private void initBasePolynom()
	{
		m_basePolynom = new AlphaPolynom();	// bP =
		m_basePolynom.addNewTerme(0, 1); // bP = a^0.x^1
		m_basePolynom.addNewTerme(0, 0); // bP = a^0.x^1 + a^0.x^0
	}
	
	// Retourne un exposant compris dans le champs de Galois 256 (entre 0 inclus et 255 inclus)
	private int fixGreaterExponent(int exponent)
	{
		return ((exponent % 255) + (int)Math.floor( (double)exponent / (double)255));
		//return exponent % 255;
	}
	
	// Retourne un nouveau TermeAlpha résultant du produit de t1 et t2
	public TermeAlpha multiplyTerms(TermeAlpha t1, TermeAlpha t2) {
			
		// Somme des exposants
		int expAlpha = t1.getExposantAlpha() + t2.getExposantAlpha();
		int expX = t1.getExposant() + t2.getExposant();
		
		// Attribution d'un exposant entre 0 et 255 si l'exposant est > 255
		if (expAlpha > 255) expAlpha = fixGreaterExponent(expAlpha);
		if (expX > 255) expX = fixGreaterExponent(expX);
		
		// Création du nouveau terme
		return new TermeAlpha(expAlpha,expX);
	}
	
	// Retourne un nouveau polynome résultant du produit de deux polynomes p1 et p2
	private AlphaPolynom multiply(AlphaPolynom p1, AlphaPolynom p2)
	{
		AlphaPolynom result = new AlphaPolynom();
		TermeAlpha ta_1;
		TermeAlpha ta_2;
		TermeAlpha ta_product;
		
		for (int i=0; i<p1.getNbTermes(); i++)	// Pour tous les termes du premier polynome
		{
			for (int j=0; j<p2.getNbTermes(); j++)	// Multiplier avec tous les termes du second polynome
			{
				ta_1 = (TermeAlpha)p1.getTermeAt(i);	// Terme i du premier polynome
				ta_2 = (TermeAlpha)p2.getTermeAt(j);	// Terme j du second polynome
				ta_product = multiplyTerms(ta_1,ta_2);	// Terme résultant du produit de ta_1 et ta_2
				result.addNewTerme(ta_product.getExposantAlpha(), ta_product.getExposant());	// Ajout du produit comme nouveau terme dans le polynome résultat
			}
		}
		
		return result;
	}
	
	// Retourne un nouveau TermeAlpha résultant de la combinaison de deux termes t1 et t2 ayant un exposant (=degré) identique
	// La combinaison consiste à convertir les valeurs alpha en coefficients entiers,
	// puis réaliser un OU-EXCLUSIF entre les deux coefficients entiers pour former un coefficient entier unique
	// ensuite, reconvertir le coefficient entier unique ainsi obtenu en alpha
	// et enfin ajouter à l'alpha de l'étape précédente l'exposant de départ pour former la combinaison de t1 et de t2.
	private TermeAlpha combineTerms(TermeAlpha t1, TermeAlpha t2) throws Exception
	{
		if (t1.getExposant() != t2.getExposant()) throw new Exception("GeneratorPolynomial->combineTerms(TermeAlpha t1, TermeAlpha t2) :" +
				"t1 and t2 must have the same exponent (= degree)");	// t1 et t2 ont un exposant différent
		
		// Conversion des alphas en entiers
		int coeff1 = m_galoisField.getAlphaValue(t1.getExposantAlpha());
		int coeff2 = m_galoisField.getAlphaValue(t2.getExposantAlpha());
		
		// Récupération du coefficient entier résultant d'un OU-EXCLUSIF
		int coeffResult = m_galoisField.XOR(coeff1,coeff2);
		
		// Reconversion du coefficient en alpha
		int alphaResult = m_galoisField.getExponantValue(coeffResult);
		
		// Création du nouveau terme résultant de la combinaison de t1 et de t2
		return new TermeAlpha(alphaResult,t1.getExposant());
	}
	
	
	// Retourne un nouveau Polynome où les termes adjacents de degrés identiques sont combinés
	private AlphaPolynom combineSameDegreesTerms(AlphaPolynom p) throws Exception
	{
		AlphaPolynom ap_result = new AlphaPolynom();	// Polynom résultat
		TermeAlpha ta_curr;	// Le terme alpha courant
		TermeAlpha ta_next;	// Le terme alpha suivant
		TermeAlpha ta_combi;// Le terme alpha combiné à partir du courant et du suivant
		p.sortByExposants();	// Tri des termes par degré décroissant
		
		
		// Parcours de l'ensemble des termes du polynome triés du plus haut degré au plus petit
		int i=0;
		while (i<p.getNbTermes())
		{
			ta_curr = (TermeAlpha) p.getTermeAt(i);	// Récupération du terme alpha courant
			
			// Nous ne sommes pas rendus au dernier terme (<=> terme ayant le plus petit degré <=> terme le plus à droite dans le polynome)
			if (i+1 < p.getNbTermes())
			{
				// Le terme suivant le terme courant a un degré (= exposant) différent du terme courant
				if (p.getTermeAt(i).getExposant() != p.getTermeAt(i+1).getExposant())
				{
					ap_result.addNewTerme(ta_curr.getExposantAlpha(), ta_curr.getExposant()); // Ajout du terme courant dans le polynome résultat
					i ++;	// Le terme suivant sera examiné au tour d'après
				}
				else	// Le terme suivant et le terme courant ont un degré identique
				{
					ta_next = (TermeAlpha) p.getTermeAt(i+1);	// Récupération du terme alpha suivant
					ta_combi = combineTerms(ta_curr,ta_next); // Combinaison du terme courant et du suivant
					ap_result.addNewTerme(ta_combi.getExposantAlpha(), ta_combi.getExposant());// Ajout du terme combiné dans le polynome résultat
					i += 2;	// Le terme situé deux termes plus loin sera examiné au tour d'après
				}
			}
			else // Nous sommes au dernier terme, il n'y a pas d'autres termes de degrés identiques
			{
				// On ajoute le dernier terme au polynome résultat
				ap_result.addNewTerme(ta_curr.getExposantAlpha(), ta_curr.getExposant());
				i++;	// Ne pas oublier d'incrémenter pour arreter la boucle
			}
		}
		
		return ap_result;
	}
	
	// Crée un générateur polynomial ayant le nombre d'octets de correction demandés
	public AlphaPolynom createGeneratorPolynomial(int nbOctetsDeCorrection) throws Exception
	{
		AlphaPolynom ap_result = m_basePolynom;	// ap_result = a^0.x^1 + a^0.x^0
		
		// Initialisation	
		AlphaPolynom current = new AlphaPolynom();
		current.addNewTerme(0, 1);
		current.addNewTerme(1, 0);					// current = a^0.x^1 + a^1.x^0
		
		// Multiplication du polynome de base par étapes (n-1 étapes)
		for (int i=0; i<nbOctetsDeCorrection-1; i++)
		{
			ap_result = multiply(ap_result, current);
			ap_result = combineSameDegreesTerms(ap_result);
			current.setTermeAt(1, new TermeAlpha(i+2, 0));
		}
		
		return ap_result;
	}
	
}
