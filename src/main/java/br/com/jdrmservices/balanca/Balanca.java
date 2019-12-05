package br.com.jdrmservices.balanca;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@ComponentScan(basePackageClasses = { Balanca.class })
public class Balanca implements BalancaInterface {

	@Override
	public Optional<BigDecimal> getPesoBalanca() {
		return Optional.ofNullable(BigDecimal.ZERO);
	}

	@Override
	public boolean conectar() {
		// funcção conectar para obter retornos da balança		
		return false;
	}

	@Override
	public boolean desconectar() {
		// função desconectar a balança do sistema
		return false;
	}

	@Override
	public void configurar() {
		// configurar paramtetros de conexão da balança
	}	
}

/* Os comentários representam funcionalidades que devem ser implementadas ao sistema */