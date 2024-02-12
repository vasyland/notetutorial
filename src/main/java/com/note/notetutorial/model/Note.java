package com.note.notetutorial.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.note.notetutorial.enumeration.Level;
import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
//import org.hibernate.annotations.Type;

@Data
@Entity
@Table(name = "note")
public class Note implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;
	@NotNull(message = "Title of this note cannot be null") 
	@NotEmpty(message = "Title of this note cannot be empty")
	@Column(name = "title")
	private String title;
	
	@NotNull(message = "Description of this note cannot be null") 
	@NotEmpty(message = "Description of this note cannot be empty")
	@Column(name = "description")
	private String description;
	
	@Column(name = "level")
//	@Enumerated(EnumType.STRING)
	@Enumerated(EnumType.ORDINAL)
	private Level level;
	
	@Column(name = "created_at")
	@JsonFormat(shape = STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSS", timezone = "America/Toronto")
	@UpdateTimestamp
    private LocalDateTime createdAt; 
}
