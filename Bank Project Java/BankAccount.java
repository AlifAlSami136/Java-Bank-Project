import java.util.ArrayList;

public class BankAccount {

    //Static fields
    public static String bankNameStatic = "MyFirstBank";
    public static int totalAccountsCreatedStatic = 0;

    //Instance fields
    private final String accountNumber;
    private String accountHolderName;
    private double currentBalance;
    private ArrayList<Transaction> accountDepositTransactionList;

    //Constructor A : Basic signup with no initial deposit
    public BankAccount(String accountNumber, String accountHolderName) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.currentBalance = 0.0;
        this.accountDepositTransactionList = new ArrayList<>();
        totalAccountsCreatedStatic++;
    }

    //Constructor B : signup with initial deposit
    public BankAccount(String accountNumber, String accountHolderName, double accountInitialDepositAmount) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.accountDepositTransactionList = new ArrayList<>();

        boolean isAccountInitialDepositValid = accountInitialDepositAmount > 0;
        if (isAccountInitialDepositValid) {
            this.currentBalance = accountInitialDepositAmount;

            Transaction openingDepositTransaction = new Transaction(accountNumber, accountInitialDepositAmount);
            openingDepositTransaction.addDepositReason("OPENING DEPOSIT");
            accountDepositTransactionList.add(openingDepositTransaction);
        } else {
            this.currentBalance = 0.0;
        }
        totalAccountsCreatedStatic++;
    }

    //Return a formatted summary box of this account
    public String getAccountInfo() {
        int accountTotalTransactionCount = accountDepositTransactionList.size();
        return String.format(
            "┌-----------------------------------------------------┐%n" +
            "│  Bank       : %-26s│%n" +
            "│  Acc No.    : %-26s│%n" +
            "│  Holder     : %-26s│%n" +
            "│  Balance    : $%-25.2f│%n" +
            "│  Tx Count   : %-26d│%n" +
            "└--------------------------------------------------┘",
            bankNameStatic,
            accountNumber,
            accountHolderName,
            currentBalance,
            accountTotalTransactionCount
        );
    }

    //Overload A : Deposits with no reasons given
    public boolean deposit(double incomingDepositAmount) {
        boolean isIncomingDepositAmountValid = incomingDepositAmount > 0;
        if (isIncomingDepositAmountValid) {
            currentBalance += incomingDepositAmount;

            Transaction newDepositTransaction = new Transaction(accountNumber, incomingDepositAmount);
            accountDepositTransactionList.add(newDepositTransaction);
            return true;
        }
        return false;
    }

    //Overload B : Deposits with reasons given
    public boolean deposit(double incomingDepositAmount, ArrayList<String> depositReasonList) {
        boolean isIncomingDepositAmountValid = incomingDepositAmount > 0;
        if (isIncomingDepositAmountValid) {
            currentBalance += incomingDepositAmount;

            Transaction newDepositTransaction = new Transaction(accountNumber, incomingDepositAmount);
            for (String singleReason : depositReasonList) {
                newDepositTransaction.addDepositReason(singleReason);
            }
            accountDepositTransactionList.add(newDepositTransaction);
            return true;
        }
        return false;
    }

    // *GETTERS (ENCAPSULATION)*

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public ArrayList<Transaction> getAccountTransactionList() {
        return accountDepositTransactionList;
    }

    public ArrayList<Transaction> getAccountDepositTransactionList() {
        return accountDepositTransactionList;
    }

    public String getAccountHolderFullName() {
        return accountHolderName;
    }

    public double getAccountCurrentBalance() {
        return currentBalance;
    }
}