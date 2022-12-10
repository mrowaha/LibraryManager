package com.librarymanager.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}

/*
 * 
 * class Body {
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String setTo) {
        this.name = setTo;
    }
}

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class GreetingsController {
    private static final String template = "Hello, %s";
    private final AtomicLong counter = new AtomicLong();
    
    @RequestMapping(value="/greeting/{id}", method=RequestMethod.POST, consumes = "application/json", produces = "application/json" )
    public Greeting greeting(
        @RequestBody Body body,
        @RequestParam(value="name", defaultValue="World", required = true) String name,
        @PathVariable(value="id") int id
    ) {
        System.out.println(body.getName());
        name = body.getName();
        return new Greeting(id, String.format(template,name));
    }
}

 */