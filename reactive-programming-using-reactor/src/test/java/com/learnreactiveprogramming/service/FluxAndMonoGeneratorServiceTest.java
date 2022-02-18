package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    @Test
    void namesFlux() {
        //given

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux();

        //then
        StepVerifier.create(namesFlux)
                .expectNext("alex", "ben")
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void namesFlux_map() {
        //given
        int stringLength = 3;

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_map(stringLength);

        //then
        StepVerifier.create(namesFlux)
                .expectNext("4-ALEX", "5-CHLOE")
                .verifyComplete();
    }

    @Test
    void namesFlux_immutability() {
        //given

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_immutability();

        //then
        StepVerifier.create(namesFlux)
//                .expectNext("ALEX", "BEN", "CHLOE")
                .expectNext("alex", "ben", "Chloe")
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmap() {
        //given
        int stringLength = 3;

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_flatmap(stringLength);

        //then
        StepVerifier.create(namesFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmap_async() {
        //given
        int stringLength = 3;

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_flatmap_async(stringLength);

        //then
        StepVerifier.create(namesFlux)
//                .expectNext("A","L","E","X","C","H","L","O","E")
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void namesFlux_concatmap() {
        //given
        int stringLength = 3;

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_concatmap(stringLength);

        //then
        StepVerifier.create(namesFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
//                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    void nameMono_map_filter() {
        //given
        int stringLength = 3;

        //when
        var namesFlux = fluxAndMonoGeneratorService.nameMono_map_filter(stringLength);

        //then
        StepVerifier.create(namesFlux)
                .expectNext("ALEX")
                .verifyComplete();
    }

    @Test
    void nameMono_flatMap() {
        //given
        int stringLength = 3;

        //when
        var value = fluxAndMonoGeneratorService.nameMono_flatMap(stringLength);

        //then
        StepVerifier.create(value)
                .expectNext(List.of("A","L","E","X"))
                .verifyComplete();
    }

    @Test
    void nameMono_flatMapMany() {
        //given
        int stringLength = 3;

        //when
        var value = fluxAndMonoGeneratorService.nameMono_flatMapMany(stringLength);

        //then
        StepVerifier.create(value)
                .expectNext("A","L","E","X")
                .verifyComplete();
    }

    @Test
    void namesFlux_transform() {
        //given
        int stringLength = 3;

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_transform(stringLength);

        //then
        StepVerifier.create(namesFlux)
                .expectNext("A","L","E","X","C","H","L","O","E")
                .verifyComplete();
    }

    @Test
    void namesFlux_transform_1() {
        //given
        int stringLength = 6;

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_transform(stringLength);

        //then
        StepVerifier.create(namesFlux)
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void namesFlux_transform_switchIfEmpty() {
        //given
        int stringLength = 6;

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_transform_switchIfEmpty(stringLength);

        //then
        StepVerifier.create(namesFlux)
                .expectNext("D","E","F","A","U","L","T")
                .verifyComplete();
    }

    @Test
    void explore_concat() {
        //given

        //when
        var concatFlux = fluxAndMonoGeneratorService.explore_concat();

        //then
        StepVerifier.create(concatFlux)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    void explore_concatWith() {
        //given

        //when
        var concatFlux = fluxAndMonoGeneratorService.explore_concatWith();

        //then
        StepVerifier.create(concatFlux)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();
    }

    @Test
    void explore_concatWithMono() {
        //given

        //when
        var concatMono = fluxAndMonoGeneratorService.explore_concatWith_mono();

        //then
        StepVerifier.create(concatMono)
                .expectNext("A","B")
                .verifyComplete();
    }

    @Test
    void explore_merge() {
        //given

        //when
        var mergeFlux = fluxAndMonoGeneratorService.explore_merge();

        //then
        StepVerifier.create(mergeFlux)
                .expectNext("A","D","B","E","C","F")
                .verifyComplete();
    }
    @Test
    void explore_mergeWith() {
        //given

        //when
        var mergeFlux = fluxAndMonoGeneratorService.explore_mergeWith();

        //then
        StepVerifier.create(mergeFlux)
                .expectNext("A","D","B","E","C","F")
                .verifyComplete();
    }
    @Test
    void explore_mergeWithMono() {
        //given

        //when
        var mergeFlux = fluxAndMonoGeneratorService.explore_mergeWithMono();

        //then
        StepVerifier.create(mergeFlux)
                .expectNext("A","D")
                .verifyComplete();
    }

    @Test
    void explore_mergeSequential() {
        //given

        //when
        var mergeFlux = fluxAndMonoGeneratorService.explore_merge_sequential();

        //then
        StepVerifier.create(mergeFlux)
                .expectNext("A","B","C","D","E ","F")
                .verifyComplete();
    }

    @Test
    void explore_zip() {
        //given

        //when
        var mergeFlux = fluxAndMonoGeneratorService.explore_zip();

        //then
        StepVerifier.create(mergeFlux)
                .expectNext("AD","BE","CF")
                .verifyComplete();
    }

    @Test
    void explore_zipWith() {
        //given

        //when
        var mergeFlux = fluxAndMonoGeneratorService.explore_zipWith();

        //then
        StepVerifier.create(mergeFlux)
                .expectNext("AD","BE","CF")
                .verifyComplete();
    }

    @Test
    void explore_zip_1() {
        //given

        //when
        var mergeFlux = fluxAndMonoGeneratorService.explore_zip_1();

        //then
        StepVerifier.create(mergeFlux)
                .expectNext("AD14","BE25","CF36")
                .verifyComplete();
    }

    @Test
    void explore_zipWith_mono() {
        //given

        //when
        var mergeFlux = fluxAndMonoGeneratorService.explore_zipWithMono();

        //then
        StepVerifier.create(mergeFlux)
                .expectNext("AD")
                .verifyComplete();
    }
}