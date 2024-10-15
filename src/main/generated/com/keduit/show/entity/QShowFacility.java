package com.keduit.show.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QShowFacility is a Querydsl query type for ShowFacility
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShowFacility extends EntityPathBase<ShowFacility> {

    private static final long serialVersionUID = -1089157819L;

    public static final QShowFacility showFacility = new QShowFacility("showFacility");

    public final StringPath adres = createString("adres");

    public final NumberPath<Double> la = createNumber("la", Double.class);

    public final NumberPath<Double> lo = createNumber("lo", Double.class);

    public final StringPath mt10id = createString("mt10id");

    public QShowFacility(String variable) {
        super(ShowFacility.class, forVariable(variable));
    }

    public QShowFacility(Path<? extends ShowFacility> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShowFacility(PathMetadata metadata) {
        super(ShowFacility.class, metadata);
    }

}

