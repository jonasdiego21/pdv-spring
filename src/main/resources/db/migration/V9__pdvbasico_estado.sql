--
-- Estrutura da tabela `estado`
--

DROP TABLE IF EXISTS `estado`;

CREATE TABLE IF NOT EXISTS `estado` (
  `codigo` bigint(20) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `sigla` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;