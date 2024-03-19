package com.example.reactivestream;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class Article {
    private final String title;
    private final String content;
}
