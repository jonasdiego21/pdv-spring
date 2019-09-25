--
-- Estrutura da tabela `conta_receber_lancamento`
--

DROP TABLE IF EXISTS `conta_receber_lancamento`;

CREATE TABLE IF NOT EXISTS `conta_receber_lancamento` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_recebimento` date DEFAULT NULL,
  `total_recebido` decimal(19,2) DEFAULT NULL,
  `conta_receber_codigo` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK7ar5julv5x8j94cbystxx0c37` (`conta_receber_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;