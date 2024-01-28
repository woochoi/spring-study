# spring-study
spring boot / security, webflux, nosql, kafka ...


# 자바의 정석 3판
- url : http://www.codechobo.com/main
- 소스파일 다운로드 (java 연습문제(약200문제)) : https://github.com/castello/javajungsuk3
- 자바의 정석 기초편(2020최신) : https://www.youtube.com/playlist?list=PLW2UjW795-f6xWA2_MUhEVgPauhGl3xIp


# Bookmark
- spring boot

  ObjectMapper
  - https://mangkyu.tistory.com/223
  - https://velog.io/@zooneon/Java-ObjectMapper%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%98%EC%97%AC-JSON-%ED%8C%8C%EC%8B%B1%ED%95%98%EA%B8%B0
  - https://velog.io/@ililil9482/ObjectMapper-%EC%82%AC%EC%9A%A9%EB%B2%95
  - https://recordsoflife.tistory.com/599#google_vignette
  - https://blog.naver.com/occidere/222512549848
  - https://pingfanzhilu.tistory.com/entry/JSON-JSON-%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%ACObjectMapper-%EC%82%AC%EC%9A%A9%EB%B2%95
  - https://oozoowos.tistory.com/entry/Java-String-to-Json-%ED%8C%8C%EC%8B%B1%ED%95%98%EA%B8%B0-Jackson-ObjectMapper
  - https://luvstudy.tistory.com/244

  Objects
  - https://m.blog.naver.com/rain483/220588106340
  - https://velog.io/@gnlals1/Objects.hash-%EA%B7%B8%EB%A6%AC%EA%B3%A0-Objects.hashCode
  - https://www.baeldung.com/java-objects-hash-vs-objects-hashcode
  - https://eno1993.tistory.com/198
  - https://www.baeldung.com/java-hashcode
  - https://staticclass.tistory.com/75

  Cache
  - https://tussle.tistory.com/1104


- Reactive
  CompletableFuture
  - https://www.youtube.com/playlist?list=PLL-4P1BOZnWwauNPsQ_Q13hVL1LHvhy30
  - https://www.youtube.com/playlist?list=PLoeSjUxfpzh8Yo_kDQxq_USWZteA1qV6d


- Webflux
  Mono
  - https://devsh.tistory.com/150
  - https://shallwelearn.com/2023/02/12/intro-to-reactor-mono-and-flux/
  - https://codersee.com/mono-just-defer-fromsupplier-create-part-1/

  defer:
  - https://yangbongsoo.tistory.com/58
  - https://velog.io/@bahar-j/Mono.defer-%EB%B6%80%EC%A0%9C-%EA%B0%80%EB%8A%A5%ED%95%9C-%ED%95%9C-Lazy%ED%95%98%EA%B2%8C-%EC%B2%98%EB%A6%AC%ED%95%98%EC%9E%90

    public static <T> Mono<T> defer(Supplier<? extends Mono<? extends T>> supplier) {
      return onAssembly(new MonoDefer(supplier));
    }


  Mono/Flux
  - https://lovon.tistory.com/35
  - https://colevelup.tistory.com/38
  - https://velog.io/@zenon8485/Reactor-Java-1.-Mono%EC%99%80-Flux%EB%A5%BC-%EC%83%9D%EC%84%B1%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95

- collection, generics, stream : 
  - https://sppohyduj22.tistory.com/category/Java
  - https://golf-dev.tistory.com/11
  - https://www.youtube.com/watch?v=7Kyf4mMjbTQ
  - https://nostress.tistory.com/2
  - https://hwan33.tistory.com/31
  - https://yarisong.tistory.com/48


- @Scheduled
  - https://rooted.tistory.com/12
  - https://dev-coco.tistory.com/176
  - https://peterica.tistory.com/18
  - https://velog.io/@penrose_15/Scheduler%EC%99%80-cron-%EC%82%AC%EC%9A%A9%EB%B2%95
  - https://velog.io/@max9106/Spring-Scheduling
  - https://vixxcode.tistory.com/261




- Spring Batch : https://velog.io/@kdj9878/Spring-Batch-%EB%B0%8F-Spring-Scheduler



- JPA : https://putapple96.tistory.com/3



- 네트워크 기초 무료 강의 : https://www.youtube.com/watch?v=dsoAkoxZ13o






Mono.defer(() -> {

.retryWhen(
}).cache();

.then
thenReturn

retryWhen

Retry.fixedDelay
thenReturn

@Scheduled(fixedRateString = "1000", initialDelay = 10000)

objectMapper.readValue(json, PushMemberInfoDto.class);

.onErrorResume(throwable -> Mono.empty())

Mono.defer(()
onErrorResume

.doOnError(throwable -> log.error("이벤트 컨슈머에 에러가 발생하였습니다.", throwable))

.publishOn(Schedulers.parallel())
Flux.defer(()
.doOnError
doOnTerminate
subscribe
thenReturn
).subscribeOn(Schedulers.boundedElastic()); // 블로킹 함수이므로 무조건 bounded elastic


