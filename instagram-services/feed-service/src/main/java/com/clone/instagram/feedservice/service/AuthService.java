package com.clone.instagram.feedservice.service;

import com.clone.instagram.feedservice.client.AuthServiceClient;
import com.clone.instagram.feedservice.exception.UnableToGetAccessTokenException;
import com.clone.instagram.feedservice.exception.UnableToGetUsersException;
import com.clone.instagram.feedservice.web.rest.payload.UserSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;


@Service
@Slf4j
public class AuthService {

    @Autowired private AuthServiceClient authClient;
    public Map<String, String> usersProfilePic(List<String> usernames) {

        ResponseEntity<List<UserSummary>> response = authClient.findByUsernameIn(usernames);

        if(!response.getStatusCode().is2xxSuccessful()) {
            String message = String.format("unable to get user summaries %s",
                    response.getStatusCode());

            log.error(message);
            throw new UnableToGetUsersException(message);
        }

       return response
                .getBody()
                .stream()
                .collect(toMap(UserSummary::getUsername,
                        UserSummary::getProfilePicture));
    }
}
