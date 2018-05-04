/**
 * date: 2018/5/4 9:43
 * Copyright (C) 2008-2018 oneapm.com. all rights reserved.
 */

package com.babyachievement;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class DummyUserRepository implements UserRepository {

    private final Map<Integer, User> users = new HashMap<>();

    public DummyUserRepository() {
        this.users.put(1, new User("Baby", 10));
        this.users.put(2, new User("Nathan", 20));
    }

    @Override
    public Mono<User> getUser(int id) {
        return Mono.justOrEmpty(this.users.get(id));
    }

    @Override
    public Flux<User> allUsers() {
        return Flux.fromIterable(this.users.values());
    }

    @Override
    public Mono<Void> saveUser(Mono<User> personMono) {
        return personMono.doOnNext(user -> {
            int id = users.size() + 1;
            users.put(id, user);
            System.out.format("Saved %s with id %d%n", user, id);
        }).thenEmpty(Mono.empty());
    }
}
