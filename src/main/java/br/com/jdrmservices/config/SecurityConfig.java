package br.com.jdrmservices.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/categorias/novo").hasAuthority("CADASTRAR_CATEGORIA")
				.antMatchers("/clientes/novo").hasAuthority("CADASTRAR_CLIENTE")
				.antMatchers("/contaspagar/novo").hasAuthority("CADASTRAR_CONTA_PAGAR")
				.antMatchers("/contasreceber/novo").hasAuthority("CADASTRAR_CONTA_RECEBER")
				.antMatchers("/contaspagarlancamento/novo").hasAuthority("CADASTRAR_CONTA_PAGAR_LANCAMENTO")
				.antMatchers("/contasreceberlancamento/novo").hasAuthority("CADASTRAR_CONTA_RECEBER_LANCAMENTO")
				.antMatchers("/empresas/novo").hasAuthority("CADASTRAR_EMPRESA")
				.antMatchers("/fornecedores/novo").hasAuthority("CADASTRAR_FORNECEDOR")
				.antMatchers("/funcionarios/novo").hasAuthority("CADASTRAR_FUNCIONARIO")
				.antMatchers("/produtos/novo").hasAuthority("CADASTRAR_PRODUTO")
				.antMatchers("/produtos/entrada").hasAuthority("CADASTRAR_PRODUTO")
				.antMatchers("/usuarios/novo").hasAuthority("CADASTRAR_USUARIO")
				.antMatchers("/vendas/novo").hasAuthority("CADASTRAR_VENDA")
				.antMatchers("/vendas/finalizarVendaPdv").hasAuthority("CADASTRAR_VENDA")
				.antMatchers("/relatorios").hasAuthority("EMITIR_RELATORIO")
				.antMatchers("/caixas/novo").hasAuthority("CADASTRAR_CAIXA")
				.antMatchers("/emprestimos/novo").hasAuthority("CADASTRAR_EMPRESTIMO")
				
				.antMatchers("/categorias").hasAuthority("PESQUISAR_CATEGORIA")
				.antMatchers("/clientes").hasAuthority("PESQUISAR_CLIENTE")
				.antMatchers("/contaspagar").hasAuthority("PESQUISAR_CONTA_PAGAR")
				.antMatchers("/contasreceber").hasAuthority("PESQUISAR_CONTA_RECEBER")
				.antMatchers("/contaspagarlancamento").hasAuthority("PESQUISAR_CONTA_PAGAR_LANCAMENTO")
				.antMatchers("/contasreceberlancamento").hasAuthority("PESQUISAR_CONTA_RECEBER_LANCAMENTO")
				.antMatchers("/empresas").hasAuthority("PESQUISAR_EMPRESA")
				.antMatchers("/fornecedores").hasAuthority("PESQUISAR_FORNECEDOR")
				.antMatchers("/funcionarios").hasAuthority("PESQUISAR_FUNCIONARIO")
				.antMatchers("/produtos").hasAuthority("PESQUISAR_PRODUTO")
				.antMatchers("/usuarios").hasAuthority("PESQUISAR_USUARIO")
				.antMatchers("/vendas").hasAuthority("PESQUISAR_VENDA")
				.antMatchers("/caixas").hasAuthority("PESQUISAR_CAIXA")
				.antMatchers("/emprestimos").hasAuthority("PESQUISAR_EMPRESTIMO")
				
				.antMatchers("/dashboard").hasAuthority("DASHBOARD")
				.anyRequest().authenticated()
			.and()
				.formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll()
			.and()
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.and()
				.exceptionHandling().accessDeniedPage("/403")
			.and()
				//.csrf().disable()
				.sessionManagement()
					.maximumSessions(500).expiredUrl("/login");
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring()
				.antMatchers("/js/**", "/css/**", "/images/**", "/vendor/**");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
