package view;

import model.Rumour;
import java.util.List;

public class RumourListView {

    public void display(List<Rumour> rumours) {
        System.out.println("\n========================================");
        System.out.println("           All Rumours");
        System.out.println("    (Sorted by Report Count: High -> Low)");
        System.out.println("========================================");
        
        if (rumours.isEmpty()) {
            System.out.println("\n   No rumours in the system.");
            return;
        }
        
        System.out.println();
        System.out.printf("%-10s %-35s %-15s %-8s%n", "ID", "Title", "Status", "Score");
        System.out.println("---------------------------------------------------------------------------");
        
        for (Rumour r : rumours) {
            String statusIcon = getStatusIcon(r.getStatus());
            String title = truncate(r.getTitle(), 35);
            
            System.out.printf("%-10s %-35s %-15s %-8s%n", 
                r.getId(), 
                title,
                statusIcon + " " + r.getStatus(),
                r.getCredibilityScore()
            );
        }
        
        System.out.println("---------------------------------------------------------------------------\n");
    }
    
    private String getStatusIcon(String status) {
        if (status.equals("panic")) {
            return "[!]";
        } else if (status.equals("verified-true")) {
            return "[V]";
        } else if (status.equals("verified-false")) {
            return "[X]";
        } else {
            return "[ ]";
        }
    }
    
    private String truncate(String text, int maxLength) {
        if (text == null) return "";
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - 3) + "...";
    }
}