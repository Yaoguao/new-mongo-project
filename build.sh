
docker-compose down
docker-compose build
docker-compose up -d

sleep 20

docker exec -it $(docker ps -q -f "name=my_mongo_container") /bin/bash -c "./init.sh"