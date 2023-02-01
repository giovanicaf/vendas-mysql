package br.com.giovanicaf.vendas.domain.repository;

import br.com.giovanicaf.vendas.domain.entity.ClienteEntity;
import br.com.giovanicaf.vendas.domain.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

    List<PedidoEntity> findByCliente(ClienteEntity cliente);
}
