package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.interfaces;

import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.application.LectureFacade;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.BusinessError;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.LectureTime;
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
    public GetLectureTimeResponse lectureTimeList(@RequestParam LocalDate date) throws BusinessError {

        System.out.println(date);

        List<LectureTime> lectureTimeList = this.lectureFacade.getLectureTimeByDate(date);

        List<LectureTimeListDto> lectureTimeListDtoList = lectureTimeList.stream().map(LectureTimeListDto::new).toList();


        GetLectureTimeResponse response = new GetLectureTimeResponse(lectureTimeListDtoList);

        System.out.println("response: " + response);

        return response;
    }


}
