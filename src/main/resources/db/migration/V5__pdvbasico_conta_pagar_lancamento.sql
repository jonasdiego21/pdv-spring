--
-- Estrutura da tabela `conta_pagar_lancamento`
--

DROP TABLE IF EXISTS `conta_pagar_lancamento`;

CREATE TABLE IF NOT EXISTS `conta_pagar_lancamento` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_pagamento` date DEFAULT NULL,
  `total_pago` decimal(19,2) DEFAULT NULL,
  `conta_pagar_codigo` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FKj3vit8ddc1kxruy91nc0eoveh` (`conta_pagar_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;