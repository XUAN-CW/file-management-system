
docker image rm hashutil:1.1
docker build -t hashutil:1.1 .

docker run --rm \
  --name hashutil \
  -v /share/SINGLE2/8T:/hash/path \
   hashutil:1.1

