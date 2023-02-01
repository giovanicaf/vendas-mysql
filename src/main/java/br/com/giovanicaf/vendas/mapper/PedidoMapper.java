package br.com.giovanicaf.vendas.mapper;

import br.com.giovanicaf.vendas.dto.PedidoDto;
import br.com.giovanicaf.vendas.domain.entity.PedidoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    PedidoDto entityToDto(PedidoEntity source);

    PedidoEntity dtoToEntity(PedidoDto source);
}
