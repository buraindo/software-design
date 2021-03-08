package org.buraindo.sd.reactive.db;

import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoDatabase;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.rx.client.MongoClients.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class RxDatabase {

    public static MongoDatabase newRxDatabase() {
        var pojoCodecRegistry = fromRegistries(
                getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );
        return MongoClients
                .create("mongodb://localhost")
                .getDatabase("shop")
                .withCodecRegistry(pojoCodecRegistry);
    }

}
