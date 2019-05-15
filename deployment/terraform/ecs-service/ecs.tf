module "ecs_service" {
  source                   = "git::https://github.com/bnc-projects/terraform-ecs-service.git?ref=initial-module"
  application_path         = "/v1/service"
  cluster_name             = "${data.terraform_remote_state.market-data.ecs_cluster_name}"
  docker_image             = "${data.terraform_remote_state.ecr.repository_url}:${var.service_version}"
  external_lb_listener_arn = "${data.terraform_remote_state.market-data.external_lb_https_listener_arn}"
  internal_lb_listener_arn = "${data.terraform_remote_state.market-data.internal_lb_https_listener_arn}"
  java_options             = "-javaagent:newrelic/newrelic.jar -Dnewrelic.environment=${terraform.workspace} -Dnewrelic.config.file=newrelic/newrelic.yml"
  is_exposed_externally    = false
  priority                 = 50
  service_name             = "${var.service_name}"
  splunk_token             = "${var.splunk_token}"
  splunk_url               = "${var.splunk_url}"
  spring_profile           = "${terraform.workspace}"
  vpc_id                   = "${data.terraform_remote_state.market-data.vpc_id}"
  tags                     = "${merge(local.common_tags, var.tags)}"
}

