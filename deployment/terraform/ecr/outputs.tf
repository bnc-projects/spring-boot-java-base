output "repository_url" {
  value = module.ecr.repository_url
}

output "deployment_role_arn" {
  sensitive = true
  value     = data.terraform_remote_state.bnc_ops.outputs.deployment_role_arn
}
