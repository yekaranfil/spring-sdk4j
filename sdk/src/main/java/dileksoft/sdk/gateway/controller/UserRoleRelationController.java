package dileksoft.sdk.gateway.controller;

import dileksoft.sdk.dto.RoleDTO;
import dileksoft.sdk.dto.UserRoleRelationDTO;
import dileksoft.sdk.persistence.domain.Role;
import dileksoft.sdk.persistence.domain.UserRoleRelation;
import dileksoft.sdk.service.ts.RoleService;
import dileksoft.sdk.service.ts.UserRoleRelationService;
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
public class UserRoleRelationController {


    private final UserRoleRelationService userRoleRelationService;
    @GetMapping("/findAll")
    public ResponseEntity<List<UserRoleRelation>> getAll() {
        return ResponseEntity.ok(userRoleRelationService.getAll());
    }


    @GetMapping("/findById")
    public ResponseEntity<UserRoleRelation> findChannelById(@RequestParam String id) {
        return ResponseEntity.ok(userRoleRelationService.findById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<UserRoleRelationDTO> saveChannel(@RequestBody UserRoleRelationDTO userRoleRelationDTO) {
        return ResponseEntity.ok(userRoleRelationService.save(userRoleRelationDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteChannel(@RequestParam String id) {

        Object user =userRoleRelationService.deleteById(id);

        if(user==null){
            return ResponseEntity.badRequest().body("Belirtilen ID ile eşleşen channel yok: " + id);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/findAllWithPage")
    public ResponseEntity<Page<UserRoleRelation>> getChannelsList(Pageable pageable) {
        Page<UserRoleRelation> userRoleRelations = userRoleRelationService.findAll(pageable);
        return ResponseEntity.ok(userRoleRelations);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateChannel(@RequestBody UserRoleRelationDTO userRoleRelationDTO) {
        Object updateRoleRelations = userRoleRelationService.update(userRoleRelationDTO);
        if (updateRoleRelations == null) {
            return ResponseEntity.badRequest().body("Belirtilen ID ile eşleşen Role yok: " );
        }
        return ResponseEntity.ok(updateRoleRelations);
    }
}
