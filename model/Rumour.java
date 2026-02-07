package model;

import java.time.LocalDate;

public class Rumour {

    private String id;
    private String title;
    private String source;
    private LocalDate createdDate;
    private int credibilityScore;
    private String status;
    private boolean verified;

    public Rumour(String id, String title, String source) {
        validateId(id);
        this.id = id;
        this.title = title;
        this.source = source;
        this.createdDate = LocalDate.now();
        this.credibilityScore = 100;
        this.status = "normal";
        this.verified = false;
    }

    public Rumour(String id, String title, String source, LocalDate createdDate, int credibilityScore) {
        validateId(id);
        this.id = id;
        this.title = title;
        this.source = source;
        this.createdDate = createdDate;
        this.credibilityScore = credibilityScore;
        this.status = "normal";
        this.verified = false;
    }

    private void validateId(String id) {
        if (id == null || id.length() != 8) {
            throw new IllegalArgumentException("Rumour ID must be 8 digits.");
        }
        if (!id.matches("\\d{8}")) {
            throw new IllegalArgumentException("Rumour ID must contain only digits.");
        }
        if (id.charAt(0) == '0') {
            throw new IllegalArgumentException("Rumour ID cannot start with 0.");
        }
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getSource() { return source; }
    public LocalDate getCreatedDate() { return createdDate; }
    public int getCredibilityScore() { return credibilityScore; }
    public String getStatus() { return status; }
    public boolean isVerified() { return verified; }
    
    public void setStatus(String status) { this.status = status; }
    public void setVerified(boolean verified) { this.verified = verified; }
    public void setCredibilityScore(int score) { this.credibilityScore = score; }

    @Override
    public String toString() {
        return id + " | " + title + " | " + status + " | Score: " + credibilityScore;
    }
}