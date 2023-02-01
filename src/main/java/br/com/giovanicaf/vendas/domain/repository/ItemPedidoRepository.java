package br.com.giovanicaf.vendas.domain.repository;

import br.com.giovanicaf.vendas.domain.entity.ItemPedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedidoEntity, Long> {


}
