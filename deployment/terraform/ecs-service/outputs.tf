output "deployment_role_arn" {
  value = "${data.terraform_remote_state.techemy.bnc_deployment_roles["market_data_${terraform.workspace}"]}"
}

output "ecs_cluster_name" {
  sensitive = true
  value     = "${data.terraform_remote_state.market-data.ecs_cluster_name}"
}
