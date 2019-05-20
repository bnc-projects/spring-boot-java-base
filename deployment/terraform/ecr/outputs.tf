output "repository_url" {
  value = "${module.ecr.repository_url}"
}

output "deployment_role_arn" {
  sensitive = true
  value     = "${data.terraform_remote_state.techemy.bnc_deployment_roles["operations"]}"
}
