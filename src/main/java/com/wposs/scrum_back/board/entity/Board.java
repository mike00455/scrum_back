package com.wposs.scrum_back.board.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "board", schema = "wposs")
public class Board {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "board_id")
    private UUID board_id;

//    @Column(name = "")


}
