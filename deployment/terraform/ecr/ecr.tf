module "ecr" {
  source                   = "git::https://github.com/bnc-projects/terraform-aws-ecr.git?ref=1.0.0"
  allowed_read_principals  = [
    "${data.terraform_remote_state.bnc_ops.bnc_account_ids["market_data_development"]}",
    "${data.terraform_remote_state.bnc_ops.bnc_account_ids["market_data_production"]}",
  ]
  allowed_write_principals = [
    "${data.terraform_remote_state.bnc_ops.deployment_role_arn}"
  ]
  ecr_repo_name            = "${var.service_name}"
  tags                     = "${merge(local.common_tags, var.tags)}"
}
