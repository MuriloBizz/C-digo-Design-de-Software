package com.example.loja.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class JPAUtil {

    private static final EntityManagerFactory FABRICA =
            Persistence.createEntityManagerFactory("forjaPU");

    private JPAUtil() { }

    public static EntityManager criarEntityManager() {
        return FABRICA.createEntityManager();
    }

    public static void fechar() {
        if (FABRICA.isOpen()) {
            FABRICA.close();
        }
    }
}