import java.util.Scanner;
/**
 * 
 * @author Stephanie Cheng
 * Class with main method to use the ATM
 *
 */
public class ATMSystem 
{

	/**
	 * The main method to use the ATM
	 * @param args 
	 */
	public static void main(String[] args) 
	{

		Bank BankofA = new Bank();
		CashCard A1 = new CashCard('A', "1", "11/24/10");
		CashCard A2 = new CashCard('A', "2", "10/22/20");
		BankAccount BA1 = new BankAccount("bankA1", 40, A1);
		BankAccount BA2 = new BankAccount("bankA2", 40, A2);
		Customer CA1 = new Customer(BA1);
		Customer CA2 = new Customer(BA2);
		BankofA.addCustomer(CA1, "1");
		BankofA.addCustomer(CA2, "2");


		Bank BankofB = new Bank();
		CashCard B1 = new CashCard('B', "1", "10/24/10");
		CashCard B2 = new CashCard('B', "2", "12/25/22");
		BankAccount BB1 = new BankAccount("bankB1", 40, B1);
		BankAccount BB2 = new BankAccount("bankB2", 40, B2);
		Customer CB1 = new Customer(BB1);
		Customer CB2 = new Customer(BB2);
		BankofB.addCustomer(CB1, "1");
		BankofB.addCustomer(CB2, "2");
		
		ATM atm1_A = new ATM(BankofA, "A");
		ATM atm2_A = new ATM(BankofA, "A");
		ATM atm1_B = new ATM(BankofB, "B");
		ATM atm2_B = new ATM(BankofB, "B");
		
		ATM activeATM = null;
		Bank activeBank = null;

		Scanner in = new Scanner(System.in);	
		boolean atmChosen = false;
		
		while(atmChosen == false)
		{
			System.out.println("Please enter your choice of ATM:");
			String bankChoice = in.nextLine();
			
			if (bankChoice.equals("atm1_A"))
			{
				activeATM = atm1_A;
				activeBank = BankofA;
				atmChosen = true;
				System.out.println("You are now using atm1_A.");
			}
			else if(bankChoice.equals("atm2_A"))
			{
				activeATM = atm2_A;
				activeBank = BankofA;
				atmChosen = true;
				System.out.println("You are now using atm2_A.");
			}
			else if(bankChoice.equals("atm1_B"))
			{
				activeATM = atm1_B;
				activeBank = BankofB;
				atmChosen = true;
				System.out.println("You are now using atm1_B.");
			}
			else if(bankChoice.equals("atm2_B"))
			{
				activeATM = atm2_B;
				activeBank = BankofB;
				atmChosen = true;
				System.out.println("You are now using atm2_B.");
			}
			else
			{
				System.out.println("This ATM does not exist. Please try again.");
			}
		}
		
		String accNum = null;
		boolean valid = false;
		while(!valid)
		{
			System.out.println("Please enter your card:");
			String cardInfo = in.nextLine();
			
			String bankType = cardInfo.substring(0, 1);
			String accountNumber = cardInfo.substring(1);
			
			if (activeBank.accountExists(accountNumber))
			{
				CashCard card = activeBank.getCustomer(accountNumber).getBankAccount().getCard();
				String cardDate = card.getExp();
					
				if (activeATM.isValid(bankType, accountNumber, cardDate))
				{
					accNum = accountNumber;
					valid = true;
				}	
			}
			else
			{
				System.out.println("Sorry, this account does not exist. Please try again.");
			}
		}
		
		System.out.print("The card is accepted. ");
		boolean verified = false;
		while(!verified)
		{
			System.out.println("Please enter your password:");
			String password = in.nextLine();
			if(activeATM.verifyPassword(password, accNum))
			{
				verified = true;
			}
			else
			{
				System.out.println("You have entered the wrong password. Please try again.");
			}
			
		}
		
		
		System.out.println("Authorization is accepted. Start your transaction by entering the amount to withdraw:");
		String amount = in.nextLine();
		boolean transacting = true;
		String next;
		while(transacting)
		{
			if(activeATM.checkIfExceeds(Integer.parseInt(amount)))
			{
				System.out.println("This amount exceeds the maximum amount you can withdraw per transaction. Enter  another amount or Quit:");
				amount = in.nextLine();
				
				if(amount.equals("quit"))
				{
					break;
				}
			}
			
			if(!activeATM.checkBalanceEnough(accNum, Integer.parseInt(amount)))
			{
				System.out.println("The amount exceeds the current balance. Enter another amount or quit:");
				amount = in.nextLine();
				
				if(amount.equals("quit"))
				{
					break;
				}
			}
			
			activeATM.Withdraw(accNum, Integer.parseInt(amount));
			System.out.print("$" + amount + " is withdrawn from your account. The remaining balance of this account is $");
			System.out.print(activeBank.getCustomer(accNum).getBankAccount().getBalance() + ".");
			System.out.println(" If you have more transactions, enter the amount or quit.");
			
			amount = in.nextLine();
			
			if(amount.equals("quit"))
			{
				break;
			}
		}
	
		System.out.println("Thank you! Have a nice day!");
		
	}   	

}
