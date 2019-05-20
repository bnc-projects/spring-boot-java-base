output "ecs_cluster_name" {
  sensitive = true
  value     = "${data.terraform_remote_state.market-data.ecs_cluster_name}"
}
