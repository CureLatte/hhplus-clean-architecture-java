package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.interfaces;

import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.application.LectureFacade;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.BusinessError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LectureController {

    final LectureFacade lectureFacade;

    public LectureController(LectureFacade lectureFacade) {
        this.lectureFacade = lectureFacade;

    }

    @PostMapping("/test")
    public HealthCheckResponse test(@RequestBody HealthCheckRequest request) throws BusinessError {


        System.out.println(request.getInput());
        if (request.getInput().equals("error")) {
            throw new BusinessError("ERROR!!");
        }

        return new HealthCheckResponse("ok");
    }
}
