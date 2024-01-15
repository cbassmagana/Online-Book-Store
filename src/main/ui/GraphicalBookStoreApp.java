package ui;

import model.Book;
import model.Branch;
import model.Company;
import model.EventLog;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Graphical Interface application for a book store company
public class GraphicalBookStoreApp extends JFrame {
    private static final String JSON_STORE = "./data/company.json";
    private static final String STAR_IMG = "./data/star.png";
    private Company company;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    // EFFECTS: runs the book store application, instantiates JSON and GUI window
    public GraphicalBookStoreApp() {
        super("Book Store Manager");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        initializeUserInterface();
    }

    // MODIFIES: this
    // EFFECTS: initializes JFrame settings and opens IntroPage card
    private void initializeUserInterface() {
        super.add(cardPanel);
        super.setSize(WIDTH, HEIGHT);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(20, 40, 20, 40));
        cardPanel.add(createIntroPage(), "introPage");
        cardLayout.show(cardPanel, "introPage");
        super.setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onWindowClosing();
            }
        });
    }

    //EFFECTS: Prints out event log when window is closed.
    private void onWindowClosing() {
        EventLog eventLog = EventLog.getInstance();

        for (Event event : eventLog) {
            System.out.println(event.toString());
        }
    }

    // MODIFIES: this
    // EFFECTS: creates and returns a JPanel for the introductory page
    private JPanel createIntroPage() {
        JPanel introPanel = new JPanel();
        introPanel.setLayout(new GridLayout(4, 1));

        introPanel.add(formatStringRow("Welcome to MyBookStoreManager!",
                20, 10, 10, 10));
        introPanel.add(formatStringRow("How would you like to initialize your company?",
                10, 10, 20, 10));
        introPanel.add(placeStartButtons());
        introPanel.add(placeQuitButton());
        setVisible(true);

        return introPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns a JPanel for the naming company page
    private JPanel createNameCompanyPage() {
        JPanel createCompanyPanel = new JPanel();
        createCompanyPanel.setLayout(new GridLayout(3, 1));

        createCompanyPanel.add(formatStringRow("Enter the name of your book store company:",
                10, 10, 20, 10));

        JTextField textField = new JTextField(1);
        textField.setBorder(new EmptyBorder(0, 20, 0, 0));
        createCompanyPanel.add(textField);

        JButton createCompanyButton = new JButton("Create");
        createCompanyButton.addActionListener(e -> {
            this.company = new Company(textField.getText());
            cardPanel.add(createMenuPage(), "menuPage");
            cardLayout.show(cardPanel, "menuPage");
        });
        JPanel buttonRow = formatButtonRow(createCompanyButton, 25, 10, 0, 10);
        buttonRow.add(placeQuitButton());

        createCompanyPanel.add(buttonRow);
        setVisible(true);

        return createCompanyPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns a JPanel for the main menu page
    private JPanel createMenuPage() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(4, 1));

        menuPanel.add(formatStringRow("Select the task that you would like to accomplish.",
                10, 10, 10, 10));
        menuPanel.add(placeFirstOperationButtons());
        menuPanel.add(placeSecondOperationButtons());
        menuPanel.add(placeSaveQuitButtons());
        setVisible(true);

        return menuPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns JPanel for the new branch page
    @SuppressWarnings("methodlength")
    private JPanel createNewBranchPage() {
        JPanel newBranchPanel = new JPanel();
        newBranchPanel.setLayout(new GridLayout(3, 2, 20, 40));
        newBranchPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        JTextField textField = new JTextField();
        textField.setBorder(new EmptyBorder(0, 10, 0, 0));
        JTextField textFieldAddress = new JTextField();
        textFieldAddress.setBorder(new EmptyBorder(0, 10, 0, 0));

        newBranchPanel.add(formatStringRow("Enter the name of the new branch:",
                20, 10, 20, 10));
        newBranchPanel.add(textField);
        newBranchPanel.add(formatStringRow("Enter the address of this branch:",
                20, 10, 20, 10));
        newBranchPanel.add(textFieldAddress);

        JButton createBranchButton = new JButton("Create");
        createBranchButton.addActionListener(e -> {
            company.addBranch(new Branch(textField.getText(), textFieldAddress.getText()));
            cardLayout.show(cardPanel, "menuPage");
        });
        JPanel buttonRow = formatButtonRow(createBranchButton, 10, 10, 10, 10);

        newBranchPanel.add(placeReturnToMenuButton());
        newBranchPanel.add(buttonRow);
        setVisible(true);

        return newBranchPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns JPanel for the selecting branch page
    @SuppressWarnings("methodlength")
    private JPanel createPickBranchPage() {
        JPanel pickBranchPanel = new JPanel();
        pickBranchPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        pickBranchPanel.setLayout(new FlowLayout());

        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        String result = "";
        for (int i = 0; i < company.getBranches().size(); i++) {
            if (result.isEmpty()) {
                result = i + "  -  " + this.company.getBranches().get(i).getName();
            } else {
                result = result + "\n" + i + "  -  " + this.company.getBranches().get(i).getName();
            }
        }

        textArea.setText(result);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 150));
        scrollPane.setBorder(new EmptyBorder(8, 10, 10, 10));

        pickBranchPanel.add(scrollPane);
        pickBranchPanel.add(formatStringRow("Enter the number corresponding to the branch you want to select:",
                20, 10, 20, 4));

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(60, 40));
        textField.setBorder(new EmptyBorder(0, 10, 0, 0));

        pickBranchPanel.add(textField);
        pickBranchPanel.add(placeQuitButton());
        pickBranchPanel.add(placeReturnToMenuButton());
        pickBranchPanel.add(placeEditBranchEnterButton(textField));
        setVisible(true);

        return pickBranchPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns JPanel for the branch menu page
    private JPanel createBranchMenuPage(Branch branch) {
        JPanel branchMenuPanel = new JPanel();
        branchMenuPanel.setLayout(new GridLayout(4, 1));

        branchMenuPanel.add(formatStringRow("Select the task that you would like to accomplish at "
                + branch.getName() + ".", 0, 0, 0, 0));
        branchMenuPanel.add(placeFirstBranchOperationButtons(branch));
        branchMenuPanel.add(placeSecondBranchOperationButtons(branch));
        branchMenuPanel.add(placeSaveQuitButtons());
        setVisible(true);

        return branchMenuPanel;
    }

    // MODIFIES: this, branch
    // EFFECTS: creates and returns JPanel for the new book page
    @SuppressWarnings("methodlength")
    private JPanel createNewBookPage(Branch branch) {
        JPanel newBookPanel = new JPanel();
        newBookPanel.setLayout(new FlowLayout());

        JTextField textFieldTitle = new JTextField();
        textFieldTitle.setPreferredSize(new Dimension(450, 50));
        textFieldTitle.setBorder(new EmptyBorder(5,10,0,0));

        JTextField textFieldAuthor = new JTextField();
        textFieldAuthor.setPreferredSize(new Dimension(450, 50));
        textFieldAuthor.setBorder(new EmptyBorder(5,10,0,0));

        JTextField textFieldPrice = new JTextField();
        textFieldPrice.setPreferredSize(new Dimension(450, 50));
        textFieldPrice.setBorder(new EmptyBorder(5,10,0,0));

        newBookPanel.add(formatStringRow("Enter the title of this new book:",
                0, 0, 0, 0));
        newBookPanel.add(textFieldTitle);
        newBookPanel.add(formatStringRow("Enter the author's name:",
                12, 0, 0, 0));
        newBookPanel.add(textFieldAuthor);
        newBookPanel.add(formatStringRow("Enter the price of the book in dollars:",
                12, 0, 0, 0));
        newBookPanel.add(textFieldPrice);

        JPanel placeholderPanel = new JPanel();
        placeholderPanel.setPreferredSize(new Dimension(500, 6));
        newBookPanel.add(placeholderPanel);

        JButton createBookButton = new JButton("Create");
        JPanel buttonRow = formatButtonRow(createBookButton, 10, 10, 10, 10);
        createBookButton.addActionListener(e -> {
            branch.addBook(new Book(textFieldTitle.getText(), textFieldAuthor.getText(),
                    Double.parseDouble(textFieldPrice.getText())));
            cardPanel.add(createBranchMenuPage(branch), "branchMenuPage");
            cardLayout.show(cardPanel, "branchMenuPage");
        });

        newBookPanel.add(placeQuitButton());
        newBookPanel.add(placeReturnToBranchButton(branch));
        newBookPanel.add(buttonRow);
        setVisible(true);

        return newBookPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns JPanel for the selecting book page
    @SuppressWarnings("methodlength")
    private JPanel createPickBookPage(Branch branch) {
        JPanel pickBookPanel = new JPanel();
        pickBookPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        pickBookPanel.setLayout(new FlowLayout());

        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        String result = "";
        for (int i = 0; i < branch.getInventory().size(); i++) {
            if (result == "") {
                result = i + "  -  " + branch.getInventory().get(i).getTitle();
            } else {
                result = result + "\n" + i + "  -  " + branch.getInventory().get(i).getTitle();
            }
        }

        textArea.setText(result);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 150));
        scrollPane.setBorder(new EmptyBorder(8, 10, 10, 10));

        pickBookPanel.add(scrollPane);
        pickBookPanel.add(formatStringRow("Enter the number corresponding to the book you want to select:",
                20, 10, 20, 4));

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(60, 40));
        textField.setBorder(new EmptyBorder(0,10,0,0));
        pickBookPanel.add(textField);

        pickBookPanel.add(placeQuitButton());
        pickBookPanel.add(placeReturnToBranchButton(branch));
        pickBookPanel.add(placeEditBookEnterButton(textField, branch));
        setVisible(true);

        return pickBookPanel;
    }

    // MODIFIES: this, book
    // EFFECTS: creates and returns JPanel for the editing book page
    @SuppressWarnings("methodlength")
    private JPanel createEditBookPage(Branch branch, Book book) {
        JPanel editBookPanel = new JPanel();
        editBookPanel.setLayout(new GridLayout(7, 2, 0, 10));

        JTextField textFieldTitle = new JTextField();
        textFieldTitle.setText(book.getTitle());
        textFieldTitle.setBorder(new EmptyBorder(0,10,0,0));

        JTextField textFieldAuthor = new JTextField();
        textFieldAuthor.setText(book.getAuthor());
        textFieldAuthor.setBorder(new EmptyBorder(0,10,0,0));

        JTextField textFieldPrice = new JTextField();
        textFieldPrice.setText(String.valueOf(book.getPrice()));
        textFieldPrice.setBorder(new EmptyBorder(0,10,0,0));

        editBookPanel.add(placeHeaderMessage("Name of book:"));
        editBookPanel.add(textFieldTitle);
        editBookPanel.add(placeHeaderMessage("Author of book:"));
        editBookPanel.add(textFieldAuthor);
        editBookPanel.add(placeHeaderMessage("Price of book:"));
        editBookPanel.add(textFieldPrice);
        editBookPanel.add(placeHeaderMessage("Reservation status:"));
        editBookPanel.add(placeOnOffButtons(book));
        editBookPanel.add(formatStringRow("Average Rating of Book: " + book.getAverageRating() + " stars.",
                0, 0, 0, 0));
        editBookPanel.add(placeRateBookButton(book, branch));
        editBookPanel.add(placeSellDeleteButtons(branch, book));

        JButton saveEditsButton = new JButton("Save Edits");
        saveEditsButton.addActionListener(e -> {
            book.setPrice(Double.parseDouble(textFieldPrice.getText()));
            book.setTitle(textFieldTitle.getText());
            book.setAuthor(textFieldAuthor.getText());
            cardPanel.add(createBranchMenuPage(branch), "branchMenuPage");
            cardLayout.show(cardPanel, "branchMenuPage");
        });
        JPanel buttonRow = formatButtonRow(saveEditsButton, 10, 10, 10, 10);
        editBookPanel.add(buttonRow);

        editBookPanel.add(placeReturnToBranchButton(branch));
        editBookPanel.add(placeQuitButton());
        setVisible(true);

        return editBookPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns JPanel for the rating book page
    private JPanel createRateBookPage(Book book, Branch branch) {
        JPanel rateBookPanel = new JPanel();
        rateBookPanel.setLayout(new GridLayout(3, 1, 0, 40));
        rateBookPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        rateBookPanel.add(formatStringRow("Submit a rating for " + book.getTitle()
                + " by clicking on a star below!", 0,0,0,0));
        rateBookPanel.add(placeStarButtons(book, branch));
        rateBookPanel.add(placeReturnToBookButton(book, branch));
        setVisible(true);

        return rateBookPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns a JPanel for any page that involves viewing a list
    @SuppressWarnings("methodlength")
    private JPanel createViewListPage(String message, ArrayList<String> list, Branch branch) {
        JPanel viewListPanel = new JPanel();
        viewListPanel.setLayout(new FlowLayout());

        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        String result = "";
        for (int i = 0; i < list.size(); i++) {
            if (result.isEmpty()) {
                result = "-  " + list.get(i);
            } else {
                result = result + "\n" + "-  " + list.get(i);
            }
        }
        textArea.setText(result);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 150));
        scrollPane.setBorder(new EmptyBorder(8, 10, 10, 10));

        viewListPanel.add(formatStringRow(message, 30, 10, 30, 10));
        viewListPanel.add(scrollPane);

        JPanel placeholderPanel = new JPanel();
        placeholderPanel.setPreferredSize(new Dimension(400, 15));
        viewListPanel.add(placeholderPanel);

        if (branch == null) {
            viewListPanel.add(placeReturnToMenuButton());
        } else {
            viewListPanel.add(placeReturnToBranchButton(branch));
        }
        viewListPanel.add(placeQuitButton());
        setVisible(true);

        return viewListPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns JPanel with a buttons for starting a company
    //          from scratch or loading from file
    private JPanel placeStartButtons() {
        JButton fromScratchButton = new JButton("Start from Scratch");
        JButton fromFileButton = new JButton("Load from File");

        JPanel buttonRow = formatButtonRow(fromScratchButton, 10, 10, 10, 10);
        buttonRow.add(fromFileButton);
        buttonRow.setSize(100, 0);

        fromScratchButton.addActionListener(e -> {
            cardPanel.add(createNameCompanyPage(), "nameCompanyPage");
            cardLayout.show(cardPanel, "nameCompanyPage");
        });

        fromFileButton.addActionListener(e -> {
            cardPanel.add(createMenuPage(), "menuPage");
            cardLayout.show(cardPanel, "menuPage");
            loadCompany();
        });

        return buttonRow;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns JPanel with buttons for opening a new branch
    //          and viewing all company branches
    private JPanel placeFirstOperationButtons() {
        JButton newBranchButton = new JButton("Open New Branch");
        JButton viewBranchesButton = new JButton("View Branches");

        JPanel buttonRow = formatButtonRow(newBranchButton, 10, 10, 10, 10);
        buttonRow.add(viewBranchesButton);
        buttonRow.setSize(300, 0);

        newBranchButton.addActionListener(e -> {
            cardPanel.add(createNewBranchPage(), "newBranchPage");
            cardLayout.show(cardPanel, "newBranchPage");
        });

        viewBranchesButton.addActionListener(e -> {
            ArrayList<String> listToSend = new ArrayList<>();
            for (Branch branch : company.getBranches()) {
                listToSend.add(branch.getName());
            }
            cardPanel.add(createViewListPage("Here is the list of all the company's branches:",
                    listToSend, null), "viewBranchesPage");
            cardLayout.show(cardPanel, "viewBranchesPage");
        });

        return buttonRow;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns JPanel with buttons for viewing the inventory,
    //          viewing the sales and editing a branch.
    @SuppressWarnings("methodlength")
    private JPanel placeSecondOperationButtons() {
        JButton viewInventoryButton = new JButton("View Inventory");
        JButton viewSalesButton = new JButton("View Sales");
        JButton editBranchButton = new JButton("Edit a Branch");

        JPanel buttonRow = formatButtonRow(viewInventoryButton, 10, 10, 10, 10);
        buttonRow.add(viewSalesButton);
        buttonRow.add(editBranchButton);
        buttonRow.setSize(300, 0);

        viewInventoryButton.addActionListener(e -> {
            ArrayList<String> listToSend = new ArrayList<>();
            for (Book book : company.getInventory()) {
                listToSend.add(book.getTitle());
            }
            cardPanel.add(createViewListPage("Here is the company's entire inventory, valued at a total of: $"
                    + company.getInventoryValue() + ".", listToSend, null), "viewInventoryPage");
            cardLayout.show(cardPanel, "viewInventoryPage");
        });

        viewSalesButton.addActionListener(e -> {
            ArrayList<String> listToSend = new ArrayList<>();
            for (Book book : company.getBooksSold()) {
                listToSend.add(book.getTitle());
            }
            cardPanel.add(createViewListPage("Here is all of company's previous sales, for a total revenue "
                    + "of: $" + company.getCashValue() + ".", listToSend, null), "viewSalesPage");
            cardLayout.show(cardPanel, "viewSalesPage");
        });

        editBranchButton.addActionListener(e -> {
            cardPanel.add(createPickBranchPage(), "pickBranchPage");
            cardLayout.show(cardPanel, "pickBranchPage");
        });

        return buttonRow;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns JPanel with a button for selecting a branch to edit
    private JPanel placeEditBranchEnterButton(JTextField textField) {
        JButton enterButton = new JButton("Enter");

        enterButton.addActionListener(e -> {
            cardPanel.add(createBranchMenuPage(company.getBranches().get(Integer.parseInt(textField.getText()))),
                    "branchMenuPage");
            cardLayout.show(cardPanel, "branchMenuPage");
        });

        JPanel buttonRow = formatButtonRow(enterButton, 10, 10, 10, 10);

        return buttonRow;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns JPanel with a button for returning to the main menu
    private JPanel placeReturnToMenuButton() {
        JButton menuButton = new JButton("Return to Menu");
        JPanel buttonRow = formatButtonRow(menuButton, 10, 10, 10, 10);
        buttonRow.setSize(300, 0);

        menuButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "menuPage");
        });

        return buttonRow;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns JPanel with buttons for adding a new book,
    //          viewing inventory and viewing sales
    @SuppressWarnings("methodlength")
    private JPanel placeFirstBranchOperationButtons(Branch branch) {
        JButton newBookButton = new JButton("Add New Book");
        JButton viewInventoryButton = new JButton("View Inventory");
        JButton viewSalesButton = new JButton("View Sales");

        JPanel buttonRow = formatButtonRow(newBookButton, 10, 10, 10, 10);
        buttonRow.add(viewInventoryButton);
        buttonRow.add(viewSalesButton);
        buttonRow.setSize(300, 0);

        newBookButton.addActionListener(e -> {
            cardPanel.add(createNewBookPage(branch), "newBookPage");
            cardLayout.show(cardPanel, "newBookPage");
        });

        viewInventoryButton.addActionListener(e -> {
            ArrayList<String> listToSend = new ArrayList<>();
            for (Book book : branch.getInventory()) {
                listToSend.add(book.getTitle());
            }
            cardPanel.add(createViewListPage("Here is the entire inventory at " + branch.getName()
                            + ", valued at a total of: $" + branch.getInventoryValue() + ".", listToSend, branch),
                    "viewInventoryPage");
            cardLayout.show(cardPanel, "viewInventoryPage");
        });

        viewSalesButton.addActionListener(e -> {
            ArrayList<String> listToSend = new ArrayList<>();
            for (Book book : branch.getBooksSold()) {
                listToSend.add(book.getTitle());
            }
            cardPanel.add(createViewListPage("Here are all of previous sales at " + branch.getName()
                            + ", for a total revenue of: $" + branch.getCashValue() + ".", listToSend, branch),
                    "viewSalesPage");
            cardLayout.show(cardPanel, "viewSalesPage");
        });

        return buttonRow;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns JPanel with buttons for returning to the main menu and editing a book
    private JPanel placeSecondBranchOperationButtons(Branch branch) {
        JButton editBookButton = new JButton("Edit a Book");
        JButton returnToMenuButton = new JButton("Return to Menu");

        JPanel buttonRow = formatButtonRow(editBookButton, 10, 10, 10, 10);
        buttonRow.add(returnToMenuButton);
        buttonRow.setSize(300, 0);

        editBookButton.addActionListener(e -> {
            cardPanel.add(createPickBookPage(branch), "pickBookPage");
            cardLayout.show(cardPanel, "pickBookPage");
        });

        returnToMenuButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "menuPage");
        });

        return buttonRow;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns JPanel with a button for returning to the branch menu
    private JPanel placeReturnToBranchButton(Branch branch) {
        JButton returnToBranchMenuButton = new JButton("Return to Branch Menu");
        JPanel buttonRow = formatButtonRow(returnToBranchMenuButton, 10, 10, 10, 10);

        returnToBranchMenuButton.addActionListener(e -> {
            cardPanel.add(createBranchMenuPage(branch), "branchMenuPage");
            cardLayout.show(cardPanel, "branchMenuPage");
        });

        return buttonRow;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns JPanel with a button for selecting a book to edit
    private JPanel placeEditBookEnterButton(JTextField textField, Branch branch) {
        JButton enterButton = new JButton("Enter");

        enterButton.addActionListener(e -> {
            cardPanel.add(createEditBookPage(branch, branch.getInventory().get(Integer.parseInt(textField.getText()))),
                    "editBookPage");
            cardLayout.show(cardPanel, "editBookPage");
        });

        JPanel buttonRow = formatButtonRow(enterButton, 10, 10, 10, 10);

        return buttonRow;
    }

    // MODIFIES: this, branch
    // EFFECTS: creates and returns JPanel with buttons for returning selling and removing a book
    private JPanel placeSellDeleteButtons(Branch branch, Book book) {
        JButton sellBookButton = new JButton("Sell Book");
        JButton deleteBookButton = new JButton("Delete Book");

        JPanel buttonRow = formatButtonRow(sellBookButton, 10, 10, 10, 10);
        buttonRow.add(deleteBookButton);
        buttonRow.setSize(300, 0);

        sellBookButton.addActionListener(e -> {
            branch.sellBook(book);
            cardPanel.add(createBranchMenuPage(branch), "branchMenuPage");
            cardLayout.show(cardPanel, "branchMenuPage");
        });

        deleteBookButton.addActionListener(e -> {
            branch.removeBook(book);
            cardPanel.add(createBranchMenuPage(branch), "branchMenuPage");
            cardLayout.show(cardPanel, "branchMenuPage");
        });

        return buttonRow;
    }

    // MODIFIES: this, book
    // EFFECTS: creates and returns JPanel with a button for setting a book's reservation status
    @SuppressWarnings("methodlength")
    private JPanel placeOnOffButtons(Book book) {
        JPanel onOffButtonPanel = new JPanel();
        onOffButtonPanel.setLayout(new FlowLayout());
        onOffButtonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0,0,0));

        JRadioButton onButton = new JRadioButton("On");
        JRadioButton offButton = new JRadioButton("Off");

        onButton.setSelected(book.getReservedStatus());
        offButton.setSelected(!book.getReservedStatus());

        ButtonGroup onOffButtons = new ButtonGroup();
        onOffButtons.add(onButton);
        onOffButtons.add(offButton);

        onOffButtonPanel.add(onButton);
        onOffButtonPanel.add(offButton);

        ItemListener itemListener = e -> {
            if (onButton.isSelected()) {
                book.setReservedStatus(true);
            } else {
                book.setReservedStatus(false);
            }
        };

        onButton.addItemListener(itemListener);
        offButton.addItemListener(itemListener);

        return onOffButtonPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns JPanel with a button for rating a book
    private JPanel placeRateBookButton(Book book, Branch branch) {
        JButton newRatingButton = new JButton("Submit New Rating");
        JPanel buttonRow = formatButtonRow(newRatingButton, 0, 0, 0, 0);

        newRatingButton.addActionListener(e -> {
            cardPanel.add(createRateBookPage(book, branch), "rateBookPage");
            cardLayout.show(cardPanel, "rateBookPage");
        });

        return buttonRow;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns JPanel with a button for returning to the edit book page
    private JPanel placeReturnToBookButton(Book book, Branch branch) {
        JButton returnToEditBookButton = new JButton("Return to Edit Book");
        JPanel buttonRow = formatButtonRow(returnToEditBookButton, 0, 10, 10, 10);

        returnToEditBookButton.addActionListener(e -> {
            cardPanel.add(createEditBookPage(branch, book), "editBookPage");
            cardLayout.show(cardPanel, "editBookPage");
        });

        return buttonRow;
    }

    // MODIFIES: this, book
    // EFFECTS: creates and returns JPanel with 5 images of stars acting as buttons
    @SuppressWarnings("methodlength")
    private JPanel placeStarButtons(Book book, Branch branch) {
        ImageIcon starIcon = new ImageIcon(STAR_IMG);

        int newWidth = 40;
        int newHeight = 40;
        ImageIcon resizedStarIcon = new ImageIcon(starIcon.getImage().getScaledInstance(newWidth, newHeight,
                Image.SCALE_SMOOTH));

        JButton starButton1 = new JButton(resizedStarIcon);
        JButton starButton2 = new JButton(resizedStarIcon);
        JButton starButton3 = new JButton(resizedStarIcon);
        JButton starButton4 = new JButton(resizedStarIcon);
        JButton starButton5 = new JButton(resizedStarIcon);

        JPanel buttonRow = formatButtonRow(starButton1, 0, 0, 0, 0);
        buttonRow.add(starButton2);
        buttonRow.add(starButton3);
        buttonRow.add(starButton4);
        buttonRow.add(starButton5);

        starButton1.addActionListener(e -> {
            book.rateBook(1);
            JOptionPane.showMessageDialog(this, "Your 1 star rating was submitted!",
                    "Rate", JOptionPane.INFORMATION_MESSAGE);
            cardPanel.add(createEditBookPage(branch, book), "editBookPage");
            cardLayout.show(cardPanel, "editBookPage");
        });

        starButton2.addActionListener(e -> {
            book.rateBook(2);
            JOptionPane.showMessageDialog(this, "Your 2 star rating was submitted!",
                    "Rate", JOptionPane.INFORMATION_MESSAGE);
            cardPanel.add(createEditBookPage(branch, book), "editBookPage");
            cardLayout.show(cardPanel, "editBookPage");
        });

        starButton3.addActionListener(e -> {
            book.rateBook(3);
            JOptionPane.showMessageDialog(this, "Your 3 star rating was submitted!",
                    "Rate", JOptionPane.INFORMATION_MESSAGE);
            cardPanel.add(createEditBookPage(branch, book), "editBookPage");
            cardLayout.show(cardPanel, "editBookPage");
        });

        starButton4.addActionListener(e -> {
            book.rateBook(4);
            JOptionPane.showMessageDialog(this, "Your 4 star rating was submitted!",
                    "Rate", JOptionPane.INFORMATION_MESSAGE);
            cardPanel.add(createEditBookPage(branch, book), "editBookPage");
            cardLayout.show(cardPanel, "editBookPage");
        });

        starButton5.addActionListener(e -> {
            book.rateBook(5);
            JOptionPane.showMessageDialog(this, "Your 5 star rating was submitted!",
                    "Rate", JOptionPane.INFORMATION_MESSAGE);
            cardPanel.add(createEditBookPage(branch, book), "editBookPage");
            cardLayout.show(cardPanel, "editBookPage");
        });

        return buttonRow;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns JPanel with a button for exiting
    //          the program after printing event log
    private JPanel placeQuitButton() {
        JButton quitButton = new JButton("Quit");

        quitButton.addActionListener(e -> {
            EventLog eventLog = EventLog.getInstance();
            for (Event event : eventLog) {
                System.out.println(event.toString());
            }
            System.exit(0);
        });

        JPanel buttonRow = formatButtonRow(quitButton, 10, 10, 10, 10);
        buttonRow.setSize(50, 0);

        return buttonRow;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns JPanel with buttons for saving the state of the
    //          program and quiting the program
    private JPanel placeSaveQuitButtons() {
        JButton saveButton = new JButton("Save");
        JButton quitButton = new JButton("Quit");

        JPanel buttonRow = formatButtonRow(saveButton, 10, 10, 10, 10);
        buttonRow.add(quitButton);
        buttonRow.setSize(300, 0);

        saveButton.addActionListener(e -> {
            saveCompany();
            JOptionPane.showMessageDialog(this, "Your progress has been saved!", "Saved",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        quitButton.addActionListener(e -> {
            EventLog eventLog = EventLog.getInstance();
            for (Event event : eventLog) {
                System.out.println(event.toString());
            }
            System.exit(0);
        });

        return buttonRow;
    }

    // EFFECTS: creates and returns JPanel containing a given button with specified border dimensions
    private JPanel formatButtonRow(JButton button, int top, int left, int bottom, int right) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        buttonPanel.add(button);

        return buttonPanel;
    }

    // EFFECTS: creates and returns JPanel containing a given label and specified border dimensions
    private JPanel formatStringRow(String string, int top, int left, int bottom, int right) {
        JPanel rowPanel = new JPanel(new BorderLayout());
        rowPanel.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));

        JLabel label = new JLabel(string, JLabel.CENTER);
        rowPanel.add(label, BorderLayout.CENTER);

        return rowPanel;
    }

    // EFFECTS: creates and returns JPanel containing a given label and specified border dimensions
    private JLabel placeHeaderMessage(String message) {
        JLabel text = new JLabel(message, JLabel.CENTER);
        text.setSize(WIDTH, 0);
        return text;
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
