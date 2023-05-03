package com.generation.blogpessoal.configuration;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;


//Define a Classe como configuração, ou seja, ela é fonte de definiões de Beans
@Configuration
public class SwaggerConfig {

/*Metodo deve ser invocado pelo Spring, bem como gerenciar o objeto retornado
 * Ou seja, esse objeto pode ser injetado em qualquer ponto */
	@Bean
	
/* Cria objeto da classe OpenAPI, que gera a documentação no Swagger */
	OpenAPI springBlogPessoalOpenAPI() {
		return new OpenAPI()
//Informações da API
				.info(new Info()
						.title ("Projeto Blog Pessoal")
						.description("Projeto Blog Pessoal - Generation Brasil")
						.version("v0.0.1")
//Informações da Licença da API
						.license(new License()
								.name("Generation Brasil")
								.url("https://brazil.generation.org/"))
//Informações de contato de quem desenvolveu (só permite 1  c/)
						.contact(new Contact()
								.name("Generation Brasil")
								.url("https://github.com/conteudoGeneration")
								.email("conteudogeneration@generation.org")))
//Informações sobre documentação externa				
				.externalDocs(new ExternalDocumentation()
						.description("Github")
						.url("https://github.com/Yasmin-CCS"));
	}

	
//Método personaliza as Respostas HTTP	
@Bean
OpenApiCustomizer customerGlobalHeaderOpenApiCustomiser() {
//Cria um objeto da classe OpenAPI que gera a documentação no Swagger
	return openApi -> {
/*Cria um looping que lerá todos os recursos e retorna os endpoints
Cria outro looping que identifica o Metodo HTTP e coloca substituições para cada um*/
		openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations()
				.forEach(operation -> {
/*Cria o objeto que vai receber as respostas http*/
					
					ApiResponses apiResponses = operation.getResponses();
//Novas respostas
					apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
					apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido!"));
					apiResponses.addApiResponse("204", createApiResponse("Objeto Excluído!"));
					apiResponses.addApiResponse("400", createApiResponse("Erro na Requisição!"));
					apiResponses.addApiResponse("401", createApiResponse("Acesso Não Autorizado!"));
					apiResponses.addApiResponse("403", createApiResponse("Acesso Proibido!"));
					apiResponses.addApiResponse("404", createApiResponse("Objeto não Encontrado!"));
					apiResponses.addApiResponse("500", createApiResponse("Erro na Aplicação!"));
							
				}));
		
	};
}

private ApiResponse createApiResponse (String message) {
	
	return new ApiResponse().description(message);
}

}
