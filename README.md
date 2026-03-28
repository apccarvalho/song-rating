# Song Rating API 🎵

Projeto desenvolvido para a disciplina de **Sistemas para Internet (Web II)** no **IFTM (Instituto Federal do Triângulo Mineiro)**. A aplicação consiste em um sistema de backend para gerenciamento e avaliação de músicas.

---

## 🚀 Tecnologias Utilizadas

* **Java 21**
* **Spring Boot 4**
* **Spring Data JPA**: Abstração de persistência de dados.
* **MySQL**: Banco de dados para armazenamento das informações.
* **Maven**: Gerenciador de dependências e automação de build.
* **Lombok**: Produtividade para geração de Getters, Setters e Construtores.

## 📋 Funcionalidades

* Cadastro completo de músicas.
* Atribuição de notas (Rate) em uma escala de 1 a 5.
* Armazenamento de metadados musicais (Artista, Gênero, Duração).

## 🗄️ Estrutura da Entidade `songs`

A tabela no banco de dados segue o mapeamento abaixo:

| Campo | Tipo | Restrição | Descrição |
| :--- | :--- | :--- | :--- |
| `name` | String | Not Null | Nome da música |
| `artist` | String | Not Null | Nome do artista ou banda |
| `genre` | String | Not Null | Gênero musical |
| `duration` | Float | Not Null | Duração da música (ex: 3.45) |
| `rate` | Integer | Not Null | Avaliação de 1 a 5 |
