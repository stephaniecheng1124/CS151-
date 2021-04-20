public class CashCard 
{
	char theBankID;
	int theAccountNum;
	String theExpDate;
	String cardNumber;

	/**
	 * Creates a new CashCard
	 * @param bankID the letter of the bank it is associated with
	 * @param accountNum the account number
	 * @param expDate the day the card expires
	 */
	public CashCard(char bankID, String accountNum, String expDate) 
	{
		cardNumber = bankID + accountNum;
		theExpDate = expDate;

	}
	
	/**
	 * Gets the expiration date of the card
	 * @return the expiration date
	 */
	public String getExp()
	{
		return theExpDate;
	}

}
