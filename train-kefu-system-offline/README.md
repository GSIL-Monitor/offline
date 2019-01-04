# 项目使用说明

## 测试环境或者生产环境发布
- 直接Maven打包即可

##domain 领域模型用作业务逻辑
##vm  viewModel 视图模型只做数据传输，不要做业务逻辑
##service 业务逻辑层
# controller 业务逻辑尽量放在service层去做，controller只做简单的数据验证和转化，保持代码整洁
#domainService 领域逻辑层 目前业务比较简单暂时不加

## 备注
- 通过src/test/resources/application.properties中的`server.port`进行本地运行时服务端口修改，测试环境或者生产环境的端口不受此配置控制