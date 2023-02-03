# Open the cron file
crontab -e
# 写入下面的任务
00 10 * * * docker container start hashutil
00 22 * * * docker container stop hashutil

