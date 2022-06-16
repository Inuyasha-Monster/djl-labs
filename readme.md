# djl的实验室
> 用于一些技术点的理论实践和总结使用

## grpc-demo
介绍并了解基本的client与server的grpc的4种通讯方式以及通过代码了解如何实现的双向通信

## grpc-feature
了解并实现grpc作为rpc协议种的一些特性，包括不限于：interceptor、load balancing、tacking等

### executor
> 关于rpc不管是client还是server都需要IO线程来执行IO的各种事件的响应，例如：acceptor处理tcp的连接；IO read/write 事件处理

## netty-demo
用于演示基于tcp传输层协议实现自定义的应用层协议（例如：http、redis、ftp等）以及通过代码展示应用层如何解决tcp的拆包和粘包的问题和区分传输协议和序列化协议的作用位置

## simpleConfigCenterV1
基于长轮询实现简易版的配置中心，参考nacos1.x的流程模型

## simpleConfigCenterV2
基于grpc的tcp长链接连接模型实现高性能的简易配置中心