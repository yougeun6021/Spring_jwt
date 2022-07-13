package com.sparta.spring_jwt.models.post;

import java.time.LocalDateTime;

public interface PostInterface {
    String getTitle();
    String getWriter();
    LocalDateTime getModifiedAt();
    String getContent();
}
