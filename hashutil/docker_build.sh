docker build -t hashutil:1.0 .

docker run --rm \
  --name hashutil \
  -v /Users/xuanchengwei/Desktop/img:/hash/path \
   hashutil:1.0


