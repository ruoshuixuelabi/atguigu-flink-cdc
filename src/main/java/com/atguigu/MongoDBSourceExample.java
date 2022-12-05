package com.atguigu;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema;
import com.ververica.cdc.connectors.mongodb.MongoDBSource;

public class MongoDBSourceExample {
    public static void main(String[] args) throws Exception {
        SourceFunction<String> sourceFunction = MongoDBSource.<String>builder()
                .hosts("172.20.30.34:28000")
//                .username("flink")
//                .password("flinkpw")
                .databaseList("qlmdb") // set captured database, support regex
                .collectionList("qlmdb.notice_info") //set captured collections, support regex
                .deserializer(new JsonDebeziumDeserializationSchema())
                .build();
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.addSource(sourceFunction).print().setParallelism(1); // use parallelism 1 for sink to keep message ordering
        env.execute();
    }
}