module "ecr" {
  source                   = "git::https://github.com/bnc-projects/terraform-aws-ecr.git?ref=1.0.0"
  allowed_read_principals  = [
    "arn:aws:iam::${data.terraform_remote_state.techemy.bnc_dev_account_id}:root",
    "arn:aws:iam::${data.terraform_remote_state.techemy.bnc_prod_account_id}:root"
  ]
  allowed_write_principals = []
  ecr_repo_name            = "${var.service_name}"
  tags                     = "${merge(local.common_tags, var.tags)}"
}
