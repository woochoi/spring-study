package org.example.netty.eventloop;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class EventLoopNonIOTaskExample {

    public static void main(String[] args) {
        log.info("start main");

        // newFixedThreadPool 메소드는 corePoolSize 와 maximumPoolSize 가 같고
        // keepAliveTime이 0인 ThreadPoolExecutor를 생성한다. 이것은 쓰레드 풀의 수는 항상 같다는 것을 의미한다.
        ExecutorService executor = Executors.newFixedThreadPool(10);
        /**
          Runnable runnableTask = () -> {
              try {
                  TimeUnit.MILLISECONDS.sleep(300);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          };

          Callable<String> callableTask = () -> {
              TimeUnit.MILLISECONDS.sleep(300);
              return "Task's execution";
          };

          List<Callable<String>> callableTasks = new ArrayList<>();
          callableTasks.add(callableTask);
          callableTasks.add(callableTask);
          callableTasks.add(callableTask);


          무효이며 작업 실행 결과를 얻거나 작업 상태(실행 중인지)를 확인할 수 있는 가능성을 제공하지 않는다.
          executorService.execute(runnableTask);

          Callable 또는 Runnable 작업을 ExecutorService 에 제출하고 Future 유형의 결과를 반환합니다.
          Future<String> future = executorService.submit(callableTask);

          ExecutorService 에 작업 모음을 할당하여 각 작업을 실행하고 한 작업의 성공적인 실행 결과를 반환합니다(성공적인 실행이 있는 경우)
          String result = executorService.invokeAny(callableTasks);

          ExecutorService 에 작업 모음을 할당하여 각 작업을 실행하고 모든 작업 실행 결과를 Future 유형의 객체 List 형식으로 반환합니다.
          List<Future<String>> futures = executorService.invokeAll(callableTasks);


          ExecutorService 종료
          ExecutorService 를 제대로 종료하기 위해 shutdown() 및 shutdownNow() API 가 있습니다 .
          shutdown() 메서드는 ExecutorService 를 즉시 파괴하지 않습니다.

          ExecutorService 가 새 작업 수락을 중지하고 실행 중인 모든 스레드가 현재 작업을 완료한 후 종료 됩니다.
          executorService.shutdown();

          ExecutorService 를 즉시 삭제하려고 시도 하지만 실행 중인 모든 스레드가 동시에 중지된다는 보장은 없습니다.
          List<Runnable> notExecutedTasks = executorService.shutDownNow();

         */


        // 1개의 쓰래드에서 돌기 때문에 순서가 보장
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1); // 별도의 쓰레드 풀을 갖는다! (1 ~ 5)
        // --> eventLoop 갯수

        // group 에서 execute 는 eventLoopGroup 내의 eventLoop 를 순회하면서 실행
        // 각각의 eventLoop 에 순차적으로ㅓ task 가 추가되고 실행하기 때문에
        // evnetㄷExecutor 단위로 놓고 보면 순서가 보장

        for (int i = 0; i < 10; i++) {
            final int idx = i;
            // void execute(Runnable command);
            eventLoopGroup.execute(() -> { // 일반적인 Executor 처럼 Non I/O task 수행
                log.info("i: {}", idx); //--> task 큐에 집어 넣는다..
            });
        }

        eventLoopGroup.shutdownGracefully();
        log.info("end main");
    }

}
