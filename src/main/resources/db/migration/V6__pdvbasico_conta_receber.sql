--
-- Estrutura da tabela `conta_receber`
--

DROP TABLE IF EXISTS `conta_receber`;

CREATE TABLE IF NOT EXISTS `conta_receber` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_vencimento` date DEFAULT NULL,
  `data_venda` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `total_recebido` decimal(19,2) DEFAULT NULL,
  `total_venda` decimal(19,2) DEFAULT NULL,
  `cliente_codigo` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK277kbtr0sborb3u80vwy0plc5` (`cliente_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;