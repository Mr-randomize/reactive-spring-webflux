package com.reactivespring.moviesinfoservice.controller;

import com.reactivespring.moviesinfoservice.domain.MovieInfo;
import com.reactivespring.moviesinfoservice.service.MovieInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
@Slf4j
public class MoviesInfoController {

    private MovieInfoService movieInfoService;

    public MoviesInfoController(MovieInfoService movieInfoService) {
        this.movieInfoService = movieInfoService;
    }

    @GetMapping("/movieinfos")
    public Flux<MovieInfo> getAllMovieInfos(@RequestParam(value = "year", required = false) Integer year) {
        log.info("Year is : {}" + year);
        if (year != null) {
            return movieInfoService.getMovieInfoByYear(year).log();
        }
        return movieInfoService.getAllMovieInfos().log();
    }

//    @GetMapping("/movieinfos/{id}")
//    public Mono<MovieInfo> getAllMovieInfoById(@PathVariable String id) {
//        return movieInfoService.getMovieInfoById(id).log();
//    }


    @GetMapping("/movieinfos/{id}")
    public Mono<ResponseEntity<MovieInfo>> getMovieInfoById_approach2(@PathVariable("id") String id) {

        return movieInfoService.getMovieInfoById(id)
                .map(movieInfo1 -> ResponseEntity.ok()
                        .body(movieInfo1))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .log();
    }

    @PostMapping("/movieinfos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> addMovieInfo(@RequestBody @Valid MovieInfo movieInfo) {

        return movieInfoService.addMovieInfo(movieInfo).log();

    }

    @PutMapping("/movieinfos/{id}")
    public Mono<ResponseEntity<MovieInfo>> updateMovieInfo(@PathVariable String id, @RequestBody MovieInfo updatedMovieInfo) {

        return movieInfoService.updateMovieInfo(id, updatedMovieInfo)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .log();

    }

    @DeleteMapping("/movieinfos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMovieInfo(@PathVariable String id) {

        return movieInfoService.deleteMovieInfo(id).log();

    }
}
