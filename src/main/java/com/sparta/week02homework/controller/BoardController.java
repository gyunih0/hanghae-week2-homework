package com.sparta.week02homework.controller;


import com.sparta.week02homework.domain.Users;
import com.sparta.week02homework.dto.BoardRequestDto;
import com.sparta.week02homework.dto.BoardResponseDto;
import com.sparta.week02homework.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/api/board")
    public List<BoardResponseDto> getBoardList() {
        return boardService.findBoards();
    }

    @PostMapping("/api/board")
    public String postBoard(@AuthenticationPrincipal Users userDetails, @Valid @RequestBody BoardRequestDto boardRequestDto) {
        return boardService.createBoard(userDetails, boardRequestDto);
    }

    @GetMapping("/api/board/{boardId}")
    public BoardResponseDto getBoard(@PathVariable Long boardId) {
        return boardService.findOneBoard(boardId);
    }

    @DeleteMapping("/api/board/{boardId}")
    public String deleteBoard(@PathVariable Long boardId) {
        return boardService.deleteBoardById(boardId);
    }

    @PutMapping("/api/board/{boardId}")
    public String updateBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto boardRequestDto) {
        return boardService.update(boardId, boardRequestDto);
    }
}
