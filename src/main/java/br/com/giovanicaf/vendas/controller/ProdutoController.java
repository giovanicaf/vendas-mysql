package br.com.giovanicaf.vendas.controller;

import br.com.giovanicaf.vendas.dto.ProdutoDto;
import br.com.giovanicaf.vendas.sevice.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping
    public ResponseEntity<Void> inserir(@RequestBody @Valid ProdutoDto produto){
        Optional<ProdutoDto> reponse = service.inserir(produto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProdutoDto> alterar(@PathVariable("id") Long id, @RequestBody @Valid ProdutoDto produto){

        produto.setId(id);
        Optional<ProdutoDto> reponse = service.alterar(produto);

        return reponse.map(produtoDto -> ResponseEntity.status(HttpStatus.CREATED).body(produtoDto))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable("id") Long id){

        service.remover(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDto> buscarPorId(@PathVariable("id") Long id ){

        Optional<ProdutoDto> reponse = service.buscarPorId(id);

        return reponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDto>> buscarPorNome(@RequestBody ProdutoDto produto){

        List<ProdutoDto> reponse = service.buscarProdutoPorDescricao(produto);

        return (reponse.isEmpty())
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(reponse);
    }

}
