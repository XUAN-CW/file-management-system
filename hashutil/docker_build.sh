
docker image rm hashutil:1.0
docker build -t hashutil:1.0 .

docker run --rm \
  --name hashutil \
  -v /Users/xuanchengwei/Desktop/test-file/20230203155141:/hash/path/20230203155141 \
  -v /Users/xuanchengwei/Desktop/test-file/20230203155151:/hash/path/20230203155151 \
   hashutil:1.0

