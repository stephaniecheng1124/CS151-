public class ATM 
{
    public static final String DATE = "06/24/16";
    Bank bank;
    public static final int MAX = 50;
    public int withdrawn;
    public String bankIdentity;

    /**
     * Constructs a new ATM object
     * @param bankAorB the Bank object that the ATM is connected to
     * @param identity letter of the bank it belongs to
     */
    public ATM(Bank bankAorB, String identity) 
    {
        bank = bankAorB;
        withdrawn = 0;
        bankIdentity = identity;
    }

    /**
     * Checks whether or not a bankID is valid
     * @param identity letter of bank current ATM belongs to
     * @param cardNum the bank number associated with the card
     * @param cardDate the expiration date of the card
     * @return true if valid, false if invalid
     */
    public boolean isValid(String identity, String cardNum, String cardDate)
    {
        if(!identity.equals(bankIdentity))
        {
            System.out.println("Sorry, this card is not supported by this ATM.");
            return false;
        }
        else if(isExpired(DATE, cardDate))
        {
            System.out.println("Sorry, this card is expired and will be returned to you.");
            return false;
        }

        return true;
    }

    /**
     * Checks to see if the card is expired
     * @param today today's date
     * @param cardDate expiration date of the card
     * @return true if expired, false if not expired
     */
    public boolean isExpired(String today, String cardDate)
    {
        String[] todayParts = today.split("[/]");
        int todayDate = Integer.parseInt(todayParts[2]+todayParts[1]+todayParts[0]);

        String[] cardDateParts = cardDate.split("[/]");
        int theCardDate = Integer.parseInt(cardDateParts[2]+cardDateParts[1]+cardDateParts[0]);

        if(todayDate > theCardDate)
        {
            //System.out.println(todayDate + ">" + theCardDate);
            return true;
        }
        return false;
    }

    /**
     * Checks whether or not the password is correct
     * @param password the password that the user enters
     * @param accountNum the account number associated with the cash card
     * @return true id password is correct, false if wrong password
     */
    public boolean verifyPassword(String password, String accountNum)
    {
        //System.out.println("Pass you entered:" + password + "ActualPass:" + bank.getCustomer(accountNum).getBankAccount().getPasscode());
        return bank.getCustomer(accountNum).getBankAccount().getPasscode().equals(password);
    }

    /**
     * Checks if the maximum withdrawal amount per transaction will be exceeded
     * @param withdrawAmt the amount the user is planning to withdraw
     * @return true if the max is exceeded, false if not exceeded
     */
    public boolean checkIfExceeds(int withdrawAmt)
    {
        if(withdrawAmt + withdrawn > MAX)
        {
            return true;
        }

        return false;
    }

    /**
     * Checks to see if there is enough money in the account to withdraw
     * @param accountNum the account number associated with the card
     * @param withdrawAmt the amount the user is planning to withdraw
     * @return true if there is enough, false if there is not enough money for the withdrawal to be made
     */
    public boolean checkBalanceEnough(String accountNum, int withdrawAmt)
    {

        if((bank.getCustomer(accountNum).getBankAccount().getBalance()) - withdrawAmt < 0)
        {
            return false;
        }

        return true;
    }

    /**
     * Withdraws the amount of money
     * @param accountNum the account number associated with the card
     * @param amount the amount the user is planning to withdraw
     */
    public void Withdraw(String accountNum, int amount)
    {
        bank.getCustomer(accountNum).getBankAccount().updateBalance(amount);
        withdrawn += amount;
        bank.getCustomer(accountNum).getBankAccount().log("You withdrew $" + amount);
    }

}
