# AWS Deployment - TaskFlow API

This guide explains how to deploy the TaskFlow API on AWS using different methods.

## 🚀 Deployment Methods

### 1. AWS Elastic Beanstalk (simple alternative)

#### Prerequisites

- AWS CLI configured
- Elastic Beanstalk account created

#### Steps (manual)

1. Build the JAR locally:

```bash
mvn clean package -DskipTests
```

2. In the Elastic Beanstalk console, create an environment on the Java or Docker platform (single-container). If using Java, upload the `target/*.jar`.

3. Configure environment variables in EB:
   - `DATABASE_URL` (jdbc:postgresql://<rds-endpoint>:5432/taskflow)
   - `DATABASE_USERNAME`
   - `DATABASE_PASSWORD`
   - `JWT_SECRET`
   - `SPRING_PROFILES_ACTIVE=prod`

### 2. AWS ECS with Fargate (recommended)

#### Prerequisites

- Docker
- AWS CLI configured

#### Steps

1. Build Docker image:

```bash
docker build -t taskflow-api .
```

2. Tag the image:

```bash
docker tag taskflow-api:latest <account-id>.dkr.ecr.<region>.amazonaws.com/taskflow-api:latest
```

3. Push to ECR:

```bash
aws ecr get-login-password --region <region> | docker login --username AWS --password-stdin <account-id>.dkr.ecr.<region>.amazonaws.com
docker push <account-id>.dkr.ecr.<region>.amazonaws.com/taskflow-api:latest
```

4. Create an ECS cluster and configure the service with the image. Use Task Definition with container on port 8080 and define environment variables:
   - `DATABASE_URL=jdbc:postgresql://<rds-endpoint>:5432/taskflow`
   - `DATABASE_USERNAME`
   - `DATABASE_PASSWORD`
   - `JWT_SECRET`
   - `SPRING_PROFILES_ACTIVE=prod`
     Configure an ALB with health check on `/actuator/health` on port 8080.

### 3. AWS EC2 with Docker (optional)

#### Prerequisites

- EC2 instance with Docker installed
- Security Group configured (ports 22, 80, 443, 8080)

#### Steps

1. Connect to EC2 instance
2. Clone the repository
3. Run your ECR image with `docker run` and configure environment variables as above.

## 🗄️ Database Configuration

### Option 1: RDS PostgreSQL (Recommended)

Create the RDS database manually in the AWS console and note the endpoint.

### Option 2: IaC (optional)

If you prefer IaC, use your own templates. This repository does not include IaC scripts.

## 🔧 Environment Variables

Configure the following variables in your application:

```bash
# Database
DATABASE_URL=jdbc:postgresql://your-rds-endpoint:5432/taskflow
DATABASE_USERNAME=taskflow_user
DATABASE_PASSWORD=your-secure-password

# JWT
JWT_SECRET=your-super-secret-key-minimum-32-characters
JWT_EXPIRATION=86400000

# Server
PORT=8080
SPRING_PROFILES_ACTIVE=prod

# CORS (optional)
CORS_ORIGINS=https://yourdomain.com
```

## 🔒 Security

### Security Groups

Configure Security Groups to allow only necessary traffic:

- **Application**: Ports 80, 443, 8080
- **Database**: Port 5432 (application only)

### RDS

- Use Multi-AZ for high availability
- Enable encryption
- Configure automatic backups
- Use private VPC

### JWT Secret

- Use a strong secret key (minimum 32 characters)
- Store in environment variables
- Never commit to code

## 📊 Monitoring

### CloudWatch

- Configure CloudWatch logs
- Monitor CPU, memory and network metrics
- Configure alerts

### Health Checks

The application includes health check endpoints:

- `/actuator/health` - Application status
- `/actuator/info` - Application information

## 💰 Cost Optimization

### Instances

- Use t3.micro instances for development
- Configure Auto Scaling for production
- Use Spot Instances when possible

### Database

- Use db.t3.micro for development
- Configure appropriate backup retention
- Monitor storage usage

### Storage

- Use EBS gp2 for development
- Consider gp3 for production
- Configure lifecycle policies

## 🚨 Troubleshooting

### Common Issues

1. **Database connection error**

   - Check Security Groups
   - Confirm RDS endpoint
   - Verify credentials

2. **Application not starting**

   - Check CloudWatch logs
   - Confirm environment variables
   - Check instance resources

3. **CORS error**
   - Configure CORS_ORIGINS
   - Check request headers

### Logs

- Elastic Beanstalk: use EB console logs tab
- ECS: CloudWatch Logs (configure awslogs log driver)
- EC2: `docker logs <container>`

## 📈 Scalability

### Horizontal Scaling

- Configure Auto Scaling Groups
- Use Application Load Balancer
- Configure health checks

### Vertical Scaling

- Monitor CPU/memory metrics
- Adjust instance size as needed

### Database Scaling

- Configure Read Replicas
- Use Connection Pooling
- Monitor performance

## 🔄 CI/CD

### GitHub Actions

Configure pipelines according to your preference (ECR + ECS deploy). This repository does not use automatic deploy .sh scripts.

### AWS CodePipeline

- Configure pipeline for automatic deployment
- Use CodeBuild for tests
- Configure notifications

## 📞 Support

For questions or issues:

1. Check application logs
2. Consult AWS documentation
3. Check CloudWatch metrics
4. Test locally first
