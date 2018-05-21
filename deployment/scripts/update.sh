#!/bin/bash

set -o errexit -o xtrace

regions=(
#  ap-northeast-1
#  ap-southeast-1
#  ap-southeast-2
#  eu-central-1
#  eu-west-1
#  us-east-1
#  us-east-2
   us-west-2
)

if [ $# -eq 0 ]
  then
    echo "Usage : Missing argument. Accepted arguments development/production"
    exit
fi

echo "Running for : " $1

for region in "${regions[@]}"
do

   aws cloudformation update-stack --stack-name sbjbECSService --template-body file://templates/service.yaml \
  --parameters file://scripts/$1/parameters-service.json --capabilities CAPABILITY_IAM $2 || true

  aws cloudformation wait stack-update-complete --stack-name sbjbECSService $2

  aws cloudformation update-stack --stack-name sbjbCacheECSDeployment --template-body file://templates/deployment-pipeline.yaml \
  --parameters file://scripts/$1/parameters.json --capabilities CAPABILITY_IAM $2

done
