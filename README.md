# annotateWebsite

## 0. 简短介绍

> 该项目通用性很差，只适合音频-文本标注。
> 具体实现细节在下文会展示。



## 1. 项目图
### 登录
> 根据输入账号不同，跳转不同权限用户界面。

![image](https://github.com/code-manba/annotateWebsite/assets/57063392/45a15ab9-8c68-4578-a677-f13ee9dbeb0c)

### 管理员后台
#### 生成界面
> 根据文件结构，将要标注的文本文件读取，并按格式入库。

![image](https://github.com/code-manba/annotateWebsite/assets/57063392/291a2195-b392-4bd0-85e2-16e3698e86ed)


#### 数据查看
> 该界面主要查看目前的标注进度。

![image](https://github.com/code-manba/annotateWebsite/assets/57063392/bebd238d-94c1-4610-8d05-a73957bca596)


### 用户界面
#### 对话列表
![image](https://github.com/code-manba/annotateWebsite/assets/57063392/4b40c5e2-bd6b-45cd-a09e-d033bcedd5f8)


#### 具体标注
![image](https://github.com/code-manba/annotateWebsite/assets/57063392/13d0ee9c-ca78-4cce-b918-130ea79fc422)




## 2. 技术栈

> 前端使用 Uniapp + Element ui  ，   注意：其实pc端更合适使用 Vue + Element ui来做。
> 后端使用 Spring Boot + Mybatis + Mybatis Plus + Maven 来做， 因为面向用户为内部群体，所以在安全鉴权方面，并没有做技术。
> 数据持久化层： 采用Mysql 8.0.24。



## 3. 部署过程和细节

### 部署环境
1. 开发时运行前端需要先安装  node.js 环境，下面是我的版本。
![image](https://github.com/code-manba/annotateWebsite/assets/57063392/8b37e4cc-b345-4dd4-8dce-0638fb234239)

2. 前端开发软件使用 Hbuild X， 在VUE文件的项目根目录运行如下指令：
> 下载包依赖：npm install
> 运行到本地开发环境： npm run serve

3. 数据库配置，创建合适的数据库，执行SQL文件，本SQL文件只有结构，没有数据。

4. 后端开发软件 Idea，需要用到Maven包管理工具，下载好Maven后做好环境配置和镜像源配置。

5. 在Spring Boot的resources文件夹内的application.yml文件进行数据库配置，maven加载完后，正常运行项目。


### 本项目的细节(注意)

> 由于本项目适配组内需求，开发风格比较随意。
1. 对于要读取的音频、文本文件搭建了静态文件服务器（通过nginx代理，教程搜索csdn）。

![image](https://github.com/code-manba/annotateWebsite/assets/57063392/2c7dbddf-7383-4c12-a54f-5b4abfac82f7)

![image](https://github.com/code-manba/annotateWebsite/assets/57063392/a80f60e9-12e3-4329-baf9-0393a8576c17)


2. 后端的resources文件夹下data文件夹为 数据集与部署到静态文件服务器格式一致，主要是用于读取文件名称，初始化数据库数据用的。
> 前端的管理员页面下数据生成界面，会调用后端某个api，后端api会扫描data的文件里文件名，然后通过文件名，请求静态文件服务器的具体txt文件。将读取的文件按自己合适的数据结构，再调用后端的入库接口入库。初始化时要观察入库是否顺利。

3. 每次标注本质就是将已经入库的初始化记录进行修改了。
















