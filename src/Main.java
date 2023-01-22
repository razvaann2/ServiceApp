public class Main {
    public static void main(String[] args) {

        HomePage homePage = new HomePage(null);
        User user = homePage.user;
        if (user == null) {
            System.out.println("failed");
            return;
        }
        System.out.println("ok");
    }
}