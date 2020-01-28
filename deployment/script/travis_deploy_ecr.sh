#! /bin/bash

cd deployment/terraform/ecr
terraform init -backend-config="bucket=${STATE_S3_BUCKET}" -backend-config="region=${AWS_DEFAULT_REGION}" -backend-config="dynamodb_table=${STATE_DYNAMODB_TABLE}" -backend-config="kms_key_id=${KMS_KEY_ID}" -backend-config="key=${KEY}" -backend-config="role_arn=${ROLE_ARN}" 1>/dev/null || exit 1
terraform apply -backup="-" -input=false -auto-approve -var role_arn=${ROLE_ARN} -var service_name=${SERVICE_NAME} -var tags="{\"AuthorName\":\"${AUTHOR_NAME}\",\"GitRepository\":\"${TRAVIS_REPO_SLUG}\"}" 1>/dev/null || exit 1
