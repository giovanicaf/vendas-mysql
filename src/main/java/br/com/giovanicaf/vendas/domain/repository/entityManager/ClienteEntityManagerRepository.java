package br.com.giovanicaf.vendas.domain.repository.entityManager;

import br.com.giovanicaf.vendas.domain.entity.ClienteEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;

@Repository
public class ClienteEntityManagerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public ClienteEntity findById(long id) {
        return entityManager.find(ClienteEntity.class, id);
    }

    public ClienteEntity persist(ClienteEntity cliente) {
        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();
        return cliente;
    }

    @Transactional
    public ClienteEntity update(ClienteEntity cliente) {
        entityManager.merge(cliente);
        return cliente;
    }

    @Transactional
    public ClienteEntity deleteById(long id) {
        ClienteEntity cliente = findById(id);
        if (Objects.nonNull(cliente)) {
            entityManager.remove(cliente);
        }

        return cliente;
    }

    @Transactional
    public int deleteAll() {
        Query query = entityManager.createQuery("DELETE FROM ClienteEntity");
        return query.executeUpdate();
    }

    public List<ClienteEntity> findClientesByIdAndName(Long id, String nome) {

        String querySql = """
                    select c
                    from ClienteEntity c
                    left join fetch c.pedidos
                    where 1=1
                """;
        if (Objects.nonNull(id)) {
            querySql += " and c.id= :id";
        }
        if (Objects.nonNull(nome)) {
            querySql += " and UPPER(c.nome) like CONCAT('%',UPPER(:nome),'%')";
        }
        TypedQuery<ClienteEntity> query = entityManager.createQuery(querySql, ClienteEntity.class);

        if (Objects.nonNull(id)) {
            query.setParameter("id", id);
        }

        if (Objects.nonNull(nome)) {
            query.setParameter("nome", nome);
        }

        return query.getResultList();
    }
}
