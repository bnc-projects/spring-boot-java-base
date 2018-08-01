#!/usr/bin/env bash

usage() {
    echo "Usage: $0 --cluster CLUSTER_NAME --service SERVICE_NAME --task TASK_NAME"
    exit 1
}

while true ; do
    case "$1" in
        -t|--task) TASK_NAME=$2 ; shift 2 ;;
        -s|--service) SERVICE_NAME=$2 ; shift 2 ;;
        -c|--cluster) CLUSTER_NAME=$2 ; shift 2 ;;
        -d|--task-def) TASK_DEF=$2 ; shift 2 ;;
        -h|--help) usage ;;
        --) shift ; break ;;
        *) break ;;
    esac
done

echo "Create new task from ${TASK_DEF}"

UPDATED_TASK_DEF=$(aws ecs register-task-definition --cli-input-json file://${TASK_DEF} | jq ".taskDefinition|.taskDefinitionArn")

echo "Set the new task as active in the service"

aws ecs update-service --service ${SERVICE_NAME} --task-definition ${TASK_NAME} --cluster ${CLUSTER_NAME} > /dev/null

echo "Wait for the new task to start ..."
aws ecs wait services-stable --services ${SERVICE_NAME} --cluster ${CLUSTER_NAME}
