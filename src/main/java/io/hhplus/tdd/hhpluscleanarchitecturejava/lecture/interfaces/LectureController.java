package io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.interfaces;

import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.domain.vo.BusinessError;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.interfaces.dto.request.HealthCheckRequest;
import io.hhplus.tdd.hhpluscleanarchitecturejava.lecture.interfaces.dto.response.HealthCheckResponse;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class LectureController {

    @PostMapping("/test")
    public HealthCheckResponse test(@RequestBody HealthCheckRequest request) throws BusinessError {


        System.out.println(request.getInput());
        if (request.getInput().equals("error")) {
            throw new BusinessError("ERROR!!");
        }

        return new HealthCheckResponse("ok");
    }
}
