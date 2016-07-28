package br.com.rangosolucoes.enumeration;

public enum Estados {
	
    ACRE("Acre", "AC"),
    ALAGOAS("Alagoas", "AL"),
    AMAPA("Amapá", "AP"),
    AMAZONAS("Amazonas", "AM"),
    BAHIA("Bahia", "BA"),
    CEARA("Ceará", "CE"),
    DISTRITOFEDERAL("Distrito Federal", "DF"),
    ESPIRITOSANTO("Espírito Santo", "ES"),
    GOIAS("Goiás", "GO"),
    MARANHAO("Maranhão", "MA"),
    MATOGROSSO("Mato Grosso", "MT"),
    MATOGROSSODOSUL("Mato Grosso do Sul", "MS"),
    MINASGERAIS("Minas Gerais", "MG"),
    PARA("Pará", "PA"),
    PARAIBA("Paraíba", "PB"),
    PARANA("Paraná", "PR"),
    PERNAMBUCO("Pernambuco", "PE"),
    PIAUI("Piauí", "PI"),
    RIODEJANEIRO("Rio de Janeiro", "RJ"),
    RIOGRANDEDONORTE("Rio Grande do Norte", "RN"),
    RIOGRANDEDOSUL("Rio Grande do Sul", "RS"),
    RONDONIA("Rondônia", "RO"),
    RORAIMA("Roraima", "RR"),
    SANTACATARINA("Santa Catarina", "SC"),
    SAOPAULO("São Paulo", "SP"),
    SERGIPE("Sergipe", "SE"),
    TOCANTINS("Tocantins", "TO");
	
    private String nome;
    private String sigla;
    
    private Estados(String nome, String sigla) {
        this.nome = nome;
        this.sigla = sigla;       
    }

    public String getNome() {
        return nome;
    }

    public String getSigla() {
        return sigla;
    }

}
