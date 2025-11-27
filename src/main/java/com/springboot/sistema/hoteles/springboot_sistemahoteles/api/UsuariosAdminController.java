package com.springboot.sistema.hoteles.springboot_sistemahoteles.api;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Usuario_Administracion;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.services.UsuarioAdministracionService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/admin/usuarios")
public class UsuariosAdminController {

    private final UsuarioAdministracionService service;

    public UsuariosAdminController(UsuarioAdministracionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Usuario_Administracion>> listar() {
        return ResponseEntity.ok(service.listarUsuarios());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Map<String, Object> body) {
        try {
            String nombres = String.valueOf(body.getOrDefault("nombres", ""));
            String apellidos = String.valueOf(body.getOrDefault("apellidos", ""));
            String email = String.valueOf(body.getOrDefault("email", ""));
            String password = String.valueOf(body.getOrDefault("password", ""));
            Usuario_Administracion u = service.crearUsuario(nombres, apellidos, email, password);
            return ResponseEntity.status(HttpStatus.CREATED).body(u);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        try {
            String nombresApellidos = (String) body.getOrDefault("nombres_apellidos", null);
            String email = (String) body.getOrDefault("email", null);
            String password = (String) body.getOrDefault("password", null);
            Usuario_Administracion u = service.actualizarUsuario(id, nombresApellidos, email, password);
            return ResponseEntity.ok(u);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        service.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/roles/{rolId}")
    public ResponseEntity<?> asignarRol(@PathVariable Long userId, @PathVariable Long rolId) {
        try {
            Usuario_Administracion u = service.asignarRol(userId, rolId);
            return ResponseEntity.ok(u);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}/roles/{rolId}")
    public ResponseEntity<?> quitarRol(@PathVariable Long userId, @PathVariable Long rolId) {
        try {
            Usuario_Administracion u = service.quitarRol(userId, rolId);
            return ResponseEntity.ok(u);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}