locals {
  ami_id = file("ami_id.txt")
  myvpc_id = file("myvpc_id.txt")
  mysubnet_id = file("mysubnet_id.txt")
  s3_bucket_name = file("s3_bucket_name.txt")
  rds_instance_endpoint = file("rds_instance_endpoint.txt")
}

# Define the webAppS3 policy
resource "aws_iam_policy" "webAppS3_policy" {
  name        = "webAppS3"
  policy      = <<EOF
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Action": [
                "s3:*"
            ],
            "Effect": "Allow",
            "Resource": [
                "arn:aws:s3:::${local.s3_bucket_name}",
                "arn:aws:s3:::${local.s3_bucket_name}/*"
            ]
        }
    ]
}
EOF
}

# Define the EC2-CSYE6225 role
resource "aws_iam_role" "ec2_csye6225_role" {
  name = "EC2-CSYE6225"
  
  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect    = "Allow"
        Action    = "sts:AssumeRole"
        Principal = {
          Service = "ec2.amazonaws.com"
        }
      }
    ]
  })
}

# Attach policies to the EC2-CSYE6225 role
resource "aws_iam_role_policy_attachment" "webAppS3_attach" {
  policy_arn = aws_iam_policy.webAppS3_policy.arn
  role       = aws_iam_role.ec2_csye6225_role.name
}

# Define the EC2 instance
resource "aws_security_group" "sg_webapp" {
  vpc_id = local.myvpc_id
  ingress {
    from_port = 22
    to_port = 22
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port = 8080
    to_port = 8080
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}


resource "aws_instance" "ec2_instance" {
  ami           = local.ami_id
  instance_type = "t2.micro"
  subnet_id     = local.mysubnet_id
  vpc_security_group_ids = [
    aws_security_group.sg_webapp.id
  ]
  iam_instance_profile = aws_iam_instance_profile.ec2_profile.name

  user_data = <<-EOF
    #!/bin/bash
    sed -i 's/{s3_bucket}/${local.s3_bucket_name}/g' /home/ec2-user/config/application.yml
    sed -i 's/{mysql_endpoint}/${local.rds_instance_endpoint}/g' /home/ec2-user/config/application.yml
  EOF
}

# Define the EC2 instance profile
resource "aws_iam_instance_profile" "ec2_profile" {
  name = "ec2_profile"
  
  role = aws_iam_role.ec2_csye6225_role.name
}


