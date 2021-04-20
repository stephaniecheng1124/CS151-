import java.util.TreeMap;
public class Bank 
{
	private TreeMap<String, Customer> customerList;
	
	/**
	 * Constructs a new bank
	 */
	public Bank() 
	{
		customerList = new TreeMap<String, Customer>();
	}
	
	/**
	 * Adds a new customer to the bank
	 * @param name the name of the customer
	 * @param accountNum the Customer's account number
	 */
	public void addCustomer(Customer name, String accountNum)
	{
		customerList.put(accountNum, name);
	}
	
	/**
	 * Gets the selected customer
	 * @param accountNum the customer's account number
	 * @return the Customer
	 */
	public Customer getCustomer(String accountNum)
	{
		return customerList.get(accountNum);
	}
	
	/**
	 * Checks to see if the account exists
	 * @param accountNum the account number associated with the card
	 * @return true if the account exists, false if it doesn't
	 */
	public boolean accountExists(String accountNum)
	{
		for(String key : customerList.keySet())
		{
			if(accountNum.equals(key))
			{
				return true;
			}
		}
		return false;
	}

}
