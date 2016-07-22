package br.com.rangosolucoes.service;

import java.io.Serializable;

public class CadastroPedidoService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/*@Inject
	private PedidoRepository pedidoRepository;
	
	@Transacional
	public Pedido salvar(Pedido pedido){
		if(pedido.isNovo()){
			pedido.setDataCriacao(new Date());
			pedido.setStatus(StatusPedido.ORCAMENTO);
		}
		
		pedido.recalcularValorTotal();
		
		if(pedido.isNaoAlteravel()){
			throw new NegocioException("Pedido não pode ser alterado no status "
				+ pedido.getStatus().getDescricao() + ".");
		}
		
		if(pedido.getItens().isEmpty()){
			throw new NegocioException("O pedido deve possuir pelo menos um item.");
		}
		
		if(pedido.isValorTotalNegativo()){
			throw new NegocioException("O valor total do pedido não pode ser negativo.");
		}
		
		pedido = this.pedidoRepository.guardar(pedido);
		return pedido;
	}*/

}
