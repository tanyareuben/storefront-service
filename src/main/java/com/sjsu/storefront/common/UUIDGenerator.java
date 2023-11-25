package com.sjsu.storefront.common;

import java.util.UUID;

public class UUIDGenerator {

    public static void main(String[] args) {
        // Generate a random UUID
        UUID uuid = generateUUID();

        // Print the generated UUID
        System.out.println("Generated UUID: " + uuid.toString());
    }

    public static UUID generateUUID() {
        // Use the static randomUUID method of the UUID class to generate a random UUID
        return UUID.randomUUID();
    }
}
