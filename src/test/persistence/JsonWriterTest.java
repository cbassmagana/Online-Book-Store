package persistence;

import model.Company;
import model.Branch;
import model.Book;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// Unit tests for class JsonWriter
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Company company = new Company("Bookmania");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterEmptyCompany() {
        try {
            Company company = new Company("Bookmania");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCompany.json");
            writer.open();
            writer.write(company);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCompany.json");
            company = reader.read();
            assertEquals("Bookmania", company.getName());
            assertEquals(0, company.getBranches().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @SuppressWarnings("methodlength")
    @Test
    void testWriterUsedCompany() {
        try {
            Company company = new Company("Bookmania");

            Book HP = new Book("Harry Potter", "JK Rowling", 10.99);
            HP.setReservedStatus(true);
            Book PJ = new Book("Percy Jackson", "Rick Riordan", 12.99);
            PJ.rateBook(3);
            Book GOT = new Book("Game of Thrones", "George Martin", 11.99);
            Book LOTR = new Book("Lord of the Rings", "Tolkien", 19.98);
            LOTR.rateBook(4);
            LOTR.rateBook(3);

            Branch VB = new Branch("Vancouver Books", "123 Granville St");
            VB.addBook(HP);
            VB.addBook(LOTR);
            Branch SB = new Branch("Surrey Books", "123 Cloverdale Ave");
            SB.addBook(PJ);
            SB.addBook(GOT);
            SB.sellBook(GOT);

            company.addBranch(VB);
            company.addBranch(SB);

            JsonWriter writer = new JsonWriter("./data/testWriterUsedCompany.json");
            writer.open();
            writer.write(company);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterUsedCompany.json");
            company = reader.read();
            assertEquals("Bookmania", company.getName());

            List<Branch> branches = company.getBranches();
            assertEquals(2, branches.size());

            checkBranch("Vancouver Books", "123 Granville St", branches.get(0));
            assertEquals(2, branches.get(0).getInventory().size());
            assertEquals(0, branches.get(0).getBooksSold().size());
            checkBranch("Surrey Books", "123 Cloverdale Ave", branches.get(1));
            assertEquals(1, branches.get(1).getInventory().size());
            assertEquals(1, branches.get(1).getBooksSold().size());

            ArrayList<Integer> noRatings = new ArrayList<>();
            noRatings.add(3);

            checkBook("Harry Potter","JK Rowling",10.99, true,
                    new ArrayList<>(), branches.get(0).getInventory().get(0));
            checkBook("Percy Jackson","Rick Riordan",12.99, false,
                    noRatings, branches.get(1).getInventory().get(0));
            checkBook("Game of Thrones","George Martin",11.99, false,
                    new ArrayList<>(), branches.get(1).getBooksSold().get(0));
            noRatings.add(0, 4);
            checkBook("Lord of the Rings","Tolkien",19.98, false,
                    noRatings, branches.get(0).getInventory().get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
