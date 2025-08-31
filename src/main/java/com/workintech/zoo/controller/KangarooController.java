package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {

    private final Map<Integer, Kangaroo> kangaroos = new LinkedHashMap<>(); // Integer

    @GetMapping
    public ResponseEntity<Collection<Kangaroo>> getAll() {
        return ResponseEntity.ok(kangaroos.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kangaroo> getById(@PathVariable Integer id) { // Integer
        var k = kangaroos.get(id);
        if (k == null) {
            throw new ZooException("Kangaroo not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(k);
    }

    @PostMapping
    public ResponseEntity<Kangaroo> create(@RequestBody Kangaroo body) {
        if (body.getId() == null || body.getName() == null) {
            throw new IllegalArgumentException("Kangaroo id and name are required");
        }
        kangaroos.put(body.getId(), body);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kangaroo> update(@PathVariable Integer id, @RequestBody Kangaroo body) {
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        body.setId(id);
        kangaroos.put(id, body);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Kangaroo> delete(@PathVariable Integer id) {
        var removed = kangaroos.remove(id);
        if (removed == null) {
            throw new ZooException("Kangaroo not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(removed);
    }
}
