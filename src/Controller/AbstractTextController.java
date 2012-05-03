package Controller;

import Vue.Fenetre;

public abstract class AbstractTextController extends AbstractController {

	private int[][] m_maximumsChars;
	
	public AbstractTextController(Fenetre f) {
		super(f);
	}
	
	public int[][] getMaximumsChars()
	{
		return m_maximumsChars;
	}
	
	public void setMaximumsChars(int[][] maximums)
	{
		m_maximumsChars = maximums;
	}
}
