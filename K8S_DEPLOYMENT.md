# 腾讯云 K8s 部署指南

本文档说明如何将基金估值监控系统部署到腾讯云 K8s 集群。

## 前置条件

1. **腾讯云 TKE 集群**已创建并配置好 kubectl
2. **腾讯云 MySQL 数据库**已创建（或使用现有的 MySQL）
3. **腾讯云容器镜像仓库** (TCR) 已创建
4. **本地环境**：
   - Docker 已安装
   - kubectl 已安装并配置好集群凭证
   - Git 已安装

---

## 第一步：准备 MySQL 数据库

### 1.1 获取 MySQL 连接信息

在腾讯云控制台获取：
- **MySQL 主机地址**（如 `cdb-xxxxx.sql.tencentcdb.com`）
- **端口**（默认 3306）
- **用户名**（如 `root`）
- **密码**

### 1.2 初始化数据库

连接到 MySQL 并执行初始化脚本：

```bash
mysql -h YOUR_MYSQL_HOST -P 3306 -u YOUR_USER -p < src/main/resources/sql/init.sql
```

---

## 第二步：构建并推送 Docker 镜像

### 2.1 登录腾讯云容器镜像仓库

```bash
# 获取登录命令（在腾讯云 TCR 控制台）
docker login ccr.ccs.tencentyun.com
```

### 2.2 构建镜像

```bash
# 在项目根目录执行
docker build -t ccr.ccs.tencentyun.com/YOUR_NAMESPACE/fund-estimate:latest .
```

### 2.3 推送镜像到 TCR

```bash
docker push ccr.ccs.tencentyun.com/YOUR_NAMESPACE/fund-estimate:latest
```

---

## 第三步：修改 K8s 配置文件

编辑 `k8s-deployment.yaml`，修改以下内容：

### 3.1 修改 MySQL 连接信息

```yaml
spring.datasource.url=jdbc:mysql://YOUR_MYSQL_HOST:3306/fund_estimate?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
spring.datasource.username=YOUR_MYSQL_USER
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### 3.2 修改镜像地址

```yaml
image: ccr.ccs.tencentyun.com/YOUR_NAMESPACE/fund-estimate:latest
```

---

## 第四步：部署到 K8s

### 4.1 应用配置

```bash
# 确保 kubectl 已连接到你的 TKE 集群
kubectl apply -f k8s-deployment.yaml
```

### 4.2 验证部署

```bash
# 查看 Namespace
kubectl get namespace fund-estimate

# 查看 Pod 状态
kubectl get pods -n fund-estimate

# 查看 Service
kubectl get svc -n fund-estimate

# 查看 Deployment
kubectl get deployment -n fund-estimate
```

### 4.3 查看日志

```bash
# 查看 Pod 日志
kubectl logs -n fund-estimate -l app=fund-estimate -f

# 查看特定 Pod 日志
kubectl logs -n fund-estimate POD_NAME -f
```

---

## 第五步：访问应用

### 5.1 获取外部 IP

```bash
kubectl get svc -n fund-estimate fund-estimate-service
```

输出示例：
```
NAME                      TYPE           CLUSTER-IP      EXTERNAL-IP      PORT(S)        AGE
fund-estimate-service     LoadBalancer   10.0.0.100      123.45.67.89     80:30123/TCP   2m
```

### 5.2 访问应用

在浏览器中访问：
```
http://EXTERNAL-IP/index.html
```

或者使用 kubectl port-forward：
```bash
kubectl port-forward -n fund-estimate svc/fund-estimate-service 8080:80
```

然后访问：`http://localhost:8080/index.html`

---

## 常用命令

### 查看资源使用情况

```bash
# 查看 Pod 资源使用
kubectl top pods -n fund-estimate

# 查看 Node 资源使用
kubectl top nodes
```

### 扩缩容

```bash
# 手动扩容到 3 个副本
kubectl scale deployment fund-estimate -n fund-estimate --replicas=3

# 查看自动扩缩容状态
kubectl get hpa -n fund-estimate
```

### 更新镜像

```bash
# 更新镜像版本
kubectl set image deployment/fund-estimate -n fund-estimate \
  fund-estimate=ccr.ccs.tencentyun.com/YOUR_NAMESPACE/fund-estimate:v2.0

# 查看更新进度
kubectl rollout status deployment/fund-estimate -n fund-estimate
```

### 删除部署

```bash
# 删除整个 Namespace（包括所有资源）
kubectl delete namespace fund-estimate
```

---

## 故障排查

### Pod 无法启动

```bash
# 查看 Pod 详细信息
kubectl describe pod POD_NAME -n fund-estimate

# 查看 Pod 日志
kubectl logs POD_NAME -n fund-estimate
```

### 数据库连接失败

1. 检查 MySQL 主机地址和端口是否正确
2. 检查 MySQL 用户名和密码
3. 确保 K8s 集群可以访问 MySQL（检查安全组规则）

### 镜像拉取失败

```bash
# 检查镜像是否存在
docker images | grep fund-estimate

# 重新推送镜像
docker push ccr.ccs.tencentyun.com/YOUR_NAMESPACE/fund-estimate:latest
```

---

## 性能优化建议

1. **增加副本数**：修改 `replicas: 2` 为更大的值
2. **调整资源限制**：根据实际需求修改 `resources` 部分
3. **启用 HPA**：自动根据 CPU/内存使用率扩缩容
4. **使用 PVC**：如果需要持久化存储

---

## 监控和日志

### 集成腾讯云监控

在腾讯云控制台配置：
- 容器监控
- 日志服务 (CLS)
- 告警规则

### 查看实时日志

```bash
kubectl logs -n fund-estimate -l app=fund-estimate -f --all-containers=true
```

---

## 更新应用

### 更新代码并重新部署

```bash
# 1. 更新代码
git pull origin main

# 2. 重新构建镜像
docker build -t ccr.ccs.tencentyun.com/YOUR_NAMESPACE/fund-estimate:v2.0 .

# 3. 推送镜像
docker push ccr.ccs.tencentyun.com/YOUR_NAMESPACE/fund-estimate:v2.0

# 4. 更新 K8s 部署
kubectl set image deployment/fund-estimate -n fund-estimate \
  fund-estimate=ccr.ccs.tencentyun.com/YOUR_NAMESPACE/fund-estimate:v2.0
```

---

## 支持

如有问题，请查看：
- [腾讯云 TKE 文档](https://cloud.tencent.com/document/product/457)
- [Kubernetes 官方文档](https://kubernetes.io/docs/)
