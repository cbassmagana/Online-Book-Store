package ui;

import model.Book;
import model.Branch;
import model.Company;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

// Interface application for the book store company
public class BookStoreApp {
    private Scanner input;

    public BookStoreApp() {
        runSite();
    }

    public void runSite() {
        boolean keepGoing = true;
        String command = null;

        keepGoing = initialize();

        while (keepGoing) {
            displayMenu();
            command = input.next();

            if (command.equals("0")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    public boolean initialize() {
        boolean keepInitializing = true;

        while (keepInitializing) {
            displayInitialize();
            String command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepInitializing = false;
                return false;
            } else if (command.equals("i")) {
                processCommand(command);
                keepInitializing = false;
                return true;
            } else {
                System.out.println("Selection not valid...");
            }
        }
        return true;
    }

    private void displayInitialize() {
        System.out.println("\tPlease press i in order to initialize your company.");
        System.out.println("\tOtherwise, press q to quit.");
    }

    private void displayMenu() {
        System.out.println("\nPlease select the function that you would like to complete:");
        System.out.println("\t1 - Open a new branch");
        System.out.println("\t2 - View all the company's branches");
        System.out.println("\t3 - Add a book to a branch's inventory");
        System.out.println("\t4 - View the inventory");
        System.out.println("\t5 - Remove a book from the inventory");
        System.out.println("\t6 - Sell a book");
        System.out.println("\t7 - View all of the books sold");
        System.out.println("\t8 - Change the reservation status of a book");
        System.out.println("\t9 - Change the price of a book");
        System.out.println("");
        System.out.println("\t0 - quit the application");
    }

    private void processCommand(String command) {
        if (command.equals("i")) {
            makeCompany();
        } else if (command.equals("1")) {
            openBranch();
        } else if (command.equals("2")) {
            viewBranches();
        } else if (command.equals("3")) {
            addABook();
        } else if (command.equals("4")) {
            viewInventory();
        } else if (command.equals("5")) {
            removeABook();
        } else if (command.equals("6")) {
            sellABook();
        } else if (command.equals("7")) {
            viewBooksSold();
        } else if (command.equals("8")) {
            changeReservation();
        } else if (command.equals("9")) {
            changePrice();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    public void makeCompany() {

    }

    public void openBranch() {

    }

    public void viewBranches() {

    }

    public void addABook() {

    }

    public void viewInventory() {

    }

    public void removeABook() {

    }

    public void sellABook() {

    }

    public void viewBooksSold() {

    }

    public void changeReservation() {

    }

    public void changePrice() {

    }
}






//// Bank teller application
//public class TellerApp {
//    private Account cheq;
//    private Account sav;
//    private Scanner input;
//
//    // EFFECTS: runs the teller application
//    public TellerApp() {
//        runTeller();
//    }
//
//    // MODIFIES: this
//    // EFFECTS: processes user input
//    private void runTeller() {
//        boolean keepGoing = true;
//        String command = null;
//
//        init();
//
//        while (keepGoing) {
//            displayMenu();
//            command = input.next();
//            command = command.toLowerCase();
//
//            if (command.equals("q")) {
//                keepGoing = false;
//            } else {
//                processCommand(command);
//            }
//        }
//
//        System.out.println("\nGoodbye!");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: processes user command
//    private void processCommand(String command) {
//        if (command.equals("d")) {
//            doDeposit();
//        } else if (command.equals("w")) {
//            doWithdrawal();
//        } else if (command.equals("t")) {
//            doTransfer();
//        } else {
//            System.out.println("Selection not valid...");
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: initializes accounts
//    private void init() {
//        cheq = new Account("Joe", 145.00);
//        sav = new Account("Joe", 256.50);
//        input = new Scanner(System.in);
//        input.useDelimiter("\n");
//    }
//
//    // EFFECTS: displays menu of options to user
//    private void displayMenu() {
//        System.out.println("\nSelect from:");
//        System.out.println("\td -> deposit");
//        System.out.println("\tw -> withdraw");
//        System.out.println("\tt -> transfer");
//        System.out.println("\tq -> quit");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: conducts a deposit transaction
//    private void doDeposit() {
//        Account selected = selectAccount();
//        System.out.print("Enter amount to deposit: $");
//        double amount = input.nextDouble();
//
//        if (amount >= 0.0) {
//            selected.deposit(amount);
//        } else {
//            System.out.println("Cannot deposit negative amount...\n");
//        }
//
//        printBalance(selected);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: conducts a withdraw transaction
//    private void doWithdrawal() {
//        Account selected = selectAccount();
//        System.out.print("Enter amount to withdraw: $");
//        double amount = input.nextDouble();
//
//        if (amount < 0.0) {
//            System.out.println("Cannot withdraw negative amount...\n");
//        } else if (selected.getBalance() < amount) {
//            System.out.println("Insufficient balance on account...\n");
//        } else {
//            selected.withdraw(amount);
//        }
//
//        printBalance(selected);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: conducts a transfer transaction
//    private void doTransfer() {
//        System.out.println("\nTransfer from?");
//        Account source = selectAccount();
//        System.out.println("Transfer to?");
//        Account destination = selectAccount();
//
//        System.out.print("Enter amount to transfer: $");
//        double amount = input.nextDouble();
//
//        if (amount < 0.0) {
//            System.out.println("Cannot transfer negative amount...\n");
//        } else if (source.getBalance() < amount) {
//            System.out.println("Insufficient balance on source account...\n");
//        } else {
//            source.withdraw(amount);
//            destination.deposit(amount);
//        }
//
//        System.out.print("Source ");
//        printBalance(source);
//        System.out.print("Destination ");
//        printBalance(destination);
//    }
//
//    // EFFECTS: prompts user to select chequing or savings account and returns it
//    private Account selectAccount() {
//        String selection = "";  // force entry into loop
//
//        while (!(selection.equals("c") || selection.equals("s"))) {
//            System.out.println("c for chequing");
//            System.out.println("s for savings");
//            selection = input.next();
//            selection = selection.toLowerCase();
//        }
//
//        if (selection.equals("c")) {
//            return cheq;
//        } else {
//            return sav;
//        }
//    }
//
//    // EFFECTS: prints balance of account to the screen
//    private void printBalance(Account selected) {
//        System.out.printf("Balance: $%.2f\n", selected.getBalance());
//    }
//}
