#!/bin/bash

#test
#. .env

echo $TAG

# echo "Performing a clean Maven build"
./mvnw clean package -DskipTests=true  -Drevision=$TAG || exit


#echo "Building the attestation service"
cd auth_microservice
docker build -t auth_microservice:$TAG .
cd ..

#echo "Building the app manager"
cd prod_mng_microservice
docker build -t prod_mng_microservice:$TAG .
cd ..