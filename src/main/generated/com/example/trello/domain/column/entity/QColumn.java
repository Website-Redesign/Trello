package com.example.trello.domain.column.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QColumn is a Querydsl query type for Column
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QColumn extends EntityPathBase<Column> {

    private static final long serialVersionUID = -299425090L;

    public static final QColumn column = new QColumn("column");

    public final com.example.trello.global.util.QTimeStamp _super = new com.example.trello.global.util.QTimeStamp(this);

    public final NumberPath<Long> board_id = createNumber("board_id", Long.class);

    public final StringPath column_name = createString("column_name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QColumn(String variable) {
        super(Column.class, forVariable(variable));
    }

    public QColumn(Path<? extends Column> path) {
        super(path.getType(), path.getMetadata());
    }

    public QColumn(PathMetadata metadata) {
        super(Column.class, metadata);
    }

}

