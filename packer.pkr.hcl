packer {
  required_plugins {
    amazon = {
      version = ">= 0.0.2"
      source  = "github.com/hashicorp/amazon"
    }
  }
}

source "amazon-ebs" "amazon_linux2" {
  ami_name      = "mywebappami-{{timestamp}}"
  instance_type = "t2.micro"
  region        = "us-west-2"
  source_ami_filter {
    filters = {
      name                = "amzn2-ami-hvm-*-x86_64-ebs"
      root-device-type    = "ebs"
      virtualization-type = "hvm"
    }
    most_recent = true
    owners      = ["amazon"]
  }
  ssh_username = "ec2-user"
}

build {
  name    = "webapp-packer"
  sources = [
    "source.amazon-ebs.amazon_linux2"
  ]
  provisioner "file" {
    source      = "userman.service"
    destination = "/home/ec2-user/"
  }
  provisioner "file" {
    source      = "target/userman-0.0.1-SNAPSHOT.jar"
    destination = "/home/ec2-user/"
  }

  provisioner "shell" {
    inline = [
      "mkdir /home/ec2-user/config"
    ]
  }
  provisioner "shell" {
    inline = [
      "sudo cp /home/ec2-user/userman.service /usr/lib/systemd/system/",
      "sudo yum install java-11-amazon-corretto -y",
      "sudo systemctl start userman",
      "sudo systemctl enable userman"
    ]
  }
  provisioner "shell" {
    inline = [
      "sudo yum update -y",
      "sudo yum install mariadb-server -y",
      "sudo systemctl enable mariadb",
      "sudo systemctl start mariadb",
      "mysql -u root <<EOF",
      "UPDATE mysql.user SET Password=PASSWORD('123456') WHERE User='root';",
      "FLUSH PRIVILEGES;",
      "EOF",
    ]
  }
}

