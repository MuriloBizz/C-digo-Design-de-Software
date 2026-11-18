package com.example.loja.repository;

import com.example.loja.model.Usuario;

import java.util.Optional;

public class UsuarioRepository extends RepositorioBase<Usuario> {

    public UsuarioRepository() {
        super(Usuario.class);
    }

    /** Read: busca pelo e-mail (único). */
    public Optional<Usuario> buscarPorEmail(String email) {
        return consultar(em -> em.createQuery(
                        "select u from Usuario u where u.email = :email", Usuario.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst());
    }
}