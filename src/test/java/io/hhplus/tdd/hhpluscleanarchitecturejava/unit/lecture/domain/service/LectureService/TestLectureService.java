package io.hhplus.tdd.hhpluscleanarchitecturejava.unit.lecture.domain.service.LectureService;

import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.*;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.repository.LectureRepository;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.repository.LectureTimeRepository;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.repository.RegisterLectureRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class TestLectureService {

    @InjectMocks
    LectureService lectureService;

    @Mock
    LectureRepository lectureRepository;

    @Mock
    LectureTimeRepository lectureTimeRepository;

    @Mock
    RegisterLectureRepository registerLectureRepository;


}
