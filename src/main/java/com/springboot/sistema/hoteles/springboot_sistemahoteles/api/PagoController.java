package com.springboot.sistema.hoteles.springboot_sistemahoteles.api; 

import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.sistema.hoteles.springboot_sistemahoteles.models.Reserva;
import com.springboot.sistema.hoteles.springboot_sistemahoteles.repositories.ReservaRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/pago")
public class PagoController {

    @Autowired
    private ReservaRepository reservaRepository;

    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmarPago(@RequestParam("id") long idReserva, @RequestBody(required = false) Map<String, Object> body){
        try{
            Optional<Reserva> opt = reservaRepository.findById(idReserva);
            if(opt.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva no encontrada.");
            }
            Reserva res = opt.get();
            String metodo = body != null ? String.valueOf(body.getOrDefault("metodo_pago", res.getMetodo_pago())) : res.getMetodo_pago();
            res.setMetodo_pago(metodo);
            // Si deseas marcar un estado "pagada", agrega un campo en Reserva y setéalo aquí.
            reservaRepository.save(res);
            return ResponseEntity.ok(Map.of("status", "ok"));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}