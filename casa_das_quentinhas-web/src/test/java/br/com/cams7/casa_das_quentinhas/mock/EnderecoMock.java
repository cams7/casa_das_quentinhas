package br.com.cams7.casa_das_quentinhas.mock;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.cams7.casa_das_quentinhas.entity.Cidade;

public class EnderecoMock extends AbstractMock {

	private static final Map<Integer, String> cidades;
	private static final Map<Integer, String[]> bairros;

	static {
		cidades = new HashMap<>();
		cidades.put(2308, "Belo Horizonte < MG >");
		cidades.put(2901, "Sabará < MG >");
		cidades.put(2917, "Santa Luzia < MG >");

		bairros = new HashMap<>();
		// Belo Horizonte - 3106200
		bairros.put(2308, new String[] {
				// Barreiro
				"Conjunto Ademar Maldonado", "Araguaia", "Vila Alta Tensão", "Conjunto Átila de Paiva",
				"Bairro das Indústrias I", "Barreiro", "Vila Bernadete", "Conjunto Boa Esperança", "Bonsucesso",
				"Conjunto Bonsucesso", "Brasil Industrial", "Cardoso", "Vila Castanheiras", "Vila Cemig", "Vila Copasa",
				"Cristo Redentor", "Diamante", "Conjunto Esperança", "Flávio Marques Lisboa",
				"Conjunto Flávio de Oliveira", "Vila Formosa", "Independência", "Itaipu", "Vila Jardim do Vale",
				"Jatobá", "Jatobá IV", "Lindéia", "Regina", "Mangueiras", "Vila Marieta", "Marilândia", "Milionários",
				"Mineirão", "Miramar", "Novo das Indústrias", "Novo Santa Cecília", "Olaria", "Olhos d'Água",
				"Petrópolis", "Vila Petrópolis", "Pilar", "Vila Piratininga", "Conjunto Pongelupe",
				"Conjunto Renato Ernesto do Nascimento", "Santa Cecília", "Santa Helena", "Santa Margarida",
				"Santa Rita", "Vila Santa Rita", "Serra do José Vieira", "Sol Nascente", "Solar", "Solar do Barreiro",
				"Teixeira Dias", "Tirol", "Conjunto Túnel de Ibirité", "Urucuia", "Vale do Jatobá",
				"Vila Nova dos Milionários", "Vila Pinho", "Washington Pires",
				// Centro-Sul
				"Anchieta", "Antiga Cidade da Serra", "Barro Preto", "Boa Viagem", "Carmo", "Centro", "Cidade Jardim",
				"Conjunto Santa Maria", "Coração de Jesus", "Cruzeiro", "Funcionários", "Lourdes", "Luxemburgo",
				"Mangabeiras", "Monte de São José", "Morro do Papagaio", "Nossa Senhora da Conceição", "Novo Anchieta",
				"Novo São Lucas", "Novo Sion", "Pilar", "Santa Efigênia", "Santa Isabel", "Santa Lúcia",
				"Santo Agostinho", "Santo Antônio", "São Bento", "São Lucas", "São Pedro", "Savassi", "Serra", "Sion",
				"Soberana", "Vila Acaba Mundo", "Vila Aparecida", "Vila Ápia", "Vila Cafezal", "Vila Estrela",
				"Vila Marçola", "Vila Morro do Querosene", "Vila Nossa Senhora de Fátima", "Vila Papagaio",
				"Vila Paris", "Vila Pindura Saia", "Vila Santa Lúcia", "Vila Santa Maria", "Vila Santana do Cafezal",
				// Leste
				"Abadia", "Alto da Boa Vista", "Alto Vera Cruz",
				"Américo Wernek" /* (antigo Colônia/Floresta) */, "Ana Lúcia", "Bairro da Graça", "Baleia", "Belém",
				"Bias Fortes" /* (antigo Colônia/Santa Ifigênia) */, "Boa Vista", "Buraco Quente", "Caetano Furquim",
				"Cafezal", "Camponesa", "Casa Branca", "Castanheira", "Cidade Jardim Taquaril", "Cônego Pinheiro",
				"Conselheiro Rocha", "Cruzeiro do Sul", "Edgard Wernek", "Esplanada", "Esplanada I", "Esplanada II-A",
				"Esplanada II-B", "Floresta", "Freitas", "Granja de Freitas", "Horto", "Horto Florestal",
				"Instituto Agronômico", "João Alfredo", "Jonas Veiga", "Mariano de Abreu", "Nova Vista",
				"Novo Horizonte", "Novo São Lucas", "Paraíso I", "Paraíso II", "Parque Cidade Jardim",
				"Cruzeiro do Sul", "Parque Nossa Senhora do Rosário", "Pirineus", "Política", "Pompeia",
				"Santa Efigênia", "Sagrada Família", "Santa Inês", "Santa Tereza", "Santana do Cafezal",
				"Santos Torres", "São Geraldo", "São Rafael", "São Vicente", "Saudade",
				"Serra" /* (8ª seção suburbana, Belo Horizonte) */, "Taquaril", "Teresa Cristina A", "Vera Cruz",
				"Vila Área", "Vila Grota", "Vila Nossa Senhora Aparecida", "Vila Nossa Senhora do Rosário",
				"Vila Rock in Rio",
				// Nordeste
				"Bairro da Graça", "Beija-Flor",
				"Beira Linha" /*
								 * (parte da Vila Dom Silvério, São Gabriel,
								 * Triba, Parque Belmonte e Jardim Belmonte)
								 */, "Belmonte", "Cachoeirinha", "Capitão Eduardo", "Cidade Nova", "Colégio Batista",
				"Concórdia", "Conjunto Habitacional Capitão Eduardo", "Conjunto Habitacional Dom Silvério",
				"Conjunto Habitacional Fernão Dias", "Conjunto Habitacional Goiânia", "Conjunto Habitacional Paulo VI",
				"Conjunto Habitacional São Gabriel", "Dom Joaquim", "Dom Silvério", "Eymard", "Fernão Dias", "Goiânia",
				"Ipê", "Ipiranga", "Jardim Vitória", "Lagoinha", "Maria Goretti", "Maria Virgínia", "Nazaré",
				"Nova Floresta", "Novo Aarão Reis", "Ouro Minas", "Palmares", "Parque Riachuelo", "Paulo VI", "Pirajá",
				"Pousada Santo Antônio", "Renascença", "Santa Cruz", "São Cristóvão", "São Gabriel",
				"São José/Gorduras", "São Marcos", "São Paulo", "Silveira", "União", "Vila Antônio Ribeiro de Abreu",
				"Vila Artur de Sá", "Vila Beija-flor" /* (Borges) */, "Vila Boa União",
				"Vila Brasília" /* (Guanabara) */, "Vila Carioca", "Vila Coqueiro" /* (Beco) */, "Vila Corococó",
				"Vila da Paz", "Vila do Pombal", "Vila Humaitá" /* (Inestan) */, "Vila Ipiranga", "Vila Maria",
				"Vila Matadouro", "Vila Nova Cachoeirinha IV", "Vila Nova Cachoeirinha III",
				"Vila Presidente Vargas" /* (São Benedito) */,
				"Vila São Paulo" /*
									 * (Andiroba, Modelo, Praça da Associação,
									 * Suzana)
									 */, "Vila Tiradentes", "Vila Três Marias", "Vila Universitário", "Vila Vista do Sol",
				"Vista do Sol",
				// Noroeste
				"Alípio de Melo", "Alto dos Caiçaras", "Alto dos Pinheiros", "Álvaro Camargos", "Aparecida",
				"Aparecida Sétima Seção", "Bom Jesus", "Bonfim", "Caiçara Adelaide", "Caiçaras", "Califórnia",
				"Camargos", "Carlos Prates", "Conjunto Califórnia", "Conjunto Califórnia Dois",
				"Conjunto Celso Machado", "Conjunto Itacolomi", "Coqueiros", "Coração Eucarístico", "Dom Bosco",
				"Dom Cabral", "Ermelinda", "Filadélfia", "Frei Eutáquio", "Gameleira", "Glalija", "Glória",
				"Governador Benedito Val", "Inconfidência", "Ipanema", "Jardim Alvorada", "Jardim Montanhês",
				"João Pinheiro", "Lagoinha", "Minas Brasil", "Monsenhor Messias", "Nova Cachoeirinha", "Nova Esperança",
				"Padre Eustáquio", "Pedreira Prado Lopes", "Pedro II", "Pindorama", "Primavera", "Santa Maria",
				"Santo André", "São Cristóvão", "São Salvador", "Sumaré", "Vila Oeste", "Vila Virgínia", "Serrano",
				// Norte
				"1º de Maio", "Conjunto Felicidade", "Conjunto Floramar", "Aarão Reis", "Canaã", "Conjunto Mariquinhas",
				"Conjunto Zilah de Souza Spósito", "Etelvina Carneiro", "Felicidade", "Floramar", "Guarani",
				"Heliópolis", "Monte Azul", "Jaqueline", "Jardim Felicidade", "Jardim Guanabara", "Juliana", "Lajedo",
				"Marize", "Planalto", "Minaslândia", "Monte Azul", "Novo Aarão Reis", "Providência",
				"Ribeiro de Abreu" /* (Conjunto Ribeiro de Abreu) */, "São Bernardo", "Solimões", "Tupi", "Tupi A",
				"Tupi B", "Vila Aeroporto", "Vila Bacuraus", "Vila Biquinhas", "Vila Boa União", "Vila Clóris",
				"Vila São Tomás", "Xodó",
				// Oeste
				"Alto Barroca", "Bairro das Industrias", "Bairro das Manções", "Barroca", "Belvedere", "Betânia",
				"Buritis", "Cabana Pai Tomaz", "Calafate", "Camargos", "Cinqüentenário", "Conjunto Betânia",
				"Conjunto Estrela d'Alva", "Conjunto Santa Maria", "Estoril", "Gameleira", "Glalijá", "Grajaú",
				"Gutierrez", "Havaí", "Jardim América", "Justinópolis", "Madre Gertrudes", "Marajó", "Morro das Pedras",
				"Nova Barroca", "Nova Cintra", "Nova Gameleira", "Nova Granada", "Nova Suíça", "Olhos d'Água",
				"Palmeiras", "Parque São José", "Patrocínio", "Prado", "Salgado Filho", "Santa Lúcia",
				"Serra do José Vieira", "Vila Oeste", "Vista Alegre",
				// Pampulha
				"Aeroporto", "Bandeirantes", "Braúnas", "Castelo",
				"Céu Azul" /* (4ª seção) */, "Cidade Universitária", "Conjunto Confisco", "Conjunto Habitacional Lagoa",
				"Conjunto Helena Antipoff", "Conjunto Nova Pampulha", "Conjunto Vila Rica", "Copacabana", "Dona Clara",
				"Engenho Nogueira", "Garças", "Indaiá", "Itapoã",
				"Itatiaia" /* (conhecido também como Santa Terezinha) */, "Jaraguá", "Jardim Atlântico", "Liberdade",
				"Manacás", "Mariana", "Novo Itapuã", "Novo Ouro Preto", "Ouro Preto", "Pampulha", "Paquetá",
				"Santa Amélia", "Santa Branca", "Santa Mônica", "Santa Rosa", "Santa Teresinha", "São Bernardo",
				"São Francisco", "São José", "São Luís", "São Tomás", "Saramenha", "Sarandí", "Serrano", "Suzana",
				"Trevo", "Universitário", "Urca", "Vila do Índio", "Vila Isabel", "Vila Maria Virgínia", "Vila Paquetá",
				"Vila Santa Cruz", "Vila Santa Rosa", "Vila São Miguel", "Vila São Vicente", "Vila Real", "Vila Unida",
				"Xangrilá",
				// Venda Nova
				"Candelária", "Cenáculo", "Centro de Venda Nova", "Copacabana", "Esplendor", "Europa", "Flamengo",
				"Jardim dos Comerciários", "Jardim Europa", "Jardim Leblon", "Kátia", "Lagoa", "Jardim Leblon",
				"Letícia", "Mantiqueira", "Maria Helena", "Minas Caixa", "Nova América", "Nova York", "Novo Letícia",
				"Paraúna", "Piratininga", "Rio Branco", "São João Batista", "São Paulo", "Serra Verde", "Sinimbu",
				"Universo", "Várzea das Palmas", "Venda Nova", "Vila Antena", "Vila Apolônia", "Vila Capri",
				"Vila Copacabana", "Vila dos Anjos", "Vila Estrela", "Vila Itamarati", "Vila Mãe dos Pobres",
				"Vila Mantiqueira", "Vila Minas Caixa", "Vila Nossa Senhora Aparecida", "Vila São João Batista",
				"Vila Santa Branca", "Vila São José", "Vila Serra Verde", "Vila Sesc" });
		// Sabará - 3156700
		bairros.put(2901,
				new String[] { "Carvalho de Brito", "Mestre Caetano", "Ravena", "Adelmolândia", "Água Férrea",
						"Alto do Cabral", "Alto do Fidalgo", "Alvorada", "Ana Lúcia", "Área Rural de Sabará",
						"Arraial Velho", "Borba Gato", "Borges", "Caieira", "Centro", "Conjunto Morada da Serra",
						"Córrego da Ilha", "Distrito Industrial Simão da Cunha", "Esplanada", "Fogo Apagou",
						"Itacolomi", "Jardim Castanheiras", "Mangabeiras", "Mangueiras", "Morro da Cruz",
						"Morro São Francisco", "Nações Unidas", "Nossa Senhora da Conceição", "Nossa Senhora de Fátima",
						"Nossa Senhora do Ó", "Novo Alvorada", "Novo Horizonte", "Novo Santa Inês", "Paciência",
						"Padre Chiquinho", "Pompeu", "Praia dos Bandeirantes", "Rio Negro", "Roça Grande", "Rosário",
						"Santana", "Santo Antônio" /* (Roça Grande) */, "São José", "Siderúrgica", "Sobradinho",
						"Terra Santa", "Valparaíso", "Vila Amélia Moreira", "Vila Bom Retiro", "Vila Campinas",
						"Vila dos Coqueiros", "Vila Esperança", "Vila Eugênio Rossi", "Vila Francisco de Moura",
						"Vila Marzagão", "Vila Michel", "Vila Nova Vista", "Vila Real", "Vila Rica", "Vila Santa Cruz",
						"Vila Santa Rita", "Vila Santo Antônio de Pádua", "Vila São Sebastião", "Códigos Postais" });
		// Santa Luzia - 3157807
		bairros.put(2917, new String[] { "Adeodato", "Área Rural de Santa Luzia",
				"Asteca" /* (São Benedito) */, "do Divino",
				"Baronesa" /* (São Benedito) */, "Barreiro do Amaral", "Bela Vista", "Belo Vale", "Bicas",
				"Boa Esperança", "Bom Destino", "Bom Jesus", "Bonanza", "Camelos", "Casa Branca", "Castanheira",
				"Centro", "Colorado", "Comunidade Sítio Teresópolis", "Condomínio Estância dos Lagos",
				"Conjunto Cristina" /* (São Benedito) */,
				"Conjunto Palmital" /* (São Benedito) */, "Córrego Frio", "Distrito Industrial Simão da Cunha",
				"Dona Rosarinha", "Duquesa I" /* (São Benedito) */,
				"Duquesa II" /* (São Benedito) */, "Esplanada", "Frimisa", "Gameleira",
				"Granja Santa Inês" /* (São Benedito) */, "Idulipê", "Imperial", "Industrial Americano", "Kennedy",
				"Liberdade", "Londrina" /* (São Benedito) */, "Luxemburgo", "Maria Adélia", "Monte Carlo",
				"Morada do Rio", "Moreira", "Natividade", "Nossa Senhora da Conceição", "Nossa Senhora das Graças",
				"Nossa Senhora do Carmo", "Nova Conquista" /* (São Benedito) */, "Nova Esperança",
				"Nova Esperança" /* (São Benedito) */, "Novo Centro", "Padre Miguel",
				"Padre Miguel" /* (São Benedito) */, "Pérola Negra", "Petrópolis", "Pinhões",
				"Pousada Del Rey" /* (São Benedito) */, "Quarenta e Dois", "Recanto Flamboyant", "Rio das Velhas",
				"Santa Helena", "Santa Matilde", "Santa Mônica", "Santa Rita", "São Benedito",
				"São Cosme de Baixo" /* (São Benedito) */,
				"São Cosme de Cima" /* (São Benedito) */, "São Geraldo", "São João Batista", "Sítio Boa Vista",
				"Três Corações", "Vale das Acácias", "Vale do Tamanduá",
				"Vila Beatriz" /* (São Benedito) */, "Vila das Mansões", "Vila Íris", "Vila Olga", "Vila Otoni",
				"Vila Santa Mônica", "Vila Santo Antônio" /* (São Benedito) */, "Vila São Mateus",
				"Outros Códigos Postais", "São Benedito" });

	}

	/**
	 * Gera um bairro aletório de acordo com o id da cidade informado
	 * 
	 * @param cidadeId
	 *            ID da cidade
	 * @return Nome do bairro
	 */
	public static String getQualquerBairro(Integer cidadeId) {
		return bairros.get(cidadeId)[getBaseProducer().randomBetween(0, bairros.get(cidadeId).length - 1)];
	}

	/**
	 * Gera um CEP aletório de acordo com o id da cidade informado
	 * 
	 * @param cidadeId
	 *            ID da cidade
	 * @return CEP
	 */
	public static String getQualquerCep(Integer cidadeId) {
		// Belo Horizonte - 2308
		// 30001970 - 31999899
		// Sabará - 2901
		// 34505000 - 34750970
		// Santa Luzia - 2917
		// 33010000 33199899
		long cep = 0l;
		switch (cidadeId) {
		case 2308:
			cep = getBaseProducer().randomBetween(30001970l, 31999899l);
			break;
		case 2901:
			cep = getBaseProducer().randomBetween(34505000l, 34750970l);
			break;
		case 2917:
			cep = getBaseProducer().randomBetween(33010000l, 33199899l);
			break;
		default:
			break;
		}

		return cep != 0 ? String.valueOf(cep) : "";
	}

	/**
	 * Gera, aleatoriamente, as informações de uma cidade
	 * 
	 * @return Cidade
	 */
	public static Cidade getQualquerCidade() {
		Integer cidadeId = cidades.keySet().stream().collect(Collectors.toList())
				.get(getBaseProducer().randomBetween(0, 2));

		Cidade cidade = new Cidade(cidadeId);
		cidade.setNome(cidades.get(cidadeId));
		cidade.setDdd(DDD);

		return cidade;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10000; i++) {
			Cidade cidade = EnderecoMock.getQualquerCidade();
			System.out.println((i + 1) + ")\t" + cidade.getId() + "\t" + cidade.getNome() + ", "
					+ EnderecoMock.getQualquerBairro(cidade.getId()));
		}
	}
}
