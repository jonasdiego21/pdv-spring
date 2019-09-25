--
-- Estrutura da tabela `empresa`
--

DROP TABLE IF EXISTS `empresa`;

CREATE TABLE IF NOT EXISTS `empresa` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `bairro` varchar(255) DEFAULT NULL,
  `cnpj` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `estado` tinyblob NOT NULL,
  `nome` varchar(24) DEFAULT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `rua` varchar(255) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  `cidade_codigo` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FKc73qbmj46r1g9jvhnq0i2ypav` (`cidade_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;