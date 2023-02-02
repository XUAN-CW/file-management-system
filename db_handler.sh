sqlite3 file-management-system.db ".dump" > dump.sql
sqlite3 new_db.db < dump.sql
