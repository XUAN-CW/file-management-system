# 复制一份
sqlite3 file-management-system.db ".dump" > dump.sql
sqlite3 file-management-system.dump.db < dump.sql && rm -rf dump.sql
# 删除旧的表
sqlite3 file-management-system.dump.db  \
  "SELECT 'DROP TABLE '||name||';' FROM sqlite_master WHERE type='table' AND name LIKE '_%old%_';" > delete_old_table.sql
sqlite3 file-management-system.dump.db < delete_old_table.sql
# 再复制过来
sqlite3 file-management-system.dump.db ".dump" > dump.sql
sqlite3 file-management-system$(date "+%Y%m%d%H%M%S").db < dump.sql

rm -rf dump.sql file-management-system.dump.db

