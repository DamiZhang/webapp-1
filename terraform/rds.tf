resource "aws_security_group" "database" {
  name        = "database"
  description = "Allow TLS inbound traffic"
   vpc_id      = aws_vpc.my_vpc.id

  ingress = [
    {
      description      = "open 3306 port to the world"
      from_port        = 3306
      to_port          = 3306
      protocol         = "tcp"
      cidr_blocks      = ["0.0.0.0/0"]
      ipv6_cidr_blocks = ["::/0"]
      prefix_list_ids  = []
      security_groups  = []
      self             = false
    }
  ]

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    "name" = "database"
  }
}

resource "aws_db_parameter_group" "mysql_parameter_group" {
  name   = "mysql-parameter-group"
  family = "mysql8.0"

  parameter {
    name  = "max_connections"
    value = "1000"
  }

  parameter {
    name  = "innodb_buffer_pool_size"
    value = "134217728"
  }
}

resource "aws_db_instance" "rds" {
  identifier            = "csye6225"
  engine                = "mysql"
  instance_class        = "db.t3.micro"
  name                  = "csye6225"
  username              = "csye6225"
  password              = "12345678"
  allocated_storage     = 20
  multi_az              = false 
  skip_final_snapshot   = true
  publicly_accessible  = false
  db_subnet_group_name   = aws_db_subnet_group.my_db_subnet_group.name
  vpc_security_group_ids = [aws_security_group.database.id]
  parameter_group_name = aws_db_parameter_group.mysql_parameter_group.name

  tags = {
    Name = "csye6225-rds"
  }
}

resource "aws_db_subnet_group" "my_db_subnet_group" {
  name        = "my-db-subnet-group"
  description = "My database subnet group"
  subnet_ids  = [aws_subnet.public_subnet_1.id, aws_subnet.public_subnet_2.id]
}

output "rds_instance_endpoint" {
  value = aws_db_instance.rds.endpoint
}

