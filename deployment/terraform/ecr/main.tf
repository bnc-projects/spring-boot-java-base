terraform {
  backend "s3" {
    encrypt = true
  }
}

locals {
  common_tags = {
    Owner       = "bravenewcoin"
    Team        = "Market Data"
    Environment = "production"
  }
}

provider "aws" {
  region  = "${var.aws_default_region}"
  version = "~> 2.10.0"
  profile = "${var.profile}"

  assume_role {
    role_arn     = "${var.bnc_deploy_role}"
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

data "terraform_remote_state" "bnc_ops" {
  backend = "s3"
  config {
    bucket   = "terraform.techemy.co"
    key      = "bnc/ops"
    region   = "${var.aws_default_region}"
    profile  = "${var.profile}"
    role_arn = "${var.role_arn}"
  }
}

