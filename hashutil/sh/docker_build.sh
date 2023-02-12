
docker image rm hashutil:1.1
docker build -t hashutil:1.1 .

docker run --rm -d \
  --name hashutil_SINGLE2 \
  -v /share/SINGLE2:/hash/path \
   hashutil:1.1

docker run --rm -d \
  --name hashutil_single1 \
  -v /share/single1:/hash/path \
   hashutil:1.1

docker run --rm -d \
  --name hashutil_RAID1 \
  -v /share/RAID1:/hash/path \
   hashutil:1.1


docker run --rm -d \
  --name hashutil_selecting \
  -v /share/RAID1/筛选中:/hash/path \
   hashutil:1.1




