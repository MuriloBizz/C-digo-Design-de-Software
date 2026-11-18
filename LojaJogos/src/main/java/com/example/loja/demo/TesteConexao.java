package com.example.loja.demo;

import com.example.loja.util.JPAUtil;
import jakarta.persistence.EntityManager;

public class TesteConexao {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.criarEntityManager();
        Object versao = em.createNativeQuery("select version()").getSingleResult();
        System.out.println("Conectado! Versão do banco: " + versao);
        em.close();
        JPAUtil.fechar();
    }
}