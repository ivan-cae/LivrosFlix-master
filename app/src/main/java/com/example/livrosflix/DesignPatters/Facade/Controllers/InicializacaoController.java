package com.example.livrosflix.DesignPatters.Facade.Controllers;

import com.example.livrosflix.Classes.Autor;
import com.example.livrosflix.Classes.Autor_Livro;
import com.example.livrosflix.Classes.Livro;
import com.example.livrosflix.Classes.Usuario;
import com.example.livrosflix.DesignPatters.Facade.Model.DAO;

public class InicializacaoController {


    public InicializacaoController(DAO dao) {
        inicializacao(dao);
    }

    private void cadastraAutores(DAO dao){

            Usuario usuario = new Usuario("Ivan Cae", "666", true, "ivan");
            dao.insert(usuario);

            usuario = new Usuario("Izabele Tome", "669", true, "izabele");
            dao.insert(usuario);

            usuario = new Usuario("Biblioteca", "999", true, "biblioteca");
            dao.insert(usuario);

            usuario = new Usuario("Marcos", "123", true, "marcos");
            dao.insert(usuario);

            usuario = new Usuario("Usuario1", "123", false, "usuario1");
            dao.insert(usuario);

            usuario = new Usuario("Usuario2", "123", false, "usuario2");
            dao.insert(usuario);

            usuario = new Usuario("Usuario3", "123", false, "usuario3");
            dao.insert(usuario);

            usuario = new Usuario("Entregador", "123", false, "entregador");
            usuario.setEhEntregador(true);
            dao.insert(usuario);

            Autor autor = new Autor("J. K. Rowling");
            autor.setDescricaoAutor("J. K. Rowling, é uma escritora, roteirista e produtora cinematográfica britânica, notória por " +
                    "escrever a série de livros Harry Potter");
            dao.insert(autor);


            autor = new Autor("J. R. R. Tolkien");
            autor.setDescricaoAutor("J. R. R. Tolkien foi um escritor, professor universitário e filólogo britânico, nascido na atual " +
                    "África do Sul, doutor em Letras e Filologia pela Universidade de Liège e Dublin, " +
                    "e autor das obras como O Hobbit, O Senhor dos Anéis e O Silmarillion. Em 28 de março de 1972, " +
                    "Tolkien foi nomeado Comendador da Ordem do Império Britânico pela Rainha Elizabeth II.");
            dao.insert(autor);


            autor = new Autor("J. R. R. Martin");
            autor.setDescricaoAutor(" George R. R. Martin é um roteirista e escritor de ficção científica, terror e fantasia " +
                    "norte-americano. É mais conhecido por escrever a série de livros de fantasia épica As Crônicas de Gelo e Fogo.");
            dao.insert(autor);

            autor = new Autor("Frank Herbert");
            autor.setDescricaoAutor("Frank Herbert foi um escritor de ficção científica e jornalista americano de grande sucesso " +
                    "comercial e de crítica. Ele é mais conhecido pela obra Duna, e os cinco livros subsequentes da série.");
            dao.insert(autor);

            autor = new Autor("Stephen King");
            autor.setDescricaoAutor("Stephen Edwin King é um escritor norte-americano de terror, ficção sobrenatural, suspense, " +
                    "ficção científica e fantasia. Os seus livros já venderam mais de 400 milhões de cópias, com publicações em mais de 40 " +
                    "países. É o 9º autor mais traduzido no mundo.");
            dao.insert(autor);

            autor = new Autor("Agatha Christie");
            autor.setDescricaoAutor("Agatha Christie foi uma escritora britânica que atuou como romancista, contista, dramaturga e " +
                    "poetisa.");
            dao.insert(autor);

            autor = new Autor("William Shakespeare");
            autor.setDescricaoAutor("William Shakespeare foi um poeta, dramaturgo e ator inglês, tido como o maior escritor do " +
                    "idioma inglês e o mais influente dramaturgo do mundo. É chamado frequentemente de poeta nacional da Inglaterra " +
                    "e de 'Bardo do Avon'.");
            dao.insert(autor);

            autor = new Autor("Tom Clancy");
            autor.setDescricaoAutor("Thomas Clancy Jr. foi um escritor e historiador norte-americano, conhecido por seus enredos " +
                    "detalhados de espionagem e de ciência militar que ocorrem durante e depois da Guerra Fria");
            dao.insert(autor);
    }

    private void cadastraLivros(DAO dao){
        Integer jk = dao.selecionaIdAutor("J. K. Rowling");
        Integer tolkien = dao.selecionaIdAutor("J. R. R. Tolkien");

        Livro livro = new Livro("O senhor dos aneis: A sociedade do anel",
                "10-08-1962", "Fantasia");
        Integer idLivro = Math.toIntExact(dao.insert(livro));
        Autor_Livro autorLivro = new Autor_Livro(tolkien, idLivro);
        dao.insert(autorLivro);

        livro = new Livro( "O senhor dos aneis: As duas torres",
                "10-08-1964", "Fantasia");
        idLivro = Math.toIntExact(dao.insert(livro));
        autorLivro = new Autor_Livro(tolkien, idLivro);
        dao.insert(autorLivro);

        livro = new Livro("O senhor dos aneis: O retorno do rei",
                "10-08-1965", "Fantasia");
        idLivro = Math.toIntExact(dao.insert(livro));
        autorLivro = new Autor_Livro(tolkien, idLivro);
        dao.insert(autorLivro);

        livro = new Livro("Harry Potter e a pedra filosofal",
                "10-08-1999", "Fantasia");
        idLivro = Math.toIntExact(dao.insert(livro));
        autorLivro = new Autor_Livro(jk, idLivro);
        dao.insert(autorLivro);

        livro = new Livro("Harry Potter e a camara secreta",
                "10-08-2001", "Fantasia");
        idLivro = Math.toIntExact(dao.insert(livro));
        autorLivro = new Autor_Livro(jk, idLivro);
        dao.insert(autorLivro);

        livro =  new Livro("Harry Potter e o prisioneiro de Azkaban",
                "10-08-2003", "Fantasia");
        idLivro = Math.toIntExact(dao.insert(livro));
        autorLivro = new Autor_Livro(jk, idLivro);
        dao.insert(autorLivro);

        livro =  new Livro("Harry Potter e o calice de fogo",
                "10-08-2005", "Fantasia");
        idLivro = Math.toIntExact(dao.insert(livro));
        autorLivro = new Autor_Livro(jk, idLivro);
        dao.insert(autorLivro);

        livro =  new Livro("Harry Potter e a ordem da fenix",
                "10-08-2006", "Fantasia");
        idLivro = Math.toIntExact(dao.insert(livro));
        autorLivro = new Autor_Livro(jk, idLivro);
        dao.insert(autorLivro);

        livro =  new Livro("Harry Potter e o principe mestiço",
                "10-08-2003", "Fantasia");
        idLivro = Math.toIntExact(dao.insert(livro));
        autorLivro = new Autor_Livro(jk, idLivro);
        dao.insert(autorLivro);

        livro =  new Livro("Harry Potter e as reliquias da morte",
                "10-08-2003", "Fantasia");
        idLivro = Math.toIntExact(dao.insert(livro));
        autorLivro = new Autor_Livro(jk, idLivro);
        dao.insert(autorLivro);
    }

    public void inicializacao(DAO dao) {

        if (dao.contaUsuarios() == 0) {

                    cadastraAutores(dao);
                    cadastraLivros(dao);



            /*
            Usuario teste = dao.valida("teste", "123");

            Emprestimo mu1 = new Emprestimo(idLivro1, teste.getIdUsuario());
            dao.insert(mu1);

            Emprestimo mu2 = new Emprestimo(idLivro2, teste.getIdUsuario());
            dao.insert(mu2);

            Emprestimo mu3 = new Emprestimo(idLivro3, teste.getIdUsuario());
            dao.insert(mu3);
            */
        }
    }
}
