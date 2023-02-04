
docker image rm hashutil:1.0
docker build -t hashutil:1.0 .

docker run --rm \
  --name hashutil \
  -v /share/single1/16T:/hash/path/20230203155141 \
   hashutil:1.0

