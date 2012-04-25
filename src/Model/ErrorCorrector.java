package Model;

public class ErrorCorrector {
	
	private String m_msg;	
	
	public ErrorCorrector(String msgBinaire)
	{
		m_msg = msgBinaire;
	}
	
	public void createPolynomialMsg(String msgBinaire)
	{
		
	}
	
	public Integer intFromBinaryString(String msgBinaire)
	{
		int value;
		try{
			value = Integer.parseInt(msgBinaire, 2);
		}
		catch(Exception e)
		{
			return null;
		}
		return value;
	}
}
