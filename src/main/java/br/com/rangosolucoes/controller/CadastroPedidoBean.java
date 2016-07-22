package br.com.rangosolucoes.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class CadastroPedidoBean implements Serializable{
	
	/*private static final long serialVersionUID = 1L;
	
	@Produces
	@PedidoEdicao
	private Pedido pedido;
	
	private List<Usuario> vendedores;
	
	private String sku;
	private Long idVendedorTemp;
	private Produto produtoLinhaEditavel;
	
	@Inject
	private ProdutoRepository produtoRepository;
	
	@Inject
	private UsuarioRepository usuarioRepository;
	
	@Inject
	private ClienteRepository clienteRepository;
	
	@Inject
	private ItemPedidoRepository itemPedidoRepository;
	
	@Inject
	private CadastroPedidoService cadastroPedidoService;
	
	public CadastroPedidoBean(){
		limpar();
	}
	
	@PostConstruct
	public void inicializar(){
		vendedores = new ArrayList<>();
		if(FacesUtil.isNotPostBack()){
			if(this.pedido.getId() == null){
				this.pedido.setStatus(StatusPedido.ORCAMENTO);
				this.pedido.adicionarItemVazio();
			}
			this.vendedores = this.usuarioRepository.vendedores();
		}
	}
	
	public void incluirItemVazioPedidoAlteradocao(){
		try {
			this.pedido.removerItemVazio();
		} finally {
			this.pedido.adicionarItemVazio();
		}
	}
	
	
	 * o @Observes fica escutando a alteracao que pode ser feito no pedido no metodo
	 * emitirPedido na classe EmissaoPedidoBean
	 
	public void pedidoAlterado(@Observes PedidoAlteradoEvent event){
		this.pedido = event.getPedido();
	}
	
	private void limpar(){
		pedido = new Pedido();
		pedido.setEnderecoEntrega(new EnderecoEntrega());
		idVendedorTemp = null;
	}

	public void salvar(){
		Usuario usuario = null;
		if(idVendedorTemp != null){
			usuario = usuarioRepository.porId(idVendedorTemp);
			pedido.setVendedor(usuario);
		}
		
		this.pedido.removerItemVazio();
		
		try{
			this.pedido = this.cadastroPedidoService.salvar(pedido);
			FacesUtil.addInfoMessage("Pedido salvo com sucesso!");
		}finally{
			this.pedido.adicionarItemVazio();
		}
	}
	
	public String novoCadastro(){
		limpar();
		if(this.pedido.getId() == null){
			this.pedido.setStatus(StatusPedido.ORCAMENTO);
			this.pedido.adicionarItemVazio();
		}
		if(this.vendedores == null || this.vendedores.size() == 0){
			this.vendedores = this.usuarioRepository.vendedores();
		}
		return "/pedidos/CadastroPedido?faces-redirect=true";
	}
	
	public void carregarProdutoPorSku(){
		if(StringUtils.isNotEmpty(this.sku)){
			this.produtoLinhaEditavel = this.produtoRepository.porSku(this.sku);
			this.carregarProdutoLinhaEditavel();
		}
	}
	
	public void carregarProdutoLinhaEditavel(){
		ItemPedido item = this.pedido.getItens().get(0);
		
		if(this.produtoLinhaEditavel != null){
			if(this.existeItemComProduto(this.produtoLinhaEditavel)){
				FacesUtil.addErrorMessage("Já existe um item no pedido com o produto informado.");
			}else{
				item.setProduto(this.produtoLinhaEditavel);
				item.setValorUnitario(this.produtoLinhaEditavel.getValorUnitario());
				
				this.pedido.adicionarItemVazio();
				this.produtoLinhaEditavel = null;
				this.sku = null;
				
				this.pedido.recalcularValorTotal();
			}
		}
	}
	
	private boolean existeItemComProduto(Produto produto){
		boolean existeItem = false;
		
		for(ItemPedido itemPedido : this.getPedido().getItens()){
			if(produto.getId().equals(itemPedido.getProduto().getId()) 
				&& produto.getNome().equals(itemPedido.getProduto().getNome())){
				existeItem = true;
				break;
			}
		}
		
		return existeItem;
	}
	
	public List<Produto> completarProduto(String nome){
		return this.produtoRepository.porNome(nome);
	}
	
	public void recalcularPedido(){
		this.pedido.recalcularValorTotal();
	}
	
	public void atualizarQuantidade(ItemPedido itemPedido, int linha){
		if(itemPedido.getQuantidade() < 1){
			if(linha == 0){
				itemPedido.setQuantidade(1);
			}else{
				this.getPedido().getItens().remove(linha);
			}
		}
		this.pedido.recalcularValorTotal();
	}
	
	public FormaPagamento[] getFormasPagamento(){
		return FormaPagamento.values();
	}
	
	public List<Cliente> completarCliente(String nome){
		return this.clienteRepository.porNome(nome);
	}
	
	public boolean isEditando(){
		return this.pedido.getId() != null;
	}

	public Pedido getPedido() {
		return pedido;
	}
	
	public void setPedido(Pedido pedido) {
		Usuario usuario = null;
		this.pedido = pedido;

		if(this.pedido.getId() != null){
			this.pedido.setItens(itemPedidoRepository.itensDoPedido(this.pedido));
		}
		
		if(idVendedorTemp != null){
			usuario = usuarioRepository.porId(idVendedorTemp);
			pedido.setVendedor(usuario);
		}else if(pedido.getVendedor() != null){
			idVendedorTemp = pedido.getVendedor().getId();
		}
	}

	public List<Usuario> getVendedores() {
		return vendedores;
	}

	public Long getIdVendedorTemp() {
		return idVendedorTemp;
	}

	public void setIdVendedorTemp(Long idVendedorTemp) {
		this.idVendedorTemp = idVendedorTemp;
	}

	public Produto getProdutoLinhaEditavel() {
		return produtoLinhaEditavel;
	}

	public void setProdutoLinhaEditavel(Produto produtoLinhaEditavel) {
		this.produtoLinhaEditavel = produtoLinhaEditavel;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}*/

}
