package br.com.giovanicaf.vendas.sevice;

import br.com.giovanicaf.vendas.domain.entity.ClienteEntity;
import br.com.giovanicaf.vendas.domain.repository.ClienteRepository;
import br.com.giovanicaf.vendas.domain.repository.entityManager.ClienteEntityManagerRepository;
import br.com.giovanicaf.vendas.dto.ClienteDto;
import br.com.giovanicaf.vendas.dto.PedidoDto;
import br.com.giovanicaf.vendas.mapper.ClienteMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private ClienteEntityManagerRepository clienteEntityManagerRepository;

    @Autowired
    private ClienteMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<ClienteDto> inserir(ClienteDto clienteDto){
        logger.debug("Salvar cliente: body: {}", clienteDto);
        try{
            ClienteEntity cliente = repository.save(mapper.dtoToEntity(clienteDto));

            return Optional.of(mapper.entityToDto(cliente));
        } catch (Exception e) {

            throw new RuntimeException("Erro durante a inclusão do cliente: " + e.getMessage());
        }
    }

    public Optional<ClienteDto> alterar(ClienteDto clienteDto){
        logger.debug("Alterar cliente: id: {}, body: {}", clienteDto.getId(), clienteDto);
        try{
            ClienteEntity cliente = repository.findById(clienteDto.getId())
                    .orElseThrow(() -> new RuntimeException("Registro não encontrado!"));

            cliente = mapper.dtoToEntity(clienteDto);

            repository.save(cliente);

            return Optional.of(mapper.entityToDto(cliente));

        } catch (Exception e) {

            throw new RuntimeException("Erro durante a alteração do cliente: " + e.getMessage());
        }
    }

    public Optional<ClienteDto> buscarPorId(Long id){
        logger.debug("Buscar cliente por Id: {}", id);
        try{
            Optional<ClienteEntity> cliente = repository.findById(id);

            return cliente.map(clienteEntity -> mapper.entityToDto(clienteEntity));

        } catch (Exception e) {

            throw new RuntimeException("Erro durante a alteração do cliente: " + e.getMessage());
        }
    }
    public void remover(Long id){
        logger.debug("Removendo cliente por Id: {}", id);
        try{
            ClienteEntity cliente = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Registro não encontrado!"));

            repository.delete(cliente);

        } catch (Exception e) {

            throw new RuntimeException("Erro durante a alteração do cliente: " + e.getMessage());
        }
    }

    public List<ClienteDto> buscarComExampleMatcher(ClienteDto cliente){
        logger.debug("Buscar cliente com ExampleMatcher: body: {}", cliente);
        try{

            ExampleMatcher matcher = ExampleMatcher
                    .matching()
                    .withIgnoreCase()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

            Example<ClienteEntity> example = Example.of(mapper.dtoToEntity(cliente), matcher);
            return repository.findAll(example).stream().map(mapper::entityToDto).toList();

        } catch (Exception e) {

            throw new RuntimeException("Erro durante a alteração do cliente: " + e.getMessage());
        }
    }

    public Page<ClienteDto> buscarComCriteriaQuery(ClienteDto cliente){
        logger.debug("Buscar cliente com CriteriaQuery: body: {}", cliente);
        try{
            final int page = 0;
            final int size = 9;
            Pageable pageRequest = PageRequest.of(page, size, Sort.by("nome").ascending().and(Sort.by("id")));

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<ClienteEntity> cq = cb.createQuery(ClienteEntity.class);

            Root<ClienteEntity> root = cq.from(ClienteEntity.class);
            root.fetch("pedidos", JoinType.LEFT);

            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(cliente.getId())){
                predicates.add(cb.equal( root.get("id"), cliente.getId()));
            }

            if (StringUtils.isNotBlank(cliente.getNome())){
                predicates.add(cb.like( cb.upper(root.get("nome")), "%" + cliente.getNome().toUpperCase() + "%"));
            }

            if (!ObjectUtils.isEmpty(cliente.getPedidos())) {
                List<Long> pedidosIds = cliente.getPedidos().stream().map(PedidoDto::getId).toList();
                predicates.add(root.get("pedidos").get("id").in(pedidosIds));
            }

            cq.where(predicates.toArray(new Predicate[0]));

            TypedQuery<ClienteEntity> query = entityManager.createQuery(cq);
            query.setFirstResult(Long.valueOf(pageRequest.getOffset()).intValue());
            query.setMaxResults(pageRequest.getPageSize());

            List<ClienteDto> result = query.getResultList().stream().map(mapper::entityToDto).toList();

            return new PageImpl<>(result, pageRequest, getTotalCount(cb, predicates.toArray(new Predicate[0])));

        } catch (Exception e) {
            throw new RuntimeException("Erro durante a alteração do cliente: " + e.getMessage());
        }
    }
    public Page<ClienteDto> buscarComCriteriaQueryRetornandoConsultaNoDto(ClienteDto cliente){
        logger.debug("Buscar cliente com CriteriaQuery: body: {}", cliente);
        try{
            final int page = 0;
            final int size = 9;
            Pageable pageRequest = PageRequest.of(page, size, Sort.by("nome").ascending().and(Sort.by("id")));

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<ClienteDto> cq = cb.createQuery(ClienteDto.class);

            Root<ClienteEntity> root = cq.from(ClienteEntity.class);
            root.fetch("pedidos", JoinType.LEFT);

            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(cliente.getId())){
                predicates.add(cb.equal( root.get("id"), cliente.getId()));
            }

            if (StringUtils.isNotBlank(cliente.getNome())){
                predicates.add(cb.like( cb.upper(root.get("nome")), "%" + cliente.getNome().toUpperCase() + "%"));
            }

            if (!ObjectUtils.isEmpty(cliente.getPedidos())) {
                List<Long> pedidosIds = cliente.getPedidos().stream().map(PedidoDto::getId).toList();
                predicates.add(root.get("pedidos").get("id").in(pedidosIds));
            }

            cq.where(predicates.toArray(new Predicate[0]));
            cq.select(cb.construct(ClienteDto.class,
                    root.get("id"),
                    root.get("nome"),
                    cb.construct(PedidoDto.class,
                            root.get("pedidos").get("id"),
                            root.get("pedidos").get("dataPedido"),
                            root.get("pedidos").get("total")
                    )
            ));

            TypedQuery<ClienteDto> query = entityManager.createQuery(cq);
            query.setFirstResult(Long.valueOf(pageRequest.getOffset()).intValue());
            query.setMaxResults(pageRequest.getPageSize());

            List<ClienteDto> result = query.getResultList();

            return new PageImpl<>(result, pageRequest, getTotalCount(cb, predicates.toArray(new Predicate[0])));

        } catch (Exception e) {
            throw new RuntimeException("Erro durante a alteração do cliente: " + e.getMessage());
        }
    }
    private Long getTotalCount(CriteriaBuilder criteriaBuilder, Predicate[] predicateArray) {
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<ClienteEntity> root = criteriaQuery.from(ClienteEntity.class);
        criteriaQuery.select(criteriaBuilder.count(root));
        criteriaQuery.where(predicateArray);
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    public List<ClienteDto> findClientesComEntityManager(ClienteDto cliente){
        logger.debug("Buscar cliente com EntityManager: body: {}", cliente);
        try{
            List<ClienteEntity> clientes = clienteEntityManagerRepository.findClientesByIdAndName(cliente.getId(), cliente.getNome());
            return clientes.stream().map(mapper::entityToDto).collect(Collectors.toList());

        } catch (Exception e) {

            throw new RuntimeException("Erro durante a alteração do cliente: " + e.getMessage());
        }
    }
}
