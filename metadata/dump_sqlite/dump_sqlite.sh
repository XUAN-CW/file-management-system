sqlite3 file-management-system.db .schema > file-management-system_schema.sql

maximum_rows=50000
for table_name in $(sqlite3 file-management-system.db ".tables"); do
  table_rows=$(sqlite3 file-management-system.db "SELECT count(*) FROM ${table_name};")
  if [ $table_rows -eq 0 ]; then
    continue
  fi
  sql_file_number=$(($table_rows / $maximum_rows))
  printf "%-30s 表导出 %-8s 行，共 %-4s 个文件\n" ${table_name} ${table_rows} $(($sql_file_number + 1))
  for i in $(seq 0 $sql_file_number); do
    offset=$((i * ${maximum_rows}))
    sqlite3 file-management-system.db ".mode insert" "SELECT * FROM ${table_name} LIMIT ${maximum_rows} OFFSET ${offset};" > "${table_name}_${i}.sql"
  done
done
