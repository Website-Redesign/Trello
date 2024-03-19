package com.example.trello.domain.card.entity;

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
    private String card_explanation;

    @Column(nullable = false)
    private String card_color;

    @Column(nullable = false)
    private Long column_id;
}
