package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("alex", "ben", "chloe")).log(); //db or a remote call
    }

    public Mono<String> nameMono() {
        return Mono.just("chloe"); //db or a remote call
    }

    public Flux<String> namesFlux_map(int stringLength) {
        //filter the string whose length is greater then 3
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .map(s -> s.length() + "-" + s)
                .log(); //db or a remote call
    }

    public Flux<String> namesFlux_immutability() {
        var namesFlux = Flux.fromIterable(List.of("alex", "ben", "chloe"));
        namesFlux.map(String::toUpperCase);
        return namesFlux;
    }

    public Mono<String> nameMono_map_filter(int stringLength) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .log();
    }

    public Mono<List<String>> nameMono_flatMap(int stringLength) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(this::splitStringMono)
                .log();
    }

    public Flux<String> nameMono_flatMapMany(int stringLength) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMapMany(this::splitString)
                .log();
    }

    private Mono<List<String>> splitStringMono(String s) {
        var charArray = s.split("");
        var charList = List.of(charArray);
        return Mono.just(charList);
    }

    public Flux<String> namesFlux_flatmap(int stringLength) {
        //filter the string whose length is greater then 3
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(s -> splitString(s))
                .log(); //db or a remote call
    }

    public Flux<String> namesFlux_flatmap_async(int stringLength) {
        //filter the string whose length is greater then 3
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(s -> splitString_withDelay(s))
                .log(); //db or a remote call
    }

    public Flux<String> namesFlux_concatmap(int stringLength) {
        //filter the string whose length is greater then 3
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .concatMap(s -> splitString_withDelay(s))
                .log(); //db or a remote call
    }

    public Flux<String> namesFlux_transform(int stringLength) {
        //filter the string whose length is greater then 3

        Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > stringLength);

        //Flux.empty()
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .transform(filterMap)
                .flatMap(s -> splitString(s))
                .defaultIfEmpty("default")
                .log();
    }

    public Flux<String> namesFlux_transform_switchIfEmpty(int stringLength) {
        //filter the string whose length is greater then 3

        Function<Flux<String>, Flux<String>> filterMap = name ->
                name.map(String::toUpperCase)
                        .filter(s -> s.length() > stringLength)
                        .flatMap(s -> splitString(s));

        var defaultFlux = Flux.just("default").transform(filterMap);

        //Flux.empty()
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .transform(filterMap)
                .switchIfEmpty(defaultFlux)
                .log();
    }


    public Flux<String> explore_concat() {
        var flux1 = Flux.just("A", "B", "C");
        var flux2 = Flux.just("D", "E", "F");

        return Flux.concat(flux1, flux2).log();
    }

    public Flux<String> explore_concatWith() {
        var flux1 = Flux.just("A", "B", "C");
        var flux2 = Flux.just("D", "E", "F");

        return flux1.concatWith(flux2).log();
    }

    public Flux<String> explore_concatWith_mono() {
        var aMono = Mono.just("A");
        var bMono = Mono.just("B");

        return aMono.concatWith(bMono).log();
    }

    public Flux<String> explore_merge() {
        var flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));
        var flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(125));

        return Flux.merge(flux1, flux2).log();
    }

    public Flux<String> explore_mergeWith() {
        var flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));
        var flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(125));

        return flux1.mergeWith(flux2).log();
    }

    public Flux<String> explore_mergeWithMono() {
        var mono1 = Mono.just("A");
        var mono2 = Mono.just("D");

        return mono1.mergeWith(mono2).log();
    }

    public Flux<String> explore_merge_sequential() {
        var flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));
        var flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(125));

        return Flux.mergeSequential(flux1, flux2).log();
    }

    public Flux<String> explore_zip() {
        var flux1 = Flux.just("A", "B", "C");
        var flux2 = Flux.just("D", "E", "F");

        return Flux.zip(flux1, flux2, (first, second) -> first + second).log();
    }

    public Flux<String> explore_zipWith() {
        var flux1 = Flux.just("A", "B", "C");
        var flux2 = Flux.just("D", "E", "F");

        return flux1.zipWith(flux2, (first, second) -> first + second).log();
    }

    public Flux<String> explore_zip_1() {
        var flux1 = Flux.just("A", "B", "C");
        var flux2 = Flux.just("D", "E", "F");
        var flux3 = Flux.just("1", "2", "3");
        var flux4 = Flux.just("4", "5", "6");

        return Flux.zip(flux1, flux2, flux3, flux4)
                .map(t4 -> t4.getT1() + t4.getT2() + t4.getT3() + t4.getT4()).log();
    }

    public Mono<String> explore_zipWithMono() {
        var mono1 = Mono.just("A");
        var mono2 = Mono.just("D");

        return mono1.zipWith(mono2)
                .map(t2 -> t2.getT1() + t2.getT2())
                .log();
    }

    public Flux<String> splitString(String name) {
        var charArray = name.split("");
        return Flux.fromArray(charArray);
    }

    public Flux<String> splitString_withDelay(String name) {
        var charArray = name.split("");
        var delay = new Random().nextInt(1000);
        return Flux.fromArray(charArray)
                .delayElements(Duration.ofMillis(delay));
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

        fluxAndMonoGeneratorService.namesFlux()
                .subscribe(name -> System.out.println(name));

        fluxAndMonoGeneratorService.nameMono()
                .subscribe(name -> System.out.println(name));
    }
}
