package todosapp.jubilee.cosc431.towson.edu.projectapp431;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

import edu.towson.cosc431.jubilee.jubilee.projectapp431.Expense;
import edu.towson.cosc431.jubilee.jubilee.projectapp431.User;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    int total = 500;
    @Test
    public void correct_total1() {
        total = 550-50;
        assertEquals(total,500);
    }

    @Test
    public void correct_total2() {
        total = 550-50;
        assertEquals(total,480);
    }

    @Test
    public void correct_total3() {
        total = 460-0;
        assertEquals(total,460);
    }

    @Test
    public void correct_total4() {
        total = 440-40;
        assertEquals(total,440);
    }

    @Test
    public void correct_total5() {
        total = 420-0;
        assertEquals(total,420);
    }

    @Test
    public void correct_fName() {

        // arrange
        User user = Mockito.mock(User.class);

        // act
        String name = user.fName;

        // assert
        assertEquals("David",name);
    }

    @Test
    public void correct_lName() {

        // arrange
        User user = Mockito.mock(User.class);

        // act
        String name = user.lName;

        assertEquals("Johnson",name);
    }

    @Test
    public void correct_email() {

        // arrange
        User user = Mockito.mock(User.class);

        // act
        String email = user.email;

        assertEquals("abc@gmail.com",email);
    }

    @Test
    public void correct_address() {

        // arrange
        User user = Mockito.mock(User.class);

        // act
        String address = user.address;

        assertEquals("7800 York Rd",address);
    }

    @Test
    public void correct_city() {

        // arrange
        User user = Mockito.mock(User.class);

        // act
        String city = user.city;

        assertEquals("Towson",city);
    }

    @Test
    public void correct_state() {

        // arrange
        User user = Mockito.mock(User.class);

        // act
        String state = user.state;

        assertEquals("Maryland",state);
    }

    @Test
    public void correct_category() {
        // arrange
        Expense expense = Mockito.mock(Expense.class);

        // act
        String expense1 = expense.amount;

        assertEquals("200",expense1);
    }

    @Test
    public void correct_groceries() {

        // arrange
        Expense expense = Mockito.mock(Expense.class);

        // act
        String category = expense.category;

        assertEquals("Groceries",category);
    }

    @Test
    public void correct_total14() {

        // arrange
        Expense expense = Mockito.mock(Expense.class);

        // act
        String date = expense.dateSpent;

        assertEquals("5/14/18",date);
    }

    @Test
    public void correct_nameExpense() {

        // arrange
        Expense expense = Mockito.mock(Expense.class);

        // act
        String name = expense.name;

        assertEquals("John",name);
    }
}