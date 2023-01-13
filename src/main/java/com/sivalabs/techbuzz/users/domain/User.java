package com.sivalabs.techbuzz.users.domain;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Arrays;

@Entity
@Table(name = "users")
@Setter
@Getter
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotEmpty()
	private String name;

	@Column(nullable = false, unique = true)
	@NotEmpty
	@Email(message = "Invalid email")
	private String email;

	@Column(nullable = false)
	@NotEmpty
	@Size(min = 4)
	private String password;

	@Column
	@Enumerated(EnumType.STRING)
	private RoleEnum role;

	@Column
	@Enumerated(EnumType.STRING)
	private AuthProvider authProvider;

	public boolean isAdminOrModerator() {
		return hasAnyRole(RoleEnum.ROLE_ADMIN, RoleEnum.ROLE_MODERATOR);
	}

	public boolean hasAnyRole(RoleEnum... roles) {
		return Arrays.asList(roles).contains(this.getRole());
	}

}
