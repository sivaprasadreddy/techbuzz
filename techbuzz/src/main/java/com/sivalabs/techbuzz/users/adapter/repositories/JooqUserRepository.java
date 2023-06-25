package com.sivalabs.techbuzz.users.adapter.repositories;

import static com.sivalabs.techbuzz.common.model.SystemClock.dateTimeNow;
import static com.sivalabs.techbuzz.jooq.tables.Users.USERS;

import com.sivalabs.techbuzz.jooq.tables.records.UsersRecord;
import com.sivalabs.techbuzz.users.domain.models.User;
import com.sivalabs.techbuzz.users.domain.models.UserProfile;
import com.sivalabs.techbuzz.users.domain.repositories.UserRepository;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.jooq.Records;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

@Repository
class JooqUserRepository implements UserRepository {
    private final DSLContext dsl;

    JooqUserRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Optional<User> findByEmail(String email) {
        return this.dsl.selectFrom(USERS).where(USERS.EMAIL.eq(email)).fetchOptional(UserRecordMapper.INSTANCE);
    }

    public Optional<UserProfile> findProfileById(Long id) {
        return this.dsl
                .select(USERS.ID, USERS.NAME, USERS.EMAIL, USERS.CREATED_AT)
                .from(USERS)
                .where(USERS.ID.eq(id))
                .fetchOptional(Records.mapping(UserProfile::new));
    }

    public Optional<String> findVerifiedUsersMailIds() {
        return this.dsl
                .select(DSL.listAgg(USERS.EMAIL, ",").withinGroupOrderBy(USERS.EMAIL))
                .from(USERS)
                .where(USERS.VERIFIED)
                .fetchOptional(r -> r.value1());
    }

    public boolean existsByEmail(String email) {
        return this.dsl.fetchExists(USERS, USERS.EMAIL.eq(email));
    }

    public Optional<User> findByEmailAndVerificationToken(String email, String token) {
        return this.dsl
                .selectFrom(USERS)
                .where(USERS.EMAIL.eq(email))
                .and(USERS.VERIFICATION_TOKEN.eq(token))
                .fetchOptional(UserRecordMapper.INSTANCE);
    }

    @Override
    public User save(User user) {
        return this.dsl
                .insertInto(USERS)
                .set(USERS.EMAIL, user.getEmail())
                .set(USERS.PASSWORD, user.getPassword())
                .set(USERS.NAME, user.getName())
                .set(USERS.ROLE, user.getRole())
                .set(USERS.VERIFIED, user.isVerified())
                .set(USERS.VERIFICATION_TOKEN, user.getVerificationToken())
                .set(USERS.CREATED_AT, dateTimeNow())
                .returning()
                .fetchOne(UserRecordMapper.INSTANCE);
    }

    @Override
    public void updateVerificationStatus(User user) {
        this.dsl
                .update(USERS)
                .set(USERS.VERIFIED, user.isVerified())
                .set(USERS.VERIFICATION_TOKEN, user.getVerificationToken())
                .where(USERS.ID.eq(user.getId()))
                .execute();
    }

    static class UserRecordMapper implements RecordMapper<UsersRecord, User> {
        static final UserRecordMapper INSTANCE = new UserRecordMapper();

        private UserRecordMapper() {}

        @Override
        public User map(UsersRecord r) {
            return new User(
                    r.getId(),
                    r.getName(),
                    r.getEmail(),
                    r.getPassword(),
                    r.getRole(),
                    r.getVerified() != null && r.getVerified(),
                    r.getVerificationToken());
        }
    }
}
