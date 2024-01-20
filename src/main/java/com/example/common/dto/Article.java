package com.example.common.dto;

import lombok.Data;

@Data
public class Article {
    private final String id;
    private final String title;
    private final String content;
}
