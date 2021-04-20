/**
 * The BankAccount class
 * @author Stephanie Cheng
 *
 */
public class BankAccount 
{
	String thePassCode;
	int balance;
	CashCard theCard;
	String log = "";
	
	/**
	 * Creates a new BankAccount object
	 * @param passCode the password of the account
	 * @param startBalance the starting balance
	 * @param card the card associated with the account
	 */
	public BankAccount(String passCode, int startBalance,CashCard card) 
	{
		thePassCode = passCode;
		theCard = card;
		balance = startBalance;
	}
	
	/**
	 * Gets the bank account's cash card
	 * @return the cash card
	 */
	public CashCard getCard()
	{
		return theCard;
	}
	
	/**
	 * Gets the password
	 * @return the password
	 */
	public String getPasscode()
	{
		return thePassCode;
	}
	
	/**
	 * Gets the account balance
	 * @return the balance
	 */
	public int getBalance()
	{
		return balance;
	}
	
	/**
	 * updates the balance after withdrawals
	 * @param amtWithdraw the amount you withdrew
	 */
	public void updateBalance(int amtWithdraw)
	{
		balance = balance - amtWithdraw;
	}
	
	/**
	 * Logs the users withdrawal actions
	 * @param action the amount withdrew in a transaction
	 */
	public void log(String action)
	{
		log = log + "\n" + action;
	}
	
	/**
	 * Returns a record of the user's withdrawals
	 * @return the log
	 */
	public String getLog()
	{
		return log;
	}

}
