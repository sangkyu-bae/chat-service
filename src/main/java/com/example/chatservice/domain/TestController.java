package com.example.chatservice.domain;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@Slf4j
public class TestController {

    @Operation(summary = "쓰레드 테스트", description = "쓰레드 테스트 입니다.")
    @PostMapping("/test")
    public String test() {
        long start = System.currentTimeMillis(); // 시작 시간 찍기

        log.info(" start");
        List<Thread> threads = IntStream.range(0, 1_000_000)
                .mapToObj(i -> new Thread(() -> {}))
                .collect(Collectors.toList());

        threads.forEach(Thread::start);

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis(); // 끝나는 시간 찍기

        log.info("걸린 시간(ms): " + (end - start));
        log.info("걸린 시간(s): " + (end - start) / 1000.0);


        return "ok";
    }


    @Operation(summary = "버추얼 쓰레드 테스트", description = "버추얼  쓰레드 테스트 입니다.")
    @PostMapping("/test/vt")
    public String testV(){
        long start = System.currentTimeMillis(); // 시작 시간 찍기

        log.info("start");

        List<Thread> threads = IntStream.range(0, 1_000_000)
                .mapToObj(i -> Thread.ofVirtual().unstarted(() -> {})) // VirtualThread로 변경!
                .collect(Collectors.toList());
//                .parallel().toList();

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis(); // 끝나는 시간 찍기

        log.info("걸린 시간(ms): " + (end - start));
        log.info("걸린 시간(s): " + (end - start) / 1000.0);

        return "ok";
    }

    @Operation(summary = "버추얼 쓰레드 테스트2", description = "버추얼  쓰레드 테스트 입니다.2")
    @PostMapping("/test/vt2")
    public String testV2(){
        long start = System.currentTimeMillis();

        log.info("start");

        long startone = System.currentTimeMillis();
        List<Thread> threads = IntStream.range(0, 1_000_000)
                .mapToObj(i -> Thread.ofVirtual().unstarted(() -> {}))
                .collect(Collectors.toList());

        threads.forEach(Thread::start);

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis();

        log.info("걸린 시간(ms): " + (end - start));
        log.info("걸린 시간(s): " + (end - start) / 1000.0);

        return "ok";
    }
}
