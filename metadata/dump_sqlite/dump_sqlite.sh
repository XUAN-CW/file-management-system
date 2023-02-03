maximum_rows=100

for table_name in $(sqlite3 file-management-system.db ".tables"); do
   table_rows=$(sqlite3 file-management-system.db "SELECT count(*) FROM ${table_name};")
  if [ $table_rows -eq 0 ]; then
    echo "${table_name} 没有数据，不导出"
    continue
  else
    echo "${table_name} 导出 ${table_rows} 行"
  fi
   sql_file_number=$(($table_rows / $maximum_rows))
   echo "${table_name}  ${table_rows} ${sql_file_number}"
  for i in $(seq 0 sql_file_number); do
    offset=$((i*1000000))
    limit=$((offset+1000000))
#    sqlite3 file-management-system.db ".mode insert" ".once ${table_name}_${i}.sql" "SELECT * FROM ${table_name} LIMIT 1000000 OFFSET ${offset};"
  done
done
