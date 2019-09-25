--
-- Estrutura da tabela `grupo_permissoes`
--

DROP TABLE IF EXISTS `grupo_permissoes`;

CREATE TABLE IF NOT EXISTS `grupo_permissoes` (
  `codigo_grupo` bigint(20) NOT NULL,
  `codigo_permissao` bigint(20) NOT NULL,
  KEY `FK7vlthmg3pq6l7pbl860gfg1c1` (`codigo_permissao`),
  KEY `FK7lo3mm9yfyjw4hb8bfdct0y44` (`codigo_grupo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;