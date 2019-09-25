--
-- Estrutura da tabela `municipio`
--

DROP TABLE IF EXISTS `municipio`;

CREATE TABLE IF NOT EXISTS `municipio` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `codigo_estado` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK9fw9jwteh8nwic84s5hj7trg7` (`codigo_estado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;