package controller;

import java.util.List;

import model.*;
import view.*;

public class RumourController {

    private RumourModel model;
    private RumourListView listView;
    private RumourDetailView detailView;
    private SummaryView summaryView;

    public RumourController(RumourModel model) {
        this.model = model;
        this.listView = new RumourListView();
        this.detailView = new RumourDetailView();
        this.summaryView = new SummaryView();
    }

    public void showAllRumours() {
        listView.display(model.getAllRumours());
    }

    public void showDetail(String id) {
        Rumour r = model.findRumour(id);
        if (r == null) {
            System.out.println("Rumour not found.");
            return;
        }
        int count = model.countReports(id);
        detailView.display(r, count);
    }

    public void report(String userId, String rumourId, String type) {
        boolean ok = model.addReport(userId, rumourId, type);

        if (ok) {
            DatabaseHandler db = new DatabaseHandler();
            db.saveReport(userId, rumourId, type);
            
            Rumour r = model.findRumour(rumourId);
            if (r.getStatus().equals("panic")) {
                db.updateRumourStatus(r.getId(), "panic", r.isVerified());
            }

            System.out.println("Report successful and saved.");
        } else {
            System.out.println("Report failed (Already reported or verified).");
        }
    }

    public void postRumour(String title, String source) {
        long number = (long) (Math.floor(Math.random() * 90000000L) + 10000000L);
        String newId = String.valueOf(number);

        Rumour newRumour = new Rumour(newId, title, source);

        model.addRumour(newRumour);

        DatabaseHandler db = new DatabaseHandler();
        db.saveRumour(newRumour);
        
        System.out.println("Success! New rumour posted. ID: " + newId);
    }

    public void verifyRumour(String rumourId, boolean isTrueNews) {
        boolean ok = model.verifyRumour(rumourId, isTrueNews);
        
        if (ok) {
            Rumour r = model.findRumour(rumourId);
            DatabaseHandler db = new DatabaseHandler();
            db.updateRumourStatus(r.getId(), r.getStatus(), r.isVerified());
            
            System.out.println("Verification successful and saved.");
        } else {
            System.out.println("Rumour not found.");
        }
    }

    public void showAllUsers() {
        List<User> users = model.getUsers();
        System.out.println("\n========================================");
        System.out.println("             User List");
        System.out.println("========================================");
        System.out.printf("%-6s %-15s %-15s%n", "ID", "Name", "Role");
        System.out.println("----------------------------------------");
        
        for (User u : users) {
            System.out.printf("%-6s %-15s %-15s%n", u.getId(), u.getName(), u.getRole());
        }
        System.out.println("========================================\n");
    }

    public void showSummary() {
        List<Rumour> panic = model.getPanicRumours();
        List<Rumour> verified = model.getVerifiedRumours();
        List<Rumour> unverified = model.getUnverifiedRumours();
        
        summaryView.display(panic, verified, unverified);
    }
}