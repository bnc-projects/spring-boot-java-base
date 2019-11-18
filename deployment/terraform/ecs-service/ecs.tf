data "aws_iam_policy_document" "task_service_assume_role" {
  statement {
    sid    = "AllowECSTaskToAssumeRole"
    effect = "Allow"

    actions = [
      "sts:AssumeRole"
    ]

    principals {
      type        = "Service"
      identifiers = [
        "ecs-tasks.amazonaws.com"
      ]
    }
  }
}

data "aws_iam_policy" "execution_policy" {
  arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

module "container_definition" {
  source            = "git::https://github.com/bnc-projects/terraform-ecs-container-definition.git?ref=1.0.0"
  environment       = [
    {
      name  = "SPRING_PROFILES_ACTIVE",
      value = terraform.workspace
    }
  ]
  healthCheck       = {
    "command"     = [
      "CMD-SHELL",
      "curl --silent --fail --max-time 30 http://localhost:8080/actuator/health || exit 1"
    ],
    "interval"    = 30,
    "retries"     = 3,
    "startPeriod" = 300,
    "timeout": 5
  }
  logConfiguration  = {
    "logDriver": "splunk",
    "options": {
      "splunk-format": "raw",
      "splunk-insecureskipverify": "true",
      "splunk-token": var.splunk_token,
      "splunk-url": var.splunk_url
    }
  }
  image             = format("%s:%s", data.terraform_remote_state.ecr.outputs.repository_url, var.service_version)
  name              = var.service_name
  cpu               = var.cpu
  memory            = var.memory
  memoryReservation = var.memory_reservation
  portMappings      = [
    {
      containerPort = 8080
      hostPort      = 8080,
      protocol      = "tcp"
    }
  ]
}

resource "aws_iam_role" "execution_task_role" {
  name               = format("%s-execution", var.service_name)
  assume_role_policy = data.aws_iam_policy_document.task_service_assume_role.json
  tags               = var.tags
}

resource "aws_iam_role_policy_attachment" "ecs_task_default_policy" {
  role       = aws_iam_role.execution_task_role.name
  policy_arn = data.aws_iam_policy.execution_policy.arn
}

resource "aws_iam_role" "task_role" {
  name               = format("%s-task", var.service_name)
  assume_role_policy = data.aws_iam_policy_document.task_service_assume_role.json
  tags               = var.tags
}

resource "aws_ecs_task_definition" "task_definition" {
  container_definitions = "[${module.container_definition.container_definition}]"
  family                = var.service_name
  cpu                   = var.cpu
  memory                = var.memory
  execution_role_arn    = aws_iam_role.execution_task_role.arn
  task_role_arn         = aws_iam_role.task_role.arn
  tags                  = merge(local.common_tags, var.tags)
}

module "ecs_service" {
  source                   = "git::https://github.com/bnc-projects/terraform-ecs-service.git?ref=1.3.2"
  application_path         = "/v1/sbjb"
  attach_load_balancer     = true
  cluster                  = data.terraform_remote_state.market_data.outputs.ecs_cluster_name
  external_lb_listener_arn = data.terraform_remote_state.market_data.outputs.external_lb_https_listener_arn
  external_lb_name         = data.terraform_remote_state.market_data.outputs.external_lb_name
  healthcheck_path         = "/actuator/health"
  internal_lb_listener_arn = data.terraform_remote_state.market_data.outputs.internal_lb_https_listener_arn
  internal_lb_name         = data.terraform_remote_state.market_data.outputs.internal_lb_name
  is_exposed_externally    = false
  priority                 = 53
  service_name             = var.service_name
  task_definition_arn      = aws_ecs_task_definition.task_definition.arn
  vpc_id                   = data.terraform_remote_state.market_data.outputs.vpc_id
  tags                     = merge(local.common_tags, var.tags)
}
