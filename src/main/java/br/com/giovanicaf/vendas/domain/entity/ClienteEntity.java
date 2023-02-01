package br.com.giovanicaf.vendas.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table( name= "cliente" )
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( name= "id" )
    private Long id;

    @Column( name= "nome", length = 200)
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private Set<PedidoEntity> pedidos;
}
