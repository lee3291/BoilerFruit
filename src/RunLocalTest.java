import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * A framework to run public test cases.
 *
 * <p>Purdue University -- CS18000 -- Fall 2022</p>
 *
 * @author Purdue CS
 * @version August 21, 2022
 */
public class RunLocalTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        System.out.printf("Test Count: %d.\n", result.getRunCount());
        if (result.wasSuccessful()) {
            System.out.print("Excellent - all local tests ran successfully.\n");
        } else {
            System.out.printf("Tests failed: %d.\n", result.getFailureCount());
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    /**
     * A set of public test cases.
     *
     * <p>Purdue University -- CS18000 -- Fall 2022</p>
     *
     * @author Purdue CS
     * @version August 21, 2022
     */
    public static class TestCase {
        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayOutputStream testOut;

        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalSysin);
            System.setOut(originalOutput);
        }

        private String getOutput() {
            return testOut.toString();
        }

        @SuppressWarnings("SameParameterValue")
        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        }

        @Test(timeout = 1000)
        public void testOne() {
            // Set the input
            //test case is if they sign up as customer
            String input = "1" + System.lineSeparator() + "1" + System.lineSeparator() + "yourmom@email.com" +
                    System.lineSeparator() + "yourmomiscool" + System.lineSeparator() + "Cool@123" +
                    System.lineSeparator() + "0" + System.lineSeparator() + "0" + System.lineSeparator();

            // Expected result
            String expected = """
                    Welcome to Marketplace!
                    Would you like to...
                    \t0.Quit
                    \t1.Sign up
                    \t2.Log in
                    ----------Signing up----------
                    Would you like to...
                    \t0.Go back (log in)
                    \t1.Sign up customer
                    \t2.Sign up seller
                    Enter your email:
                    Create a username:
                    Username successfully created!
                    Create a password:
                    \tPassword must have:
                    \t\tMinimum 5
                    \t\tMaximum 15 characters
                    \t\tAt least one uppercase letter
                    \t\tAt least one lowercase letter
                    \t\tAt least one number and one special character
                    Password successfully created!
                    Account successfully created.
                    Welcome, yourmomiscool!
                    ----------Main Menu----------
                    Do you want to...
                    \t0. Logout
                    \t1. Search by stores
                    \t2. Search by products
                    \t3. Sort products
                    \t4. View Shopping Cart
                    \t5. View or Export purchase history
                    \t6. View Mail Page
                    \t7. View dashboard
                    \t8. Edit profile
                    Logging out...
                    Would you like to...
                    \t0.Quit
                    \t1.Sign up
                    \t2.Log in
                    Good bye!
                    """;

            // Runs the program with the input values
            receiveInput(input);
            Marketplace.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            output = output.replace("\r\n", "\n");
            assertEquals("Error detected, ensure that your results are correct!",
                    expected.trim(), output.trim());
        }
    }
}