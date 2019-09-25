--
-- Estrutura da tabela `categoria`
--

DROP TABLE IF EXISTS `categoria`;

CREATE TABLE IF NOT EXISTS `categoria` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;