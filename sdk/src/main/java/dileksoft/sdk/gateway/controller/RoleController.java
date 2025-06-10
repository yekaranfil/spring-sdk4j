package dileksoft.sdk.gateway.controller;

import dileksoft.sdk.dto.RoleDTO;
import dileksoft.sdk.dto.UserDTO;
import dileksoft.sdk.persistence.domain.Role;
import dileksoft.sdk.persistence.domain.User;
import dileksoft.sdk.service.ts.RoleService;
import dileksoft.sdk.service.ts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    @GetMapping("/findAll")
    public ResponseEntity<List<Role>> getAll() {
        return ResponseEntity.ok(roleService.getAll());
    }


    @GetMapping("/findById")
    public ResponseEntity<Role> findChannelById(@RequestParam String id) {
        return ResponseEntity.ok(roleService.findById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<RoleDTO> saveChannel(@RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(roleService.save(roleDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteChannel(@RequestParam String id) {

        Object user =roleService.deleteById(id);

        if(user==null){
            return ResponseEntity.badRequest().body("Belirtilen ID ile eşleşen channel yok: " + id);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/findAllWithPage")
    public ResponseEntity<Page<Role>> getChannelsList(Pageable pageable) {
        Page<Role> roles = roleService.findAll(pageable);
        return ResponseEntity.ok(roles);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateChannel(@RequestBody RoleDTO roleDTO) {
        Object updateRole = roleService.update(roleDTO);
        if (updateRole == null) {
            return ResponseEntity.badRequest().body("Belirtilen ID ile eşleşen Role yok: " );
        }
        return ResponseEntity.ok(updateRole);
    }
}
