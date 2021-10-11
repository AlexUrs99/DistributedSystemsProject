package com.example.ds.client.controller;

import com.example.ds.client.dto.ClientDto;
import com.example.ds.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClientAtId(@PathVariable String id) {
        return ResponseEntity.ok(clientService.getClientAtId(id));
    }

    @PostMapping
    public ResponseEntity<ClientDto> saveClient(@RequestBody ClientDto body) {
        return new ResponseEntity<>(clientService.saveClient(body), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> editClient(@PathVariable String id, @RequestBody ClientDto body) {
        return ResponseEntity.ok(clientService.editClient(id, body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable String id) {
        return ResponseEntity.ok(clientService.deleteClient(id));
    }
}
