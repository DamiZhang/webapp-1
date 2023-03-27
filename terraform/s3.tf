resource "random_pet" "bucket_name" {
  length = 2  #随机生成的字段
}   

resource "aws_s3_bucket" "private_bucket" {
  bucket = "csye6225-${random_pet.bucket_name.id}"
  force_destroy = true   #不为空也可以删除存储桶
  acl = "private"

  server_side_encryption_configuration {
    rule {
      apply_server_side_encryption_by_default { 
        sse_algorithm = "AES256"
      }  # 默认加密
    }
  }

  lifecycle_rule {
    id      = "standard_ia_transition"
    enabled = true
   # 生命周期 
    transition {
      days          = 30
      storage_class = "STANDARD_IA"
    }
  }

}

output "s3_bucket_name" {
  value = aws_s3_bucket.private_bucket.bucket
}