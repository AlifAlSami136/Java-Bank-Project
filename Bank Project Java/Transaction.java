import java.util.ArrayList;

public class Transaction {

    private String ownerAccountNumber; // which account this deposit belongs to
    private double depositAmount; // amount of the money that was deposited
    private ArrayList<String> depositReasonList; // list of notes describing why

    //*1* CONSTRUCTOR
    public Transaction(String ownerAccountNumber, double depositAmount) {
        this.ownerAccountNumber = ownerAccountNumber;
        this.depositAmount = depositAmount;
        this.depositReasonList = new ArrayList<>();
    }

    //*2* ADD ONE REASON TO DEPOSIT LIST
    public void addDepositReason(String reasonNote) {
        boolean isReasonValid = (reasonNote != null) && (!reasonNote.isBlank());
        if (isReasonValid) {
            String formattedReason = reasonNote.toUpperCase();
            depositReasonList.add(formattedReason);
        }
    }

    //*3* GETTERS
    public String getOwnerAccountNumber() {
        return ownerAccountNumber;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    public ArrayList<String> getDepositReasonList() {
        return depositReasonList;
    }

    //*4* DEFINES WHAT THE OBJECT LOOKS LIKE WHILE PRINTED
    public String toString() {
        String joinedReasons;
        if (depositReasonList.isEmpty()) {
            joinedReasons = "N/A";
        } else {
            joinedReasons = String.join(",", depositReasonList);
        }
        return String.format("Transaction {account=%-10s, amount=$%8.2f reasons=[%s]}",
            ownerAccountNumber, depositAmount, joinedReasons
        );
    }
}