package org.scoula.board.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.board.domain.BoardAttachmentVO;
import org.scoula.board.domain.BoardVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "게시글 DTO")    // DTO 클래스 설명
public class BoardDTO {

    @ApiModelProperty(
            value = "게시글 ID",
            example = "1",
            required = true,
            position = 1                // 문서에서의 표시 순서
    )
    private Long no;

    @ApiModelProperty(
            value = "제목",
            example = "게시글 제목 예시",
            required = true,
            position = 2
    )
    private String title;

    @ApiModelProperty(
            value = "글 본문",
            example = "게시글 내용 예시입니다.",
            required = true,
            position = 3
    )
    private String content;

    @ApiModelProperty(
            value = "작성자",
            example = "user1",
            required = true,
            position = 4
    )
    private String writer;

    @ApiModelProperty(
            value = "등록일",
            example = "2025-01-20T10:30:00",
            position = 5
    )
    private Date regDate;

    @ApiModelProperty(
            value = "수정일",
            example = "2025-01-20T15:45:00",
            position = 6
    )
    private Date updateDate;

    @ApiModelProperty(
            value = "첨부파일 목록",
            position = 7
    )
    private List<BoardAttachmentVO> attaches;

    @ApiModelProperty(
            value = "업로드 파일 목록",
            hidden = true            // Swagger UI에서 숨김 (내부 처리용)
    )
    private List<MultipartFile> files = new ArrayList<>();
    /**
     * BoardVO를 BoardDTO로 변환하는 정적 팩토리 메서드
     * @param vo 변환할 BoardVO 객체
     * @return 변환된 BoardDTO 객체 (vo가 null이면 null 반환)
     */
    public static BoardDTO of(BoardVO vo) {
        return vo == null ? null : BoardDTO.builder()
                .no(vo.getNo())
                .title(vo.getTitle())
                .content(vo.getContent())
                .writer(vo.getWriter())
                .regDate(vo.getRegDate())
                .updateDate(vo.getUpdateDate())
                .attaches(vo.getAttaches())
                .regDate(vo.getRegDate())
                .build();

    }

    /**
     * 현재 BoardDTO를 BoardVO로 변환
     * @return 변환된 BoardVO 객체
     */
    public BoardVO toVo() {
        return BoardVO.builder()
                .no(no)                    // this.no와 동일
                .title(title)              // this.title과 동일
                .content(content)
                .writer(writer)
                .regDate(regDate)
                .updateDate(updateDate)

                .attaches(attaches)
                .regDate(regDate)
                .build();
    }
}