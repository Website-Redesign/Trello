package com.example.trello.domain.comment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import javax.management.Query;

import com.querydsl.core.types.Path;


/**
 * QComment is a Querydsl query type for Comment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComment extends EntityPathBase<Comment> {

    private static final long serialVersionUID = 798512344L;

    public static final QComment comment1 = new QComment("comment1");

    public final com.example.trello.global.util.QTimeStamp _super = new com.example.trello.global.util.QTimeStamp(this);

    public final NumberPath<Long> cardId = createNumber("cardId", Long.class);

    public final StringPath comment = createString("comment");

    public final NumberPath<Long> commentId = createNumber("commentId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QComment(String variable) {
        super(Comment.class, forVariable(variable));
    }

    public QComment(Path<? extends Comment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QComment(PathMetadata metadata) {
        super(Comment.class, metadata);
    }

}

