output "repository_url" {
  value = "${module.ecr.repository_url}"
}

output "operations_role_arn" {
  value = "${data.terraform_remote_state.techemy.bnc_deployment_roles["operations"]}"
}
