package com.example.clientapp.model;

public record Users(Integer id, String username,
                    String email, String fullName,
                    String address, String position) {
}