package org.scoula.board.mapper;

import org.scoula.board.domain.BoardAttachmentVO;
import org.scoula.board.domain.BoardVO;
import org.scoula.common.pagination.PageRequest;

import java.util.List;

public interface BoardMapper {
    // === 게시글 CRUD ===
    List<BoardVO> getList();                    // 게시글 목록 조회
    BoardVO get(Long no);                       // 게시글 상세 조회 (첨부파일 포함)
    void create(BoardVO board);                 // 게시글 생성
    int update(BoardVO board);                  // 게시글 수정
    int delete(Long no);                        // 게시글 삭제

    // === 첨부파일 관리 ===
    void createAttachment(BoardAttachmentVO attach);           // 첨부파일 등록
    List<BoardAttachmentVO> getAttachmentList(Long bno);       // 게시글별 첨부파일 목록
    BoardAttachmentVO getAttachment(Long no);                  // 첨부파일 상세 조회
    int deleteAttachment(Long no);
    // 첨부파일 삭제

    // === Pagination ===
    // 전체 게시글 수 조회
    int getTotalCount();

    // 페이징된 게시글 목록 조회
    List<BoardVO> getPage(PageRequest pageRequest);
}