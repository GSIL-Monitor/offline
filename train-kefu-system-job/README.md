# 项目使用说明

**代码中src/main/java/com.ctrip.train.kefu.system.job.worker包是QSchedule的样例Job，包括多种类型的Job**

## 本地运行调试
- 因为需要QSchedule服务端调度任务，可以将env设置为uat进行测试
- 如果是非Windows环境，请确保存在如下两个文件：/opt/status/server.status，/opt/status/webapp.status，且内容为on，否则会导致vi点火失败
- 运行src/test/java/com.ctrip.train.kefu.system.job.worker.JobStarter.java即可，QSchedule会自动注册任务到QSchedule管理端
- 访问[QSchedule管理端](http://qschedule.uat.qa.nt.ctripcorp.com/jobs.do)可以看到worker目录下的任务已经自动注册，如果看不到，请确保自己在[应用中心](http://appcenter.ctripcorp.com/)有appid的权限
- 在管理端点击"启动"，然后点击"立即启动"等即可测试Job，具体可以看[QSchedule文档](http://conf.ctripcorp.com/pages/viewpage.action?pageId=118310163)

## 新增Job
1. 在worker包下增加带@Component的类，且包含带有@QSchedule(value = "JOB.ID")注解的方法即可，注意其中JOB.ID需要唯一，具体可参看[QSchedule文档](http://conf.ctripcorp.com/pages/viewpage.action?pageId=118310163)


## 测试环境或者生产环境发布
- 直接Maven打包即可

## 备注
- 通过src/test/resources/application.properties中的`server.port`进行本地运行时服务端口修改，测试环境或者生产环境的端口不受此配置控制