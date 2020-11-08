package com.codingbit.context;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Mono;
import reactor.util.context.Context;

public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    /**
     * Get email for a user via {@link Context}.
     *
     * @return the user email
     * @throws java.util.NoSuchElementException when the {@link ContextType#USER} is not found in the stream
     */
    public Mono<String> getUserEmail() {
        // access the context
        return Mono.subscriberContext()
                .map(context -> {
                    // return the user id
                    User user = context.get(ContextType.USER.name());
                    return Mono.just(user.getId());
                })
                // get the email for this user
                .map(idMono -> idMono.map(this::emailFor))
                .flatMap(one -> one);
    }

    /**
     * Get the email for a user and log the content of the {@link Context}. (This is useful for logging trace ability)
     *
     * @param userId the user id to find the email for
     * @throws java.util.NoSuchElementException when the {@link ContextType#USER} is not found in the stream
     */
    public Mono<String> getUserEmail(final UUID userId) {
        // get the user email
        return Mono.just(emailFor(userId))
                // log the context info
                .doOnEach(stringSignal -> {
                    if (stringSignal.isOnNext()) {
                        final Context context = stringSignal.getContext();
                        log.info("getting email for user context: {}", context.get(ContextType.USER.name()).toString());
                    }
                });
    }

    /**
     * Fetches the email for a user id
     *
     * @param userId the user id to find the email for
     * @return the user email
     */
    private String emailFor(final UUID userId) {
        return String.format("%s@dev.dev", userId);
    }
}
