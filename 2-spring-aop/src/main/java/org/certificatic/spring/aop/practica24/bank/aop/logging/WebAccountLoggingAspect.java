package org.certificatic.spring.aop.practica24.bank.aop.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.certificatic.spring.aop.practica24.bank.app.model.Account;
import org.certificatic.spring.aop.util.bean.api.IColorWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

//Define el Bean como Aspecto
@Aspect
@Component("webAccountLoggingAspect")
@Slf4j
public class WebAccountLoggingAspect implements Ordered {

	private @Getter int order = 1;

	@Autowired
	private IColorWriter colorWriter;

	// Define Advice Before que intercepte webLayer() y cache los argumentos
	// (la cuenta debe especificar como nombre de parámetro "cuenta")
	@Before(value = "org.certificatic.spring.aop.practica24.bank.aop.PointcutDefinition.webLayer() "
			+ "&& args(cuenta, ..)", argNames = "cuenta")
	public void beforeAccountMethodExecutionAccount(JoinPoint jp, Account acc) {

		String method = jp.getSignature().getName();
		log.info("Inside accountWebView." + method + "(). Account: {}", acc.getAccountNumber());
	}

	// Define Advice Before que intercepte webLayer() y cache los argumentos
	// (el customer Id debe especificar como nombre de parámetro "id")
	@Before(value = "org.certificatic.spring.aop.practica24.bank.aop.PointcutDefinition.webLayer() "
			+ "&& args(id, ..)", argNames = "id")
	public void beforeAccountMethodExecutionLong(JoinPoint jp, Long numberId) {

		String method = jp.getSignature().getName();
		log.info("Inside accountWebView." + method + "(). Showing accounts for customer: {}", numberId);

	}
}
