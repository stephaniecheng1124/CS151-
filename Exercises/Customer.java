public class Customer {
	
	BankAccount theAccount;

	/**
	 * Creates a new Customer
	 * @param account the account number of the bank
	 */
	public Customer(BankAccount account)
	{
		theAccount = account;
		
	}
	
	/**
	 * Gets the customer's bank account
	 * @return the bank account
	 */
	public BankAccount getBankAccount()
	{
		return theAccount;
	}
}
