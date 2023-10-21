package persistence;

import model.Company;
import model.Branch;
import model.Book;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.json.*;

// Represents a reader that reads the company from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads company from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Company read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCompany(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses company from JSON object and returns it
    private Company parseCompany(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Company company = new Company(name);
        addBranches(company, jsonObject);
        return company;
    }

    // MODIFIES: company
    // EFFECTS: parses branches from JSON object and adds them to company
    private void addBranches(Company company, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("branches");
        for (Object branch : jsonArray) {
            JSONObject nextBranch = (JSONObject) branch;
            addBranch(company, nextBranch);
        }
    }

    // MODIFIES: company
    // EFFECTS: adds next branch from JsonArray into company
    private void addBranch(Company company, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String address = jsonObject.getString("address");
        Branch branch = new Branch(name, address);
        addBooksInventory(branch, jsonObject);
        addBooksSold(branch, jsonObject);
        company.addBranch(branch);
    }

    // MODIFIES: branch
    // EFFECTS: parses inventory books from JSON object and adds them to branch inventory
    private void addBooksInventory(Branch branch, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("inventory");
        for (Object book : jsonArray) {
            JSONObject nextBook = (JSONObject) book;
            addBook(branch, nextBook, false);
        }
    }

    // MODIFIES: branch
    // EFFECTS: parses sold books from JSON object and adds them to branch inventory before selling
    private void addBooksSold(Branch branch, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("sales");
        for (Object book : jsonArray) {
            JSONObject nextBook = (JSONObject) book;
            addBook(branch, nextBook, true);
        }
    }

    // MODIFIES: branch
    // EFFECTS: adds next book from JsonArray into branch, sells afterwards if applicable
    private void addBook(Branch branch, JSONObject jsonObject, boolean sold) {
        String title = jsonObject.getString("title");
        String author = jsonObject.getString("author");
        double price = jsonObject.getDouble("price");
        boolean reserved = jsonObject.getBoolean("reserved");
        Book book = new Book(title, author, price);
        book.setReservedStatus(reserved);

        JSONArray jsonArray = jsonObject.getJSONArray("ratings");
        for (Object rating : jsonArray) {
            Integer nextRating = (Integer) rating;
            book.rateBook(nextRating);
        }
        branch.addBook(book);

        if (sold) {
            branch.sellBook(book);
        }
    }
}
