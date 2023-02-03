
docker run --rm \
  --name hashutil \
  -v /Users/xuanchengwei/Desktop/test-file/20230203145655:/hash/path/20230203145655 \
  -v /Users/xuanchengwei/Desktop/test-file/20230203145652:/hash/path/20230203145652 \
   hashutil:1.0


# Open the cron file
crontab -e
# 写入下面的任务
00 10 * * * docker container start hashutil
00 22 * * * docker container stop hashutil