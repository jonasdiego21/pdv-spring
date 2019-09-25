--
-- Estrutura da tabela `cliente`
--

DROP TABLE IF EXISTS `cliente`;

CREATE TABLE IF NOT EXISTS `cliente` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `bairro` varchar(255) DEFAULT NULL,
  `complemento` varchar(255) DEFAULT NULL,
  `cpf` varchar(255) DEFAULT NULL,
  `data_nascimento` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `estado` tinyblob NOT NULL,
  `limite_compra` decimal(19,2) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `rua` varchar(255) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  `cidade_codigo` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK217jpcwxw6r3770uvvn1imott` (`cidade_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;