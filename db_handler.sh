sqlite3 file-management-system.db ".dump" > dump.sql
sqlite3 new_db.db < dump.sql
sqlite3 file-management-system.db    "SELECT 'DROP TABLE '||name||';' FROM sqlite_master WHERE type='table' AND name LIKE '_%old%_';"
