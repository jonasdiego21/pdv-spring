--
-- Estrutura da tabela `item_venda`
--

DROP TABLE IF EXISTS `item_venda`;

CREATE TABLE IF NOT EXISTS `item_venda` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `quantidade` decimal(13,3) DEFAULT NULL,
  `valor_unitario` decimal(12,2) DEFAULT NULL,
  `produto_codigo` bigint(20) DEFAULT NULL,
  `venda_codigo` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK5hfw16v3yic7depx2e0qosqx1` (`produto_codigo`),
  KEY `FK2hga7ydmrd7vnndgv0k924l5r` (`venda_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;