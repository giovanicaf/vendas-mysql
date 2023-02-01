package br.com.giovanicaf.vendas.domain.repository;

import br.com.giovanicaf.vendas.domain.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {

    @Query("  SELECT p FROM ProdutoEntity p " +
            " WHERE UPPER(p.descricao) LIKE CONCAT('%',UPPER(:descricao),'%') ")
    List<ProdutoEntity> buscarProdutoPorDescricao(@Param("descricao") String descricao);
}
