package com.example.loja.repository;

import com.example.loja.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class RepositorioBase<T> {

    private final Class<T> classe;

    protected RepositorioBase(Class<T> classe) {
        this.classe = classe;
    }

    // ---- CRUD ----

    public T salvar(T entidade) {                          // Create
        return executar(em -> {
            em.persist(entidade);
            return entidade;
        });
    }

    public Optional<T> buscarPorId(Object id) {            // Read
        return consultar(em -> Optional.ofNullable(em.find(classe, id)));
    }

    public List<T> listar() {                              // Read
        return consultar(em -> em.createQuery(
                "select e from " + classe.getSimpleName() + " e", classe).getResultList());
    }

    public T atualizar(T entidade) {                       // Update
        return executar(em -> em.merge(entidade));
    }

    public boolean excluir(Object id) {                    // Delete
        return executar(em -> {
            T entidade = em.find(classe, id);
            if (entidade == null) {
                return false;
            }
            em.remove(entidade);
            return true;
        });
    }

    // ---- Infraestrutura ----

    /** Executa dentro de uma transação, com rollback em caso de erro. */
    protected <R> R executar(Function<EntityManager, R> acao) {
        EntityManager em = JPAUtil.criarEntityManager();
        try {
            em.getTransaction().begin();
            R resultado = acao.apply(em);
            em.getTransaction().commit();
            return resultado;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    /** Executa uma leitura, sem transação. */
    protected <R> R consultar(Function<EntityManager, R> acao) {
        EntityManager em = JPAUtil.criarEntityManager();
        try {
            return acao.apply(em);
        } finally {
            em.close();
        }
    }
}