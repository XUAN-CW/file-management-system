maximum_rows=100

for table_name in $(sqlite3 file-management-system.db ".tables"); do
   table_rows=$(sqlite3 file-management-system.db "SELECT count(*) FROM ${table_name};")
   sql_file_number=$(($table_rows / $maximum_rows))
   echo "${table_name}  ${table_rows} ${sql_file_number}"
  for i in $(seq 0 sql_file_number); do
    offset=$((i*1000000))
    limit=$((offset+1000000))
#    sqlite3 file-management-system.db ".mode insert" ".once ${table_name}_${i}.sql" "SELECT * FROM ${table_name} LIMIT 1000000 OFFSET ${offset};"
  done
done
