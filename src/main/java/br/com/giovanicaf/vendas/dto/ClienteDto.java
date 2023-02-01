package br.com.giovanicaf.vendas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto {

    private Long id;

    @NotNull
    @Length(min = 4, max = 200)
    private String nome;

    private List<PedidoDto> pedidos;
}
