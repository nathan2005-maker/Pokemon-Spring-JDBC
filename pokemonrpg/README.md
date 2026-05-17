# **Entendendo Estrutura do Projeto**

Tecnologias:
Java 21
Spring Boot (3.2.5)
SQLite
Maven: Gerenciador de dependências e builds.

Requisitos:

[JDK 21] (https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)


[Maven](https://maven.apache.org/)

# **Como Executar o Projeto**


Clone este repositório para a sua máquina:
git clone https://github.com/seu-usuario/pokemonrpg.git


Acesse a pasta através do git:


cd pokemonrpg


Compile e inicie o jogo via Maven:


mvn spring-boot:run

# **Estrutura e Funcionamento do Projeto:**


\src\main\java\com\pokemon\pokemonrpg\model:


**ModelPokemon.java:**


Estrutura:
public abstract class: Essa classe não pode ser instanciada diretamente. Existe para ser herdada
pela classe Pokemon,passando os métodos de acesso de Name, ID e Hp.


Variáveis:
Integer id: Identificador único do Pokémon. Usa classe empacotadora (Integer) em vez do primitivo
(int)


String name: Armazena o nome do Pokémon (ex: "Bulbasaur").


Integer hp: Pontos de vida.


Funcionamento:
Centraliza atributos primários que todos Pokémons devem possuir. Aplicando métodos de
acessos para outras classes herdarem essas estruturas e valores.


Pokemon.java


Estrutura:


Esta classe concreta herda diretamente de modelPokemon. Diferente de sua classe pai, ela pode
ser instanciada diretamente. É anotada com `@Entity` como uma entidade mapeada para o banco de
dados relacional, correspondendo à tabela especificada pela anotação @Table(name = "Pokemon").


Variáveis:


private Integer attack: Armazena o valor do atributo de ataque físico do Pokémon.


private Integer defense: Armazena o valor do atributo de defesa física do Pokémon.


@Column(name = "Type1") private String type1: Armazena o tipo primário do Pokémon (ex: "Grass").
Mapeado a coluna "Type1" no banco de dados.


@Column(name = "Type2") private String type2: Armazena o tipo secundário do Pokémon (ex: "Poison").
Pode ser nulo caso o Pokémon tenha apenas um tipo. Mapeado para a coluna "Type2".


private Integer speed: Armazena o valor de velocidade do Pokémon.


@Column(name = "spAttack") private Integer spAttack: Armazena o valor do ataque especial. Mapeado
para a coluna "spAttack".


@Column(name = "spDefense") private Integer spDefense: Armazena o valor da defesa especial.
Mapeado para a coluna "spDefense".


private Double total: Armazena o somatório acumulado de todos os status do Pokémon (geralmente
usados para rankeamento ou balanço).


private Double average: Armazena a média aritmética dos status do Pokémon.


Funcionamento:
Ela herda os dados de identificação e sobrevivência primários ( `id`, `name`, `hp` ) de `modelPokemon` e
adiciona as propriedades detalhadas de batalha e tipagem necessárias. Objetos desta classe
representam diretamente as linhas de dados salvas na tabela do banco de dados


Types.java


Estrutura:


Esta é uma estrutura de enumeração (Enum) do Java. Ela define um conjunto fixo e imutável de
constantes que representam os tipos dos Pokémon.

Variáveis:
`private EnumSet<Types> FRAQUEZAS` : armazena quais tipos de ataques causam danos efetivo.


`private EnumSet<Types> VANTAGENS` : armazena contra quais tipos este tipo atual é super efetivo.


`private EnumSet<Types> IMUNIDADE` : armazena os tipos de ataques aos quais o tipo atual é
completamente imune (dano nulo).


Funcionamento:
mapeia o comportamento tático de cada elemento, permitindo que o controlador de batalha calcule
dinamicamente a eficiência de qualquer confronto apenas consultando as coleções configuradas no
bloco estático.


\src\main\java\com\pokemon\pokemonrpg\repository


**PokemonRepository.java**


Estrutura:
Ela é anotada com `@Repository`, uma anotação estereotipada do Spring Framework. Encapsula o
armazenamento, a recuperação e a busca de dados no banco de dados.


Variáveis:


`JdbcTemplate` : uso de bancos de dados relacionais em Java. Gerencia a abertura e fechamento de
conexões, a execução dos comandos SQL (como `SELECT`, `INSERT etc.` )


`RowMapper<Pokemon>:` Ele pega uma linha do banco de dados (ResultSet rs) e transforma em um
objeto do tipo Pokemon.

Exemplo: Ele pega uma linha do banco de dados (ResultSet rs) e transforma em um objeto do tipo
Pokemon.
rs.getInt("id") -> pega o valor 25 e joga no p.setId(25)


`findById(int` id): Busca um Pokémon específico baseado no ID enviado por parâmetro.
SELECT * FROM pokemon WHERE id = value;


Funcionamento:
Ao centralizar as queries SQL (SELECT) e o mapeamento de tabelas para objetos dentro deste arquivo,
você garante que o restante do sistema possa requisitar instâncias da tabela “Pokémon”.


\src\main\java\com\pokemon\pokemonrpg\service


**CombatAction.java**


Esta e uma interface. Objetivo e definir regras de ataque e defesa durante o jogo. Calcula os
multiplicadores de entrada: atacante, defensor, movePower, moveType e isCritical.


Variáveis:


`Pokemon atacante:` Obtém os dados de quem está atacando (lê os tipos e o valor attack).

`Pokemon defensor:` Obtém os dados de quem está defendendo (lê os tipos e o valor defense).

`int movePower:` AttackService repassa esse mesmo movePower para o método
calculateRawDamage da classe damageCalculate, onde o cálculo matemático real será feito `.`


`Type moveType:` Recebe o dado tipo de elemento (Ex: FIRE, ICE, WATER ...)

`boolean isCritical:` Verifica se o golpe causou um dano crítico.


**Funcionamento**


Quando o método executeAttack básico é chamado, ele dispara duas funções auxiliares privadas:

`calcularEficacia():` Busca no seu Enum Types a relação de efetividade do golpe contra o tipo 1 e o
tipo 2 do defensor


`calcularStab():` Compara o tipo do golpe com os tipos 1 e 2 do atacante


\src\main\java\com\pokemon\pokemonrpg\repository


**AttackService.java**


componente de serviço do Spring Boot, anotação @Service, que implementa a interface CombatAction.
Sua função é processar um ataque calculando todas as variáveis de contexto de uma luta, ela delega a
conta matemática final para classe chamada DamageCalculate.


Variáveis:

Entradas:
atacante (Pokemon): O objeto com os dados de quem está desferindo o golpe.


defensor (Pokemon): O objeto com os dados de quem está sofrendo o golpe.


movePower (int): O poder base do golpe utilizado (ex: 90).


moveType (Types): O tipo elemental do golpe (FIRE, ICE, WATER ...).


isCritical (boolean): Um sinalizador (verdadeiro/falso) para indicar se o golpe foi um acerto crítico.


**Funcionamento:**


O funcionamento da classe é dividido em regras principais:
`public int executeAttack:` Classe principal para o funcionamento de um ataque comum. Todos
os fatores contextuais da batalha (modificadores elementais e sorte) e passar a responsabilidade da


conta matemática bruta para uma calculadora isolada (damageCalculate).


`public int executeAttack:` fluxo do combate e decidir se o dano calculado deve receber uma
bonificação massiva (multiplicação por 1.5) ou se deve prosseguir com o dano padrão.


Dano padrão:


atacante: Pikachu


defensor: Pidgeot


movePower: 90


moveType: Types.ELECTRIC


isCritical: false
Acontece que isCritical recebe false por nao ser um dano crítico, retornando: baseDagame.


Dano Critico:

Exemplo de Entrada:


atacante: Pikachu


defensor: Pidgeot


movePower: 90


moveType: Types.ELECTRIC


isCritical: true
Acontece que isCritical recebe verdadeiro por ser um dano crítico, retornando: baseDagame *1.5.


`private double calcularStab` : Função auxiliar privada, a sua finalidade é calcular o STAB (Bônus
de Ataque do Mesmo Tipo).

Variaveis :
Pokemon atacante: O objeto que contém as informações do Pokémon que está realizando o ataque.
Types moveType: O tipo elemental do golpe que foi escolhido para o ataque (FIRE, ICE, WATER ...)


Valores de Saída:
1.5: Se houver correspondência de tipo (o golpe fica 50% mais forte).


1.0: Se não houver correspondência (o dano permanece padrão, multiplicado por 1).


Entrada sem Bonus:
Pokemon Atacante: Pikachu
Type 1: ELETRIC
Type 2: NULL

Type 1 e Type 2 são difente, atribuindo o valor 1.0.

Entrada com Bonus:
Pokemon Atacante: Chamander
Type 1: FIRE
Type 2: NULL


O código vai testar: O tipo do golpe (FIRE) é igual ao Type1 do Charmander ("Fire")?


Saida: Sim, são iguais! Atribui o valor 1.5.


`private double calcularEficacia` : É este método que define se um golpe será "Super Efetivo"
(causando o dobro ou o quádruplo de dano).


Variáveis:
Pokemon defensor: O objeto que representa a criatura que está sofrendo o golpe.
ypes moveType: O tipo elemental do golpe que foi usado pelo atacante


Funcionamento:

Verifica se a entrada de Type 1 possui um valor diferente de NULL.
Pokemon: Pikachu:
Type 1: ELETRIC
Type 2: NULL


if (defensor.getType1() != null) : Verifica que o Type1 possui o dado ELETRIC, entrando na estrutura
condicional e calculando o ataque do Type ELETRIC.

if (defensor.getType2() != null: Verifica se o Type 2 possui um dado, se for null nao multiplica o dano
recebido. Caso ao contrário, multiplica o dano.


**DamageCalculate.java**


Um componente utilitário do Spring Boot @Component, calculando a matemática do sistema utilizando a
fórmula do game. Recebendo os números brutos e retornando para baseDamage.

Variaveis de Entrada:

int level: O nível do Pokémon atacante (que o AttackService envia fixado em 50).


int basePower: O poder numérico do golpe que está a ser usado (ex: 90).


int attackStat: Os pontos de atributos de ataque do Pokémon agressor.


int defenseStat: Os pontos de atributos de defesa do Pokémon alvo.


double modifiers: O valor consolidado contendo toda a multiplicação de STAB

Funcionamento:
O funcionamento dessa classe e separado em partes:
Passo 1: Verifica o fator nível do Pokemon, maior o nível do Pokémon, maior será esse multiplicador
inicial.

Passo 2: O Impacto dos Atributos e Defesa, junta o fator de nível (22) com a força do golpe e o poder de
ataque do agressor. Esse total acumulado é dividido pela defesa do oponente.

Passo 3: Finalização do Dano Base, O resultado anterior (Step2) é dividido pelo valor fixo de 50 (que faz
parte da fórmula matemática padrão do jogo) e soma-se 2 pontos no final.

Passo 4: calculadora pega o dano de base neutro e multiplica-o pela variável modifiers (que contém os
bónus de STAB, fraquezas, resistências e sorte calculados pelo AttackService).

Entrada:
Atacante: Pikachu


AttackStat: 55
Type1: ELETRIC
Type2: NULL

Entrada:
Defensor: Charizard
DefenseStat: 78
Type1: FIRE
Type2: FLYING

Dados do Golpe:

Nivel: 50 (fixo para TODOS Pokémons)
basePower: 90
moveType: type.ELETRIC


IsCritical: false (Ataque Padrão)


Processando ataque:
AttackService (STAP) : Como Pikachu e do tipo ELETRIC e usou o ataque type.ELETRIC, atribui o valor 1.5.
CalcularEficacia: Verifica a eficiência do ataque sobre o defensor:
Charizard:
Type.FIRE = ataque neutro * 1.0.
Type.FLYING = ataque super efetivo * 2.0.

Acomulador: 1.0 * 2.0 = 2.0.

Fator Sorte: Math.random()
Gerou uma sorte média-alta, resultando em um rng = 0.95.

totalModifiers: = 1.5 * 2.0 * 0.95 = 2.85.

Calculando a Damage:
A): Nível: 50
`((2 * 50) / 5) + 2` = **`22`** .


B) Poder / Atributo do Defensor:
(22 * 90 * 55) / 78 = 108900 / 78 = 1396


C) Dano Base:
(1396 / 50 Nível) + 2 = 27 + 2 = 29

D) Aplicação dos Modificadores Finais:

baseDamage * totalModifiers
29 * 2.85 = 82.65.


Math.floor(82.65) (arredonda para baixo) e converte para int, resultando em 82.


