module "ecr" {
  source                   = "git::https://github.com/bnc-projects/terraform-aws-ecr.git?ref=1.0.0"
  allowed_read_principals  = [
    "${data.terraform_remote_state.techemy.bnc_account_ids["market_data_development"]}",
    "${data.terraform_remote_state.techemy.bnc_account_ids["market_data_production"]}",
  ]
  allowed_write_principals = [
    "${data.terraform_remote_state.techemy.bnc_deployment_roles["operations"]}",
  ]
  ecr_repo_name            = "${var.service_name}"
  tags                     = "${merge(local.common_tags, var.tags)}"
}
