-- INSERT GRUPOS --
insert into grupo (nome) values ('ADMINISTRADOR');
insert into grupo (nome) values ('VENDEDOR');

-- INSERT PERMISSÕES --
insert into permissao (nome) values ('CADASTRAR_CATEGORIA');
insert into permissao (nome) values ('CADASTRAR_CLIENTE');
insert into permissao (nome) values ('CADASTRAR_CONTA_PAGAR');
insert into permissao (nome) values ('CADASTRAR_CONTA_RECEBER');
insert into permissao (nome) values ('CADASTRAR_CONTA_PAGAR_LANCAMENTO');
insert into permissao (nome) values ('CADASTRAR_CONTA_RECEBER_LANCAMENTO');
insert into permissao (nome) values ('CADASTRAR_EMPRESA');
insert into permissao (nome) values ('CADASTRAR_FORNECEDOR');
insert into permissao (nome) values ('CADASTRAR_FUNCIONARIO');
insert into permissao (nome) values ('CADASTRAR_PRODUTO');
insert into permissao (nome) values ('CADASTRAR_USUARIO');
insert into permissao (nome) values ('CADASTRAR_VENDA');
insert into permissao (nome) values ('EMITIR_RELATORIO');
insert into permissao (nome) values ('CADASTRAR_CAIXA');
-- -------------------------------------------------------- --
insert into permissao (nome) values ('PESQUISAR_CATEGORIA');
insert into permissao (nome) values ('PESQUISAR_CLIENTE');
insert into permissao (nome) values ('PESQUISAR_CONTA_PAGAR');
insert into permissao (nome) values ('PESQUISAR_CONTA_RECEBER');
insert into permissao (nome) values ('PESQUISAR_CONTA_PAGAR_LANCAMENTO');
insert into permissao (nome) values ('PESQUISAR_CONTA_RECEBER_LANCAMENTO');
insert into permissao (nome) values ('PESQUISAR_EMPRESA');
insert into permissao (nome) values ('PESQUISAR_FORNECEDOR');
insert into permissao (nome) values ('PESQUISAR_FUNCIONARIO');
insert into permissao (nome) values ('PESQUISAR_PRODUTO');
insert into permissao (nome) values ('PESQUISAR_USUARIO');
insert into permissao (nome) values ('PESQUISAR_VENDA');
insert into permissao (nome) values ('PESQUISAR_CAIXA');
insert into permissao (nome) values ('DASHBOARD');
insert into permissao (nome) values ('PESQUISAR_VENDA_ADMIN');
insert into permissao (nome) values ('CADASTRAR_EMPRESTIMO');
insert into permissao (nome) values ('PESQUISAR_EMPRESTIMO');

-- INSERT GRUPO_PERMISSÕES --
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (2, 1);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (2, 4);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (2, 6);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (2, 8);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (2, 10);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (2, 12);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (2, 15);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (2, 18);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (2, 20);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (2, 22);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (2, 24);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (2, 26);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (2, 28);
-- ----------------------------------------------------------------------- --
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 1);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 2);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 3);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 4);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 5);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 6);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 7);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 8);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 9);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 10);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 11);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 12);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 13);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 14);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 15);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 16);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 17);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 18);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 19);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 20);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 21);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 22);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 23);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 24);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 25);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 26);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 27);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 28);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 29);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 30);
insert into grupo_permissoes (codigo_grupo, codigo_permissao) values (1, 31);

-- INSERT USUARIO ADMIN --
insert into pdvbasico.usuario (nome, email, senha, ativo, codigo_grupo) values ('Administrador', 'admin@localhost', '$2a$10$VG0VxfwUKhzGZn7R7NWa9e0qA/jv03tCbs.9DADeb0KWcmQPRzEnS', 1, 1);