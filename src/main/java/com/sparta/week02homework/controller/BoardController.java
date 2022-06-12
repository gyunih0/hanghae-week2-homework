package com.sparta.week02homework.controller;


import com.sparta.week02homework.domain.Board;
import com.sparta.week02homework.domain.Users;
import com.sparta.week02homework.dto.BoardDto;
import com.sparta.week02homework.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/api/board")
    public List<Board> getBoardList() {
        return boardService.findBoards();
    }

    @PostMapping("/api/board")
    public String postBoard(@AuthenticationPrincipal Users userDetails, @RequestBody BoardDto boardDto) {
        return boardService.createBoard(userDetails, boardDto);
    }

    @GetMapping("/api/board/{boardId}")
    public BoardDto getBoard(@PathVariable Long boardId) {
        return boardService.findOneBoard(boardId);
    }

    @DeleteMapping("/api/board/{boardId}")
    public String deleteBoard(@PathVariable Long boardId) {
        return boardService.deleteBoardById(boardId);
    }

    @PutMapping("/api/board/{boardId}")
    public String updateBoard(@PathVariable Long boardId, @RequestBody BoardDto boardDto) {
        return boardService.update(boardId, boardDto);
    }
}
