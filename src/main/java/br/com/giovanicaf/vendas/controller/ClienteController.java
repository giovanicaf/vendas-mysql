package br.com.giovanicaf.vendas.controller;

import br.com.giovanicaf.vendas.dto.ClienteDto;
import br.com.giovanicaf.vendas.sevice.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping
    public ResponseEntity<Void> inserir(@RequestBody @Valid ClienteDto cliente){
        Optional<ClienteDto> reponse = service.inserir(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDto> alterar(@PathVariable("id") Long id, @RequestBody @Valid ClienteDto cliente){

        cliente.setId(id);
        Optional<ClienteDto> reponse = service.alterar(cliente);

        return reponse.map(clienteDto -> ResponseEntity.status(HttpStatus.CREATED).body(clienteDto))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable("id") Long id){

        service.remover(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> buscarPorId(@PathVariable("id") Long id ){

        Optional<ClienteDto> reponse = service.buscarPorId(id);

        return reponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/example-matcher")
    public ResponseEntity<List<ClienteDto>> buscarComExampleMatcher(@RequestBody ClienteDto cliente){

        List<ClienteDto> reponse = service.buscarComExampleMatcher(cliente);

        return (reponse.isEmpty())
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(reponse);
    }
    @GetMapping("/criteria")
    public ResponseEntity<Page<ClienteDto>> buscarComCriteriaQuery(@RequestBody ClienteDto cliente){

        Page<ClienteDto> reponse = service.buscarComCriteriaQuery(cliente);

        return (reponse.isEmpty())
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(reponse);
    }

    @GetMapping("/criteria-projection")
    public ResponseEntity<Page<ClienteDto>> buscarComCriteriaQueryRetornandoConsultaNoDto(@RequestBody ClienteDto cliente){

        Page<ClienteDto> reponse = service.buscarComCriteriaQueryRetornandoConsultaNoDto(cliente);

        return (reponse.isEmpty())
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(reponse);
    }

    @GetMapping("/entity-manager")
    public ResponseEntity<List<ClienteDto>> findClientesComEntityManager(@RequestBody ClienteDto cliente){

        List<ClienteDto> reponse = service.findClientesComEntityManager(cliente);

        return (reponse.isEmpty())
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(reponse);
    }
}
