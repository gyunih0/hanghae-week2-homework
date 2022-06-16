package com.sparta.week02homework.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.week02homework.domain.Users;
import com.sparta.week02homework.dto.BoardRequestDto;
import com.sparta.week02homework.dto.BoardResponseDto;
import com.sparta.week02homework.service.BoardService;
import com.sparta.week02homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final UserService userService;
    private final BoardService boardService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/api/board")
    public List<BoardResponseDto> getBoardList() {
        return boardService.findBoards();
    }

    @GetMapping("/api/board/{boardId}")
    public BoardResponseDto getBoard(@PathVariable Long boardId) {
        return boardService.findOneBoard(boardId);
    }

    @PostMapping("/api/board")
    public String postBoard(@AuthenticationPrincipal Users userDetails,
                            @RequestParam("board") String board,
                            @RequestPart(required = false) MultipartFile file) throws JsonProcessingException{

        userService.loginCheck(userDetails);

        BoardRequestDto boardRequestDto = objectMapper.readValue(board, BoardRequestDto.class);

        return boardService.createBoard(userDetails, boardRequestDto, file);
    }

    @DeleteMapping("/api/board/{boardId}")
    public String deleteBoard(@AuthenticationPrincipal Users userDetails,
                              @PathVariable Long boardId) {
        userService.loginCheck(userDetails);

        return boardService.deleteBoardById(boardId);
    }

    @PutMapping("/api/board/{boardId}")
    public String updateBoard(@PathVariable Long boardId,
                              @AuthenticationPrincipal Users userDetails,
                              @RequestParam("board") String board,
                              @RequestPart(required = false) MultipartFile file) throws JsonProcessingException {
        userService.loginCheck(userDetails);

        BoardRequestDto boardRequestDto = objectMapper.readValue(board, BoardRequestDto.class);

        return boardService.update(boardId, boardRequestDto, file);
    }
}
