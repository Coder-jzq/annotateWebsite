# annotateWebsite

## 0. 简短介绍

> 该项目通用性很差，只适合音频-文本标注。
> 具体实现细节在下文会展示。



## 1. 项目图
### 登录
> 根据输入账号不同，跳转不同权限用户界面

![image](https://github.com/code-manba/annotateWebsite/assets/57063392/45a15ab9-8c68-4578-a677-f13ee9dbeb0c)

### 管理员后台
#### 生成界面
> 根据文件结构，将要标注的文本文件读取，并按格式入库。

![image](https://github.com/code-manba/annotateWebsite/assets/57063392/291a2195-b392-4bd0-85e2-16e3698e86ed)


#### 数据查看
> 该界面主要查看目前的标注进度

![image](https://github.com/code-manba/annotateWebsite/assets/57063392/bebd238d-94c1-4610-8d05-a73957bca596)


### 用户界面
#### 对话列表
![image](https://github.com/code-manba/annotateWebsite/assets/57063392/4b40c5e2-bd6b-45cd-a09e-d033bcedd5f8)


#### 具体标注
![image](https://github.com/code-manba/annotateWebsite/assets/57063392/13d0ee9c-ca78-4cce-b918-130ea79fc422)




## 2. 技术栈

> 前端使用 Uniapp + Element ui  ，   注意：其实pc端更合适使用 Vue + Element ui来做
> 后端使用 Spring Boot + Mybatis + Mybatis Plus 来做， 因为面向用户为内部群体，所以在安全鉴权方面，并没有做技术
> 数据持久化层： 采用Mysql 8.0.24



## 3. 部署过程和细节

### 部署环境
1. 开发时运行前端需要先安装  node.js 环境，下面是我的版本
![image](https://github.com/code-manba/annotateWebsite/assets/57063392/8b37e4cc-b345-4dd4-8dce-0638fb234239)

2. 






















