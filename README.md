# 📊 实时基金估值监控系统 (Fund Estimate Monitor)

这是一个基于 Spring Boot 和 Vue.js 实现的实时基金估值监控系统。它能够从天天基金接口抓取实时估值数据，并提供友好的前端界面进行自选基金管理。

## 🌟 功能特性

- **实时估值更新**：每 60 秒自动刷新一次基金实时估值数据。
- **自选基金管理**：支持通过 6 位基金代码添加/删除自选基金。
- **本地持久化**：自选基金列表自动保存至浏览器 `localStorage`，刷新页面不丢失。
- **可视化展示**：
  - 动态涨跌幅颜色展示（红涨绿跌）。
  - 支持查看单位净值、净值日期、估算值、估算涨幅及估值时间。
- **响应式界面**：基于 Element UI 构建，支持简单的手动刷新。

## 🛠️ 技术栈

- **后端**：
  - Java 11
  - Spring Boot 2.7.x
  - [Hutool](https://hutool.cn/) (HTTP 请求与 JSON 解析)
  - Lombok
- **前端**：
  - Vue.js 2.6
  - Element UI
  - Axios

## 🚀 快速开始

### 前置条件

- 已安装 JDK 11 或更高版本。
- 已安装 Maven 3.x。

### 1. 克隆并编译

```bash
git clone https://github.com/Shinnosukeys/fund_estimate.git
cd fund_estimate
mvn clean compile
```

### 2. 运行后端服务

您可以通过 IDE (如 IntelliJ IDEA) 运行 `FundEstimateApplication.java`，或者使用命令行：

```bash
mvn spring-boot:run
```

服务启动后，默认端口为 `8080`。

### 3. 访问监控页面

在浏览器中打开：
[http://localhost:8080/index.html](http://localhost:8080/index.html)

## 📸 界面说明

- **输入框**：输入 6 位基金代码（如 `001186`），按回车或点击“添加自选”。
- **手动刷新**：点击顶部的“手动刷新”按钮可立即获取最新估值。
- **表格列**：
  - **代码/名称**：基金基本信息。
  - **单位净值**：最新的盘后净值数据。
  - **当前估值**：根据盘中走势计算的实时估算净值。
  - **估算涨幅**：相对于上一个净值日的涨跌百分比。

## 📄 申明

本系统数据抓取自天天基金公开接口，仅供学习交流使用，不构成任何投资建议。
