package com.sparta.week02homework.service;

import com.sparta.week02homework.domain.Board;
import com.sparta.week02homework.domain.Favorite;
import com.sparta.week02homework.domain.FavoriteStatus;
import com.sparta.week02homework.domain.Users;
import com.sparta.week02homework.repository.BoardRepository;
import com.sparta.week02homework.repository.FavoriteRepository;
import com.sparta.week02homework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    public int like(Users userDetails, Long boardId) {
        Users user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다.")
        );

        Favorite favorite = new Favorite(FavoriteStatus.Like);
        favorite.setUserAndBoard(user, board);
        favoriteRepository.save(favorite);

        return favoriteRepository.countByBoard(board);


    }

    public int dislike(Users userDetails, Long boardId) {
        Users user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다.")
        );

        favoriteRepository.deleteByUserAndBoard(user, board);
        return favoriteRepository.countByBoard(board);
    }
}
