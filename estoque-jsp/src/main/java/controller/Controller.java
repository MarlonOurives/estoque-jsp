package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select", "/update", "/delete", "/report" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DAO dao = new DAO();
	JavaBeans produto = new JavaBeans();

	public Controller() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// teste conexão
		// dao.testeConexao();

		String action = request.getServletPath();
		System.out.println(action);
		if (action.equals("/main")) {
			produtos(request, response);
		} else if (action.equals("/insert")) {
			novoProduto(request, response);
		} else if (action.equals("/select")) {
			listarProduto(request, response);
		} else if (action.equals("/update")) {
			editarProduto(request, response);
		} else if (action.equals("/delete")) {
			removerProduto(request, response);
		} else if (action.equals("/report")) {
			gerarRelatorio(request, response);
		} else {
			response.sendRedirect("index.html");
		}
	}

	// Listar produtos
	protected void produtos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Criando objeto que irá receber dados do JavaBenas
		ArrayList<JavaBeans> lista = dao.listarProdutos();
		// Encaminhas a lista ao documento produto.jsp
		request.setAttribute("produtos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("estoque.jsp");
		rd.forward(request, response);
	}

	// novo produto
	protected void novoProduto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Setando os valores
		produto.setNome(request.getParameter("nome"));
		produto.setQuantidade(request.getParameter("quantidade"));
		produto.setValor(request.getParameter("valor"));

		// Invocando o método inserirProduto passando o objeto produto
		dao.inserirProduto(produto);
		// Redicionar para o documento produto.jsp
		response.sendRedirect("main");

	}

	// editar produto (Primeiro seleciona o produto)
	protected void listarProduto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Id do contato que será editado
		String id = request.getParameter("id");
		// Setar a variável
		produto.setId(id);
		// Executando o método selecionar contato
		dao.selecionarProduto(produto);
		// Settar os atributos do formulário com o conteúdo JavaBenas
		request.setAttribute("id", produto.getId());
		request.setAttribute("nome", produto.getNome());
		request.setAttribute("quantidade", produto.getQuantidade());
		request.setAttribute("valor", produto.getValor());
		// Encaminhar ao documento editar.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
	}

	protected void editarProduto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Setar as variáveis JavaBeans
		produto.setId(request.getParameter("id"));
		produto.setNome(request.getParameter("nome"));
		produto.setQuantidade(request.getParameter("quantidade"));
		produto.setValor(request.getParameter("valor"));
		// Executar o método alterarProduto
		dao.alterarProduto(produto);
		// Rediricionar para o documento estoque.jsp (com os dados atualizados)
		response.sendRedirect("main");
	}

	// Excluir um produto
	protected void removerProduto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// recebimento do id do produto a ser excluído (validador.js)
		String id = request.getParameter("id");
		// setar a variável id
		produto.setId(id);
		// Executar o método deletarProduto (DAO) passando o objeto produto
		dao.deletarProduto(produto);
		// Rediricionar para o documento estoque.jsp (com os dados atualizados)
		response.sendRedirect("main");
	}
	//Gerar relatório em pdf
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Document documento = new Document();
		try {
			//tipo de conteúdo
			response.setContentType("apllication/pdf");
			//nome do documento
			response.addHeader("Content-Disposition", "inline; filename=" + "produto.pdf");
			//criar o documento
			PdfWriter.getInstance(documento, response.getOutputStream());
			//abrir o documento para gerar o conteúdo
			documento.open();
			documento.add(new Paragraph("Lista de produtos: "));
			documento.add(new Paragraph(" "));
			//criar uma tabela
			PdfPTable tabela = new PdfPTable(3); //3 é quantidade de colunas
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Quantidade"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Valor"));
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			//popular a tabela
			ArrayList<JavaBeans> lista = dao.listarProdutos();
			for(int i = 0; i< lista.size(); i++) {
				tabela.addCell(lista.get(i).getNome());
				tabela.addCell(lista.get(i).getQuantidade());
				tabela.addCell(lista.get(i).getValor());

			}
			documento.add(tabela);
			documento.close();
		} catch (Exception e) {
			System.out.println(e);
			documento.close();
		}
		
	}
	
}
