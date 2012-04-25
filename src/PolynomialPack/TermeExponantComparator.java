package PolynomialPack;

import java.util.Comparator;

public class TermeExponantComparator implements Comparator<Terme> {
	
	public int compare(Terme t1, Terme t2) {	
        if(t1.getExposant() > t2.getExposant())
            return 1;
        else if(t1.getExposant() < t2.getExposant())
            return -1;
        else
            return 0;
	}

}
