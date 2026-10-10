public class DepositAmountValidationTest {

    private static int failures = 0;

    public static void main(String[] args) {
        assertFalse(".", "lone decimal point must be rejected");
        assertFalse("", "empty string must be rejected");
        assertFalse("12.50.1", "two decimal points must be rejected");
        assertFalse("abc", "letters must be rejected");
        assertFalse("-5", "minus sign must be rejected by the scanner");

        assertTrue("12.50", "normal decimal must be accepted");
        assertTrue("0", "zero is parseable even though deposit later rejects it");
        assertTrue(".5", "leading-dot fraction must be accepted");
        assertTrue("5.", "trailing-dot whole number must be accepted");

        BankAccount account = new BankAccount("ACC-TEST", "Test Holder", 100.00);
        if (account.deposit(0.0)) {
            fail("zero deposit must not succeed");
        }
        if (account.getAccountCurrentBalance() != 100.00) {
            fail("zero deposit must not change the balance");
        }
        if (!account.deposit(25.00)) {
            fail("positive deposit must succeed");
        }
        if (account.getAccountCurrentBalance() != 125.00) {
            fail("positive deposit must increase the balance");
        }

        if (failures == 0) {
            System.out.println("DepositAmountValidationTest: all checks passed");
        } else {
            System.out.println("DepositAmountValidationTest: " + failures + " check(s) failed");
            System.exit(1);
        }
    }

    private static void assertTrue(String input, String message) {
        if (!Main.isValidDepositAmountText(input)) {
            fail(message + " [input=" + input + "]");
        }
    }

    private static void assertFalse(String input, String message) {
        if (Main.isValidDepositAmountText(input)) {
            fail(message + " [input=" + input + "]");
        }
    }

    private static void fail(String message) {
        failures++;
        System.out.println("FAIL: " + message);
    }
}
