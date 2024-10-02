package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.interfaces;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterLectureListDto {
    private long lectureTimeId;
    private long lectureId;
    private long studentId;
    private long time;
    private long studentCnt;
    private LocalDateTime createAt;
    private String title;
    private String instructer;


}
