package org.example;

import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;


@Component("communication")
public class Communication {

    @Autowired
    private RestTemplate restTemplate;
    private final String URL = "http://94.198.50.185:7081/api/users";

    public List<String> cookies = new ArrayList<>();

    public List<User> getAllUsers() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>() {});
        cookies = responseEntity.getHeaders().get("Set-Cookie");
        List<User> users = responseEntity.getBody();
        return users;
    }

    public void saveUser(User user) {
        Long id = user.getId();
        HttpHeaders headers = new HttpHeaders();
        if (id > 2) {
            headers.put(HttpHeaders.COOKIE, cookies);
            HttpEntity<User> entity = new HttpEntity<User>(user, headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL, entity, String.class);
            System.out.println("New user was added");
            System.out.println(responseEntity.getBody());
        }
    }

    public void updateUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.COOKIE, cookies);
        HttpEntity<User> entity = new HttpEntity<User>(user, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);
        restTemplate.put(URL, entity);
        System.out.println("User was updated");
        System.out.println(responseEntity.getBody());
    }

    public void deleteUser(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.COOKIE, cookies);
        HttpEntity<User> entity = new HttpEntity<User>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, entity, String.class);
        System.out.println("User with " + id + " was deleted");
        System.out.println(responseEntity.getBody());
    }
}