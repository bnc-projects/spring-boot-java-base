#!/usr/bin/env bash

echo "Login into ECR ..."

$(aws ecr get-login --no-include-email)

echo "Push docker image to ECR ..."

./gradlew docker dockerTag dockerPush -PTAG=$TRAVIS_BUILD_NUMBER -PREPOSITORY_URI=$REPOSITORY_URI

printf '{"SPRING_PROFILES":"%s","SPLUNK_TOKEN":"%s", "ENVIRONMENT":"%s", "REPOSITORY_URI":"%s"}' "$SPRING_PROFILES" "$SPLUNK_TOKEN" "$ENVIRONMENT" "$REPOSITORY_URI:$TRAVIS_BUILD_NUMBER" | jq -f config/ecs/task-def.json > task_def_${TRAVIS_BUILD_NUMBER}.json
