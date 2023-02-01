package br.com.giovanicaf.vendas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoDto {

    private Long id;

    private ClienteDto cliente;

    private LocalDate dataPedido;

    private BigDecimal total;

    public List<ItemPedidoDto> itens;

    public PedidoDto(Long id, ClienteDto cliente, LocalDate dataPedido, BigDecimal total) {
        this.id = id;
        this.dataPedido = dataPedido;
        this.total = total;
    }
}
