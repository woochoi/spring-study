mongosh -u root -p

https://velog.io/@youngeui_hong/Docker%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%98%EC%97%AC-MongoDB-Replica-Set-%EA%B5%AC%EC%B6%95%ED%95%98%EA%B8%B0
https://seohyun0120.tistory.com/entry/MongoDB-Replica-Set-%EA%B5%AC%EC%84%B1%ED%95%98%EA%B8%B0-Docker-Swarm


<aside>
💡 mongodb 5 버전이상은 ARMv8.2-A이상만 지원함.
https://github.com/ohhoonim/docker-servers/blob/main/mongodb/docker-compose.yml

https://youngwonhan-family.tistory.com/entry/

Docker-mongodb-docker-compose-%EC%84%A4%EC%B9%98-%EB%B0%8F-%EB%8D%B0%EC%9D%B4%ED%84%B0-CRUD-%EC%98%88%EC%A0%9C

https://www.sktenterprise.com/bizInsight/blogDetail/dev/2652

https://www.nextree.io/mongodb-transaction/

</aside>

## Collection 생성/조회

```json
// database 변경
use('testdb')
// collection 생성
db.createCollection('book')
// 데이터 입력
db.book.insertOne({name:"hello mongo", author:"choi"})
db.book.insertMany([{name:"hello java", author:"kim"}, {name:"hello docker", author:"lee"}])
// 데이터 조회
db.book.find().pretty()
// 데이터 업데이트
db.book.updateOne( { _id: ObjectId("61e374779cbbcefe0d6d744d") }, { $set: { author: "lee docker" } } )
// 업데이트 데이터 조회
db.book.find({name:"hello docker"})
// 데이터 삭제
db.book.deleteOne({name:"hello docker"})
```

## 참고문헌

[도커(Docker)로 MongoDB 서버 구축하기](https://wooiljeong.github.io/server/docker-mongo/)