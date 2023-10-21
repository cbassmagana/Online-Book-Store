package persistence;

import model.Company;
import model.Branch;
import model.Book;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for class JsonReader
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Company c = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCompany() {
        JsonReader reader = new JsonReader("./data/testEmptyCompany.json");
        try {
            Company company = reader.read();
            assertEquals("Bookmania", company.getName());
            ArrayList<Branch> noBranches = new ArrayList<Branch>();
            assertEquals(noBranches, company.getBranches());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderUsedCompany() {
        JsonReader reader = new JsonReader("./data/testUsedCompany.json");
        try {
            Company company = reader.read();
            assertEquals("Bookmania", company.getName());
            List<Branch> branches = company.getBranches();
            assertEquals(2, branches.size());

            checkBranch("Vancouver Books", "123 Granville St", branches.get(0));
            assertEquals(2, branches.get(0).getInventory().size());
            assertEquals(0, branches.get(0).getBooksSold().size());
            checkBranch("Surrey Books", "123 Cloverdale Ave", branches.get(1));
            assertEquals(1, branches.get(1).getInventory().size());
            assertEquals(1, branches.get(1).getBooksSold().size());

            ArrayList<Integer> noRatings = new ArrayList<Integer>();
            noRatings.add(3);

            checkBook("Harry Potter","JK Rowling",10.99, true,
                    new ArrayList<Integer>(), branches.get(0).getInventory().get(0));

            checkBook("Percy Jackson","Rick Riordan",12.99, false,
                    noRatings, branches.get(1).getInventory().get(0));

            checkBook("Game of Thrones","George Martin",11.99, false,
                    new ArrayList<Integer>(), branches.get(1).getBooksSold().get(0));

            noRatings.add(0, 4);

            checkBook("Lord of the Rings","Tolkien",19.98, false,
                    noRatings, branches.get(0).getInventory().get(1));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}