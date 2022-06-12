package com.sparta.week02homework.repository;

import com.sparta.week02homework.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
