package com.example.cadapi.controller;

import com.example.cadapi.model.*;
import com.example.cadapi.service.CadEngine;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cad")
public class CadController {

    private final CadEngine cadEngine;

    public CadController(CadEngine cadEngine) {
        this.cadEngine = cadEngine;
    }

    @PostMapping("/sketch")
    public ResponseEntity<CadModel> sketch(@RequestBody SketchRequest req) {
        return ResponseEntity.ok(cadEngine.createSketch(req));
    }

    @PostMapping("/extrude")
    public ResponseEntity<CadModel> extrude(@RequestBody ExtrudeRequest req) {
        return ResponseEntity.ok(cadEngine.extrude(req));
    }

    @PostMapping("/fillet")
    public ResponseEntity<CadModel> fillet(@RequestBody FilletRequest req) {
        return ResponseEntity.ok(cadEngine.fillet(req));
    }

    @GetMapping("/preview/{id}")
    public ResponseEntity<CadModel> preview(@PathVariable Long id) {
        CadModel model = cadEngine.getModel(id);
        if (model == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(model);
    }
}
