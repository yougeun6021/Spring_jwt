package com.sparta.spring_jwt.models.post;

import java.time.LocalDateTime;

public interface ListPostInterface {
    Long getId();
    String getTitle();
    String getWriter();
    LocalDateTime getModifiedAt();
}