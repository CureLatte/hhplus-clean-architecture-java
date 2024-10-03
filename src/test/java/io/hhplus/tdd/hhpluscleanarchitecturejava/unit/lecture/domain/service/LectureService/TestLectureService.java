package io.hhplus.tdd.hhpluscleanarchitecturejava.unit.lecture.domain.service.LectureService;

import io.hhplus.tdd.hhpluscleanarchitecturejava.common.domain.BusinessError;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
