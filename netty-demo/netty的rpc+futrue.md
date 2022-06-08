# 演示netty的rpc模式下的同步、异步的实践

## ChannelDuplexHandler

该对象的部分方法是需要区分client和server端的

## pipeline
> 注意该对象是双向链表，addFirst 和 addLast 分别对应左边添加和右边添加

### ctx.writeAndFlush 从当前handler向前找outHandler执行
### ctx.channel().writeAndFlush 从tailHandler向前找outHandler执行