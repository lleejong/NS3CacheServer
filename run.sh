args=("$@");
java -cp ./target/NS3CacheServer-0.0.1-SNAPSHOT.jar:/root/.m2/repository/mysql/mysql-connector-java/5.1.38/mysql-connector-java-5.1.38.jar proj.skt.App ${args[0]} ${args[1]} ${args[2]} 