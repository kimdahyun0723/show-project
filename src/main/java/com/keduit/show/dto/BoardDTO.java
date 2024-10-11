package com.keduit.show.dto;

import com.keduit.show.entity.Board;
import com.keduit.show.entity.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class BoardDTO {

    Long num;

    @NotEmpty(message = "제목은 필수 입력 입니다.")
    private String title;

    @NotEmpty(message = "제목은 필수 입력 입니다.")
    private String content;

    private String createBy;

    private static ModelMapper modelMapper = new ModelMapper();


    // DTO -> Entity
    public Board createBoard(Member member) {
        ModelMapper modelMapper = new ModelMapper();
        Board board = modelMapper.map(this, Board.class);
        board.setMember(member);
        return board;
    }

    public static BoardDTO of(Board board) {
        return modelMapper.map(board, BoardDTO.class);
    }




}
