package br.com.giovanicaf.vendas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemPedidoDto {

    private Long id;

    private PedidoDto pedido;

    private ProdutoDto produto;

    private Long quantidade;
}
