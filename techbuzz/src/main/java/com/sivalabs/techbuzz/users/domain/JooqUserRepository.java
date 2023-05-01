package com.sivalabs.techbuzz.users.domain;

import static com.sivalabs.techbuzz.jooq.tables.Users.USERS;

import com.sivalabs.techbuzz.jooq.tables.records.UsersRecord;
import java.time.LocalDateTime;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;

@Repository
class JooqUserRepository implements UserRepository {
    private final DSLContext dsl;

    JooqUserRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Optional<User> findByEmail(String email) {
        return this.dsl.selectFrom(USERS).where(USERS.EMAIL.eq(email)).fetchOptional(mapToUser());
    }

    public boolean existsByEmail(String email) {
        return this.dsl.fetchExists(USERS, USERS.EMAIL.eq(email));
    }

    public Optional<User> findByEmailAndVerificationToken(String email, String token) {
        return this.dsl
                .selectFrom(USERS)
                .where(USERS.EMAIL.eq(email))
                .and(USERS.VERIFICATION_TOKEN.eq(token))
                .fetchOptional(mapToUser());
    }

    @Override
    public User save(User user) {
        this.dsl
                .insertInto(USERS)
                .set(USERS.EMAIL, user.getEmail())
                .set(USERS.PASSWORD, user.getPassword())
                .set(USERS.NAME, user.getName())
                .set(USERS.ROLE, user.getRole().name())
                .set(USERS.VERIFIED, user.isVerified())
                .set(USERS.VERIFICATION_TOKEN, user.getVerificationToken())
                .set(USERS.CREATED_AT, LocalDateTime.now())
                .execute();
        return findByEmail(user.getEmail()).orElseThrow();
    }

    @Override
    public void update(User user) {
        this.dsl
                .update(USERS)
                .set(USERS.VERIFIED, user.isVerified())
                .set(USERS.VERIFICATION_TOKEN, user.getVerificationToken())
                .where(USERS.ID.eq(user.getId()))
                .execute();
    }

    private static RecordMapper<UsersRecord, User> mapToUser() {
        return r -> new User(
                r.getId(),
                r.getName(),
                r.getEmail(),
                r.getPassword(),
                r.getRole() != null ? RoleEnum.valueOf(r.getRole()) : null,
                r.getVerified() != null,
                r.getVerificationToken());
    }
}
