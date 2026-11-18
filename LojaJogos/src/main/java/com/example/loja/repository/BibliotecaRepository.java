package com.example.loja.repository;

import com.example.loja.model.Biblioteca;
import com.example.loja.model.Jogo;
import com.example.loja.model.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public class BibliotecaRepository extends RepositorioBase<Biblioteca> {

    public BibliotecaRepository() {
        super(Biblioteca.class);
    }

    /** Create: registra que o usuário possui o jogo. Se já possui, devolve o registro existente. */
    public Biblioteca adicionar(Integer usuarioId, Integer jogoId) {
        return executar(em -> {
            Usuario usuario = em.find(Usuario.class, usuarioId);
            Jogo jogo = em.find(Jogo.class, jogoId);
            if (usuario == null || jogo == null) {
                throw new IllegalArgumentException("Usuário ou jogo não encontrado.");
            }
            List<Biblioteca> existentes = em.createQuery(
                            "select b from Biblioteca b where b.usuario.id = :u and b.jogo.id = :j",
                            Biblioteca.class)
                    .setParameter("u", usuarioId)
                    .setParameter("j", jogoId)
                    .getResultList();
            if (!existentes.isEmpty()) {
                return existentes.get(0);
            }
            Biblioteca registro = new Biblioteca(usuario, jogo);
            em.persist(registro);
            return registro;
        });
    }

    /** Read: registros da biblioteca de um usuário, do mais antigo ao mais novo. */
    public List<Biblioteca> listarPorUsuario(Integer usuarioId) {
        return consultar(em -> em.createQuery(
                        "select b from Biblioteca b where b.usuario.id = :id "
                                + "order by b.adquiridoEm, b.id", Biblioteca.class)
                .setParameter("id", usuarioId)
                .getResultList());
    }

    /** Read: só os jogos que o usuário possui. */
    public List<Jogo> listarJogosDoUsuario(Integer usuarioId) {
        return consultar(em -> em.createQuery(
                        "select b.jogo from Biblioteca b where b.usuario.id = :id "
                                + "order by b.adquiridoEm, b.id", Jogo.class)
                .setParameter("id", usuarioId)
                .getResultList());
    }

    /** Read: o usuário já possui o jogo? */
    public boolean possuiJogo(Integer usuarioId, Integer jogoId) {
        return consultar(em -> em.createQuery(
                        "select count(b) from Biblioteca b where b.usuario.id = :u and b.jogo.id = :j",
                        Long.class)
                .setParameter("u", usuarioId)
                .setParameter("j", jogoId)
                .getSingleResult() > 0);
    }

    /** Update: altera a data de aquisição de um registro. */
    public Biblioteca atualizarDataAquisicao(Integer bibliotecaId, LocalDateTime novaData) {
        return executar(em -> {
            Biblioteca registro = em.find(Biblioteca.class, bibliotecaId);
            if (registro == null) {
                throw new IllegalArgumentException("Registro não encontrado: " + bibliotecaId);
            }
            registro.setAdquiridoEm(novaData);   // objeto gerenciado: o commit grava
            return registro;
        });
    }
}