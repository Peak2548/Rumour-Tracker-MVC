import model.*;
import controller.*;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        
        RumourModel model = new RumourModel();
        DatabaseHandler db = new DatabaseHandler();
        
        System.out.println("... Connecting to Database ...");

        db.initializeDatabase();

        List<User> dbUsers = db.getAllUsers();
        for (User u : dbUsers) {
            model.addUser(u);
        }
        
        List<Rumour> dbRumours = db.getAllRumours();
        for (Rumour r : dbRumours) {
            model.addRumour(r);
        }
        
        List<Report> dbReports = db.getAllReports();
        for (Report rp : dbReports) {
            model.addReport(rp.getUserId(), rp.getRumourId(), rp.getType());
        }
        
        System.out.println("... Data Loaded Successfully! (" + dbRumours.size() + " rumours)\n");

        RumourController controller = new RumourController(model);
        showMenu(controller);
    }
    
    private static void showMenu(RumourController controller) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n========================================");
            System.out.println("   Rumour Tracker System");
            System.out.println("========================================");
            System.out.println("  1. Show All Rumours");
            System.out.println("  2. View Rumour Details");
            System.out.println("  3. Report a Rumour");
            System.out.println("  4. Verify Rumour (Admin/Fact-checker)");
            System.out.println("  5. Show Summary");
            System.out.println("  6. Show All Users");
            System.out.println("  7. Post a New Rumour");
            System.out.println("  0. Exit");
            System.out.println("========================================");
                    
            if (!sc.hasNextInt()) {
                System.out.println("\n  Please enter a valid number.\n");
                sc.nextLine(); 
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine(); 
            
            if (choice == 1) {
                controller.showAllRumours();
            } else if (choice == 2) {
                System.out.print("  Enter Rumour ID (8 digits): ");
                String id = sc.nextLine();
                controller.showDetail(id);
            } else if (choice == 3) {
                System.out.print("  User ID: ");
                String userId = sc.nextLine();
                System.out.print("  Rumour ID: ");
                String rumourId = sc.nextLine();
                System.out.print("  Type (Fake/Misleading/Hate Speech): ");
                String type = sc.nextLine();
                controller.report(userId, rumourId, type);
            } else if (choice == 4) {
                System.out.print("  Rumour ID: ");
                String vId = sc.nextLine();
                System.out.print("  Is this rumour TRUE news? (true/false): ");
                boolean isTrue = sc.nextBoolean();
                controller.verifyRumour(vId, isTrue);
            } else if (choice == 5) {
                    controller.showSummary();
            } else if (choice == 6) {
                controller.showAllUsers();
            } else if (choice == 7) {
                System.out.println("\n--- Post New Rumour ---");
                System.out.print("  Enter Title/Headline: ");
                String title = sc.nextLine();
                System.out.print("  Enter Source (e.g. Facebook, Twitter): ");
                String source = sc.nextLine();
                controller.postRumour(title, source);
            } else if (choice == 0) {
                System.out.println("\n  Goodbye!\n");
                sc.close();
                return;
            } else {
                System.out.println("\n  Invalid choice. Please select 0-5.\n");
            }
        }
    }
}