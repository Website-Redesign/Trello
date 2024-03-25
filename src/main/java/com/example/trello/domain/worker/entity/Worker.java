package com.example.trello.domain.worker.entity;

import com.example.trello.global.util.TimeStamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "worker")
@DynamicInsert
@DynamicUpdate
public class Worker extends TimeStamp {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long cardId;

	@Column(nullable = false)
	private Long userId;

	public Worker(Long cardId, Long userId) {
		this.cardId = cardId;
		this.userId = userId;
	}
}
