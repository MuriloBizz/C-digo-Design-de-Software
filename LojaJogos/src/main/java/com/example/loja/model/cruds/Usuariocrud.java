package com.example.loja.model.cruds;

import com.example.loja.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class Usuariocrud {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("forjaPU");

    // CREATE / UPDATE
    public void salvar(Usuario usuario) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if (usuario.getId() == null) {
                em.persist(usuario);
            } else {
                em.merge(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // READ (Buscar por ID)
    public Usuario buscarPorId(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    // READ (Listar todos)
    public List<Usuario> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        } finally {
            em.close();
        }
    }

    // DELETE
    public void excluir(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Usuario usuario = em.find(Usuario.class, id);
            if (usuario != null) {
                em.remove(usuario);
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