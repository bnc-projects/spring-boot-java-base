variable "aws_default_region" {
  type    = string
  default = "us-west-2"
}

variable "cpu" {
  type        = number
  default     = 128
  description = "The CPU limit for the task and container."
}

variable "memory" {
  type        = number
  default     = 512
  description = "The hard memory limit for the task and container"
}

variable "memory_reservation" {
  type        = number
  default     = 512
  description = "The soft memory limit for the task and container"
}

variable "profile" {
  type    = string
  default = "default"
}

variable "role_arn" {
  type        = string
  description = "The role to assume to access the terraform remote state"
}

variable "service_name" {
  type        = string
  description = "The name of the ECS service"
}

variable "service_version" {
  type        = string
  description = "The Travis build number"
}

variable "splunk_url" {
  type        = string
  description = "The URL of Splunk"
}

variable "splunk_token" {
  type        = string
  description = "The token used to send log to Splunk collector"
}

variable "tags" {
  type        = map(string)
  description = "A map of tags to add to all resources"
  default     = {}
}
