#!/usr/bin/env bash

updateStack()
{
  STACK_NAME=$1
  TEMPLATE=$2
  PARAMS_JSON=scripts/${environment}/parameters-${TEMPLATE//\//-}.json

  echo "Updating stack - ${STACK_NAME}"

  if [ -e ${PARAMS_JSON} ]
  then
    aws cloudformation update-stack --stack-name ${STACK_NAME} --template-body file://templates/${TEMPLATE}.yaml \
    --parameters file://${PARAMS_JSON} --capabilities CAPABILITY_IAM --profile=${aws_profile} && \
    aws cloudformation wait stack-update-complete --stack-name ${STACK_NAME} --profile=${aws_profile} || true
  else
    aws cloudformation update-stack --stack-name ${STACK_NAME} --template-body file://templates/${TEMPLATE}.yaml \
    --capabilities CAPABILITY_IAM --profile=${aws_profile} && \
    aws cloudformation wait stack-update-complete --stack-name ${STACK_NAME} --profile=${aws_profile} || true
  fi
}

createStack()
{
  STACK_NAME=$1
  TEMPLATE=$2
  PARAMS_JSON=scripts/${environment}/parameters-${TEMPLATE//\//-}.json

  echo "Creating stack - ${STACK_NAME}"

  if [ -e ${PARAMS_JSON} ]
  then
    aws cloudformation create-stack --timeout-in-minutes 30 --enable-termination-protection --timeout-in-minutes 30 --stack-name ${STACK_NAME} --template-body file://templates/${TEMPLATE}.yaml \
    --parameters file://${PARAMS_JSON} --capabilities CAPABILITY_IAM --profile=${aws_profile} && aws cloudformation wait stack-create-complete --stack-name ${STACK_NAME} --profile=${aws_profile} || true
  else
    aws cloudformation create-stack --timeout-in-minutes 30 --enable-termination-protection --timeout-in-minutes 30 --stack-name ${STACK_NAME} --template-body file://templates/${TEMPLATE}.yaml \
    --capabilities CAPABILITY_IAM --profile=${aws_profile} && aws cloudformation wait stack-create-complete --stack-name ${STACK_NAME} --profile=${aws_profile} || true
  fi
}

stackExists()
{
  STACK_NAME=$1

  echo "Checking if stack exists - ${STACK_NAME}"
  if aws cloudformation describe-stacks --stack-name=${STACK_NAME} --profile=${aws_profile}
  then
    return 0;
  else
    return 1;
  fi
}

createOrUpdateStack() {
  STACK_NAME=$1
  TEMPLATE=$2
  stackExists ${STACK_NAME}
  STACK_EXISTS=$?

  if [ "${STACK_EXISTS}" -eq 1 ]
  then
    createStack ${STACK_NAME} ${TEMPLATE}
  else
    updateStack ${STACK_NAME} ${TEMPLATE}
  fi
}

syncBaseHealthcheckImage() {
  ACCOUNT_NUMBER=$(aws ec2 describe-security-groups --profile=${aws_profile} --query 'SecurityGroups[0].OwnerId' --output text)
  ECR_REPOSITORY_NAME=$1
  REPOSITORY_SIZE=$(aws ecr list-images --profile=${aws_profile} --repository-name=${ECR_REPOSITORY_NAME} --query 'length(imageIds)')

  if [ ${REPOSITORY_SIZE} -eq 0 ]
  then
    echo "Pushing base health check image ...."
    $(aws ecr get-login --no-include-email --region ${region} --profile=${aws_profile})
    docker pull bncprojects/base-healthcheck:latest
    docker tag bncprojects/base-healthcheck:latest ${ACCOUNT_NUMBER}.dkr.ecr.${region}.amazonaws.com/${ECR_REPOSITORY_NAME}:latest
    docker push ${ACCOUNT_NUMBER}.dkr.ecr.${region}.amazonaws.com/${ECR_REPOSITORY_NAME}:latest
  else
    echo "Images already exist in the repository. Will not push base image ..."
  fi
}
