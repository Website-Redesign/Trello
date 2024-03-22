package com.example.trello.domain.card.entity;

import com.example.trello.domain.card.dto.CardDeadLineRequestDto;
import com.example.trello.domain.card.dto.CardRequestDto;
import com.example.trello.global.util.TimeStamp;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "card")
@DynamicInsert
@DynamicUpdate
public class Card extends TimeStamp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String cardname;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private String color;

	@Column(nullable = false)
	private Long columnId;

	@Column
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private Date deadLine;

	public Card(Long columnId, CardRequestDto requestDto) {
		this.cardname = requestDto.getCardname();
		this.description = requestDto.getDescription();
		this.color = requestDto.getColor();
		this.columnId = columnId;
	}

	public void update(CardRequestDto requestDto){
		this.cardname = requestDto.getCardname();
		this.description = requestDto.getDescription();
		this.color = requestDto.getColor();
	}

	public void deadLineUpdate(CardDeadLineRequestDto requestDto){
		deadLine = requestDto.getDeadTime();
	}
}
