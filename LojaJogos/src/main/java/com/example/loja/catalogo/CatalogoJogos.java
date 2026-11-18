package com.example.loja.catalogo;

import com.example.loja.model.cruds.Jogocrud;
import com.example.loja.model.Jogo;
import com.example.loja.repository.JogoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CatalogoJogos {

    private static CatalogoJogos instancia;
    private final Jogocrud jogocrud = new Jogocrud();

    private CatalogoJogos() {
        // Construtor privado para o padrão Singleton
    }

    public static synchronized CatalogoJogos getInstancia() {
        if (instancia == null) {
            instancia = new CatalogoJogos();
        }
        return instancia;
    }

    /** Carrega do banco; na primeira execução (tabela vazia), grava os jogos iniciais. */
    private void carregarJogos() {
        JogoRepository repositorio = new JogoRepository();
        if (repositorio.listar().isEmpty()) {
            for (Jogo inicial : jogosIniciais()) {
                repositorio.salvar(inicial);
            }
        }
        jogos.addAll(repositorio.listar());
    }

    private List<Jogo> jogosIniciais() {
        return List.of(
                new Jogo("Aura das Lâminas", new BigDecimal("199.90"), "RPG", "aura.png",
                        "Um RPG de ação em mundo aberto onde você forja seu próprio destino.",
                        "SO: Windows 10\nProcessador: Intel i5\nMemória: 8GB RAM\nPlaca de Vídeo: GTX 1060",
                        null),
                new Jogo("Forja Galáctica", new BigDecimal("149.50"), "Ficção científica", "forja.png",
                        "Explore o universo, construa naves e combata impérios alienígenas.",
                        "SO: Windows 10/11\nProcessador: Intel i7\nMemória: 16GB RAM\nPlaca de Vídeo: RTX 3060",
                        new BigDecimal("89.90")),
                new Jogo("Minecraft", new BigDecimal("89.90"), "Sandbox", "minecraft.png",
                        "Construa, explore e sobreviva em um mundo infinito de blocos.",
                        "SO: Windows 10\nProcessador: Intel i3\nMemória: 4GB RAM\nPlaca de Vídeo: Integrada",
                        null),
                new Jogo("Hollow Knight", new BigDecimal("46.99"), "Metroidvania", "hollowknight.png",
                        "Explore um reino subterrâneo de insetos e segredos.",
                        "SO: Windows 10\nProcessador: Intel i3\nMemória: 4GB RAM\nPlaca de Vídeo: GTX 460",
                        null),
                new Jogo("Stardew Valley", new BigDecimal("24.99"), "Simulação", "stardew.png",
                        "Herde uma fazenda e construa uma nova vida no campo.",
                        "SO: Windows 7\nProcessador: 2 GHz\nMemória: 2GB RAM\nPlaca de Vídeo: 256MB",
                        new BigDecimal("19.99")));
    }

    public List<Jogo> listar() {
        return jogocrud.listarTodos();
    }

    public Optional<Jogo> buscarPorId(int id) {
        Jogo jogo = jogocrud.buscarPorId(id);
        return Optional.ofNullable(jogo);
    }

    public List<Jogo> buscarPorTitulo(String consulta) {
        String termo = consulta == null ? "" : consulta.trim().toLowerCase();
        return jogocrud.listarTodos().stream()
                .filter(j -> j.getTitulo().toLowerCase().contains(termo))
                .collect(Collectors.toList());
    }
}