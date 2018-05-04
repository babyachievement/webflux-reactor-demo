/**
 * date: 2018/5/4 9:42
 * Copyright (C) 2008-2018 oneapm.com. all rights reserved.
 */

package com.babyachievement;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public class PersonHandler {
    private final UserRepository repository;

    public PersonHandler(UserRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getPerson(ServerRequest request) {
        int personId = Integer.valueOf(request.pathVariable("id"));
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<User> personMono = this.repository.getUser(personId);
        return personMono
                .flatMap(user -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(
                        user)))
                .switchIfEmpty(notFound);
    }


    public Mono<ServerResponse> createPerson(ServerRequest request) {
        Mono<User> person = request.bodyToMono(User.class);
        return ServerResponse.ok().build(this.repository.saveUser(person));
    }

    public Mono<ServerResponse> listUsers(ServerRequest request) {
        Flux<User> users = this.repository.allUsers();
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(users, User.class);
    }
}
