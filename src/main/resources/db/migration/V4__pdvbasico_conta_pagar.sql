--
-- Estrutura da tabela `conta_pagar`
--

DROP TABLE IF EXISTS `conta_pagar`;

CREATE TABLE IF NOT EXISTS `conta_pagar` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_compra` date DEFAULT NULL,
  `data_vencimento` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `total_compra` decimal(19,2) DEFAULT NULL,
  `total_pago` decimal(19,2) DEFAULT NULL,
  `fornecedor_codigo` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK9melmx9kpvxpxj4jg31r3vn04` (`fornecedor_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;