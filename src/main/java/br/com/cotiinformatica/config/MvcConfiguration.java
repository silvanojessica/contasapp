package br.com.cotiinformatica.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.cotiinformatica.cache.CacheControl;
import br.com.cotiinformatica.repositories.ContaRepository;
import br.com.cotiinformatica.repositories.UsuarioRepository;

@Configuration
@ComponentScan(basePackages = "br.com.cotiinformatica")
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter {

	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	/*
	 * Adicionando a configuração para conexão com o banco de dados do projeto (DATA
	 * SOURCE)
	 */
	@Bean
	public DataSource getDataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5433/bd_contasapp");
		dataSource.setUsername("postgres");
		dataSource.setPassword("coti");

		return dataSource;
	}

	/*
	 * Adicionando a configuração para o UsuarioRepository
	 */
	@Bean
	public UsuarioRepository getUsuarioRepository() {
		// retornando uma instância da classe UsuarioRepository
		// passsando para o construtor da classe o DATA SOURCE
		return new UsuarioRepository(getDataSource());
	}

	/*
	 * Adicionando a configuração para o ContaRepository
	 */
	@Bean
	public ContaRepository getContaRepository() {
		// retornando uma instância da classe ContaRepository
		// passsando para o construtor da classe o DATA SOURCE
		return new ContaRepository(getDataSource());
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CacheControl());
	}
}
