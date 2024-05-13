package com.example.userapp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Unmarshaller;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MainController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
//    private UsersRepository usersRepository;
    private CustomUsersRepository customUsersRepository;

//    @PostMapping(path="/add") // Map ONLY POST Requests
//    public @ResponseBody String addNewUser (@RequestParam String login
//            , @RequestParam String name) {
//        // @ResponseBody means the returned String is the response, not a view name
//        // @RequestParam means it is a parameter from the GET or POST request
//
//        Users n = new Users();
//        n.setLogin(login);
//        n.setName(name);
//
//        usersRepository.save(n);
//        return "Saved";
//    }
@CrossOrigin(origins = "http://localhost:3000")

@PostMapping(path = "add")
public ResponseEntity<String> addNewUser(@RequestParam("file") MultipartFile file) {
    if (file.isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to upload empty file");
    }

    try {
        XmlMapper xmlMapper = new XmlMapper();
        UsersList usersList = xmlMapper.readValue(file.getBytes(), UsersList.class);

        // Zapisz użytkowników do bazy danych
        List<Users> users = usersList.getUsers();
        customUsersRepository.saveAll(users);

        int numberOfRecordsAdded = users.size(); // Liczba dodanych rekordów
        String responseMessage = "Saved " + numberOfRecordsAdded + " records";

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(responseMessage);

        return ResponseEntity.ok(jsonResponse);
    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process XML file.");
    }
}

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Users> getAllUsers() {
        return customUsersRepository.findAll();
    }

    @GetMapping("/recordCount")
    public ResponseEntity<Long> getRecordCount() {
        long count = customUsersRepository.customCountQuery();
        return ResponseEntity.ok(count);
    }
    @GetMapping("/users")
    public Page<Users> getUsers(@RequestParam(value = "page",  defaultValue = "0") int page,
                                @RequestParam(value = "sort", defaultValue = "id") String sort)
//    public Page<Users> getUsers(@PathVariable int page, @RequestParam(defaultValue = "id") String sort) {
    {
        Sort.Direction direction = Sort.Direction.ASC;
//        if (sort.startsWith("-")) {
//            direction = Sort.Direction.DESC;
//            sort = sort.substring(1);
//        }
        int size = 10;

        switch (sort) {
            case "login":
                System.out.println("uzyto login");
                System.out.println();
                return customUsersRepository.findAll(PageRequest.of(page, size, direction, "login"));
            case "name":
                return customUsersRepository.findAll(PageRequest.of(page, size, direction, "name"));
            case "surname":
                return customUsersRepository.findAll(PageRequest.of(page, size, direction, "surname"));
            default:
                System.out.println("uzyto default");
                System.out.println();
                return customUsersRepository.findAll(PageRequest.of(page, size, direction, "id"));
        }
    }

}


