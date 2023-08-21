public class App {
    public static void main(String[] args) throws Exception {

        Data d = new Data();
        d.init();

        Thread session = new Thread(new BookstoreController());
        session.start();

    }
}
