package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.mysql.cj.protocol.Resultset;

public class DAO {

	// Parametros
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/dbestoque?useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password = "root";

	// Método de conexão
	private Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/*
	 * teste de conexão public void testeConexao() { try { Connection con =
	 * conectar(); System.out.println(con); con.close(); } catch (Exception e) {
	 * System.out.println(e); } }
	 */
	/** CRUD CREATE **/

	public void inserirProduto(JavaBeans produto) {
		String create = "INSERT INTO PRODUTOS (NOME, QUANTIDADE, VALOR) VALUES (?,?,?)";
		try {
			// abrindo a conexão
			Connection con = conectar();
			// preparando a query
			PreparedStatement pst = con.prepareStatement(create);
			// Substituindo os parâmetros (?) pelo conteúdo da variável JavaBenas
			pst.setString(1, produto.getNome());
			pst.setString(2, produto.getQuantidade());
			pst.setString(3, produto.getValor());
			// Executando a query
			pst.executeUpdate();
			// Encerrar a conexão com o banco
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/** CRUD READ **/

	public ArrayList<JavaBeans> listarProdutos() {
		// Objeto para acessar a classe JavaBeans
		ArrayList<JavaBeans> produtos = new ArrayList<>();

		String read = "SELECT * FROM PRODUTOS ORDER BY NOME";
		try {
			// abrindo a conexão
			Connection con = conectar();
			// preparando a query
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery();
			// Enquanto houver produtos
			while (rs.next()) {
				// Variáveis de apoio que recebem os dados do banco
				String id = rs.getString(1);
				String nome = rs.getString(2);
				String quantidade = rs.getString(3);
				String valor = rs.getString(4);
				// Populando o ArrayList
				produtos.add(new JavaBeans(id, nome, quantidade, valor));

			}
			con.close();
			return produtos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/** CRUD UPDATE **/
	// seleciona o Produto pelo id
	public void selecionarProduto(JavaBeans produto) {
		String read2 = "SELECT * FROM PRODUTOS WHERE ID = ?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read2);
			pst.setString(1, produto.getId());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				produto.setId(rs.getString(1));
				produto.setNome(rs.getString(2));
				produto.setQuantidade(rs.getString(3));
				produto.setValor(rs.getString(4));
			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Editar o Produto
	public void alterarProduto(JavaBeans produto) {
		String update = "UPDATE PRODUTOS SET NOME = ? , QUANTIDADE = ? , VALOR = ? WHERE ID = ? ";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(update);
			pst.setString(1, produto.getNome());
			pst.setString(2, produto.getQuantidade());
			pst.setString(3, produto.getValor());
			pst.setString(4, produto.getId());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/** CRUD DELETE **/
	public void deletarProduto(JavaBeans produto) {
		String delete = "DELETE FROM PRODUTOS WHERE ID = ?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(delete);
			pst.setString(1, produto.getId());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
