#!/usr/bin/env bash

echo "Login into ECR ..."

$(aws ecr get-login --no-include-email)

echo "Push docker image to ECR ..."

./gradlew docker dockerTag dockerPush -PTAG=$TRAVIS_BUILD_NUMBER -PREPOSITORY_URI=$REPOSITORY_URI
