package view;

import model.Rumour;
import java.util.List;

public class SummaryView {

    public void display(List<Rumour> panicRumours, List<Rumour> verifiedRumours, List<Rumour> unverifiedRumours) {
        System.out.println("\n========================================");
        System.out.println("           Rumour Summary");
        System.out.println("========================================");
        

        System.out.println("\n[!] Panic Rumours:");
        System.out.println("----------------------------------------");
        if (panicRumours.isEmpty()) {
            System.out.println("   - None -");
        } else {
            for (Rumour r : panicRumours) {
                System.out.println("   [!] ID: " + r.getId() + " | " + r.getTitle());
            }
        }
        
        System.out.println("\n[V] Verified Rumours:");
        System.out.println("----------------------------------------");
        if (verifiedRumours.isEmpty()) {
            System.out.println("   - None -");
        } else {
            for (Rumour r : verifiedRumours) {
                String badge = r.getStatus().equals("verified-true") ? "[TRUE]" : "[FALSE]";
                System.out.println("   " + badge + " ID: " + r.getId() + " | " + r.getTitle());
            }
        }

        System.out.println("\n[?] Unverified / Normal Rumours:");
        System.out.println("----------------------------------------");
        if (unverifiedRumours.isEmpty()) {
            System.out.println("   - None -");
        } else {
            for (Rumour r : unverifiedRumours) {
                System.out.println("   [ ] ID: " + r.getId() + " | " + r.getTitle() + " (Score: " + r.getCredibilityScore() + ")");
            }
        }
        
        System.out.println("========================================\n");
    }
}