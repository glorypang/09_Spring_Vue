package org.scoula.board.controller;


import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.scoula.board.domain.BoardAttachmentVO;
import org.scoula.board.dto.BoardDTO;
import org.scoula.board.service.BoardService;
import org.scoula.common.util.UploadFiles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.File;

@RestController                    // REST API 컨트롤러 선언 (@Controller + @ResponseBody)
@RequestMapping("/api/board")   // 기본 URL 매핑
@RequiredArgsConstructor           // final 필드 생성자 자동 생성
@Slf4j                             // 로깅 기능
@Api(
        tags = "게시글 관리",                    // 그룹 이름 (필수)
        description = "게시판 CRUD API",        // 상세 설명
        value = "BoardController"              // 컨트롤러 식별자
)
public class BoardController {

    private final BoardService service; // 의존성 주입

    @ApiOperation(value = "게시글 목록 조회", notes = "게시글 목록을 얻는 API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = BoardDTO.class),
            @ApiResponse(code = 400, message = "잘못된 요청입니다."),
            @ApiResponse(code = 500, message = "서버에서 오류가 발생했습니다.")
    })
    @GetMapping("")
    public ResponseEntity<List<BoardDTO>> getList() {
        log.info("============> 게시글 전체 목록 조회");

        List<BoardDTO> list = service.getList();
        return ResponseEntity.ok(list); // 200 OK + 데이터 반환
    }

    @ApiOperation(value = "상세정보 얻기", notes = "게시글 상세 정보를 얻는 API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = BoardDTO.class),
            @ApiResponse(code = 400, message = "잘못된 요청입니다."),
            @ApiResponse(code = 404, message = "게시글을 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "서버에서 오류가 발생했습니다.")
    })
    @GetMapping("/{no}")
    public ResponseEntity<BoardDTO> get(
            @ApiParam(
                    value = "게시글 ID",           // 매개변수 설명
                    required = true,              // 필수 여부
                    example = "1"                 // 예시 값
            )
            @PathVariable Long no) {
        log.info("============> 게시글 상세 조회: " + no);

        BoardDTO board = service.get(no);
        return ResponseEntity.ok(board);
    }

    @ApiOperation(value = "게시글 생성", notes = "새로운 게시글을 생성하는 API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = BoardDTO.class),
            @ApiResponse(code = 400, message = "잘못된 요청입니다."),
            @ApiResponse(code = 500, message = "서버에서 오류가 발생했습니다.")
    })
    @PostMapping("")
    public ResponseEntity<BoardDTO> create(
            @ApiParam(value = "게시글 객체", required = true)
            /*@RequestBody*/ //  mulipart/form-data 요청 매핑 시 매개변수 @RequestBody 삭제!!!
                    BoardDTO board) {
        log.info("============> 게시글 생성: " + board);

        // 새 게시글 생성 후 결과 반환
        BoardDTO createdBoard = service.create(board);
        return ResponseEntity.ok(createdBoard);
    }


    @ApiOperation(value = "게시글 수정", notes = "기존 게시글을 수정하는 API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = BoardDTO.class),
            @ApiResponse(code = 400, message = "잘못된 요청입니다."),
            @ApiResponse(code = 404, message = "게시글을 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "서버에서 오류가 발생했습니다.")
    })
    @PutMapping("/{no}")
    public ResponseEntity<BoardDTO> update(
            @ApiParam(value = "게시글 ID", required = true, example = "1")
            @PathVariable Long no,           // URL에서 게시글 번호 추출
            @ApiParam(value = "게시글 객체", required = true)
            /*@RequestBody*/ //  mulipart/form-data 요청 매핑 시 매개변수 @RequestBody 삭제!!!
                    BoardDTO board      // 수정할 데이터 (JSON)
    ) {
        log.info("============> 게시글 수정: " + no + ", " + board);

        // 게시글 번호 설정 (안전성을 위해)
        board.setNo(no);
        BoardDTO updatedBoard = service.update(board);
        return ResponseEntity.ok(updatedBoard);
    }

    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제하는 API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 요청이 처리되었습니다.", response = BoardDTO.class),
            @ApiResponse(code = 400, message = "잘못된 요청입니다."),
            @ApiResponse(code = 404, message = "게시글을 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "서버에서 오류가 발생했습니다.")
    })
    @DeleteMapping("/{no}")
    public ResponseEntity<BoardDTO> delete(
            @ApiParam(value = "게시글 ID", required = true, example = "1")
            @PathVariable Long no) {
        log.info("============> 게시글 삭제: " + no);

        // 삭제된 게시글 정보를 반환
        BoardDTO deletedBoard = service.delete(no);
        return ResponseEntity.ok(deletedBoard);
    }

    @ApiOperation(value = "첨부파일 다운로드", notes = "게시글의 첨부파일을 다운로드하는 API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "파일 다운로드 성공"),
            @ApiResponse(code = 404, message = "첨부파일을 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "서버에서 오류가 발생했습니다.")
    })
    @GetMapping("/download/{no}")
    public void download(
            @ApiParam(value = "첨부파일 ID", required = true, example = "1")
            @PathVariable Long no,
            HttpServletResponse response) throws Exception {
        // 1. 첨부파일 정보 조회
        BoardAttachmentVO attachment = service.getAttachment(no);

        // 2. 실제 파일 객체 생성
        File file = new File(attachment.getPath());

        // 3. 파일 다운로드 처리 (브라우저로 전송)
        UploadFiles.download(response, file, attachment.getFilename());
    }

    @ApiOperation(value = "첨부파일 삭제", notes = "게시글의 첨부파일을 삭제하는 API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "첨부파일 삭제 성공", response = Boolean.class),
            @ApiResponse(code = 404, message = "첨부파일을 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "서버에서 오류가 발생했습니다.")
    })
    @DeleteMapping("/deleteAttachment/{no}")
    public ResponseEntity<Boolean> deleteAttachment(
            @ApiParam(value = "첨부파일 ID", required = true, example = "1")
            @PathVariable Long no) throws Exception {
        return ResponseEntity.ok(service.deleteAttachment(no));
    }


}
