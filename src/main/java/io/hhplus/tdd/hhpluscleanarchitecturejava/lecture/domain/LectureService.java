package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain;


import io.hhplus.tdd.hhpluscleanarchitecturejava.common.domain.BusinessError;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.entity.LectureTime;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.entity.RegisterLecture;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.repository.LectureRepository;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.repository.LectureTimeRepository;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.repository.RegisterLectureRepository;
import io.hhplus.tdd.hhpluscleanarchitecturejava.student.domain.Student;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

@Service
@AllArgsConstructor

public class LectureService {
    final LectureRepository lectureRepository;
    final LectureTimeRepository lectureTimeRepository;
    final RegisterLectureRepository registerLectureRepository;

    public final String NOT_FOUND_LECTURE_TIME_ERROR_MESSAGE = "존재하지 않은 강의 시간입니다.";
    public final String DUPLICATE_REGISTER_LECTURE_ERROR_MESSAGE = "중복된 신청이 존재합니다.";

    public final Map<Long, Lock> locks = new ConcurrentHashMap<>();

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
    @Transactional
    public RegisterLecture register(Student student, LectureTime lectureTime) throws BusinessError {

        // 마감 인원 초과
        lectureTime.registerValidate();


        //  중복 check ( 동일 시간 )
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

    public <T> T lock(Long id, Supplier<T> supplier) throws BusinessError {
        final Lock lock = this.locks.computeIfAbsent(id, k -> new ReentrantLock(true));
        lock.lock();
        try {
            return supplier.get();
        } finally {
            lock.unlock();
        }
    }

    @Transactional
    public RegisterLecture registerWithLock(Student student, LectureTime lectureTime) throws BusinessError {

        return this.lock(student.getId(), () -> {
            try {
                return this.register(student, lectureTime);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }

        });

    }

}
