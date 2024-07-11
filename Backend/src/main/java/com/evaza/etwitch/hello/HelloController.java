package com.evaza.etwitch.hello;

import com.github.javafaker.Faker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

// 接受请求的 - Springboot写出来的，接受你自己的前端发的请求的

// Though your class is called controller, but only when you add the "RestController" annotation,
// can the programming know that your "HelloController" is a controller.
@RestController
public class HelloController {

    // type in "http://localhost:8080/hello" in the browser, and you can see the "Halo!"
    // localhost:8080 is your self computer

//    @GetMapping("/hello")
//    public String sayHello() {
//        Faker faker = new Faker();
//        String name = faker.name().fullName();
//        String company = faker.company().name();
//        String street = faker.address().streetAddress();
//        String city = faker.address().city();
//        String state = faker.address().state();
//        String bookTitle = faker.book().title();
//        String bookAuthor = faker.book().author();
//
//        // 直接
//        String template = "This is %s.\n" +
//                " I work at %s.\n" +
//                " I live at %s in %s %s.\n" +
//                " My favorite book is %s by %s.\n";
//        return template.formatted(
//                name,
//                company,
//                street,
//                city,
//                state,
//                bookTitle,
//                bookAuthor
//        );
//    }

    // Update the return using record
    @GetMapping("/hello")
    public Person sayHello(@RequestParam(required = false) String locale) {
        if (locale == null) {
            locale = "en_US";
        }
        Faker faker = new Faker(new Locale(locale));
        String name = faker.name().fullName();
        String company = faker.company().name();
        String street = faker.address().streetAddress();
        String city = faker.address().city();
        String state = faker.address().state();
        String bookTitle = faker.book().title();
        String bookAuthor = faker.book().author();
        return new Person(name, company, new Address(street, city, state,
                null), new Book(bookTitle, bookAuthor));
    }


    // type in "http://localhost:8080/no" in the browser, and you can see the "No!"
    @GetMapping("/no")    // annotation cannot be in conflict with another (be the same)
    public String sayNo() {
        return "No!";
    }
}
