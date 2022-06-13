package com.sparta.week02homework.repository;

import com.sparta.week02homework.domain.Board;
import com.sparta.week02homework.domain.Favorite;
import com.sparta.week02homework.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    void deleteByUserAndBoard(Users user, Board board);
    int countByBoard(Board board);
}
