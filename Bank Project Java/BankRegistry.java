 import java.util.ArrayList;

public class BankRegistry {

    // List of every account present in the bank
    private ArrayList<BankAccount> bankAccountList;

    // Constructor
    public BankRegistry() {

        // *1* DECLARING EVERY VARIABLE
        bankAccountList = new ArrayList<>();
        ArrayList<String> accountHolderNames = new ArrayList<>();
        ArrayList<String> accountNumbers = new ArrayList<>();
        ArrayList<Double> initialDepositAmounts = new ArrayList<>();

        // *2* LOADING 10 ACCOUNT DATA
        accountHolderNames.add("Alif Al Sami");         accountNumbers.add("ACC-10001");  initialDepositAmounts.add(2000.00);
        accountHolderNames.add("Tahmina Begum");        accountNumbers.add("ACC-10002");  initialDepositAmounts.add(5000.00);
        accountHolderNames.add("Mahfuzur Rahman");      accountNumbers.add("ACC-10003");  initialDepositAmounts.add(1200.00);
        accountHolderNames.add("Sazzad Hossain");       accountNumbers.add("ACC-10004");  initialDepositAmounts.add(1750.00);
        accountHolderNames.add("Aminul Ehsan");         accountNumbers.add("ACC-10005");  initialDepositAmounts.add(300.00);
        accountHolderNames.add("Zahin Abdullah");       accountNumbers.add("ACC-10006");  initialDepositAmounts.add(349.99);
        accountHolderNames.add("Mirazul Hasan Jim");    accountNumbers.add("ACC-10007");  initialDepositAmounts.add(500.75);
        accountHolderNames.add("Riadus Salehin Ratul"); accountNumbers.add("ACC-10008");  initialDepositAmounts.add(2340.00);
        accountHolderNames.add("Sazid Akhand");         accountNumbers.add("ACC-10009");  initialDepositAmounts.add(5083.50);
        accountHolderNames.add("Kamrul Hasan Maruf");   accountNumbers.add("ACC-100010"); initialDepositAmounts.add(3200.00);

        // *3* LOOPING THROUGH SEED DATA AND CREATING OBJECTS
        int totalEntries = accountHolderNames.size();
        for (int index = 0; index < totalEntries; index++) {
            String currentAccountHolderName = accountHolderNames.get(index);
            String currentAccountNumber = accountNumbers.get(index);
            double currentInitialDepositAmount = initialDepositAmounts.get(index);

            boolean hasInitialDeposit = currentInitialDepositAmount > 0;

            if (hasInitialDeposit) {
                BankAccount newAccount = new BankAccount(currentAccountNumber, currentAccountHolderName, currentInitialDepositAmount);
                bankAccountList.add(newAccount);
            } else {
                BankAccount newAccount = new BankAccount(currentAccountNumber, currentAccountHolderName);
                bankAccountList.add(newAccount);
            }
        }
    }


    // *4* FINDING AN ACCOUNT BY ITS HOLDER NAME
    public BankAccount findAccountByHolderName(String searchHolderName) {
        for (BankAccount currentAccount : bankAccountList) {
            boolean isMatch = currentAccount.getAccountHolderName().equalsIgnoreCase(searchHolderName);
            if (isMatch) {
                return currentAccount;
            }
        }
        return null;
    }


    // *5* FINDING AN ACCOUNT BY ITS ACCOUNT NUMBER
    public BankAccount findAccountByNumber(String searchAccountNumber) {
        for (BankAccount currentAccount : bankAccountList) {
            boolean isMatch = currentAccount.getAccountNumber().equalsIgnoreCase(searchAccountNumber);
            if (isMatch) {
                return currentAccount;
            }
        }
        return null;
    }


    // *6* PRINTS A SUMMARY OF EVERY ACCOUNT IN THE REGISTRY
    public void printAllAccountsSummary() {
        String dividerLine = "+--------------------------------------------------------+";
        System.out.println("\n" + dividerLine);
        System.out.printf(" %-5s %-14s %-22s %-13s %s%n", "#", "Acc No.", "Holder", "Balance", "Tx");
        System.out.println(dividerLine);

        int totalAccounts = bankAccountList.size();
        for (int rowIndex = 0; rowIndex < totalAccounts; rowIndex++) {
            BankAccount currentAccount = bankAccountList.get(rowIndex);

            int displayRowNumber = rowIndex + 1;
            String displayAccountNumber = currentAccount.getAccountNumber();
            String displayHolderName = currentAccount.getAccountHolderName();
            double displayBalance = currentAccount.getCurrentBalance();
            int displayTxCount = currentAccount.getAccountTransactionList().size();

            System.out.printf(" %-5d %-14s %-22s $%-11.2f %d%n",
                    displayRowNumber, displayAccountNumber, displayHolderName, displayBalance, displayTxCount);
        }
        System.out.println(dividerLine);
    }


    // *GETTERS (ENCAPSULATION)*

    public ArrayList<BankAccount> getBankAccountList() {
        return bankAccountList;
    }

    public int getTotalAccountCount() {
        return bankAccountList.size();
    }
}