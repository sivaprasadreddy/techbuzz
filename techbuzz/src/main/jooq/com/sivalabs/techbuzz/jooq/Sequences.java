/*
 * This file is generated by jOOQ.
 */
package com.sivalabs.techbuzz.jooq;


import org.jooq.Sequence;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;


/**
 * Convenience access to all sequences in public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

    /**
     * The sequence <code>public.post_id_seq</code>
     */
    public static final Sequence<Long> POST_ID_SEQ = Internal.createSequence("post_id_seq", Public.PUBLIC, SQLDataType.BIGINT.nullable(false), null, 5, null, null, false, null);

    /**
     * The sequence <code>public.user_id_seq</code>
     */
    public static final Sequence<Long> USER_ID_SEQ = Internal.createSequence("user_id_seq", Public.PUBLIC, SQLDataType.BIGINT.nullable(false), null, 5, null, null, false, null);

    /**
     * The sequence <code>public.vote_id_seq</code>
     */
    public static final Sequence<Long> VOTE_ID_SEQ = Internal.createSequence("vote_id_seq", Public.PUBLIC, SQLDataType.BIGINT.nullable(false), null, 5, null, null, false, null);
}
