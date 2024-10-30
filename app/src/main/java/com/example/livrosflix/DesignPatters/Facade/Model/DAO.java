package com.example.livrosflix.DesignPatters.Facade.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.livrosflix.Classes.Autor;
import com.example.livrosflix.Classes.Autor_Livro;
import com.example.livrosflix.Classes.Emprestimo;
import com.example.livrosflix.Classes.Entrega;
import com.example.livrosflix.Classes.Livro;
import com.example.livrosflix.Classes.Usuario;

import java.util.List;

/*
 * Interface DAO(Data Access Object) utilizada para executar as querys no BD
 */
@Dao
public interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Autor autor);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Emprestimo emprestimo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Livro livro);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Usuario usuario);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Autor_Livro autorLivro);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Entrega entrega);


    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Autor autor);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Emprestimo emprestimo);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Livro livro);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Usuario usuario);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Entrega entrega);

    @Delete
    void delete(Usuario usuario);

    @Delete
    void delete(Livro livro);

    @Delete
    void delete(Autor_Livro autorLivro);


    @Query("SELECT * FROM EMPRESTIMO WHERE status=0")
    List<Emprestimo> entregasPendetes();

    @Query("SELECT * FROM EMPRESTIMO WHERE status BETWEEN 0 AND 3")
    List<Emprestimo> todosEmprestimosAbertos();

    @Query("SELECT * FROM ENTREGA WHERE idEntregador=:taskId AND FINALIZADA=0")
    List<Entrega> minhasEntregas(Integer taskId);

    @Query("SELECT * FROM Autor ORDER BY idAutor ASC")
    List<Autor> todosAutores();

    @Query("SELECT idAutor FROM AUTOR WHERE nomeAutor=:nome")
    Integer selecionaIdAutor(String nome);


    @Query("SELECT * FROM Usuario WHERE login=:taskLogin AND senha=:taskSenha")
    Usuario valida(String taskLogin, String taskSenha);

    @Query("SELECT * FROM Usuario WHERE login=:taskLogin")
    Usuario consultaLoginUsuario(String taskLogin);


    @Query("SELECT * FROM Livro WHERE qtdDisponivel > 0 ORDER BY nomeLivro ASC")
    List<Livro> listaUsuarioLivroNomeASC();

    @Query("SELECT * FROM Livro WHERE qtdDisponivel > 0 ORDER BY Livro.nomeLivro DESC")
    List<Livro> listaUsuarioLivroNomeDESC();

    @Query("SELECT * FROM Livro WHERE qtdDisponivel > 0 ORDER BY genero ASC")
    List<Livro> listaUsuarioLivroGeneroASC();

    @Query("SELECT * FROM Livro WHERE qtdDisponivel > 0  ORDER BY genero DESC")
    List<Livro> listaUsuarioLivroGeneroDESC();


    @Query("SELECT * FROM Livro ORDER BY nomeLivro ASC")
    List<Livro> listaAdmLivroNomeASC();

    @Query("SELECT * FROM Livro ORDER BY Livro.nomeLivro DESC")
    List<Livro> listaAdmLivroNomeDESC();

    @Query("SELECT * FROM Livro ORDER BY genero ASC")
    List<Livro> listaAdmLivroGeneroASC();

    @Query("SELECT * FROM Livro ORDER BY genero DESC")
    List<Livro> listaAdmLivroGeneroDESC();


    @Query("SELECT * FROM Livro WHERE idLivro=:id")
    Livro selecionaLivro(Integer id);

    @Query("SELECT * FROM Emprestimo WHERE idUsuario=:taskIdUser AND idLivro=:taskIdLivro AND dataEmprestimo=:data")
    Emprestimo selecionaEmprestimo(Integer taskIdUser, Integer taskIdLivro, String data);

    @Query("SELECT * FROM EMPRESTIMO WHERE idUsuario=:idUsuario AND " +
            "idLivro=:idLivro AND STATUS BETWEEN 0 AND 3")
    Emprestimo temEmprestimoAtivo(Integer idUsuario, Integer idLivro);



    @Query("SELECT COUNT (*) FROM Usuario WHERE ehAdm = 0 AND ehEntregador = 0")
    Integer contaUsuarios();

    @Query("SELECT COUNT (*) FROM Livro")
    Integer contaLivros();


    @Query("SELECT * FROM Usuario WHERE ehEntregador = 0 AND ehAdm = 0")
    List<Usuario> todosUsuarios();


    @Query("SELECT nomeLivro FROM LIVRO WHERE idLivro=:taskId")
    String selecionaNomeLivro(Integer taskId);


    @Query("SELECT COUNT(*) FROM Emprestimo")
    Integer contaEmprestimos();


    @Query("SELECT * FROM Emprestimo LEFT JOIN Livro ON Emprestimo.idLivro = Livro.idLivro " +
            "WHERE idUsuario=:taskId GROUP BY Livro.nomeLivro ORDER BY Livro.nomeLivro ASC")
    List<Emprestimo> listaEmprestimoNomeASC(Integer taskId);

    @Query("SELECT * FROM Emprestimo LEFT JOIN Livro ON Emprestimo.idLivro = Livro.idLivro " +
            "WHERE idUsuario=:taskId GROUP BY Livro.nomeLivro ORDER BY Livro.nomeLivro DESC")
    List<Emprestimo> listaEmprestimoNomeDESC(Integer taskId);


    @Query("SELECT * FROM Usuario WHERE idUsuario=:taskId")
    Usuario selecionaUsuario(Integer taskId);

    @Query("SELECT nomeUsuario FROM Usuario WHERE idUsuario=:taskId")
    String selecionaNomeUsuario(Integer taskId);

    @Query("SELECT * FROM AUTOR WHERE nomeAutor=:nome")
    Autor selecionaAutorPeloNome(String nome);

    @Query("SELECT * FROM LIVRO WHERE nomeLivro=:nome")
    Livro selecionaLivroPeloNome(String nome);

    @Query("SELECT * FROM AUTOR_LIVRO WHERE idAutor=:idAutor AND idLivro=:idLivro")
    Autor_Livro selecionaAutorLivro(Integer idAutor, Integer idLivro);

    @Query("SELECT * FROM AUTOR_LIVRO WHERE idLivro=:idLivro")
    List<Autor_Livro> autoresDoLivro(Integer idLivro);

    @Query("SELECT * FROM AUTOR WHERE idAutor=:idAutor")
    Autor selecionaAutor(Integer idAutor);

    @Query("UPDATE Livro SET qtdDisponivel = qtdDisponivel+1 WHERE idLivro=:idLivro ")
    void incrementaQtdDisponivel(Integer idLivro);

    @Query("UPDATE Livro SET qtdDisponivel = qtdDisponivel-1 WHERE idLivro=:idLivro ")
    void decrementaQtdDisponivel(Integer idLivro);
}