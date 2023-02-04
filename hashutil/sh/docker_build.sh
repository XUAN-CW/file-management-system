
docker image rm hashutil:1.0
docker build -t hashutil:1.0 .

docker run --rm \
  --name hashutil2 \
  -v /share/SINGLE2/8T:/hash/path \
   hashutil:1.0

