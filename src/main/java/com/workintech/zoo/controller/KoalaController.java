package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/koalas")
public class KoalaController {

    private final Map<Integer, Koala> koalas = new LinkedHashMap<>(); // Integer

    @GetMapping
    public ResponseEntity<Collection<Koala>> getAll() {
        return ResponseEntity.ok(koalas.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Koala> getById(@PathVariable Integer id) { // Integer
        var k = koalas.get(id);
        if (k == null) {
            throw new ZooException("Koala not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(k);
    }

    @PostMapping
    public ResponseEntity<Koala> create(@RequestBody Koala body) {
        if (body.getId() == null || body.getName() == null) {
            throw new IllegalArgumentException("Koala id and name are required");
        }
        koalas.put(body.getId(), body);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Koala> update(@PathVariable Integer id, @RequestBody Koala body) {
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        body.setId(id);
        koalas.put(id, body);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        var removed = koalas.remove(id);
        if (removed == null) {
            throw new ZooException("Koala not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().build();
    }
}
