for table_name in $(sqlite3 file-management-system.db ".tables"); do
  for i in $(seq 0 1); do
    offset=$((i*1000000))
    limit=$((offset+1000000))
    sqlite3 file-management-system.db ".mode insert" ".once ${table_name}_${i}.sql" "SELECT * FROM ${table_name} LIMIT 1000000 OFFSET ${offset};"
  done
done
