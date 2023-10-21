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

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Company read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCompany(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines( Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Company parseCompany(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Company company = new Company(name);
        addBranches(company, jsonObject);
        return company;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addBranches(Company company, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("branches");
        for (Object branch : jsonArray) {
            JSONObject nextBranch = (JSONObject) branch;
            addBranch(company, nextBranch);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addBranch(Company company, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String address = jsonObject.getString("address");
        Branch branch = new Branch(name, address);

        addBooksInventory(company, jsonObject);
        addBooksSold(company, jsonObject);
    }


    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addBooksInventory(Company company, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("inventory");
        for (Object book : jsonArray) {
            JSONObject nextBook = (JSONObject) book;
            addBook(company, nextBook);
        }
    }


    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addBooksSold(Company company, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("sales");
        for (Object book : jsonArray) {
            JSONObject nextBook = (JSONObject) book;
            addBook(company, nextBook);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addBook(Company company, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String author = jsonObject.getString("author");
        double price = jsonObject.getDouble("price");
        boolean reserved = jsonObject.getBoolean("reserved");
        Book book = new Book(title, author, price);
        book.setReservedStatus(reserved);

//        JSONArray jsonArray = jsonObject.getJSONArray("ratings");
//        for (Object rating : jsonArray) {
//            JSONObject nextRating = (JSONObject) rating;
//            Integer nextIntRating = Integer.parseInt(String.valueOf(nextRating));
//
//
//            book.rateBook(nextIntRating);
//        }

    }
}