package com.sparta.week02homework.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.week02homework.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Board extends Timestamped{

    @Id
    @Column(name = "BOARD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Users user;


    @Column(nullable = false)
    private String title;

    @Column
    private String body;

    @Column(nullable = false)
    private int viewCount = 0;


    public Board(BoardDto boardDto) {
        this.title = boardDto.getTitle();
        this.body = boardDto.getBody();
        this.viewCount = boardDto.getViewCount();
    }

    public void update(BoardDto boardDto) {
        this.title = boardDto.getTitle();
        this.body = boardDto.getBody();
        this.viewCount = boardDto.getViewCount();
    }


    public void setUser(Users user) {
        this.user = user;
        user.getBoardList().add(this);
    }
}
