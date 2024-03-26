* NIOEventLoop
* ![img.png](img.png)
- ioRatio : 기본 50
- 100 이면 I/O task 먼저 처리, Non I/O task

* NIOEventLoopGroup
- ![img_1.png](img_1.png)
- NIOEventLoop 은 직접 생성할수 없기 때문에 NIOEventLoopGroup 을 사용

* ExecutorService
- ![img_2.png](img_2.png)

* ChannelPipeline (Channel 은 Pipeline 을 가지고 있다)
- ![img_3.png](img_3.png)
- ![img_4.png](img_4.png)

- ChannelHandlerContext
- ![img_5.png](img_5.png)

* ![img_6.png](img_6.png)
* ![img_7.png](img_7.png)
* ![img_8.png](img_8.png)
* ![img_9.png](img_9.png)