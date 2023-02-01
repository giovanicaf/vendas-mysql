package br.com.giovanicaf.vendas.mapper;

import br.com.giovanicaf.vendas.dto.ItemPedidoDto;
import br.com.giovanicaf.vendas.domain.entity.ItemPedidoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PedidoMapper.class, ProdutoMapper.class})
public interface ItemPedidoMapper {

    ItemPedidoDto entityToDto(ItemPedidoEntity source);

    ItemPedidoEntity dtoToEntity(ItemPedidoDto source);
}
