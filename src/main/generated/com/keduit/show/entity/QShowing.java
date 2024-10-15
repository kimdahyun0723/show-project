package com.keduit.show.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QShowing is a Querydsl query type for Showing
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShowing extends EntityPathBase<Showing> {

    private static final long serialVersionUID = 2008325152L;

    public static final QShowing showing = new QShowing("showing");

    public final EnumPath<com.keduit.show.constant.Location> area = createEnum("area", com.keduit.show.constant.Location.class);

    public final StringPath entrpsnm = createString("entrpsnm");

    public final StringPath entrpsnmH = createString("entrpsnmH");

    public final StringPath fcltynm = createString("fcltynm");

    public final EnumPath<com.keduit.show.constant.Genre> genrenm = createEnum("genrenm", com.keduit.show.constant.Genre.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final StringPath mt10id = createString("mt10id");

    public final StringPath mt20id = createString("mt20id");

    public final NumberPath<Integer> pcseguidance = createNumber("pcseguidance", Integer.class);

    public final StringPath poster = createString("poster");

    public final StringPath prfage = createString("prfage");

    public final StringPath prfcast = createString("prfcast");

    public final StringPath prfcrew = createString("prfcrew");

    public final StringPath prfnm = createString("prfnm");

    public final DatePath<java.time.LocalDate> prfpdfrom = createDate("prfpdfrom", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> prfpdto = createDate("prfpdto", java.time.LocalDate.class);

    public final StringPath prfruntime = createString("prfruntime");

    public final EnumPath<com.keduit.show.constant.State> prfstate = createEnum("prfstate", com.keduit.show.constant.State.class);

    public final StringPath styurl = createString("styurl");

    public QShowing(String variable) {
        super(Showing.class, forVariable(variable));
    }

    public QShowing(Path<? extends Showing> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShowing(PathMetadata metadata) {
        super(Showing.class, metadata);
    }

}

