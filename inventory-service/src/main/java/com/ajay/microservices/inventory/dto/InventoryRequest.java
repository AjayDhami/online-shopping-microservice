package com.ajay.microservices.inventory.dto;

public record InventoryRequest(Long id, String skuCode, int quantity) {
}
