import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) {

        //  *0* DECLARING ALL THE NECESSARY VARIABLES FIRST

        Scanner userKeyboardInputScanner = new Scanner(System.in);

        BankRegistry mainBankRegistry = null;
        ArrayList<Transaction> currentSessionTransactionLog = new ArrayList<>();

        boolean isMainMenuProgramRunning = true;
        String userEnteredMenuChoice = "";

        String userEnteredAccountNumber;
        String userEnteredDepositAmountAsText; // N.B — Scanner can only return String
        double userEnteredDepositAmountAsDouble; // converted from text after input
        String userEnteredSingleReasonNote;
        ArrayList<String> userEnteredDepositReasonList;

        BankAccount searchResultFoundAccount;

        // CREATING DIVIDERS TO USE AS SHORTCUTS IN PRINTLN METHOD
        String menuBorderDividerLine    = "+------------------------------------------------+";
        String statsSectionDividerLine  = "--------------------------------------------------";
        String welcomeBannerDividerLine = "=======================================================";

        //  *1* GREETING THE USER (static field read before any object exists)

        System.out.println();
        System.out.println(welcomeBannerDividerLine);
        System.out.println("  Welcome to " + BankAccount.bankNameStatic);
        System.out.println(welcomeBannerDividerLine);

        //  *2* CREATING THE REGISTRY (LOADS THE 10 ACCOUNTS)

        mainBankRegistry = new BankRegistry();

        System.out.println("\n" + mainBankRegistry.getTotalAccountCount() + " accounts loaded.");
        System.out.println("Total accounts created so far: " + BankAccount.totalAccountsCreatedStatic);

        //  *3* MAIN MENU LOOP

        while (isMainMenuProgramRunning) {

            System.out.println();
            System.out.println(menuBorderDividerLine);
            System.out.println("|  " + BankAccount.bankNameStatic + " -- Main Menu          |");
            System.out.println(menuBorderDividerLine);
            System.out.println("|  1. List All Accounts          |");
            System.out.println("|  2. View Account Details       |");
            System.out.println("|  3. Make A Deposit             |");
            System.out.println("|  4. Session Transaction Log    |");
            System.out.println("|  5. System Statistics          |");
            System.out.println("|  0. Exit                       |");
            System.out.println(menuBorderDividerLine);
            System.out.print("Please choose an option: ");

            userEnteredMenuChoice = userKeyboardInputScanner.nextLine().trim();

            switch (userEnteredMenuChoice) {

                // ── OPTION 1: list all accounts ───
                case "1":
                    mainBankRegistry.printAllAccountsSummary();
                    break;

                // ── OPTION 2: view account details ───
                case "2":
                    System.out.println("\n Enter Account Number (e.g. ACC-10001)");
                    userEnteredAccountNumber = userKeyboardInputScanner.nextLine().trim();

                    searchResultFoundAccount = mainBankRegistry.findAccountByNumber(userEnteredAccountNumber);

                    if (searchResultFoundAccount == null) {
                        System.out.println("Account Not Found.");
                    } else {
                        System.out.println();
                        System.out.println(searchResultFoundAccount.getAccountInfo());

                        ArrayList<Transaction> foundAccountTransactionHistory = searchResultFoundAccount.getAccountDepositTransactionList();
                        int foundAccountTransactionHistorySize = foundAccountTransactionHistory.size();

                        System.out.println("\n Transaction History (" + foundAccountTransactionHistorySize + " entries):");

                        if (foundAccountTransactionHistorySize == 0) {
                            System.out.println("No Transactions Yet");
                        } else {
                            int transactionHistoryDisplayRowNumber = 1;
                            for (Transaction singleTransactionHistoryEntry : foundAccountTransactionHistory) {
                                System.out.println("    " + transactionHistoryDisplayRowNumber + " . " + singleTransactionHistoryEntry);
                                transactionHistoryDisplayRowNumber++;
                            }
                        }
                    }
                    break;

                // ── OPTION 3: make a deposit ───
                case "3":
                    System.out.println("\n Enter Account Number:");
                    userEnteredAccountNumber = userKeyboardInputScanner.nextLine().trim();

                    searchResultFoundAccount = mainBankRegistry.findAccountByNumber(userEnteredAccountNumber);

                    if (searchResultFoundAccount == null) {
                        System.out.println("Account Not Found.");
                    } else {
                        System.out.println(" Deposit Amount ($) : ");
                        userEnteredDepositAmountAsText = userKeyboardInputScanner.nextLine().trim();

                        boolean isUserEnteredAmountParsableAsDouble = isValidDepositAmountText(userEnteredDepositAmountAsText);

                        if (isUserEnteredAmountParsableAsDouble) {
                            userEnteredDepositAmountAsDouble = Double.parseDouble(userEnteredDepositAmountAsText);
                        } else {
                            userEnteredDepositAmountAsDouble = 0.0;
                        }

                        if (!isUserEnteredAmountParsableAsDouble) {
                            System.out.println("Ivalid amount entered");
                        } else {

                            userEnteredDepositReasonList = new ArrayList<>();

                            System.out.println("Enter deposit Reasons");
                            System.out.println("Press enter when your're done.");

                            boolean isStillCollectingUserReasonNotes = true;
                            while (isStillCollectingUserReasonNotes) {
                                System.out.println("Reason:");
                                userEnteredSingleReasonNote = userKeyboardInputScanner.nextLine().trim();

                                if (userEnteredSingleReasonNote.isEmpty()) {
                                    isStillCollectingUserReasonNotes = false;
                                } else {
                                    userEnteredDepositReasonList.add(userEnteredSingleReasonNote);
                                }
                            }

                            boolean wereAnyDepositReasonsGivenByUser = !userEnteredDepositReasonList.isEmpty();
                            boolean didDepositSucceed;
                            if (wereAnyDepositReasonsGivenByUser) {
                                didDepositSucceed = searchResultFoundAccount.deposit(userEnteredDepositAmountAsDouble, userEnteredDepositReasonList);
                            } else {
                                didDepositSucceed = searchResultFoundAccount.deposit(userEnteredDepositAmountAsDouble);
                            }

                            if (didDepositSucceed) {
                                Transaction newlyLoggedSessionTransaction = new Transaction(userEnteredAccountNumber, userEnteredDepositAmountAsDouble);
                                for (String singleSessionReasonNote : userEnteredDepositReasonList) {
                                    newlyLoggedSessionTransaction.addDepositReason(singleSessionReasonNote);
                                }
                                currentSessionTransactionLog.add(newlyLoggedSessionTransaction);
                            } else {
                                System.out.println("Amount must be greater than 0.");
                            }
                        }
                    }
                    break;

                // ── OPTION 4: session transaction log ───
                case "4":
                    int currentSessionTransactionLogSize = currentSessionTransactionLog.size();
                    System.out.println("\n  Session transaction log (" + currentSessionTransactionLogSize + " entries):");

                    if (currentSessionTransactionLogSize == 0) {
                        System.out.println("  (no deposits made this session)");
                    } else {
                        int sessionLogDisplayRowNumber = 1;
                        for (Transaction singleSessionTransactionLogEntry : currentSessionTransactionLog) {
                            System.out.println("  " + sessionLogDisplayRowNumber + ". " + singleSessionTransactionLogEntry);
                            sessionLogDisplayRowNumber++;
                        }
                    }
                    break;

                // ── OPTION 5: system statistics ───
                case "5":
                    ArrayList<BankAccount> allRegisteredBankAccounts = mainBankRegistry.getBankAccountList();

                    double allAccountsCombinedBalanceTotal     = 0.0;
                    double highestAccountBalanceFound          = Double.MIN_VALUE;
                    String accountHolderNameWithHighestBalance = "";

                    for (BankAccount currentlyIteratedBankAccount : allRegisteredBankAccounts) {
                        double currentlyIteratedAccountBalance = currentlyIteratedBankAccount.getAccountCurrentBalance();

                        allAccountsCombinedBalanceTotal += currentlyIteratedAccountBalance;

                        boolean isCurrentBalanceHigherThanHighestFound = currentlyIteratedAccountBalance > highestAccountBalanceFound;
                        if (isCurrentBalanceHigherThanHighestFound) {
                            highestAccountBalanceFound          = currentlyIteratedAccountBalance;
                            accountHolderNameWithHighestBalance = currentlyIteratedBankAccount.getAccountHolderFullName();
                        }
                    }

                    System.out.println();
                    System.out.println(statsSectionDividerLine);
                    System.out.printf("  Bank             : %s%n",         BankAccount.bankNameStatic);
                    System.out.printf("  Total accounts   : %d%n",         BankAccount.totalAccountsCreatedStatic);
                    System.out.printf("  Combined balance : $%.2f%n",      allAccountsCombinedBalanceTotal);
                    System.out.printf("  Highest balance  : $%.2f (%s)%n", highestAccountBalanceFound, accountHolderNameWithHighestBalance);
                    System.out.println(statsSectionDividerLine);
                    break;

                // ── OPTION 0: exit ─────
                case "0":
                    System.out.println("\n  Goodbye. Session ended.");
                    System.out.println("  Final total account count: " + BankAccount.totalAccountsCreatedStatic);
                    isMainMenuProgramRunning = false;
                    break;

                // ── INVALID INPUT ──────
                default:
                    System.out.println("  Invalid option. Please choose a number from the menu.");
            }
        }

        //  *4* CLEAN UP

        userKeyboardInputScanner.close();
    }

    // Accepts non-negative decimals that Double.parseDouble can actually parse.
    // A lone "." used to pass the digit/dot scan and then crash on parse.
    static boolean isValidDepositAmountText(String amountText) {
        if (amountText == null || amountText.isEmpty()) {
            return false;
        }

        boolean decimalPointAlreadySeen = false;
        boolean digitAlreadySeen = false;

        for (int i = 0; i < amountText.length(); i++) {
            char currentCharacter = amountText.charAt(i);

            if (currentCharacter == '.') {
                if (decimalPointAlreadySeen) {
                    return false;
                }
                decimalPointAlreadySeen = true;
            } else if (Character.isDigit(currentCharacter)) {
                digitAlreadySeen = true;
            } else {
                return false;
            }
        }

        return digitAlreadySeen;
    }
}