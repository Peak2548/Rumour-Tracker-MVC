package model;

import java.util.*;

public class RumourModel {

    private List<Rumour> rumours = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<Report> reports = new ArrayList<>();

    private final int PANIC_THRESHOLD = 3;

    public void addRumour(Rumour r) {
        rumours.add(r);
    }

    public void addUser(User u) {
        users.add(u);
    }

    public List<Rumour> getAllRumours() {
        List<Rumour> sorted = new ArrayList<>(rumours);
        sorted.sort((r1, r2) -> {
            int count1 = countReports(r1.getId());
            int count2 = countReports(r2.getId());
            return Integer.compare(count2, count1);
        });
        return sorted;
    }

    public List<Rumour> getRumoursByCredibility() {
        List<Rumour> sorted = new ArrayList<>(rumours);
        sorted.sort((r1, r2) -> Integer.compare(r2.getCredibilityScore(), r1.getCredibilityScore()));
        return sorted;
    }

    public boolean addReport(String userId, String rumourId, String type) {
        Rumour rumour = findRumour(rumourId);
        
        if (rumour == null) {
            return false;
        }

        if (rumour.isVerified()) {
            return false;
        }

        if (alreadyReported(userId, rumourId)) {
            return false;
        }

        reports.add(new Report(userId, rumourId, type));

        int count = countReports(rumourId);
        if (count >= PANIC_THRESHOLD) {
            rumour.setStatus("panic");
        }

        return true;
    }

    public Rumour findRumour(String id) {
        for (Rumour r : rumours) {
            if (r.getId().equals(id)) {
                return r;
            }
        }
        return null;
    }

    public int countReports(String rumourId) {
        int count = 0;
        for (Report r : reports) {
            if (r.getRumourId().equals(rumourId)) {
                count++;
            }
        }
        return count;
    }

    private boolean alreadyReported(String userId, String rumourId) {
        for (Report r : reports) {
            if (r.getUserId().equals(userId) && r.getRumourId().equals(rumourId)) {
                return true;
            }
        }
        return false;
    }

    public List<Rumour> getPanicRumours() {
        List<Rumour> result = new ArrayList<>();
        for (Rumour r : rumours) {
            if (r.getStatus().equals("panic")) {
                result.add(r);
            }
        }
        return result;
    }

    public List<Rumour> getVerifiedRumours() {
        List<Rumour> result = new ArrayList<>();
        for (Rumour r : rumours) {
            if (r.isVerified()) {
                result.add(r);
            }
        }
        return result;
    }

    public boolean verifyRumour(String rumourId, boolean isTrueNews) {
        Rumour rumour = findRumour(rumourId);
        if (rumour == null) {
            return false;
        }
        rumour.setVerified(true);
        rumour.setStatus(isTrueNews ? "verified-true" : "verified-false");
        return true;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Rumour> getUnverifiedRumours() {
        List<Rumour> result = new ArrayList<>();
        for (Rumour r : rumours) {
            if (r.getStatus().equals("normal")) {
                result.add(r);
            }
        }
        return result;
    }

    public List<Report> getAllReports() {
        return new ArrayList<>(reports);
    }
}