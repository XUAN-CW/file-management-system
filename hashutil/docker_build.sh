
docker image rm hashutil:1.0
docker build -t hashutil:1.0 .

docker run --rm \
  --name hashutil \
  -v /Users/xuanchengwei/Desktop/test-file/20230203145655:/hash/path/20230203145655 \
  -v /Users/xuanchengwei/Desktop/test-file/20230203145652:/hash/path/20230203145652 \
   hashutil:1.0

