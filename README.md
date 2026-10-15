# C-digo-Design-de-Software
Código do projeto de design de software

# Loja de Jogos

Aplicacao desktop de uma loja de jogos, feita em Java com JavaFX e Maven.

## Requisitos

- JDK 24 instalado e configurado. O pom.xml define Java 24 como versao de compilacao.
- IntelliJ IDEA, caso prefira executar pela IDE.
- Para executar pelo terminal, o projeto inclui o Maven Wrapper (mvnw.cmd no Windows e mvnw no macOS/Linux). Nao e necessario instalar o Maven separadamente.

Confira se o JDK esta disponivel no terminal:

text
java -version


O resultado deve indicar a versao 24. Se o comando nao for encontrado ou mostrar outra versao, configure o JAVA_HOME e o PATH para apontarem para o JDK 24.

## Executar pelo IntelliJ IDEA

1. Abra o IntelliJ IDEA e escolha *Open*.
2. Selecione a pasta LojaJogos ou diretamente o arquivo pom.xml.
3. Se o IntelliJ perguntar, escolha abrir/importar o projeto como um projeto Maven e aguarde a sincronizacao das dependencias.
4. Em *File > Project Structure > Project, selecione o JDK 24 como **Project SDK* e escolha o nivel de linguagem correspondente.
5. Abra o painel *Maven* e execute *Plugins > javafx > javafx:run*.

Como alternativa, abra src/main/java/com/example/loja/App.java e execute o metodo main. Se a IDE nao resolver as dependencias do JavaFX, sincronize novamente o projeto Maven e execute javafx:run pelo painel Maven.

## Executar pelo terminal

Abra o terminal na pasta LojaJogos, onde estao pom.xml e o Maven Wrapper.

No Windows (PowerShell ou Prompt de Comando):

powershell
.\mvnw.cmd clean javafx:run


No macOS ou Linux:

bash
./mvnw clean javafx:run


O comando baixa as dependencias Maven necessarias, compila o projeto e inicia a aplicacao JavaFX. Na primeira execucao, e necessaria uma conexao com a internet para baixar dependencias.

Para apenas compilar e executar os testes:

powershell
.\mvnw.cmd test


No macOS/Linux, use ./mvnw test.

## Solucao de problemas

- *Versao Java incorreta:* confirme que java -version mostra Java 24 e que o IntelliJ esta usando o mesmo JDK nas configuracoes do projeto.
- *Dependencias nao carregadas:* sincronize/recarregue o projeto Maven ou execute novamente javafx:run.
- *Falha ao baixar dependencias:* verifique a conexao com a internet e tente executar o comando novamente.