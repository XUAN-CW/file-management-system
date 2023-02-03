#source ~/.bash_profile
#mvn clean package

docker image rm hashutil:1.0
docker build -t hashutil:1.0 .

docker run --rm \
  --name hashutil \
  -v /Users/xuanchengwei/Desktop/test-file:/hash/path \
   hashutil:1.0


