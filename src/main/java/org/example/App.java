package org.example;

import org.example.configuration.MyConfig;
import org.example.model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.List;

public class App {

    public static void main( String[] args ) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        Communication communication = context.getBean("communication", Communication.class);
        List<User> allUsers = communication.getAllUsers();
        System.out.println(allUsers);

        User user = new User(3L, "James", "Brown", (byte) 25);
        User updatedUser = new User(3L, "Thomas", "Shelby", (byte) 25);
        communication.saveUser(user);

        communication.updateUser(updatedUser);

        communication.deleteUser(3L);
    }
}