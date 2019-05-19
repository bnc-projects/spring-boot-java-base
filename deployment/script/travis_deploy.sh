cd deployment/terraform/ecs-service
terraform apply -backup="-" -input=false -auto-approve -var role_arn=${ROLE_ARN} -var bnc_deploy_role=${DEVELOPMENT_ROLE_ARN} -var service_name=${SERVICE_NAME} -var service_version=${TRAVIS_BUILD_NUMBER} -var splunk_url=${SPLUNK_URL} -var splunk_token=${SPLUNK_TOKEN} > /dev/null
CLUSTER_NAME=$(terraform output ecs_cluster_name)
aws ecs wait services-stable --services ${SERVICE_NAME} --cluster ${CLUSTER_NAME}
