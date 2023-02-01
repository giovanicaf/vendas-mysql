package br.com.giovanicaf.vendas.mapper;

import br.com.giovanicaf.vendas.domain.entity.ClienteEntity;
import br.com.giovanicaf.vendas.dto.ClienteDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PedidoMapper.class})
public interface ClienteMapper {

    ClienteDto entityToDto(ClienteEntity source);

    ClienteEntity dtoToEntity(ClienteDto source);
}
