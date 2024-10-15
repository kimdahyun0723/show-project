package com.keduit.show.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberImg is a Querydsl query type for MemberImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberImg extends EntityPathBase<MemberImg> {

    private static final long serialVersionUID = 501663844L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberImg memberImg = new QMemberImg("memberImg");

    public final QMember member;

    public final NumberPath<Long> num = createNumber("num", Long.class);

    public final StringPath url = createString("url");

    public QMemberImg(String variable) {
        this(MemberImg.class, forVariable(variable), INITS);
    }

    public QMemberImg(Path<? extends MemberImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberImg(PathMetadata metadata, PathInits inits) {
        this(MemberImg.class, metadata, inits);
    }

    public QMemberImg(Class<? extends MemberImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

