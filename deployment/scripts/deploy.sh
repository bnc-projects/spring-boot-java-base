#!/bin/bash

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
  echo "Missing argument"
  echo "Usage : scripts/deploy.sh [development/production] [profile]"
  exit
fi


#environment=production
environment=$1

if [ -z "$2" ]
then
  aws_profile=default
else
  aws_profile=$2
fi

echo "Environment is : " ${environment}
echo "AWS Profile is is : " ${aws_profile}

source scripts/common/cfn-common.sh

for region in "${regions[@]}"
do
  createOrUpdateStack SbjbECRRepository repository
  syncBaseHealthcheckImage sbjb-service
  createOrUpdateStack SBJBECSService service
done
