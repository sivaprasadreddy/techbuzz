package com.sivalabs.techbuzz.posts.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	@NotEmpty()
	private String name;

	@Column(nullable = false, unique = true)
	@NotEmpty()
	private String slug;

	@Column(nullable = false, unique = true)
	@NotEmpty()
	private String description;

	private String image;

	private Integer displayOrder;

	@Column(updatable = false)
	protected LocalDateTime createdAt;

	@Column(insertable = false)
	protected LocalDateTime updatedAt;

	@PrePersist
	public void onCreate() {
		createdAt = LocalDateTime.now();
	}

	@PreUpdate
	public void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

}
