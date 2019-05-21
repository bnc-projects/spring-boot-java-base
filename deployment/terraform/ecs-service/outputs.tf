output "deployment_role_arn" {
  sensitive = true
  value     = "${data.terraform_remote_state.market_data.deployment_role_arn}"
}

output "ecs_cluster_name" {
  sensitive = true
  value     = "${data.terraform_remote_state.market_data.ecs_cluster_name}"
}
