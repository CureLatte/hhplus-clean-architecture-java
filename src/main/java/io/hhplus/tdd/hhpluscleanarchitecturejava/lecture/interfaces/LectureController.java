package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.interfaces;

import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.application.LectureFacade;
import io.hhplus.tdd.hhpluscleanarchitecturejava.common.domain.BusinessError;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.entity.LectureTime;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.entity.RegisterLecture;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.interfaces.dto.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/lecture")
public class LectureController {

    final LectureFacade lectureFacade;

    public LectureController(LectureFacade lectureFacade) {
        this.lectureFacade = lectureFacade;
    }

    /**
     * 날짜별 수강 가능한 강의 조회
     *
     * @param date LocalDate
     * @return getLectureTimeResponse
     * @throws BusinessError BusinessError
     */
    @GetMapping("/time")
    public GetLectureTimeResponseDto lectureTimeList(@RequestParam LocalDate date) throws BusinessError {

        System.out.println(date);

        List<LectureTime> lectureTimeList = this.lectureFacade.getLectureTimeByDate(date);

        List<LectureTimeListDto> lectureTimeListDtoList = lectureTimeList.stream().map(LectureTimeListDto::new).toList();


        GetLectureTimeResponseDto response = new GetLectureTimeResponseDto(lectureTimeListDtoList);

        System.out.println("response: " + response);

        return response;
    }


    @PostMapping("/register")
    public PostRegisterLectureResponseDto postRegisterLecture(@RequestBody PostRegisterLectureRequestDto requestDto) throws BusinessError {

        System.out.println(requestDto);

        this.lectureFacade.postRegisterLecture(requestDto.getStudentId(), requestDto.getLectureTimeId());

        return new PostRegisterLectureResponseDto(true);
    }


    @GetMapping("/register/{id}/history")
    public GetRegisterLectureHistoryResponseDto getRegisterLectureHistory(@PathVariable Long id) throws BusinessError {

        List<RegisterLecture> historyList = this.lectureFacade.getLectureHistory(id);


        for (RegisterLecture history : historyList) {
            System.out.println("history ::  " + history);
            System.out.println(history.toString());


        }

        List<RegisterLectureListDto> resultList = historyList.stream().map(registerLecture -> {
            return RegisterLectureListDto.builder()
                    .lectureId(registerLecture.getLectureTime().lectureId)
                    .createAt(registerLecture.getCreatedAt())
                    .instructer(registerLecture.lectureTime.lecture.lecturer)
                    .title(registerLecture.lectureTime.lecture.title)
                    .studentId(registerLecture.student.getId())
                    .lectureTimeId(registerLecture.lectureTime.getId())
                    .studentCnt(registerLecture.lectureTime.studentCnt)
                    .build();
        }).toList();


        return GetRegisterLectureHistoryResponseDto.builder().lectureHistory(resultList).build();
    }

}
