package dileksoft.sdk.gateway.controller;

import dileksoft.sdk.persistence.domain.User;
import dileksoft.sdk.service.ts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//BİSMİLLAHİRRAHMANİRRAHİM
@Controller
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @GetMapping("/findAll")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }


    @GetMapping("/findById")
    public ResponseEntity<User> findChannelById(@RequestParam String id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<User>> filter(@RequestParam String id,
                                             @RequestParam(required = false) String username,
                                             @RequestParam(required = false) String email,
                                             @RequestParam(required = false) String firstName,
                                             @RequestParam(required = false) String lastName,
                                             Pageable pageable) {
        return ResponseEntity.ok(userService.filter(id,username, email, firstName, lastName, pageable));
    }

    @GetMapping("/findAllWithPage")
    public ResponseEntity<Page<User>> getChannelsList(Pageable pageable) {
        Page<User> users = userService.findAllWithPage(pageable);
        return ResponseEntity.ok(users);
    }



}
