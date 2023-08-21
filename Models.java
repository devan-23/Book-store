import java.util.List;
import java.util.Scanner;

import Models.Books;
import Models.Orders;
import Models.User;
import Models.Wishlist;

public class BookstoreController implements Runnable {

    public void run() {
        boolean out = true;

        Scanner input = new Scanner(System.in);

        List<Books> allBooks;

        User currentUser = null;

        do {
            if (currentUser == null) {
                boolean check = true;
                List<User> allUsers = Data.getAllUsers();
                System.out.println("\n1.Login");
                System.out.println("2.Signup");
                System.out.println("3.Exit");
                System.out.print("Enter the choice=");
                int ch = input.nextInt();
                if (ch == 3)
                    break;
                System.out.print("Enter the username=");
                input.nextLine();
                String username = input.nextLine();
                System.out.print("Enter the password=");
                String password = input.nextLine();

                if (ch == 1) {
                    for (User users : allUsers) {
                        if (username.equals(users.getUserName()) && password.equals(users.getPassword())) {
                            System.out.print("\n Welcome back, ");
                            System.out.println(username);
                            currentUser = users;
                            check = false;
                            break;
                        }
                    }

                    if (check) {
                        System.out.println("\nThe user doesnt exist\n");
                    }
                }

                else if (ch == 2) {
                    for (User users : allUsers) {
                        if (username.equals(users.getUserName())) {
                            System.out.println("\nThe user already Exist\n");
                            check = false;
                            break;
                        }
                    }
                    if (check) {
                        allUsers.add(new User(
                                username,
                                password,
                                "User"));
                        Data.setAllUsers(allUsers);
                    }
                }

                else if (ch == 3) {
                    System.out.println("Leaving Session....");
                    break;
                }

                else {
                    System.out.println("Invalid choice");
                }

            } else {
                int index = 2;
                System.out.println("\n1.View Catalog");
                System.out.print("2.View a Book.");
                System.out.format("\n%d.View History", ++index);
                System.out.format("\n%d.View Wish list", ++index);
                if (currentUser.getOrders().size() != 0) {
                    System.out.format("\n%d.View Cart", ++index);
                }
                if (currentUser.getPermission().equals("admin")) {
                    index += 2;
                    System.out.format("\n%d.Add new Book", index - 1);
                    System.out.format("\n%d.Add or Remove admin permission", index);
                }
                System.out.format("\n%d.Logout", ++index);
                System.out.print("\nEnter the choice=");
                int ch = input.nextInt();
                boolean flag = true;
                if (ch % index == 0) {
                    System.out.println("\n1.Logout");
                    System.out.println("2.Logout and Exit");
                    System.out.print("Enter the choice =");
                    List<User> allUsers = Data.getAllUsers();
                    ch = input.nextInt();
                    for (int i = 0; i < allUsers.size(); i++) {
                        if (allUsers.get(i).getUserName().equals(currentUser.getUserName())) {
                            allUsers.set(i, currentUser);
                            break;
                        }
                    }
                    currentUser = null;
                    System.out.println("\nLogging out....");
                    if (ch == 2) {
                        System.out.println("Leaving Session....");
                        out = false;
                    }
                }

                else if (ch == 1) {
                    allBooks = Data.getAllBooks();
                    for (int i = 0; i < 149; i++)
                        System.out.print("-");
                    System.out.printf("\n|| %-55s || %-55s || %-14s || %-7s ||\n", "Book Name", "Author",
                            "Quantity", "Price");
                    for (int i = 0; i < 149; i++)
                        System.out.print("-");
                    for (Books book : allBooks) {
                        System.out.printf("\n|| %-55s || %-55s || %-14d || %-7d ||", book.getBookName(), book.getArtistname(), book.getQuantity(), book.getPrice());
                    }
                    System.out.println();
                    for (int i = 0; i < 149; i++)
                        System.out.print("-");
                }

                else if (ch == 2) {
                    allBooks = Data.getAllBooks();
                    System.out.print("Enter the Book name =");
                    input.nextLine();
                    String name = input.nextLine();
                    for (Books book : allBooks) {
                        if (name.equals(book.getBookName())) {
                            for (int i = 0; i < 149; i++)
                                System.out.print("=");
                            System.out.printf("\n|| %-55s || %-55s || %-14s || %-7s ||\n", "Book Name", "Author",
                                    "Quantity", "Price");
                            for (int i = 0; i < 149; i++)
                                System.out.print("=");
                            System.out.printf("\n|| %-55s || %-55s || %-14d || %-7d || \n", book.getBookName(),
                                    book.getArtistname(), book.getQuantity(), book.getPrice());
                            for (int i = 0; i < 149; i++)
                                System.out.print("=");
                            do {
                                System.out.println("\n\n1.View reviews");
                                System.out.println("2.add to cart");
                                System.out.println("3.add review");
                                System.out.println("4.add to wishlist");
                                System.out.println("5.Go Back");
                                System.out.print("Enter the choice=");
                                ch = input.nextInt();
                                switch (ch) {
                                    case 1:
                                        List<String> reviews = book.getReviews();
                                        System.out.println("\nReviews\n");
                                        if (reviews.size() == 0) {
                                            System.out.println("No review Yet");
                                            break;
                                        }
                                        for (String review : reviews) {
                                            System.out.println(review);
                                            System.out.println();
                                        }
                                        break;

                                    case 2:
                                        System.out.print("How many you want to buy?");
                                        int qty = input.nextInt();
                                        currentUser.addToCart(new Orders(
                                                book.getBookName(),
                                                book.getPrice(),
                                                qty));
                                        break;
                                    
                                    case 4:
                                        currentUser.addToWishlist(new Wishlist(
                                            book.getBookName(),
                                            book.getArtistname(),
                                            book.getPrice()
                                        ));
                                        break;

                                    case 3:
                                        System.out.print("Enter the review =");
                                        input.nextLine();
                                        String review = input.nextLine();
                                        book.addReview(review);
                                        break;
                                    case 5:
                                        flag = false;
                                        break;
                                    default:
                                        System.out.println("Invalid choice");
                                }
                            } while (flag);
                        }
                    }
                    if (flag) {
                        System.out.println("\nBook not found");
                    }
                }

                else if(ch == 3) {
                    List<Orders> histories = currentUser.getHistory();
                    if(histories.size() == 0) {
                        System.out.println("No History yet");
                    }
                    else{
                        for (int i = 0; i < 90; i++)
                                System.out.print("=");
                        System.out.printf("\n|| %-55s || %-14s || %-7s ||\n", "Book Name", "Quantity", "Price");
                        for (int i = 0; i < 91; i++)
                                System.out.print("=");
                        histories.forEach((history) -> {
                            System.out.printf("\n|| %-55s || %-14d || %-7d ||", history.getBookName(), history.getQuantity(), history.getPrice());
                        });
                        System.out.println();
                        for (int i = 0; i < 91; i++)
                                System.out.print("=");
                    }
                }

                else if (ch == 4) {
                    List<Wishlist> wishes = currentUser.getWishes();
                    if(wishes.size() == 0) {
                        System.out.println("No books in wish list");
                        continue;
                    }
                    do {
                        if(currentUser.getWishes().size() == 0) {
                            System.out.println("Wishlist is empty");
                            break;
                        }
                        for (int i = 0; i < 131; i++)
                            System.out.print("-");
                        System.out.printf("\n|| %-55s || %-55s || %-7s ||\n", "Book Name", "Author", "Price");
                        for (int i = 0; i < 131; i++)
                            System.out.print("-");
                        for (Wishlist list : wishes) {
                            System.out.printf("\n|| %-55s || %-55s || %-7d ||", list.getBname(), list.getAname(), list.getPrice());
                        }
                        System.out.println();
                        for (int i = 0; i < 131; i++)
                            System.out.print("-");
                        
                        System.out.println("\n1.Remove from wishlist");
                        System.out.println("2.Go back");
                        System.out.print("Enter the choice =");
                        ch= input.nextInt();
                        if(ch==1) {
                            input.nextLine();
                            System.out.println("Enter the book name=");
                            String name = input.nextLine();
                            name = currentUser.removeFromWishList(name);
                            System.out.println(name);
                        }
                    }while (ch != 2);   
                }

                else if (ch == 5 && currentUser.getOrders().size() != 0) {

                    do {
                        List<Orders> orders = currentUser.getOrders();
                        for (int i = 0; i < 90; i++)
                                System.out.print("=");
                        System.out.printf("\n|| %-55s || %-14s || %-7s ||\n", "Book Name", "Quantity", "Price");
                        for (int i = 0; i < 91; i++)
                                System.out.print("=");
                        orders.forEach((order) -> {
                            System.out.printf("\n|| %-55s || %-14d || %-7d ||", order.getBookName(), order.getQuantity(), order.getPrice());
                        });
                        System.out.println();
                        for (int i = 0; i < 91; i++)
                                System.out.print("=");
                         System.out.println("\n 1. proceed to checkout.");
                         System.out.println("2.remove item from cart.");
                         System.out.println("3.Clear cart");
                         System.out.println("4.Go back");
                         System.out.print("Enter the choice =");
                         ch = input.nextInt();
                         switch(ch) {
                            case 1:
                                checkout(currentUser);
                                flag = false;
                                break;
                            
                            case 2:
                                Boolean exist = false;
                                System.out.print("Enter the name of book to be removed");
                                input.nextLine();
                                String name = input.nextLine();
                                for(int i=0; i<orders.size(); i++) {
                                    if(orders.get(i).getBookName().equals(name)) {
                                        orders.remove(i);
                                        exist=true;
                                        currentUser.setOrders(orders);
                                        break;
                                    }
                                }
                                if(!exist) {
                                    System.out.println("The book doesnt exist in your cart.");
                                }
                                if(orders.size() == 0) {
                                    System.out.println("The cart is now Empty ....");
                                    flag = false ;
                                }
                                break;

                            case 3:
                                System.out.println("The cart is cleared");
                                orders.clear();
                                currentUser.setOrders(orders);

                            case 4:
                                flag = false;
                                break;
                            
                            default:
                                System.out.println("Invalid choice");
                         }
                    }while(flag);
                }

                else if(currentUser.getPermission().equals("admin") && (ch == 5 || (ch == 6 && currentUser.getOrders().size() !=0))) {
                    System.out.print("Enter the name of the book =");
                    input.nextLine();
                    String name = input.nextLine();
                    System.out.print("Enter the name of the author of the book =");
                    String aname = input.nextLine();
                    System.out.print("Enter the no of copies of the book =");
                    int Qty = input.nextInt();
                    System.out.print("Enter the price of the book =");
                    int price = input.nextInt();
                    Data.getAllBooks().add(new Books(
                        name, aname, price, Qty
                    ));
                }

               

                else if (currentUser.getPermission().equals("admin") && (ch == 6 || (ch == 7 && currentUser.getOrders().size() !=0))) {
                    System.out.println("1.Add");
                    System.out.println("2.Remove");
                    System.out.println("3.Go back");
                    System.out.print("Enter the choice");
                    boolean checker =false;
                    ch = input.nextInt();
                    if(ch == 3 || (ch!=1 && ch!=2)) {
                        System.out.println("Invalid choice");
                        continue;
                    }
                    System.out.println("Enter username of person to change permission =");
                    input.nextLine();
                    String name = input.nextLine();
                    if(currentUser.getUserName().equals(name)) {
                        System.out.println("You cannot change your own permission");
                    }
                    else{
                        for(User user: Data.getAllUsers()) {
                            if(user.getUserName().equals(name)) {
                                if(ch==1)user.setPermission("admin");
                                if(ch==2)user.setPermission("user");
                                checker = true;
                            }
                        }
                        if(!checker) {
                            System.out.println("The username not found");
                        }
                    }
                }

                 else {
                    System.out.println("\nInvalid choice");
                }
            }
        } while (out);

        input.close();
    }

    synchronized public void checkout(User user) {
        Scanner input = new Scanner(System.in);
        int total = 0;
        List<Books> allBooks = Data.getAllBooks();
        List<Orders> orders = user.getOrders();
        for(int i=0; i<user.getOrders().size(); i++) {
            Boolean available = true;
            for(Books book : allBooks) {
                if(book.getBookName().equals(orders.get(i).getBookName()) && book.getQuantity() < orders.get(i).getQuantity()) {
                    System.out.printf("\n%s currently dont have enough copy in stock and have been removed from cart.", book.getBookName());
                    orders.remove(i);
                    i=i-1;
                    available = false;
                    break;
                }
            }
            if(available) {
                total = total + (orders.get(i).getQuantity() * orders.get(i).getPrice());
            }
        }
        user.setOrders(orders);
        if(orders.size() == 0) {
            System.out.println("Cart is Empty...");
            return;
        }
        System.out.println("\nItems in cart");
        for (int i = 0; i < 90; i++)
            System.out.print("=");
        System.out.printf("\n|| %-55s || %-14s || %-7s ||\n", "Book Name", "Quantity", "Price");
        for (int i = 0; i < 91; i++)
            System.out.print("=");
        orders.forEach((order) -> {
            System.out.printf("\n|| %-55s || %-14d || %-7d ||", order.getBookName(), order.getQuantity(), order.getPrice());
        });
        System.out.println();
        for (int i = 0; i < 91; i++)
            System.out.print("=");
        
        System.out.printf("\n1.Proceed to pay %d and purchase books", total);
        System.out.println("\n2.go back");
        System.out.print("Enter the choice =");
        int ch = input.nextInt();
        switch (ch) {
            case 1:
                for(int i=0; i<user.getOrders().size(); i++) {
                    for(Books book : allBooks) {
                        if(book.getBookName().equals(orders.get(i).getBookName())) {
                            book.setQuantity(book.getQuantity() - orders.get(i).getQuantity());
                            user.getHistory().add(0, orders.get(i));
                            break;
                        }
                    }
                }
                orders.clear();
                user.setOrders(orders);
                return;

            case 2:
                return;

            default:
                System.out.println("Invalid choice");
        }
    }
}
