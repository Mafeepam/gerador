Funcionamento Geral do Sistema
O sistema é uma aplicação Java baseada no padrão Command e Injeção de Dependências, projetada para realizar operações sobre produtos em um ambiente web. Ele facilita o processamento modular das requisições HTTP e o roteamento dinâmico dos comandos. O sistema segue a estrutura básica do padrão MVC (Model-View-Controller), mas com ênfase na flexibilidade de rotas e injeção de dependências para facilitar a manutenção e a escalabilidade.
1. Model (Modelo)
Classe Produto: Representa os dados de um produto. É um POJO simples com atributos como id, nome e preco, além de seus respectivos métodos de acesso (getters/setters).
2. Controller (Controlador)
ProdutoController: Este servlet é responsável por interceptar as requisições HTTP e delegar o processamento para os comandos correspondentes. Ele utiliza um mapa de comandos, configurado no listener, para determinar qual comando executar com base na URL da requisição.
3. Services (Serviços)
ProdutoService: Lógica de negócios que interage com os repositórios para gerenciar as operações de produtos (adicionar, listar, remover).
4. Repositórios (Persistence)
MemoriaProdutoRepository e HSQLProdutoRepository: Implementam a persistência de dados. O primeiro armazena dados em memória, enquanto o segundo utiliza um banco de dados HSQL em memória. A troca entre os repositórios é gerida por um Factory Method.

1. Explicação de Cada Anotação Criada e Seu Propósito
1.1 @Rota
Descrição: Define a URL associada a um servlet que implementa a interface Command. Por exemplo, @Rota(value="/adicionarProdutos") indica que a classe será executada quando a URL /adicionarProdutos for acessada.
Funcionamento: Durante a inicialização do contexto (InicializadorListener), a anotação é escaneada utilizando reflexão. O caminho (rota) é registrado em um mapa que associa a URL à classe correspondente.
Objetivo: Elimina a necessidade de mapeamento manual de rotas, proporcionando flexibilidade na configuração de URLs de maneira dinâmica.
1.2 @Inject
Descrição: Marca os campos que devem receber dependências automaticamente. Exemplo: @Inject private ProdutoService produtoService; injeta dinamicamente uma instância de ProdutoService.
Funcionamento: A classe Injector usa reflexão para identificar campos anotados com @Inject e instancia a dependência apropriada com base no tipo do campo.
Objetivo: Facilita o desacoplamento entre as classes e automatiza o processo de injeção de dependências.
1.3 @Singleton
Descrição: Marca classes que devem seguir o padrão Singleton, garantindo que apenas uma instância da classe exista na aplicação.
Funcionamento: A classe InstanciaUnica verifica, usando reflexão, a anotação @Singleton e retorna a instância única da classe. Se a instância ainda não existir, ela é criada.
Objetivo: Gerencia a criação e o ciclo de vida de instâncias únicas, economizando recursos e garantindo consistência.

2. Uso de Reflexão para Carga Dinâmica de Rotas e Dependências
2.1 Reflexão para Carga de Rotas
Onde ocorre: Na classe InicializadorListener.
Como funciona:
A biblioteca Reflections escaneia o pacote br.com.ucsal.controller em busca de classes anotadas com @Rota.
Cada classe anotada é carregada em tempo de execução e registrada no mapa commands.
Quando uma URL é requisitada, o ProdutoController busca o comando correspondente no mapa e executa a lógica associada.
2.2 Reflexão para Injeção de Dependências
Onde ocorre: Na classe Injector.
Como funciona:
A classe Injector identifica campos anotados com @Inject.
Ela verifica o tipo do campo e cria dinamicamente a dependência necessária.
A dependência é injetada, permitindo que a classe a utilize sem precisar instanciá-la diretamente.

3. Factory Method para Troca entre Repositórios e Singleton
3.1 Troca de Repositórios com Factory Method
Onde ocorre: Na classe PersistenciaFactory.
Como funciona:
O método estático getProdutoRepository(int type) retorna a implementação adequada de ProdutoRepository:
Memória: Retorna uma instância do MemoriaProdutoRepository (Singleton).
HSQL: Retorna uma nova instância do HSQLProdutoRepository.
Essa abordagem permite que a implementação do repositório seja trocada dinamicamente sem alterar o código nas classes de serviço.
3.2 Implementação do Singleton
Onde ocorre: Na classe MemoriaProdutoRepository.
Como funciona:
A classe é anotada com @Singleton, e a criação da instância é centralizada no método getInstancia().
O método getInstancia() verifica se a instância já existe. Se sim, retorna a instância existente. Caso contrário, cria uma nova instância e a retorna.
O gerenciamento da instância única é feito por reflexão, na classe InstanciaUnica, garantindo que a instância seja compartilhada por toda a aplicação.

Resumo Detalhado
Anotações:
@Rota: Automatiza o mapeamento de URLs, simplificando o roteamento dinâmico.
@Inject: Gerencia as dependências de forma automática, facilitando a manutenção e o desacoplamento.
@Singleton: Centraliza a criação de instâncias únicas, garantindo eficiência e consistência no uso de recursos.
Reflexão:
Usada para carregar rotas dinamicamente e injetar dependências em tempo de execução, reduzindo o acoplamento e aumentando a flexibilidade.
Factory Method:
Proporciona flexibilidade para alternar entre implementações de repositórios (Memória ou HSQL) sem a necessidade de alterações no código de serviço.
Singleton:
Garante a economia de recursos e a consistência no acesso a instâncias de repositórios, implementando o padrão Singleton de forma eficiente.
Com esta estrutura modular e flexível, o sistema é facilmente extensível, reutilizável e fácil de manter, permitindo adicionar novos recursos e repositórios conforme necessário.


<a href=“[Link do drive](https://docs.google.com/document/d/1296A9Zd6ZBJeFVGEkyKEtipCDHI1jX6KIW7o3fEdcfY/edit?usp=sharing)“>Flex Grid</a>

![Diagrama de classe](https://media.discordapp.net/attachments/1312061747271831564/1312159781019975810/pooa.drawio.png?ex=674ec746&is=674d75c6&hm=7e025c8be6f96e8d922951544fa07d0a86eac1a737703b9db865212a5c011a9f&=&format=webp&quality=lossless&width=1087&height=662)

![Diagrama de Sequência](https://media.discordapp.net/attachments/1312061747271831564/1313156519151534140/Diagrama_sem_nome.drawio_2.png?ex=674f1bcf&is=674dca4f&hm=83c4d9c45491aec691dc17e53a4b06d8bdc757199c250f8f42979f878c275c3f&=&format=webp&quality=lossless&width=750&height=395)
