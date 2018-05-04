/**
 * date: 2018/5/4 9:41
 * Copyright (C) 2008-2018 oneapm.com. all rights reserved.
 */

package com.babyachievement;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> getUser(int id);

    Flux<User> allUsers();

    Mono<Void> saveUser(Mono<User> user);
}
