/**
 * date: 2018/5/4 9:43
 * Copyright (C) 2008-2018 oneapm.com. all rights reserved.
 */

package com.babyachievement;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.ExchangeFunctions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

public class Client {
    private ExchangeFunction exchange = ExchangeFunctions.create(new ReactorClientHttpConnector());


    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.createUser();
        client.printAllUsers();
    }

    public void printAllUsers() {
        URI uri = URI.create(String.format("http://%s:%d/user", ReactiveServer
                .HOST, ReactiveServer.PORT));
        ClientRequest request = ClientRequest.create(HttpMethod.GET, uri).build();

        Flux<User> people = exchange.exchange(request)
                                    .flatMapMany(response -> response.bodyToFlux(User.class));

        Mono<List<User>> peopleList = people.collectList();
        System.out.println(peopleList.block());
    }

    public void createUser() {
        URI uri = URI.create(String.format("http://%s:%d/user", ReactiveServer
                .HOST, ReactiveServer.PORT));
        User jack = new User("Jack Doe", 16);


        ClientRequest request = ClientRequest.create(HttpMethod.POST, uri)
                                             .body(BodyInserters.fromObject(jack)).build();

        Mono<ClientResponse> response = exchange.exchange(request);

        System.out.println(response.block().statusCode());
    }
}
