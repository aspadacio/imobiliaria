/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     21/07/2016 13:36:43                          */
/*==============================================================*/


drop table if exists IMOBILIARIA.TB_USUARIO;

drop table if exists IMOBILIARIA.TB_GRUPO;

drop table if exists IMOBILIARIA.TB_USUARIO_GRUPO;

drop table if exists IMOBILIARIA.TB_BAIRRO;

drop table if exists IMOBILIARIA.TB_CONTRATO;

drop table if exists IMOBILIARIA.TB_CONTRATO_MODIFICADOR;

drop table if exists IMOBILIARIA.TB_ENDERECO_PESSOA;

drop table if exists IMOBILIARIA.TB_IMOVEL;

drop table if exists IMOBILIARIA.TB_LOCADOR;

drop table if exists IMOBILIARIA.TB_LOCATARIO;

drop table if exists IMOBILIARIA.TB_MODIFICADOR;

drop table if exists IMOBILIARIA.TB_MUNICIPIO;

/*drop index IDX_PESSOA_JURIDICA on IMOBILIARIA.TB_PESSOA;*/

/*drop index IDX_PESSOA_FISICA on IMOBILIARIA.TB_PESSOA;*/

drop table if exists IMOBILIARIA.TB_PESSOA;

drop table if exists IMOBILIARIA.TB_PESSOA_FISICA;

drop table if exists IMOBILIARIA.TB_PESSOA_JURIDICA;

drop table if exists IMOBILIARIA.TB_PESSOA_TELEFONE;

/*==============================================================*/
/* User: IMOBILIARIA                                            */
/*==============================================================*/
/*create user IMOBILIARIA;*/

/*==============================================================*/
/* Table: TB_USUARIO                                              */
/*==============================================================*/
create table IMOBILIARIA.TB_USUARIO(
   ID_USUARIO bigint(20) not null auto_increment comment 'Chave prim�ria da tabela com numera��o autom�tica.',
   NOME varchar(100),
   EMAIL varchar(100),
   SENHA varchar(100),
   primary key (ID_USUARIO)
);

/*==============================================================*/
/* Table: TB_GRUPO                                              */
/*==============================================================*/
create table IMOBILIARIA.TB_GRUPO(
   ID_GRUPO bigint(20) not null auto_increment comment 'Chave prim�ria da tabela com numera��o autom�tica.',
   NOME varchar(100),
   DESCRICAO varchar(100),
   primary key (ID_GRUPO)
);

/*==============================================================*/
/* Table: TB_USUARIO_GRUPO                                      */
/*==============================================================*/
create table IMOBILIARIA.TB_USUARIO_GRUPO(
    USUARIO_ID bigint(20),
    GRUPO_ID bigint(20)
);

alter table IMOBILIARIA.TB_USUARIO_GRUPO add constraint fk_usuario_grupo_usuario foreign key (USUARIO_ID) references IMOBILIARIA.TB_USUARIO (ID_USUARIO);
alter table IMOBILIARIA.TB_USUARIO_GRUPO add constraint fk_usuario_grupo_grupo foreign key (GRUPO_ID) references IMOBILIARIA.TB_GRUPO (ID_GRUPO);

/*==============================================================*/
/* Table: TB_BAIRRO                                             */
/*==============================================================*/
create table IMOBILIARIA.TB_BAIRRO
(
   ID_BAIRRO            bigint(20) not null auto_increment comment 'Chave prim�ria da tabela com numera��o autom�tica.',
   ID_MUNICIPIO         bigint(20) not null comment 'FK que identifica o munic�pio e UF, na tabela TB_MUNICIPIO.',
   NO_BAIRRO            varchar(100) not null comment 'Nome do bairro.',
   primary key (ID_BAIRRO)
);

alter table IMOBILIARIA.TB_BAIRRO comment 'Tabela que armazena os bairros.
A constraint �nica UK_';

/*==============================================================*/
/* Table: TB_CONTRATO                                           */
/*==============================================================*/
create table IMOBILIARIA.TB_CONTRATO
(
   ID_CONTRATO          bigint(20) not null auto_increment comment 'Chave prim�ria da tabela com numera��o autom�tica.',
   ID_LOCATARIO         bigint(20) not null comment 'FK que identifica o locat�rio na tabela TB_LOCATARIO.',
   ID_LOCADOR           bigint(20) not null comment 'FK que identifica o locador na tabela TB_LOCADOR.',
   ID_PESSOA_FIADOR     bigint(20) not null comment 'FK que identifica o fiador na tabela TB_PESSOA, porque pode ser pessoa F�sica ou Jur�dica.',
   DT_INICIO            datetime not null comment 'Data de in�cio do contrato.',
   NU_DURACAO           int(2) not null comment 'Dura��o do contrato em meses.',
   NU_DIA_VENCIMENTO    int(2) not null comment 'N�mero do dia do vencimento.',
   TX_MULTA_POR_ATRASO  decimal(5,2) not null comment 'Valor percentual de multa por atraso.',
   NU_PARCELA_ANTERIOR  int(2) not null comment 'N�mero da �ltima parcela paga.',
   TX_COMISSAO          decimal(5,2) not null comment 'Valor percentual da comiss�o do escrit�rio.',
   ST_CONTRATO_ATIVO    char(1) not null comment 'Indica se o contrato est� ativo. S=Sim; N=N�o',
   primary key (ID_CONTRATO)
);

alter table IMOBILIARIA.TB_CONTRATO comment 'Tabela que cont�m os contratos de loca��o de im�vel.';

/*==============================================================*/
/* Table: TB_CONTRATO_MODIFICADOR                               */
/*==============================================================*/
create table IMOBILIARIA.TB_CONTRATO_MODIFICADOR
(
   ID_MODIFICADOR       bigint(20) not null comment 'FK que identifica o modificador na tabela TB_MODIFICADOR. Parte da chave prim�ria da tabela.',
   ID_CONTRATO          bigint(20) not null comment 'FK que identifica o contrato na tabela TB_CONTRATO. Parte da chave prim�ria da tabela.',
   NU_MES_ANO_INICIAL   varchar(6) not null comment 'N�mero do M�S e ANO referentes ao per�odo inicial do reajuste impactante. N�o possui m�scara.',
   NU_MES_ANO_FINAL     varchar(6) not null comment 'N�mero do M�S e ANO referentes ao per�odo final do reajuste impactante. N�o possui m�scara.',
   TX_REAJUSTE          decimal(5,2) comment 'Valor percentual referente ao reajuste, quando for uma Receita.
            Esta coluna ser� obrigat�ria quando o valor da coluna TP_MODIFICADOR for igual a "R". Esta restri��o � controlada pela CK_TBDESPESACONTRATO_TPMODIFIC.',
   VL_VALOR             decimal(8,2) not null comment 'Valor do item modificador da mensalidade do contrato.',
   TP_MODIFICADOR       char(1) not null comment 'Tipo do modificador. Dom�nio: R=Receita; D=Despesa.',
   primary key (ID_MODIFICADOR, ID_CONTRATO)
);

alter table IMOBILIARIA.TB_CONTRATO_MODIFICADOR comment 'Tabela de controle de despesas e receitas relativos aos cont';

/*==============================================================*/
/* Table: TB_ENDERECO_PESSOA                                    */
/*==============================================================*/
create table IMOBILIARIA.TB_ENDERECO_PESSOA
(
   ID_ENDERECO_PESSOA   bigint(20) not null auto_increment comment 'Chave prim�ria da tabela com numera��o autom�tica.',
   ID_PESSOA            bigint(20) not null comment 'FK que identifica a pessoa na tabela TB_PESSOA.',
   ID_MUNICIPIO         bigint(20) not null comment 'FK que identifica o munic�pio e UF na tabela TB_MUNICIPIO.',
   ID_BAIRRO            bigint(20) comment 'FK que identifica o bairro na tabela TB_BAIRRO. O relacionamento � atrav�s da UK_BAIRRO_MUNICIPIO para garantir que o munic�pio seja obrigatoriamente o mesmo do endere�o.',
   NU_CEP               int(8) not null comment 'N�mero do CEP do endere�o da pessoa.',
   DS_ENDERECO          varchar(200) not null comment 'Descri��o do endere�o da pessoa.',
   NU_ENDERECO          int(5) not null comment 'N�mero da casa, apartamento, lote, etc.',
   DS_COMPLEMENTO       varchar(200) comment 'Descri��o do complemento, informa��o extra para identifica��o do endere�o.',
   NU_DDD               varchar(2) comment 'N�mero do DDD do telefone, referente ao endere�o.',
   NU_TELEFONE          int(9) comment 'N�mero do telefone referente ao endere�o.',
   TP_ENDERECO          char(1) not null default 'R' comment 'Tipo do endere�o da pessoa. Dom�nio: R=Residencia; C=Cobran�a.',
   primary key (ID_ENDERECO_PESSOA)
);

alter table IMOBILIARIA.TB_ENDERECO_PESSOA comment 'Tabela que armazena os endere�os da pessoa.';

/*==============================================================*/
/* Table: TB_IMOVEL                                             */
/*==============================================================*/
create table IMOBILIARIA.TB_IMOVEL
(
   ID_IMOVEL            bigint(20) not null auto_increment comment 'Chave prim�ria da tabela com numera��o autom�tica.',
   ID_LOCATARIO         bigint(20) not null comment 'FK que identifica o locat�rio na tabela TB_LOCATARIO.',
   ID_MUNICIPIO         bigint(20) not null comment 'FK que identifica o munic�pio e UF na tabela TB_MUNICIPIO.',
   ID_BAIRRO            bigint(20) comment 'FK que identifica o bairro na tabela TB_BAIRRO. O relacionamento � atrav�s da UK_BAIRRO_MUNICIPIO para garantir que o munic�pio seja obrigatoriamente o mesmo do endere�o.',
   DS_IMOVEL            varchar(200) not null comment 'Descri��o do im�vel.',
   NU_CEP               varchar(8) not null comment 'N�mero do CEP do endere�o do im�vel.',
   DS_ENDERECO          varchar(200) not null comment 'Descri��o do endere�o do im�vel.',
   NU_ENDERECO          int(6) not null comment 'N�mero da casa, apartamento, lote, etc.',
   DS_COMPLEMENTO       varchar(200) comment 'Descri��o do complemento, informa��o extra para identifica��o do endere�o.',
   DS_OBSERVACOES       varchar(2000) comment 'Observa��es gerais sobre o im�vel.',
   primary key (ID_IMOVEL)
);

alter table IMOBILIARIA.TB_IMOVEL comment 'Tabela que cont�m os im�veis utilizados nas loca��es.';

/*==============================================================*/
/* Table: TB_LOCADOR                                            */
/*==============================================================*/
create table IMOBILIARIA.TB_LOCADOR
(
   ID_LOCADOR           bigint(20) not null auto_increment comment 'Chave prim�ria da tabela com numera��o autom�tica.',
   ID_PESSOA            bigint(20) not null comment 'FK da pessoa relacionada com o locat�rio, na tabela TB_PESSOA.',
   DT_CADASTRO          datetime not null comment 'Data do cadastro do locador.',
   primary key (ID_LOCADOR)
);

alter table IMOBILIARIA.TB_LOCADOR comment 'Tabela que cont�m os locadores.';

/*==============================================================*/
/* Table: TB_LOCATARIO                                          */
/*==============================================================*/
create table IMOBILIARIA.TB_LOCATARIO
(
   ID_LOCATARIO         bigint(20) not null auto_increment comment 'Chave prim�ria da tabela com numera��o autom�tica.',
   ID_PESSOA            bigint(20) not null comment 'FK da pessoa relacionada com o locat�rio, na tabela TB_PESSOA.',
   DT_CADASTRO          datetime not null comment 'Data do cadastro do locat�rio.',
   primary key (ID_LOCATARIO)
);

alter table IMOBILIARIA.TB_LOCATARIO comment 'Tabela que armazena os locat�rios.';

/*==============================================================*/
/* Table: TB_MODIFICADOR                                        */
/*==============================================================*/
create table IMOBILIARIA.TB_MODIFICADOR
(
   ID_MODIFICADOR       bigint(20) not null auto_increment comment 'Chave prim�ria da tabela com numera��o autom�tica.',
   NO_MODIFICADOR       varchar(100) not null comment 'Nome do modificador.',
   DS_MODIFICADOR       varchar(2000) comment 'Descri��o detalhada do modificador.',
   primary key (ID_MODIFICADOR)
);

alter table IMOBILIARIA.TB_MODIFICADOR comment 'Tabela de dom�nio que cont�m os modificadores poss�veis de s';

/*==============================================================*/
/* Table: TB_MUNICIPIO                                          */
/*==============================================================*/
create table IMOBILIARIA.TB_MUNICIPIO
(
   ID_MUNICIPIO         bigint(20) not null auto_increment comment 'Chave prim�ria da tabela com numera��o autom�tica.',
   NO_MUNICIPIO         varchar(100) not null comment 'Nome do munic�pio.',
   SG_UF                char(2) not null comment 'Sigla da Unidade Federativa.',
   primary key (ID_MUNICIPIO)
);

alter table IMOBILIARIA.TB_MUNICIPIO comment 'Tabela que armazena os munic�pios.';

/*==============================================================*/
/* Table: TB_PESSOA                                             */
/*==============================================================*/
create table IMOBILIARIA.TB_PESSOA
(
   ID_PESSOA            bigint(20) not null auto_increment comment 'Chave prim�ria da tabela com numera��o autom�tica.',
   NU_CNPJ              varchar(14) comment 'N�mero do CNPJ. Identifica a pessoa juridica. N�o � obrigat�rio porque � gerenciado pela constraint CK_PESSOA_FISICA_JURIDICA e �ndice IDX_PESSOA_JURIDICA.',
   NU_CPF               varchar(11) comment 'N�mero do CPF. Identifica a pessoa f�sica. N�o � obrigat�rio porque � gerenciado pela constraint CK_PESSOA_FISICA_JURIDICA e �ndice IDX_PESSOA_FISICA.',
   DS_EMAIL             varchar(200) not null comment 'E-mail para contato com a pessoa.',
   DT_ULTIMA_ALTERACAO  datetime not null comment 'Data da �ltima altera��o.',
   DS_OBSERVACAO        varchar(2000) comment 'Observa��es gerais da pessoa.',
   primary key (ID_PESSOA),
   check (( NU_CNPJ IS NULL AND NU_CPF IS NOT NULL) OR ( NU_CNPJ IS NOT NULL AND NU_CPF IS NULL))
);

alter table IMOBILIARIA.TB_PESSOA comment 'Tabela principal que identifica as pessoas dentro do modelo.';

/*==============================================================*/
/* Index: IDX_PESSOA_FISICA                                     */
/*==============================================================*/
/*create unique index IDX_PESSOA_FISICA on IMOBILIARIA.TB_PESSOA(NU_CPF);*/

/*==============================================================*/
/* Index: IDX_PESSOA_JURIDICA                                   */
/*==============================================================*/
/*create unique index IDX_PESSOA_JURIDICA on IMOBILIARIA.TB_PESSOA(NU_CNPJ);*/

/*==============================================================*/
/* Table: TB_PESSOA_FISICA                                      */
/*==============================================================*/
create table IMOBILIARIA.TB_PESSOA_FISICA
(
   NU_CPF               varchar(11) not null comment 'N�mero do CPF. Identifica a pessoa f�sica como �nica.',
   NO_PESSOA_FISICA     varchar(200) not null comment 'Nome da pessoa f�sica.',
   primary key (NU_CPF)
);

alter table IMOBILIARIA.TB_PESSOA_FISICA comment 'Tabela que cont�m as dados exclusivos � pessoas f�sicas.';

/*==============================================================*/
/* Table: TB_PESSOA_JURIDICA                                    */
/*==============================================================*/
create table IMOBILIARIA.TB_PESSOA_JURIDICA
(
   NU_CNPJ              varchar(14) not null comment 'N�mero do CNPJ. Identifica a pessoa juridica.',
   NO_RAZAO_SOCIAL      varchar(200) not null comment 'Nome da Raz�o Social da Pessoa Jur�dica.',
   NO_FANTASIA          varchar(200) not null comment 'Nome Fantasia da Pessoa Jur�dica.',
   NU_INSCRICAO_ESTADUAL varchar(13) not null comment 'N�mero da Inscri��o Estadual da empresa.',
   NO_CONTATO           varchar(200) not null comment 'Nome do contato, principal respons�vel para tratar qualquer assunto com a empresa.',
   primary key (NU_CNPJ)
);

alter table IMOBILIARIA.TB_PESSOA_JURIDICA comment 'Tabela que cont�m as dados exclusivos � pessoas jur�dicas.';

/*==============================================================*/
/* Table: TB_PESSOA_TELEFONE                                    */
/*==============================================================*/
create table IMOBILIARIA.TB_PESSOA_TELEFONE
(
   ID_PESSOA_TELEFONE   bigint(20) not null auto_increment comment 'Chave prim�ria da tabela com numera��o autom�tica.',
   ID_PESSOA            bigint(20) not null comment 'FK da tabela TB_PESSOA.',
   NU_TELEFONE_DDD      varchar(2) not null comment 'N�mero do DDD do telefone.',
   NU_TELEFONE          int(9) not null comment 'N�mero do telefone sem m�scara.',
   TP_TELEFONE          char(1) not null comment 'Identifica o tipo do telefone. Dom�nio: P=Principal; F=Fax; C=Celular ',
   primary key (ID_PESSOA_TELEFONE)
);

alter table IMOBILIARIA.TB_PESSOA_TELEFONE comment 'Tabela que cont�m os telefones da pessoa.';

alter table IMOBILIARIA.TB_BAIRRO add constraint FK_BAIRRO_MUNICIPIO foreign key (ID_MUNICIPIO)
      references IMOBILIARIA.TB_MUNICIPIO (ID_MUNICIPIO) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_CONTRATO add constraint FK_CONTRATO_LOCADOR foreign key (ID_LOCADOR)
      references IMOBILIARIA.TB_LOCADOR (ID_LOCADOR) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_CONTRATO add constraint FK_CONTRATO_LOCATARIO foreign key (ID_LOCATARIO)
      references IMOBILIARIA.TB_LOCATARIO (ID_LOCATARIO) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_CONTRATO add constraint FK_CONTRATO_PESSOA foreign key (ID_PESSOA_FIADOR)
      references IMOBILIARIA.TB_PESSOA (ID_PESSOA) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_CONTRATO_MODIFICADOR add constraint FK_DESPESACONTRATO_CONTRATO foreign key (ID_CONTRATO)
      references IMOBILIARIA.TB_CONTRATO (ID_CONTRATO) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_CONTRATO_MODIFICADOR add constraint FK_DESPESACONTRATO_MODIFICADOR foreign key (ID_MODIFICADOR)
      references IMOBILIARIA.TB_MODIFICADOR (ID_MODIFICADOR) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_ENDERECO_PESSOA add constraint FK_ENDERECOPESSOA_BAIRRO foreign key (ID_BAIRRO)
      references IMOBILIARIA.TB_BAIRRO (ID_BAIRRO) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_ENDERECO_PESSOA add constraint FK_ENDERECOPESSOA_MUNICIPIO foreign key (ID_MUNICIPIO)
      references IMOBILIARIA.TB_MUNICIPIO (ID_MUNICIPIO) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_ENDERECO_PESSOA add constraint FK_ENDERECOPESSOA_PESSOA foreign key (ID_PESSOA)
      references IMOBILIARIA.TB_PESSOA (ID_PESSOA) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_IMOVEL add constraint FK_IMOVEL_BAIRRO foreign key (ID_BAIRRO)
      references IMOBILIARIA.TB_BAIRRO (ID_BAIRRO) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_IMOVEL add constraint FK_IMOVEL_LOCATARIO foreign key (ID_LOCATARIO)
      references IMOBILIARIA.TB_LOCATARIO (ID_LOCATARIO) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_IMOVEL add constraint FK_IMOVEL_MUNICIPIO foreign key (ID_MUNICIPIO)
      references IMOBILIARIA.TB_MUNICIPIO (ID_MUNICIPIO) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_LOCADOR add constraint FK_LOCADOR_PESSOA foreign key (ID_PESSOA)
      references IMOBILIARIA.TB_PESSOA (ID_PESSOA) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_LOCATARIO add constraint FK_LOCATARIO_PESSOA foreign key (ID_PESSOA)
      references IMOBILIARIA.TB_PESSOA (ID_PESSOA) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_PESSOA add constraint FK_PESSOA_PESSOAFISICA foreign key (NU_CPF)
      references IMOBILIARIA.TB_PESSOA_FISICA (NU_CPF) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_PESSOA add constraint FK_PESSOA_PESSOAJURIDICA foreign key (NU_CNPJ)
      references IMOBILIARIA.TB_PESSOA_JURIDICA (NU_CNPJ) on delete restrict on update restrict;

alter table IMOBILIARIA.TB_PESSOA_TELEFONE add constraint FK_PESSOATELEFONE_PESSOA foreign key (ID_PESSOA)
      references IMOBILIARIA.TB_PESSOA (ID_PESSOA) on delete restrict on update restrict;
