package com.sparta.week02homework.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sparta.week02homework.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


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

    // UserId
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


    // Favorite 연관관계
    @JsonManagedReference
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Favorite> favorites = new ArrayList<>();


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
