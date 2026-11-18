package com.example.loja.model.cruds;


import com.example.loja.model.Jogo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class Jogocrud {

    // Aponta para o "forjaPU" definido no seu persistence.xml
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("forjaPU");

    // CREATE / UPDATE
    public void salvar(Jogo jogo) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if (jogo.getId() == null) {
                em.persist(jogo); // Cria um novo registro
            } else {
                em.merge(jogo);   // Atualiza o registro existente
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
    public Jogo buscarPorId(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Jogo.class, id);
        } finally {
            em.close();
        }
    }

    // READ (Listar todos)
    public List<Jogo> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT j FROM Jogo j", Jogo.class).getResultList();
        } finally {
            em.close();
        }
    }

    // DELETE
    public void excluir(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Jogo jogo = em.find(Jogo.class, id);
            if (jogo != null) {
                em.remove(jogo);
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
