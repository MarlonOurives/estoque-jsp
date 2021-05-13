/**
 * Confirmação de exclusão de um produto
 * @author Marlon Ourives
 */

function confirmar(id){
	let resposta = confirm("Confirma a exclusão deste produto?")
	if(resposta === true){
		//faz o encaminhamento para outro local 
		window.location.href = "delete?id=" + id;
	}
}