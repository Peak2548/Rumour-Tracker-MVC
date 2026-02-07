-- สร้างตาราง Users
CREATE TABLE IF NOT EXISTS users (
    id TEXT PRIMARY KEY, 
    name TEXT NOT NULL, 
    role TEXT NOT NULL
);

-- สร้างตาราง Rumours
CREATE TABLE IF NOT EXISTS rumours (
    id TEXT PRIMARY KEY, 
    title TEXT NOT NULL, 
    source TEXT, 
    created_date TEXT, 
    credibility_score INTEGER, 
    status TEXT, 
    verified INTEGER
);

-- สร้างตาราง Reports
CREATE TABLE IF NOT EXISTS reports (
    user_id TEXT, 
    rumour_id TEXT, 
    report_date TEXT, 
    type TEXT, 
    PRIMARY KEY (user_id, rumour_id)
);

-- ข้อมูลตัวอย่าง
INSERT INTO users VALUES ('U001', 'Alice', 'General User');
INSERT INTO users VALUES ('U003', 'Charlie', 'Fact Checker');
INSERT INTO rumours VALUES ('10000001', 'Aliens in NY', 'Twitter', '2023-10-25', 10, 'panic', 0);
INSERT INTO rumours VALUES ('30000003', 'Public Holiday', 'Gov Web', '2023-10-20', 100, 'verified-true', 1);