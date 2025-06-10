package dileksoft.sdk.gateway.controller;


import dileksoft.sdk.dto.UserDTO;
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

    @PostMapping("/save")
    public ResponseEntity<UserDTO> saveChannel(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.save(userDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteChannel(@RequestParam String id) {

        Object user =userService.delete(id);

        if(user==null){
            return ResponseEntity.badRequest().body("Belirtilen ID ile eşleşen channel yok: " + id);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/findAllWithPage")
    public ResponseEntity<Page<User>> getChannelsList(Pageable pageable) {
        Page<User> users = userService.findAllWithPage(pageable);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateChannel(@RequestBody UserDTO userDTO) {
        Object updateUser = userService.update(userDTO);
        if (updateUser == null) {
            return ResponseEntity.badRequest().body("Belirtilen ID ile eşleşen channel yok: " );
        }
        return ResponseEntity.ok(updateUser);
    }

}
