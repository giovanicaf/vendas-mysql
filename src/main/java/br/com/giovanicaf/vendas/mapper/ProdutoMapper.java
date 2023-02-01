package br.com.giovanicaf.vendas.mapper;

import br.com.giovanicaf.vendas.dto.ProdutoDto;
import br.com.giovanicaf.vendas.domain.entity.ProdutoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    ProdutoDto entityToDto(ProdutoEntity source);

    ProdutoEntity dtoToEntity(ProdutoDto source);
}
