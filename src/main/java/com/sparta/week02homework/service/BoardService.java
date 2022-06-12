package com.sparta.week02homework.service;


import com.sparta.week02homework.domain.Board;
import com.sparta.week02homework.domain.Users;
import com.sparta.week02homework.dto.BoardDto;
import com.sparta.week02homework.repository.BoardRepository;
import com.sparta.week02homework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    public List<Board> findBoards() {
        return boardRepository.findAll();
    }

    public Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다.")
        );
    }

    @Transactional
    public BoardDto findOneBoard(Long boardId) {
        Board board = findBoardById(boardId);
        BoardDto boardDto = new BoardDto(board); // viewCount ++
        board.update(boardDto);
        return boardDto;
    }

    public String createBoard(Users userDetails, BoardDto boardDto) {
        Users user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        Board board = new Board(boardDto);
        board.setUser(user);
        boardRepository.save(board);
        return "[" + boardDto.getTitle() + "] 생성 완료";
    }


    public String deleteBoardById(Long boardId) {
        Board board = findBoardById(boardId); //있나 없나 확인
        boardRepository.deleteById(boardId);
        return "[" + board.getTitle() + "] 삭제 완료";
    }

    @Transactional
    public String update(Long boardId, BoardDto boardDto) {
        Board board = findBoardById(boardId);
        boardDto.setViewCount(board.getViewCount());
        board.update(boardDto);
        return "[" + board.getTitle() + "] 수정 완료";
    }
}
