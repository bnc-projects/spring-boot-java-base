terraform {
  backend "s3" {
    workspace_key_prefix = "bnc"
    encrypt              = true
  }
}

locals {
  common_tags = {
    Owner       = "bravenewcoin"
    Team        = "Market Data"
    Environment = "${terraform.workspace}"
  }
}

provider "aws" {
  region  = "${var.aws_default_region}"
  version = "~> 2.11.0"
  profile = "${var.profile}"

  allowed_account_ids = [
    "${data.terraform_remote_state.techemy.bnc_account_ids["market_data_${terraform.workspace}"]}",
  ]

  assume_role {
    role_arn     = "${data.terraform_remote_state.techemy.bnc_deployment_roles["market_data_${terraform.workspace}"]}"
    session_name = "terraform"
  }
}

data "terraform_remote_state" "techemy" {
  backend = "s3"
  config {
    bucket   = "terraform.techemy.co"
    key      = "techemy/master"
    region   = "${var.aws_default_region}"
    profile  = "${var.profile}"
    role_arn = "${var.role_arn}"
  }
}

data "terraform_remote_state" "market-data" {
  backend   = "s3"
  workspace = "${terraform.workspace}"
  config {
    bucket               = "terraform.techemy.co"
    key                  = "market-data"
    region               = "${var.aws_default_region}"
    profile              = "${var.profile}"
    role_arn             = "${var.role_arn}"
    workspace_key_prefix = "bnc"
  }
}

data "terraform_remote_state" "ecr" {
  backend = "s3"
  config {
    bucket   = "terraform.techemy.co"
    key      = "bnc/market-data/ecr/${var.service_name}"
    region   = "${var.aws_default_region}"
    profile  = "${var.profile}"
    role_arn = "${var.role_arn}"
  }
}
