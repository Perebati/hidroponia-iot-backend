CREATE TABLE usuario (
	user_id int primary key,
	nome varchar(128),
	email varchar(128),
	senha varchar(128)
)

CREATE TABLE cliente(
	cli_id int primary key,
	endereço varchar(128),
	data_nasc date,
	user_key int,
	foreign key (user_key) references usuario (user_id)
)

CREATE TABLE equipamento(
	equi_id int primary key,
	nome varchar(128),
	cli_id int,
	foreign key (cli_id) references cliente(cli_id)
)

CREATE TABLE ambiente_cli(
	amb_id int primary key,
	amb_nome varchar(128),
	ambCli_id int,
	foreign key (ambCli_id) references cliente (cli_id)
)

CREATE TABLE ambiente(
	tempAtual int,
	tempMin int,
	tempMax int,
	phAtual decimal(14,2),
	phTarget decimal(14,2),
	lumAtual int,
	amb_id int,
	dataRegistro date,
	horaRegistro time,
	foreign key (amb_id) references ambiente_cli (amb_id)
)

CREATE TABLE ph_log(
	phTarget decimal(14,2),
	dataRegistro date,
	horaRegistro time,
	amb_id int,
	cli_amb int,
	foreign key (amb_id) references ambiente_cli (amb_id),
	foreign key (cli_amb) references cliente(cli_id)
)
select * from temp_log
CREATE TABLE temp_log(
	tempMin int,
	tempMax int,
	dataRegistro date,
	horaRegistro time,
	amb_id int,
	cli_amb int,
	foreign key (amb_id) references ambiente_cli (amb_id),
	foreign key (cli_amb) references cliente(cli_id)
)

CREATE TABLE relatorio_log(
	cli_id int,
	amb_id int,
	dataRegistro date,
	horaRegistro time,
	foreign key (amb_id) references ambiente_cli (amb_id),
	foreign key (cli_id) references cliente(cli_id)
)
