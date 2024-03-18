package com.example.trello.domain.comment.entity;

import lombok.Getter;

@Getter
public enum DeletionStatus {
    DELETED("Y"),
    NOT_DELETED("N");

    private final String code;

    DeletionStatus(String code) {
        this.code = code;
    }
}
