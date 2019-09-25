--
-- Estrutura da tabela `usuario`
--

DROP TABLE IF EXISTS `usuario`;

CREATE TABLE IF NOT EXISTS `usuario` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `ativo` bit(1) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `codigo_grupo` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK55cw96aj2dhpy1pn9as3ktda3` (`codigo_grupo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;