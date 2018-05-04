/**
 * date: 2018/5/4 9:41
 * Copyright (C) 2008-2018 oneapm.com. all rights reserved.
 */

package com.babyachievement;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepository {
    Mono<Person> getPerson(int id);

    Flux<Person> allPeople();

    Mono<Void> savePerson(Mono<Person> person);
}
