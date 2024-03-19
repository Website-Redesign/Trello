package com.example.trello.domain.card.entity;

import com.example.trello.domain.card.dto.CardRequestDto;
import com.example.trello.global.util.TimeStamp;
import jakarta.persistence.*;
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
	private String card_name;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private String color;

	@Column(nullable = false)
	private Long column_id;

	public Card(Long column_id, CardRequestDto requestDto) {
		card_name = requestDto.getCard_name();
		description = requestDto.getDescription();
		color = requestDto.getColor();
		this.column_id = column_id;
	}

	public void update(CardRequestDto requestDto){
		card_name = requestDto.getCard_name();
		description = requestDto.getDescription();
		color = requestDto.getColor();
	}
}
