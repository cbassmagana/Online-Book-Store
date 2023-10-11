package ui;

import model.Book;
import model.Branch;
import model.Company;

import java.util.Scanner;

// Interface application for a book store company
public class BookStoreApp {
    private Company company;
    private Scanner input;

    // EFFECTS: runs the book store application
    public BookStoreApp() {
        runSite();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runSite() {
        boolean keepGoing = true;

        keepGoing = initialize();

        while (keepGoing) {
            displayMenu();
            String command = input.next();

            if (command.equals("0")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nHave a good day!");
    }

    // MODIFIES: this
    // EFFECTS: initializes the company and the input scanner
    private boolean initialize() {
        while (true) {
            input = new Scanner(System.in);
            input.useDelimiter("\n");

            displayInitialize();
            String command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
//                System.exit(0);
                return false;
            } else if (command.equals("i")) {
                makeCompany();
                return true;
            } else {
                System.out.println("Selection not valid, please try again.");
                System.out.println("");
            }
        }
    }

    // EFFECTS: prints the initializing options to the terminal
    private void displayInitialize() {
        System.out.println("\tPress i to initialize your company.");
        System.out.println("\tOtherwise, press q to quit.");
        System.out.println("");
    }

    // EFFECTS: prints the application's functional options to the terminal
    private void displayMenu() {
        System.out.println("\nPlease select the function that you would like to complete:");
        System.out.println("");
        System.out.println("\t1 - Open a new branch");
        System.out.println("\t2 - View all the company's branches");
        System.out.println("\t3 - Add a book to a branch's inventory");
        System.out.println("\t4 - View the inventory");
        System.out.println("\t5 - Remove a book from the inventory");
        System.out.println("\t6 - Sell a book");
        System.out.println("\t7 - View all of the books sold");
        System.out.println("\t8 - Change the reservation status of a book");
        System.out.println("\t9 - Change the price of a book");
        System.out.println("\t10 - Rate a book");
        System.out.println("\t0 - quit the application");
        System.out.println("");
    }

    // EFFECTS: decides what method to call based on what function the user wants to do
    private void processCommand(String command) {
        switch (command) {
            case "1":
                openBranch();
                break;
            case "2":
                viewBranches();
                break;
            case "3":
                addABook();
                break;
            case "4":
                viewInventory();
                break;
            case "5":
                removeABook();
                break;
            case "6":
                sellABook();
                break;
            case "7":
                viewBooksSold();
                break;
            case "8":
                changeReservation();
                break;
            case "9":
                changePrice();
                break;
            case "10":
                rateBook();
                break;
            default:
                System.out.println("Selection not valid...");
                System.out.println("");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new company with a name of the user's choosing
    private void makeCompany() {
        System.out.println("\nTo initialize your company, please enter the business' name:");
        String companyName = input.next();
        company = new Company(companyName);
        System.out.println("\nYour company " + companyName + " has now been created!");
        System.out.println("");
    }

    // MODIFIES: this
    // EFFECTS: creates a new branch of the company with user's choice of name and address
    private void openBranch() {
        System.out.println("\nEnter the name of this new branch:");
        String name = input.next();
        System.out.println("\nEnter the address of this new branch:");
        String address = input.next();
        Branch branch = new Branch(name, address);
        company.addBranch(branch);
        System.out.println("\nYour new branch " + branch.getName() + " is now open!");
        System.out.println("");
    }

    // EFFECTS: prints to the terminal the name of all branches in the company
    private void viewBranches() {
        System.out.println("\nThe names of all company branches are:");
        for (Branch br : company.getBranches()) {
            System.out.println(br.getName());
        }
        System.out.println("");
    }

    // MODIFIES: this
    // EFFECTS: creates a new book with user's choice of title, author and price and
    //          adds it to the inventory of user's choice of company branch
    private void addABook() {
        System.out.println("\nWhich of the following branches would you like to add a book to:");
        System.out.println("");
        Branch branch = selectBranch();

        System.out.println("\nWhat is the title of the book: ");
        String title = input.next();
        System.out.println("\nWho is the author of the book: ");
        String author = input.next();
        System.out.println("\nTo 2 decimals, what is the price of the book: ");
        double price = input.nextDouble();

        branch.addBook(new Book(title, author, price));
        System.out.println("\nThe book " + title + " has now been added!");
    }

    // MODIFIES: this
    // EFFECTS: prints to the terminal the name of all books in the inventory of company branch of
    //          user's choice or of the entire company's inventory
    private void viewInventory() {
        System.out.println("\nTo view the inventory for the entire company, enter 0: ");
        System.out.println("\nTo view the inventory for a specific branch, enter 1: ");
        int location = input.nextInt();

        if (location == 0) {
            System.out.println("\nThe company's inventory contains the following books: ");
            for (Book b : company.getInventory()) {
                System.out.println(b.getTitle());
            }
            System.out.println("\nFor a total inventory value of: " + company.getInventoryValue() + " dollars.");

        } else if (location == 1) {
            System.out.println("\nWhich branches' inventory would you like to view:");
            System.out.println("");
            Branch branch = selectBranch();
            System.out.println("\nThis branch's inventory contains the following books: ");

            for (Book b : branch.getInventory()) {
                System.out.println(b.getTitle());
            }
            System.out.println("\nFor a total inventory value of: " + branch.getInventoryValue() + " dollars.");
        }
        System.out.println("");
    }

    // MODIFIES: this
    // EFFECTS: removes user's choice of book from inventory of user's choice of company branch
    private void removeABook() {
        System.out.println("\nWhich of the following branches would you like to remove a book from:");
        System.out.println("");
        Branch branch = selectBranch();

        System.out.println("\nWhich of the following books would you like to remove:");
        System.out.println("");
        Book book = selectBook(branch);
        branch.removeBook(book);
        System.out.println("\nThe book " + book.getTitle() + " has now been removed!");
        System.out.println("");
    }

    // MODIFIES: this
    // EFFECTS: moves user's choice of book from inventory of user's choice of company branch into list of
    //          books sold for the same company branch
    private void sellABook() {
        System.out.println("\nWhich of the following branches would you like to sell a book from:");
        System.out.println("");
        Branch branch = selectBranch();

        System.out.println("\nWhich of the following books would you like to sell:");
        System.out.println("");
        Book book = selectBook(branch);

        if (book.getReservedStatus()) {
            System.out.println("This book is reserved, change the reservation status to continue.");
        } else {
            branch.sellBook(book);
            System.out.println("\nThe book " + book.getTitle() + " has now been sold!");
        }
        System.out.println("");
    }

    // MODIFIES: this
    // EFFECTS: prints to the terminal the name of all books sold from the company branch of
    //          user's choice or from the entire company
    private void viewBooksSold() {
        System.out.println("\nTo view the books sold for the entire company, enter 0: ");
        System.out.println("\nTo view the books sold at a specific branch, enter 1: ");
        int location = input.nextInt();

        if (location == 0) {
            System.out.println("\nThe company has sold the following books: ");
            for (Book b : company.getBooksSold()) {
                System.out.println(b.getTitle());
            }
            System.out.println("\nFor a total revenue of: " + company.getCashValue() + " dollars.");

        } else if (location == 1) {
            System.out.println("\nWhich branch would you like to view:");
            System.out.println("");
            Branch branch = selectBranch();
            System.out.println("\nThis branch has sold the following books: ");

            for (Book b : branch.getBooksSold()) {
                System.out.println(b.getTitle());
            }
            System.out.println("\nFor a total revenue of: " + branch.getCashValue() + " dollars.");
        }
        System.out.println("");
    }

    // MODIFIES: this
    // EFFECTS: changes the reservation status of user's choice of book in the company to opposite of
    //          the previous reservation status
    private void changeReservation() {
        System.out.println("\nAt which branch would you like to change a book's reservation status:");
        System.out.println("");
        Branch branch = selectBranch();

        System.out.println("\nWhich book would you like to change the reservation status of:");
        System.out.println("");
        Book book = selectBook(branch);

        boolean newStatus = !(book.getReservedStatus());
        book.setReservedStatus(newStatus);
        System.out.println("");

        if (newStatus) {
            System.out.println("The book " + book.getTitle() + " has now been reserved!");
        } else {
            System.out.println("The book " + book.getTitle() + " is no longer reserved!");
        }
        System.out.println("");
    }

    // MODIFIES: this
    // EFFECTS: changes the price of user's choice of book in the company user's choice of new price
    private void changePrice() {
        System.out.println("\nAt which branch would you like to change a book's price:");
        System.out.println("");
        Branch branch = selectBranch();

        System.out.println("\nWhich book would you like to change the price of:");
        System.out.println("");
        Book book = selectBook(branch);

        System.out.println("\nThe price is currently " + book.getPrice() + " dollars, enter the new price:");
        double newPrice = input.nextDouble();
        book.setPrice(newPrice);
        System.out.println("\nThe book's price is now " + newPrice + " dollars.");
        System.out.println("");
    }

    // MODIFIES: this
    // EFFECTS: adds a rating of user's choice to list of ratings of user's choice of book in the company
    //          and prints to terminal the new average rating of the book
    private void rateBook() {
        System.out.println("\nAt which branch would you like to rate a book:");
        System.out.println("");
        Branch branch = selectBranch();

        System.out.println("\nWhich book would you like to rate:");
        System.out.println("");
        Book book = selectBook(branch);

        System.out.println("\nWhat would you like to rate the book from 0 to 5 stars:");
        int rating = input.nextInt();
        book.rateBook(rating);
        System.out.println("\nThe book's average rating is now " + book.getAverageRating() + " stars.");
        System.out.println("");
    }

    // MODIFIES: this
    // EFFECTS: returns the branch of the user's choosing
    private Branch selectBranch() {
        for (int i = 0; i < company.getBranches().size(); i++) {
            System.out.println(i + " - " + company.getBranches().get(i).getName());
        }
        System.out.println("\nEnter the number corresponding to the branch here: ");
        int branchIndex = input.nextInt();
        return company.getBranches().get(branchIndex);
    }

    // MODIFIES: this
    // EFFECTS: returns the book of the user's choosing in a given branch's inventory
    private Book selectBook(Branch branch) {
        for (int i = 0; i < branch.getInventory().size(); i++) {
            System.out.println(i + " - " + branch.getInventory().get(i).getTitle());
        }
        System.out.println("\nEnter the number corresponding to the book here: ");
        int bookIndex = input.nextInt();
        return branch.getInventory().get(bookIndex);
    }
}