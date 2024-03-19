
함수 호출 관점
![img_5.png](img_5.png)

내부 동작 관점
![img_4.png](img_4.png)


* Java IO
파일과 네트워크에 데이터를 읽고 쓰는
byte 단위 기반 stream (InputStream, OutputStream)
동기 blocking 으로 동작
메모리 카피가 생긴다

* Java NIO
buffer 단위 기반 (New)
non-blocking 지원
selector, channel 도입으로 높은 성능 보장


![img_6.png](img_6.png)


* Java AIO (NIO2)
AsynchronousChannel 
AsynchronousSocketChannel
AsynchronousServerSocketChannel
AsynchronousFileChannel
callback 과 future 지원
Thread pool 과 epoll, kqueue 등의 이벤트 알림 system call 이용
