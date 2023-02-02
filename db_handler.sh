sqlite3 file-management-system.db ".dump" > dump.sql
sqlite3 new_db.db < dump.sql && rm -rf dump.sql
sqlite3 file-management-system.db    "SELECT 'DROP TABLE '||name||';' FROM sqlite_master WHERE type='table' AND name LIKE '_%old%_';" > delete_old_table.sql
sqlite3 new_db.db < delete_old_table.sql
rm -rf dump.sql new_db.db
sqlite3 file-management-system.db ".dump" > dump.sql
sqlite3 new_db.db < dump.sql