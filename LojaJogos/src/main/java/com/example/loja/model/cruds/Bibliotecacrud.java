package com.example.loja.model.cruds;

import com.example.loja.model.Biblioteca;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class Bibliotecacrud {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("forjaPU");

    // CREATE (Adicionar jogo à biblioteca / Comprar jogo)
    public void salvar(Biblioteca biblioteca) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(biblioteca);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // READ (Listar todos os jogos vinculados a um usuário específico)
    public List<Biblioteca> listarPorUsuario(Integer usuarioId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT b FROM Biblioteca b WHERE b.usuario.id = :usuarioId", Biblioteca.class)
                    .setParameter("usuarioId", usuarioId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // DELETE (Remover jogo da biblioteca do usuário)
    public void excluir(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Biblioteca biblioteca = em.find(Biblioteca.class, id);
            if (biblioteca != null) {
                em.remove(biblioteca);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}