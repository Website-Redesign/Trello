package com.example.trello.domain.card.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCard is a Querydsl query type for Card
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCard extends EntityPathBase<Card> {

    private static final long serialVersionUID = -1994781070L;

    public static final QCard card = new QCard("card");

    public final com.example.trello.global.util.QTimeStamp _super = new com.example.trello.global.util.QTimeStamp(this);

    public final StringPath cardname = createString("cardname");

    public final StringPath color = createString("color");

    public final NumberPath<Long> columnId = createNumber("columnId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    public final DateTimePath<java.time.LocalDateTime> deadLine = createDateTime("deadLine", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCard(String variable) {
        super(Card.class, forVariable(variable));
    }

    public QCard(Path<? extends Card> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCard(PathMetadata metadata) {
        super(Card.class, metadata);
    }

}

