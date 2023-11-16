package ui;

import model.Book;
import model.Branch;
import model.Company;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.lang.Math;
import java.util.ArrayList;

// Interface application for a book store company
public class BookStoreApp extends JFrame {
    private static final String JSON_STORE = "./data/company.json";
    private Company company;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    // EFFECTS: runs the book store application and instantiates JSON
    public BookStoreApp() {
        super("Book Store Manager");
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runSite();
    }

    // MODIFIES: this
    // EFFECTS: processes user input until user quits the application
    private void runSite() {
        boolean keepGoing = true;

        initializeCards();

        cardLayout.show(cardPanel, "introPage");

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
        System.out.println("\nThank you for using our application, have a good day!");
    }

    private void initializeCards() {
        cardPanel.add(createIntroPage(), "introPage");
        cardPanel.add(createMenuPage(), "menuPage");
        add(cardPanel);

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setVisible(true);
    }

    private JPanel createViewListPage(String message, ArrayList list, Branch branch) {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        JTextArea textArea = new JTextArea();

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        String result = "";
        for (int i=0; i < list.size(); i++) {
            result = result + "\n" + list.get(i);
        }

        textArea.setText(result);

        panel.add(placeHeaderMessage(message));
        panel.add(textArea);

        if (branch == null) {
            panel.add(placeMenuButton());
        } else {
            panel.add(returnToBranchButton(branch));
        }
        panel.add(placeQuitButton());
        setVisible(true);

        return panel;
    }

    private JPanel placeMenuButton() {
        JButton b1 = new JButton("Return to Menu");

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.setSize(300, 0);

        b1.addActionListener(e -> {
            cardLayout.show(cardPanel, "menuPage");
        });

        return buttonRow;
    }

    private JPanel editBookPage(Branch branch, Book book) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(15, 1));
        JTextField textFieldTitle = new JTextField();
        textFieldTitle.setText(book.getTitle());
        JTextField textFieldAuthor = new JTextField();
        textFieldAuthor.setText(book.getAuthor());
        JTextField textFieldPrice = new JTextField();
        textFieldPrice.setText(String.valueOf(book.getPrice()));

        panel.add(placeHeaderMessage("Name of book:"));
        panel.add(textFieldTitle);
        panel.add(placeHeaderMessage("Author of book:"));
        panel.add(textFieldAuthor);
        panel.add(placeHeaderMessage("Price of book:"));
        panel.add(textFieldPrice);
        panel.add(placeHeaderMessage("Reservation status:"));
        panel.add(placeOnOffButton(book));

        panel.add(placeSellRemoveButtons(branch, book));


//        panel.add(placeSubmitButton(book, Double.parseDouble(textFieldPrice.getText()), textFieldTitle.getText(),
//                textFieldAuthor.getText()));

        JButton b1 = new JButton("Save Changes");

        JPanel buttonRowOfOne = formatButtonRow(b1);

        b1.addActionListener(e -> {
            book.setPrice(Double.parseDouble(textFieldPrice.getText()));
            book.setTitle(textFieldTitle.getText());
            book.setAuthor(textFieldAuthor.getText());
            cardPanel.add(createBranchMenuPage(branch), "branchMenuPage");
            cardLayout.show(cardPanel, "branchMenuPage");
        });

        panel.add(buttonRowOfOne);

        panel.add(returnToBranchButton(branch));
        panel.add(placeQuitButton());
        setVisible(true);

        return panel;
    }

    private JPanel placeSellRemoveButtons(Branch branch, Book book) {
        JButton b1 = new JButton("Sell Book");
        JButton b2 = new JButton("Delete Book");

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.add(b2);
        buttonRow.setSize(300, 0);

        b1.addActionListener(e -> {
            branch.sellBook(book);
            cardLayout.show(cardPanel, "menuPage");
        });

        b2.addActionListener(e -> {
            branch.removeBook(book);
            cardLayout.show(cardPanel, "menuPage");
        });

        return buttonRow;
    }

    private JPanel placeOnOffButton(Book book) {
        JRadioButton on = new JRadioButton("On");
        JRadioButton off = new JRadioButton("Off");

        on.setSelected(book.getReservedStatus());
        off.setSelected(!book.getReservedStatus());

        ButtonGroup buttons = new ButtonGroup();
        buttons.add(on);
        buttons.add(off);

        JPanel onOffButton = new JPanel();
        onOffButton.add(on);
        onOffButton.add(off);

        ItemListener itemListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (on.isSelected()) {
                    book.setReservedStatus(true);
                } else {
                    book.setReservedStatus(false);
                }
            }
        };

        on.addItemListener(itemListener);
        off.addItemListener(itemListener);

        return onOffButton;
    }

//    private JPanel placeSubmitButton(Book book, Double price, String title, String author) {
//        JButton b1 = new JButton("Submit");
//
//        JPanel buttonRowOfOne = formatButtonRow(b1);
//        buttonRowOfOne.setSize(50, 0);
//
//        b1.addActionListener(e -> {
//            book.setPrice(price);
//            book.setTitle(title);
//            book.setAuthor(author);
//            cardLayout.show(cardPanel, "menuPage");
//        });
//
//        return buttonRowOfOne;
//    }

    private JPanel createNewBookPage(Branch branch) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));
        JTextField textField = new JTextField();
        JTextField textFieldAuthor = new JTextField();
        JTextField textFieldPrice = new JTextField();

        panel.add(placeHeaderMessage("What is name of book:"));
        panel.add(textField);
        panel.add(placeHeaderMessage("Who is author:"));
        panel.add(textFieldAuthor);
        panel.add(placeHeaderMessage("How much:"));
        panel.add(textFieldPrice);


        JButton b1 = new JButton("Create");

        JPanel buttonRow = formatButtonRow(b1);

        b1.addActionListener(e -> {
            branch.addBook(new Book(textField.getText(), textFieldAuthor.getText(),
                    Double.parseDouble(textFieldPrice.getText())));
            cardLayout.show(cardPanel, "menuPage");
        });

        panel.add(buttonRow);
        panel.add(returnToBranchButton(branch));
        panel.add(placeQuitButton());
        setVisible(true);

        return panel;
    }

    private JPanel returnToBranchButton(Branch branch) {
        JButton b1 = new JButton("Return to Branch Menu");

        JPanel buttonRow = formatButtonRow(b1);

        b1.addActionListener(e -> {
            cardPanel.add(createBranchMenuPage(branch), "branchMenuPage");
            cardLayout.show(cardPanel, "branchMenuPage");
        });

        return buttonRow;
    }

    private JPanel createNewBranchPage() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        JTextField textField = new JTextField();
        JTextField textFieldAddress = new JTextField();

        panel.add(placeHeaderMessage("What is name of branch:"));
        panel.add(textField);
        panel.add(placeHeaderMessage("What is address:"));
        panel.add(textFieldAddress);

        JButton b1 = new JButton("Create");

        JPanel buttonRow = formatButtonRow(b1);

        b1.addActionListener(e -> {
            company.addBranch(new Branch(textField.getText(), textFieldAddress.getText()));
            cardLayout.show(cardPanel, "menuPage");
        });

        panel.add(buttonRow);
        panel.add(placeQuitButton());
        setVisible(true);

        return panel;
    }

    private JPanel createCompanyNamePage() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        JTextField textField = new JTextField();

        panel.add(placeHeaderMessage("What do you want to name it:"));
        panel.add(textField);

        JButton b1 = new JButton("Create");

        JPanel buttonRow = formatButtonRow(b1);

        b1.addActionListener(e -> {
            this.company = new Company(textField.getText());
            cardLayout.show(cardPanel, "menuPage");
        });

        panel.add(buttonRow);
        panel.add(placeQuitButton());
        setVisible(true);

        return panel;
    }

    private JPanel createIntroPage() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        panel.add(placeHeaderMessage("How do you wanna start?"));
        panel.add(placeStartButtons());
        panel.add(placeQuitButton());
        setVisible(true);

        return panel;
    }

    private JPanel createMenuPage() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        panel.add(placeHeaderMessage("What you wanna do?"));
        panel.add(placeFirstOperationButtons());
        panel.add(placeSecondOperationButtons());
        panel.add(placeSaveQuitButtons());
        setVisible(true);

        return panel;
    }

    private JPanel createBranchMenuPage(Branch branch) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        panel.add(placeHeaderMessage("What you wanna do?"));
        panel.add(placeFirstBranchOperationButtons(branch));
        panel.add(placeSecondBranchOperationButtons(branch));
        panel.add(placeSaveQuitButtons());
        setVisible(true);

        return panel;
    }

    private JPanel placeSecondBranchOperationButtons(Branch branch) {
        JButton b1 = new JButton("Edit a Book");
        JButton b2 = new JButton("Return to Menu");

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.add(b2);
        buttonRow.setSize(300, 0);

        b1.addActionListener(e -> {
            cardPanel.add(createPickBookPage(branch), "pickBookPage");
            cardLayout.show(cardPanel, "pickBookPage");
        });

        b2.addActionListener(e -> {
            cardLayout.show(cardPanel, "menuPage");
        });

        return buttonRow;
    }

    private JPanel placeFirstBranchOperationButtons(Branch branch) {
        JButton b1 = new JButton("New Book");
        JButton b2 = new JButton("View Inventory");
        JButton b3 = new JButton("View Sales");

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.add(b2);
        buttonRow.add(b3);
        buttonRow.setSize(300, 0);

        b1.addActionListener(e -> {
            cardPanel.add(createNewBookPage(branch), "newBookPage");
            cardLayout.show(cardPanel, "newBookPage");
        });

        b2.addActionListener(e -> {
            ArrayList<String> listToSend = new ArrayList<>();

            for (Book book : branch.getInventory()) {
                listToSend.add(book.getTitle());
            }

            cardPanel.add(createViewListPage("Here is the inventory", listToSend, branch), "viewInventoryPage");
            cardLayout.show(cardPanel, "viewInventoryPage");
        });

        b3.addActionListener(e -> {
            ArrayList<String> listToSend = new ArrayList<>();

            for (Book book : branch.getBooksSold()) {
                listToSend.add(book.getTitle());
            }

            cardPanel.add(createViewListPage("Here is the sales", listToSend, branch), "viewSalesPage");
            cardLayout.show(cardPanel, "viewSalesPage");
        });

        return buttonRow;
    }


    private JPanel createPickBookPage(Branch branch) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        JTextArea textArea = new JTextArea();

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        String result = "";

        for (int i=0; i < branch.getInventory().size() ; i++) {
            result = result + "\n" + i + branch.getInventory().get(i).getTitle();
        }

        textArea.setText(result);
        panel.add(textArea);
        panel.add(placeHeaderMessage("Enter the number of book:"));
        JTextField textField = new JTextField();
        panel.add(textField);

        panel.add(placeBookEnterButton(textField, branch));
        panel.add(returnToBranchButton(branch));
        panel.add(placeQuitButton());
        setVisible(true);

        return panel;
    }

    private JPanel placeBookEnterButton(JTextField textField, Branch branch) {
        JButton b1 = new JButton("Enter");

        b1.addActionListener(e -> {
            cardPanel.add(editBookPage(branch, branch.getInventory().get(textField.getX())), "editBookPage");
            cardLayout.show(cardPanel, "editBookPage");
        });

        JPanel buttonRowOfOne = formatButtonRow(b1);
        return buttonRowOfOne;
    }


    private JPanel createPickBranchPage() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        JTextArea textArea = new JTextArea();

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        String result = "";

        for (int i=0; i < company.getBranches().size() ; i++) {
            result = result + "\n" + i + this.company.getBranches().get(i).getName();
        }

        textArea.setText(result);
        panel.add(textArea);
        panel.add(placeHeaderMessage("Enter the number of branch:"));
        JTextField textField = new JTextField();
        panel.add(textField);

        panel.add(placeBranchEnterButton(textField));
        panel.add(placeMenuButton());
        panel.add(placeQuitButton());
        setVisible(true);

        return panel;
    }

    private JPanel placeBranchEnterButton(JTextField textField) {
        JButton b1 = new JButton("Enter");

        b1.addActionListener(e -> {
            cardPanel.add(createBranchMenuPage(company.getBranches().get(textField.getX())), "branchMenuPage");
            cardLayout.show(cardPanel, "branchMenuPage");
        });

        JPanel buttonRowOfOne = formatButtonRow(b1);
        return buttonRowOfOne;
    }

    private JPanel placeQuitButton() {
        JButton b1 = new JButton("Quit");

        b1.addActionListener(e -> {
            System.exit(0);
        });

        JPanel buttonRowOfOne = formatButtonRow(b1);
        buttonRowOfOne.setSize(50, 0);

        return buttonRowOfOne;
    }

    private JPanel placeStartButtons() {
        JButton b1 = new JButton("Start from Scratch");
        JButton b2 = new JButton("Load from File");

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.add(b2);
        buttonRow.setSize(100, 0);

        b1.addActionListener(e -> {
            cardPanel.add(createCompanyNamePage(), "nameCompanyPage");
            cardLayout.show(cardPanel, "nameCompanyPage");
        });

        b2.addActionListener(e -> {
            cardLayout.show(cardPanel, "menuPage");
            loadCompany();
        });

        return buttonRow;
    }

    private JPanel placeFirstOperationButtons() {
        JButton b1 = new JButton("Open Branch");
        JButton b2 = new JButton("View Branches");

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.add(b2);
        buttonRow.setSize(300, 0);

        b1.addActionListener(e -> {
            cardPanel.add(createNewBranchPage(), "newBranchPage");
            cardLayout.show(cardPanel, "newBranchPage");
        });

        b2.addActionListener(e -> {
            ArrayList<String> listToSend = new ArrayList<>();

            for (Branch branch : company.getBranches()) {
                listToSend.add(branch.getName());
            }

            cardPanel.add(createViewListPage("Here is all the branches", listToSend, null), "viewBranchesPage");
            cardLayout.show(cardPanel, "viewBranchesPage");
        });

        return buttonRow;
    }

    private JPanel placeSecondOperationButtons() {
        JButton b1 = new JButton("View Inventory");
        JButton b2 = new JButton("View Sales");
        JButton b3 = new JButton("Edit a Branch");

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.add(b2);
        buttonRow.add(b3);
        buttonRow.setSize(300, 0);

        b1.addActionListener(e -> {
            ArrayList<String> listToSend = new ArrayList<>();

            for (Book book : company.getInventory()) {
                listToSend.add(book.getTitle());
            }

            cardPanel.add(createViewListPage("Here is the inventory", listToSend, null), "viewInventoryPage");
            cardLayout.show(cardPanel, "viewInventoryPage");
        });

        b2.addActionListener(e -> {
            ArrayList<String> listToSend = new ArrayList<>();

            for (Book book : company.getBooksSold()) {
                listToSend.add(book.getTitle());
            }

            cardPanel.add(createViewListPage("Here is the sales", listToSend, null), "viewSalesPage");
            cardLayout.show(cardPanel, "viewSalesPage");
        });

        b3.addActionListener(e -> {
            cardPanel.add(createPickBranchPage(), "pickBranchPage");
            cardLayout.show(cardPanel, "pickBranchPage");
        });

        return buttonRow;
    }

    private JPanel placeSaveQuitButtons() {
        JButton b1 = new JButton("Save");
        JButton b2 = new JButton("Quit");

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.add(b2);
        buttonRow.setSize(300, 0);

        b1.addActionListener(e -> {
            saveCompany();
        });

        b2.addActionListener(e -> {
            System.exit(0);
        });

        return buttonRow;
    }

    private JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

    private JLabel placeHeaderMessage(String msg) {
        JLabel greeting = new JLabel(msg, JLabel.CENTER);
        greeting.setSize(WIDTH, HEIGHT / 3);
        return greeting;
    }

    // MODIFIES: this
    // EFFECTS: initializes the company and the input scanner, can load company status
    //          or start from scratch
    private boolean initialize() {
        while (true) {
            input = new Scanner(System.in);
            input.useDelimiter("\n");

            displayInitialize();
            String command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                return false;
            } else if (command.equals("i")) {
                makeCompany();
                return true;
            } else if (command.equals("l")) {
                loadCompany();
                return true;
            } else {
                System.out.println("Selection not valid, please try again.");
                System.out.println("");
            }
        }
    }

    // EFFECTS: prints the initializing options to the terminal
    private void displayInitialize() {
        System.out.println("\nWelcome to our book company application! Follow the instructions to get started!");
        System.out.println("");
        System.out.println("\tPress i to initialize a new company from scratch.");
        System.out.println("\tPress l to load in the company saved on file.");
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
        System.out.println("\t10- Rate a book");
        System.out.println("\ts - Save the company status to file");
        System.out.println("\t0 - Quit the application");
        System.out.println("");
    }

    @SuppressWarnings("methodlength")
    // EFFECTS: decides what method to call based on what function the user wants to do
    private void processCommand(String command) {
        if (command.equals("1")) {
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
        } else if (command.equals("10")) {
            rateBook();
        } else if (command.equals("s")) {
            saveCompany();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new company with a name of the user's choosing
    private void makeCompany() {
        System.out.println("\nTo initialize your company, please enter the company's desired name:");
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
        System.out.println("\nWhat is the price of the book: ");
        double price = Math.floor(input.nextDouble() * 100) / 100;

        branch.addBook(new Book(title, author, price));
        System.out.println("\nThe book " + title + " has now been added!");
    }

    // MODIFIES: this
    // EFFECTS: prints to the terminal the name of all books in the inventory of company branch of
    //          user's choice, or of the entire company's inventory
    private void viewInventory() {
        System.out.println("\nTo view the inventory for the entire company, enter 0: ");
        System.out.println("\nTo view the inventory for a specific branch, enter 1: ");
        int location = input.nextInt();

        if (location == 0) {
            System.out.println("\nThe company's inventory contains the following books: ");
            System.out.println("");
            for (Book b : company.getInventory()) {
                System.out.println(b.getTitle());
            }
            System.out.println("\nFor a total inventory value of: " + company.getInventoryValue() + " dollars.");

        } else if (location == 1) {
            System.out.println("\nWhich branches' inventory would you like to view:");
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
    // EFFECTS: removes user's choice of book from its respective branch's inventory
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
    // EFFECTS: moves user's choice of book from its respective branch's inventory into list of
    //          books sold for the same branch
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
    // EFFECTS: prints to the terminal the name of all books sold from user's choice of
    //          company branch, or from the entire company
    private void viewBooksSold() {
        System.out.println("\nTo view the books sold for the entire company, enter 0: ");
        System.out.println("\nTo view the books sold at a specific branch, enter 1: ");
        int location = input.nextInt();

        if (location == 0) {
            System.out.println("\nThe company has sold the following books: ");
            System.out.println("");
            for (Book b : company.getBooksSold()) {
                System.out.println(b.getTitle());
            }
            System.out.println("\nFor a total revenue of: " + company.getCashValue() + " dollars.");

        } else if (location == 1) {
            System.out.println("\nWhich branch would you like to view:");
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
    // EFFECTS: changes the reservation status of user's choice of book to the opposite of
    //          its previous reservation status
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
    // EFFECTS: changes the price of user's choice of book to a new price, specified by the user
    private void changePrice() {
        System.out.println("\nAt which branch would you like to change a book's price:");
        System.out.println("");
        Branch branch = selectBranch();

        System.out.println("\nWhich book would you like to change the price of:");
        System.out.println("");
        Book book = selectBook(branch);

        System.out.println("\nThe price is currently " + book.getPrice() + " dollars, enter the new price:");
        double newPrice = Math.floor(input.nextDouble() * 100) / 100;
        book.setPrice(newPrice);
        System.out.println("\nThe book's price is now " + newPrice + " dollars.");
        System.out.println("");
    }

    // MODIFIES: this
    // EFFECTS: adds the user's rating to the list of ratings for a book of the user's choosing
    //          and prints to terminal the new average rating of said book
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
    // EFFECTS: returns the book of the user's choosing from a given branch's inventory
    private Book selectBook(Branch branch) {
        for (int i = 0; i < branch.getInventory().size(); i++) {
            System.out.println(i + " - " + branch.getInventory().get(i).getTitle());
        }
        System.out.println("\nEnter the number corresponding to the book here: ");
        int bookIndex = input.nextInt();
        return branch.getInventory().get(bookIndex);
    }

    // EFFECTS: saves the company to file, prints message if unable to write to the file
    private void saveCompany() {
        try {
            jsonWriter.open();
            jsonWriter.write(company);
            jsonWriter.close();
            System.out.println(company.getName() + "'s status has been saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the company from file, prints message if unable to read the file
    private void loadCompany() {
        try {
            company = jsonReader.read();
            System.out.println("Loaded " + company.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
