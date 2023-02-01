package br.com.giovanicaf.vendas.domain.repository;

import br.com.giovanicaf.vendas.domain.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {


//    @Query(value = " select * from cliente c where c.nome like '%:nome%' ", nativeQuery = true)
//    List<ClienteEntity> encontrarPorNome(@Param("nome") String nome);
//
//    @Query(value = " delete from Cliente c where c.nome = :nome ")
//    @Modifying
//    void deleteByNome(@Param("nome") String nome);
//
//    boolean existsByNome(String nome);
//
//    @Query(" select c from Cliente c left join fetch c.pedidos where c.id = :id ")
//    ClienteEntity findClienteFetchPedidos(@Param("id") Long id );


}
