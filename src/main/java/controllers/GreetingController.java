package controllers;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.ebean.EbeanServer;
import messages.Greeting;
import models.User;

@RestController
public class GreetingController {
	
	@Autowired
    EbeanServer server;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    
    @RequestMapping("/counter")
    public Integer counter() {
        User user = new User("" + new Random().nextInt(1000000));
        user.save();
        return server.find(User.class).findCount();
    }
}
