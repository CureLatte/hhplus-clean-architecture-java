package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain;


import io.hhplus.tdd.hhpluscleanarchitecturejava.common.domain.BusinessError;
import io.hhplus.tdd.hhpluscleanarchitecturejava.student.domain.Student;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class LectureService {
    final LectureRepository lectureRepository;
    final LectureTimeRepository lectureTimeRepository;
    final RegisterLectureRepository registerLectureRepository;

    public final String NOT_FOUND_LECTURE_TIME_ERROR_MESSAGE = "존재하지 않은 강의 시간입니다.";
    public final String DUPLICATE_REGISTER_LECTURE_ERROR_MESSAGE = "중복된 신청이 존재합니다.";


    /**
     * LectureTime ID 조회
     *
     * @param lectureTimeId long
     * @return LectureTIme
     * @throws BusinessError BusinessError
     */
    public LectureTime getLectureTimeById(long lectureTimeId) throws BusinessError {

        LectureTime lectureTime = this.lectureTimeRepository.findOneById(lectureTimeId);


        if (lectureTime == null) {
            throw new BusinessError(this.NOT_FOUND_LECTURE_TIME_ERROR_MESSAGE);
        }

        return lectureTime;
    }

    /**
     * 날자별 강좌 조회
     *
     * @param targetDate LocalDate
     * @return List<LectureTime>
     */
    public List<LectureTime> showlectureTimeList(LocalDate targetDate) {
        return this.lectureTimeRepository.findAllByTime(targetDate);
    }


    /**
     * 강의 신청하기
     *
     * @param student     Student
     * @param lectureTime LectureTime
     */
    public RegisterLecture register(Student student, LectureTime lectureTime) throws BusinessError {

        // 마감 인원 초과
        lectureTime.registerValidate();

        //  중복 check
        RegisterLecture existRegisterLecture = this.registerLectureRepository.check(student, lectureTime);
        if (existRegisterLecture != null) {
            throw new BusinessError(this.DUPLICATE_REGISTER_LECTURE_ERROR_MESSAGE);
        }


        // 생성

        return this.registerLectureRepository.create(student, lectureTime);

    }


    /**
     * 수강 신청 리스트 조회
     *
     * @param student Student
     * @return List<RegisterLecture>
     */
    public List<RegisterLecture> showLectureHistory(Student student) {

        return this.registerLectureRepository.findAllByStudent(student);
    }

}
