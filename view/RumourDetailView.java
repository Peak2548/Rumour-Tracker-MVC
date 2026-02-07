package view;

import model.Rumour;

public class RumourDetailView {

    public void display(Rumour r, int reportCount) {
        System.out.println("\n========================================");
        System.out.println("           Rumour Details");
        System.out.println("========================================");
        
        System.out.println("\n  ID            : " + r.getId());
        System.out.println("  Title         : " + r.getTitle());
        System.out.println("  Source        : " + r.getSource());
        System.out.println("  Created Date  : " + r.getCreatedDate());
        System.out.println("  Score         : " + r.getCredibilityScore());
        System.out.println("  Status        : " + getStatusWithIcon(r.getStatus()));
        System.out.println("  Report Count  : " + reportCount + " reports");
        System.out.println("  Verified      : " + (r.isVerified() ? "Yes" : "No"));
        
        System.out.println("========================================\n");
    }
    
    private String getStatusWithIcon(String status) {
        if (status.equals("panic")) {
            return "[!] PANIC (High Alert)";
        } else if (status.equals("verified-true")) {
            return "[V] Verified TRUE News";
        } else if (status.equals("verified-false")) {
            return "[X] Verified FALSE News";
        } else {
            return "[ ] Normal";
        }
    }
}