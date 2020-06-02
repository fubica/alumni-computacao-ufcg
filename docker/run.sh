#!/bin/bash

WORK_DIR=$(pwd)

# Remove container from earlier installation
sudo docker stop alumni
sudo docker rm alumni

# Create container

sudo docker run -idt --name "alumni" \
	-p 80:8080 \
	-v $WORK_DIR/src/main/resources/private:/root/membership-service/src/main/resources/private \
	fubica/alumni-computacao-ufcg:master

sudo docker exec alumni /bin/bash -c "./mvnw spring-boot:run -X > log.out 2> log.err" &