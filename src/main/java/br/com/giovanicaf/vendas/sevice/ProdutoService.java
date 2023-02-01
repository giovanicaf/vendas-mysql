package br.com.giovanicaf.vendas.sevice;

import br.com.giovanicaf.vendas.domain.entity.ProdutoEntity;
import br.com.giovanicaf.vendas.domain.repository.ProdutoRepository;
import br.com.giovanicaf.vendas.dto.ProdutoDto;
import br.com.giovanicaf.vendas.mapper.ProdutoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoService.class);

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private ProdutoMapper mapper;

    public Optional<ProdutoDto> inserir(ProdutoDto produtoDto){
        logger.debug("Salvar produto: body: {}", produtoDto);
        try{
            ProdutoEntity produto = repository.save(mapper.dtoToEntity(produtoDto));

            return Optional.of(mapper.entityToDto(produto));
        } catch (Exception e) {

            throw new RuntimeException("Erro durante a inclusão do produto: " + e.getMessage());
        }
    }

    public Optional<ProdutoDto> alterar(ProdutoDto produtoDto){
        logger.debug("Alterar produto: id: {}, body: {}", produtoDto.getId(), produtoDto);
        try{
            ProdutoEntity produto = repository.findById(produtoDto.getId())
                    .orElseThrow(() -> new RuntimeException("Registro não encontrado!"));

            produto = mapper.dtoToEntity(produtoDto);

            repository.save(produto);

            return Optional.of(mapper.entityToDto(produto));

        } catch (Exception e) {

            throw new RuntimeException("Erro durante a alteração do produto: " + e.getMessage());
        }
    }

    public Optional<ProdutoDto> buscarPorId(Long id){
        logger.debug("Buscar produto por Id: {}", id);
        try{
            Optional<ProdutoEntity> produto = repository.findById(id);

            return produto.map(produtoEntity -> mapper.entityToDto(produtoEntity));

        } catch (Exception e) {

            throw new RuntimeException("Erro durante a alteração do produto: " + e.getMessage());
        }
    }
    public void remover(Long id){
        logger.debug("Removendo produto por Id: {}", id);
        try{
            ProdutoEntity produto = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Registro não encontrado!"));

            repository.delete(produto);

        } catch (Exception e) {

            throw new RuntimeException("Erro durante a alteração do produto: " + e.getMessage());
        }
    }

    public List<ProdutoDto> buscarProdutoPorDescricao(ProdutoDto produto){
        logger.debug("Buscar produto com ExampleMatcher: body: {}", produto);
        try{

            List<ProdutoEntity> produtoEntities = repository.buscarProdutoPorDescricao(produto.getDescricao());
            return produtoEntities.stream().map(mapper::entityToDto).toList();

        } catch (Exception e) {

            throw new RuntimeException("Erro durante a alteração do produto: " + e.getMessage());
        }
    }
}
