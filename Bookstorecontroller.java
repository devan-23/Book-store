import java.util.ArrayList;
import java.util.List;

import Models.Books;
import Models.User;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter 
public class Data {

    @Getter
    @Setter 
    private static List<User> allUsers = new ArrayList<>();

    @Getter 
    @Setter 
    private static List<Books> allBooks = new ArrayList<>();


    public void init() {
        List<User> allUser = getAllUsers();
        List<Books> allBook = getAllBooks();

        allUser.add(new User(
            "root",
            "root",
            "admin"
        ));

        allUser.add(new User(
            "user1",
            "user1",
            "user"
        ));

        allBook.add(new Books(
            "In Search of Lost Time",
            "Marcel Proust",
            2000,
            20
        ));

        allBook.add(new Books(
            "Ulysses",
            "James Joyce",
            1750,
            30
        ));

        allBook.add(new Books(
            "Don Quixote",
            "Miguel de Cervantes",
            1500,
            40
        ));

        allBook.add(new Books(
            "The Great Gatsby",
            "F. Scott Fitzgerald",
            1250,
            50
        ));

        allBook.add(new Books(
            "Moby Dick",
            "Herman Melville",
            1000,
            60
        ));

        setAllUsers(allUser);

        allUser=getAllUsers();
    }
}
