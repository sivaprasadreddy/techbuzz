package com.sivalabs.techbuzz.users.mappers;

import com.sivalabs.techbuzz.users.domain.User;
import com.sivalabs.techbuzz.users.domain.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.isVerified(),
                user.getVerificationToken());
    }
}
