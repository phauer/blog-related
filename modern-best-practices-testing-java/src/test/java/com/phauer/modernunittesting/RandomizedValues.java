package com.phauer.modernunittesting;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

public class RandomizedValues {

    @Test
    public void randomized() {
        // Don't
        Instant ts1 = Instant.now(); // 1557582788
        Instant ts2 = ts1.plusSeconds(1); // 1557582789
        ObjectId generatedId = new ObjectId(); // 5cd6d3c469974112f4be30d2
        int randomAmount = new Random().nextInt(500); // 232
        UUID uuid = UUID.randomUUID(); // d5d1f61b-0a8b-42be-b05a-bd458bb563ad
    }

    @Test
    public void fixed() {
        // Do
        Instant ts1 = Instant.ofEpochSecond(1550000001);
        Instant ts2 = Instant.ofEpochSecond(1550000002);
        ObjectId id1 = new ObjectId("000000000000000000000001");
        int amount = 50;
        UUID uuid = UUID.fromString("00000000-000-0000-0000-000000000000");
    }
}
