drop database if exists sigem;
create database sigem;
use sigem;

create table sala(
salid int primary key auto_increment,
salnome varchar(20) not null,
salativa boolean default true
);
insert into sala(salnome) values('LAB V'), ('LAB VII');


create table materia(
matid int primary key auto_increment,
matnome varchar(50) not null,
matativa boolean default true
);
insert into materia(matnome) values('Indefinido'),('ATP'), ('ED');


create table horario(
horhora varchar(15) primary key
);
insert into horario values ('11:00-12:00'), ('12:00-13:00'), ('13:00-14:00'), ('14:00-15:00'), ('15:00-16:00');

# Isso sim é uma modelo de verdade.
#Nao coloca só o horario inicial
# coloca os dois assim `11:00-12:00`
--                   -Neto, Izaltino

create table diadasemana(
diaid int primary key auto_increment,
dianome varchar (20) not null
);
insert into diadasemana(dianome) values ('Segunda'), ('Terça'), ('Quarta'), ('Quinta'), ('Sexta');

create table usuario(
usucpf varchar(15) primary key,
usunome varchar(50) not null,
usulogin varchar(20) not null,
ususenha varchar(50) not null
);
insert into usuario values('','Indefinido', '', ''),
						('111.111.111-11', 'Sandro Haiden Teixeira', 'sandro.teixeira', 'ifam123'),
						('222.222.222-22', 'Izaltino Viana Neto', 'izaltino.neto', 'ifam123'),
						('333.333.333-33', 'Ruy de Ascenção Neto', 'ruy.neto', 'ifam123'),
						('444.444.444-44', 'Estaife Lima', 'estaife.lima', 'ifam123'), 
						('555.555.555-55', 'Gustavo Rocha', 'gustavo.rocha', 'ifam123');
                        
update usuario set ususenha = 'tads' where usucpf='111.111.111-11';

create table funcao(
fcoid int primary key auto_increment,
fconome varchar(20) not null
);
insert into funcao(fconome) values('Aluno'), ('Coordenador'), ('Monitor');

create table tipousuario(
tususucpf varchar(15) not null,
tusfcoid int not null,
tusdatainicio date not null,
tusdatafim date default null,
tusmatid int,
foreign key (tususucpf) references usuario(usucpf),
foreign key (tusfcoid) references funcao(fcoid),
foreign key (tusmatid) references materia(matid),
primary key(tususucpf,tusfcoid, tusdatainicio)
);
insert into tipousuario (tususucpf, tusfcoid, tusdatainicio, tusmatid) 
values ('111.111.111-11', 1, '2018-10-17', null),
('222.222.222-22', 1, '2018-10-17', null),
('333.333.333-33', 1, '2018-10-17', null),
('444.444.444-44', 1, '2018-10-17', null),
('555.555.555-55', 1, '2018-10-17', null),
('444.444.444-44', 3, '2018-10-17', 3),
('555.555.555-55', 3, '2018-10-17', 2);

use sigem;
insert into tipousuario (tususucpf, tusfcoid, tusdatainicio, tusmatid) values('111.111.111-11', 3, '2018-10-17', 2);

insert into usuario(usucpf, usunome, usulogin, ususenha) select mpibd_2018.cliente.clicodigo, mpibd_2018.cliente.clinome, '', '' from mpibd_2018.cliente;
insert into tipousuario(tususucpf, tusfcoid, tusdatainicio, tusmatid) select usucpf, 1, curdate(), null from usuario;


create table monitoria(
miaid int primary key auto_increment,
miavagas tinyint default 12,
miasalid int not null,
miausucpf varchar(15) not null default '', 
miadiaid int not null,
miahorhora varchar(15) not null,
miadatainicio date not null,
miadatafim date,
miaativa boolean default true,
foreign key (miasalid) references sala(salid),
foreign key (miausucpf) references usuario(usucpf),
foreign key (miadiaid) references diadasemana(diaid),
foreign key (miahorhora) references horario(horhora)
);
insert into monitoria(miasalid,miadiaid,miausucpf,miahorhora, miadatainicio) values
(1, 1, '', '11:00-12:00', curdate()),
(1, 1, '', '12:00-13:00', curdate()),
(1, 2, '', '11:00-12:00', curdate()),
-- (2, 2, '555.555.555-55', '11:00-12:00', curdate()),
-- (2, 2, '555.555.555-55', '12:00-13:00', curdate()),
(1, 2, '444.444.444-44', '12:00-13:00', curdate());


create table inscricao(
insusucpf varchar(15) not null,
insmiaid int not null,
insdata date,
primary key (insusucpf, insmiaid),
foreign key (insusucpf) references usuario(usucpf),
foreign key (insmiaid) references monitoria(miaid)
);


-- Procedure que retorna o ID da aula da monitoria que está gerando conflito de horário durante a inscrição
-- MonitoriaDAO >>>> verificaConflito()
delimiter #
create function f_verificaconflito(p_miadiaid int, p_miahorhora varchar(15), p_alucpf varchar(15))returns int
begin
	declare v_retorno int default 0;
    
    set v_retorno=(select miaid from monitoria
					inner join inscricao on miaid = insmiaid
                    inner join diadasemana on miadiaid = diaid
                    where miadiaid = p_miadiaid and insusucpf = p_alucpf
                    and p_miahorhora = miahorhora);
	if(v_retorno is null)then
		set v_retorno=0;
	end if;
	return v_retorno;
end#
delimiter ;
select f_verificaconflito(2, '11:00-12:00', '111.111.111-11');

-- Procedure que retorna o ID da aula da monitoria que está gerando conflito de horário durante a reserva
-- de horário do monitor
-- MonitoriaDAO >>>> verificaConflitoMonitor()
delimiter #
create function f_verificaconflitomonitor(p_miadiaid int, p_miahorhora varchar(15), p_moncpf varchar(15))returns int
begin
	declare v_retorno int default 0;
    
    set v_retorno=(select miaid from monitoria
					where miadiaid = p_miadiaid
                    AND miahorhora = p_miahorhora
                    AND miausucpf = p_moncpf);
	if(v_retorno is null)then
		set v_retorno=0;
	end if;
	return v_retorno;
end#
delimiter ;
select f_verificaconflitomonitor(2, '11:00-12:00', '555.555.555-55');

delimiter #
create procedure sp_inserirmonitoria(p_miasalid int, p_miadiaid int, p_miahorhora varchar(20))
begin
	INSERT INTO monitoria(miasalid, miadiaid, miausucpf, miahorhora, miadatainicio) values
    (p_miasalid, p_miadiaid, '', p_miahorhora, curdate());
end#
delimiter ;

delimiter #
create procedure sp_excluirmonitoria(p_miaid int)
begin
	UPDATE monitoria SET miadatafim = curdate() WHERE miaid = p_miaid;
end#
delimiter ;


-- função que retorna quantas monitorias um determinado monitor tem
delimiter #
create function f_contmonitorias(moncpf varchar(15)) returns int
begin
	return (SELECT count(*) FROM monitoria WHERE miausucpf = moncpf);
end #
delimiter ;
select f_contmonitorias('555.555.555-55');

delimiter #
create procedure sp_consultamonitoria(p_miaid int)
begin
    SELECT salid, salnome, horhora, diaid, dianome,
    monitor.usucpf 'moncpf', monitor.usunome 'monnome',
    matid, matnome, miaid, miavagas
    FROM monitoria
	INNER JOIN sala ON salid = miasalid
	INNER JOIN horario ON horhora = miahorhora
	INNER JOIN diadasemana ON diaid = miadiaid
	INNER JOIN usuario monitor ON monitor.usucpf = miausucpf
    INNER JOIN tipousuario ON tususucpf = monitor.usucpf
    INNER JOIN materia ON matid = tusmatid
	WHERE miaid = p_miaid
	order by diaid asc, matnome asc, horhora asc;
end#
delimiter ;
call sp_consultamonitoria(5);


-- Procedure para consultar por sala quais aulas de monitorias existem
-- MonitoriaDAO >>>> consultarMonitoria() utilizando a sql
delimiter #
create procedure sp_consultamonitoriascoord(p_salnome varchar(20))
begin
	SELECT salid, salnome, horhora, diaid, dianome,
    monitor.usucpf 'moncpf', monitor.usunome 'monnome',
    matid, matnome, miaid, miavagas
    FROM monitoria
	INNER JOIN sala ON salid = miasalid
	INNER JOIN horario ON horhora = miahorhora
	INNER JOIN diadasemana ON diaid = miadiaid
	LEFT OUTER JOIN usuario monitor ON monitor.usucpf = miausucpf
    LEFT OUTER JOIN tipousuario ON tususucpf = monitor.usucpf
    LEFT OUTER JOIN funcao ON fcoid = tusfcoid
    LEFT OUTER JOIN materia on matid = tusmatid
	WHERE salnome LIKE p_salnome AND
    (fconome = 'Monitor' OR miausucpf = '') AND
    miadatafim IS NULL
    
	order by diaid asc, matnome asc, horhora asc;
end#
delimiter ;
call sp_consultamonitoriascoord('%_%');
select * from monitoria;


-- Procedure para consultar por matéria quais aulas de monitorias estão disponíveis para inscrição
-- MonitoriaDAO >>>> consultarMonitoria() utilizando a sql2
delimiter #
create procedure sp_consultamonitoriasdisponiveis(p_matnome varchar(50), p_alucpf varchar(15))
begin
    SELECT salid, salnome, horhora, diaid, dianome,
    monitor.usucpf 'moncpf', monitor.usunome 'monnome',
    matid, matnome, miaid, miavagas
    FROM monitoria
	INNER JOIN sala ON salid = miasalid
	INNER JOIN horario ON horhora = miahorhora
	INNER JOIN diadasemana ON diaid = miadiaid
	INNER JOIN usuario monitor ON monitor.usucpf = miausucpf
    INNER JOIN tipousuario ON tususucpf = monitor.usucpf
    INNER JOIN materia ON matid = tusmatid
	WHERE matnome LIKE p_matnome AND miausucpf != ''
    AND miavagas > 0 AND miadatafim IS NULL
    AND monitor.usucpf != p_alucpf
    AND miaid NOT IN (SELECT miaid FROM monitoria
						INNER JOIN inscricao ON insmiaid = miaid
                        WHERE insusucpf = p_alucpf)
	order by diaid asc, matnome asc, horhora asc;
end#
delimiter ;
call sp_consultamonitoriasdisponiveis('ATP', '111.111.111-11');


-- Procedure voltada ao aluno para consultar por matéria quais aulas de monitorias estou inscrito
-- MonitoriaDAO >>>> consultarMonitoria() utilizando a sql3
delimiter #
create procedure sp_consultamonitoriasinscrito(p_matnome varchar(50), p_alucpf varchar(15))
begin
	SELECT salid, salnome, horhora, diaid, dianome,
    monitor.usucpf 'moncpf', monitor.usunome 'monnome',
    matid, matnome, insusucpf 'insalucpf',
    miaid, miavagas
	FROM monitoria
	INNER JOIN sala ON salid = miasalid
	INNER JOIN horario ON horhora = miahorhora
	INNER JOIN diadasemana ON diaid = miadiaid
    INNER JOIN inscricao ON miaid = insmiaid
	INNER JOIN usuario aluno ON aluno.usucpf = insusucpf
	INNER JOIN usuario monitor ON monitor.usucpf = miausucpf
    INNER JOIN tipousuario ON tususucpf = miausucpf
    INNER JOIN materia ON matid = tusmatid
	
	WHERE matnome LIKE p_matnome AND insusucpf LIKE  p_alucpf
	order by diaid asc, matnome asc, horhora asc;
end#
delimiter ;
call sp_consultamonitoriasinscrito('ED', '111.111.111-11');


-- Procedure para consultar por matéria quais aulas de monitorias estou inscrito
-- MonitoriaDAO >>>> consultarMonitoria() utilizando a sql3
delimiter #
create procedure sp_consultamonitoriaslivres(p_salnome varchar(20))
begin
	SELECT salid, salnome, horhora, diaid, dianome,
    monitor.usucpf 'moncpf', monitor.usunome 'monnome',
    matid, matnome, insusucpf 'insalucpf',
    miaid, miavagas
    FROM monitoria
	INNER JOIN sala ON salid = miasalid
	INNER JOIN horario ON horhora = miahorhora
	INNER JOIN diadasemana ON diaid = miadiaid
	LEFT OUTER JOIN usuario monitor ON monitor.usucpf = miausucpf
    LEFT OUTER JOIN tipousuario ON tususucpf = monitor.usucpf
    LEFT OUTER JOIN funcao ON fcoid = tusfcoid
    LEFT OUTER JOIN materia on matid = tusmatid
    LEFT OUTER JOIN inscricao ON miaid = insmiaid
	WHERE salnome LIKE p_salnome
    AND monitor.usucpf = ''
    AND miadatafim IS NULL
	order by diaid asc, matnome asc, horhora asc;
end#
delimiter ;
call sp_consultamonitoriaslivres('LAB V');

delimiter #
create procedure sp_consultamonitoriasmonitor(p_salnome varchar(20), p_moncpf varchar(15))
begin
	SELECT salid, salnome, horhora, diaid, dianome,
    monitor.usucpf 'moncpf', monitor.usunome 'monnome',
    matid, matnome, miaid, miavagas
    FROM monitoria
	INNER JOIN sala ON salid = miasalid
	INNER JOIN horario ON horhora = miahorhora
	INNER JOIN diadasemana ON diaid = miadiaid
	INNER JOIN usuario monitor ON monitor.usucpf = miausucpf
    INNER JOIN tipousuario ON tususucpf = monitor.usucpf
    INNER JOIN funcao ON fcoid = tusfcoid
    INNER JOIN materia ON matid = tusmatid
	WHERE salnome LIKE p_salnome
    AND monitor.usucpf = p_moncpf    
	ORDER BY diaid ASC, matnome ASC, horhora ASC;
end#
delimiter ;
CALL  sp_consultamonitoriasmonitor('LAB VII', '555.555.555-55');

delimiter #
create procedure sp_consultaalunosmonitoria(p_miaid int)
begin
	SELECT usunome, insdata FROM inscricao
    INNER JOIN usuario ON usucpf = insusucpf
    WHERE insmiaid = p_miaid;
end#
delimiter ;

delimiter #
create procedure sp_alunocheckboxmarcado(p_miaid int, p_alucpf varchar(15))
begin
	declare v_alucpf varchar(15);
    
    set v_alucpf = (select insusucpf from inscricao where insmiaid = p_miaid and insusucpf = p_alucpf);
    
    if v_alucpf is null then
		INSERT INTO inscricao VALUES(p_alucpf, p_miaid, curdate());
        UPDATE monitoria SET miavagas = miavagas -1 WHERE miaid = p_miaid;
	end if;
end #
delimiter ;


delimiter #
create procedure sp_alunocheckboxdesmarcado(p_miaid int, p_alucpf varchar(15))
begin
	declare v_alucpf varchar(15);
    
    set v_alucpf = (select insusucpf from inscricao where insmiaid = p_miaid and insusucpf = p_alucpf);
    
    if v_alucpf = p_alucpf then
		DELETE FROM inscricao WHERE p_miaid = insmiaid AND insusucpf = p_alucpf;
        UPDATE monitoria SET miavagas = miavagas + 1 WHERE miaid = p_miaid;
	end if;
end #
delimiter ;

delimiter #
create procedure sp_monitorcheckboxmarcado(p_miaid int, p_moncpf varchar(15))
begin
	declare v_moncpf varchar(15);
    
    set v_moncpf = (SELECT miausucpf FROM monitoria WHERE miaid = p_miaid);
    
    if v_moncpf != p_moncpf then
		UPDATE monitoria SET miausucpf = p_moncpf WHERE miaid = p_miaid;
	end if;
end#
delimiter ;

delimiter #
create procedure sp_monitorcheckboxdesmarcado(p_miaid int, p_moncpf varchar(15))
begin
	declare v_moncpf varchar(15);
    
    set v_moncpf = (SELECT miausucpf FROM monitoria WHERE miaid = p_miaid);
    
    if v_moncpf = p_moncpf then
		UPDATE monitoria SET miausucpf = '' WHERE miaid = p_miaid;
	end if;
end#
delimiter ;

delimiter #
create procedure sp_consultaalunos()
begin
	SELECT usucpf, usunome FROM usuario
    INNER JOIN tipousuario ON tususucpf = usucpf
    INNER JOIN funcao ON fcoid = tusfcoid
    WHERE usucpf NOT IN (
						SELECT tususucpf FROM tipousuario
                        INNER JOIN funcao ON fcoid = tusfcoid
						WHERE fconome = 'Monitor'AND tusdatafim IS NULL
                        )
	AND fconome = 'Aluno'
    ORDER BY usunome;
end#
delimiter ;

delimiter #
create procedure sp_consultamonitores(p_monnome varchar(50))
begin
	SELECT usucpf, usunome, matid, matnome FROM usuario
    INNER JOIN tipousuario ON tususucpf = usucpf
    INNER JOIN funcao ON fcoid = tusfcoid
    INNER JOIN materia ON tusmatid = matid
    WHERE tusdatafim is null
    AND usunome LIKE p_monnome
    ORDER BY usunome;
end#
delimiter ;

delimiter #
create procedure sp_consultamonitor(p_moncpf varchar(15))
begin
	SELECT usucpf, usunome, matid, matnome FROM usuario
    INNER JOIN tipousuario ON tususucpf = usucpf
    INNER JOIN funcao ON fcoid = tusfcoid
    INNER JOIN materia ON tusmatid = matid
    WHERE tusdatafim is null
    AND usucpf = p_moncpf;
end#
delimiter ;


delimiter #
create procedure sp_consultamateriasativas()
begin
	SELECT * FROM materia
    WHERE matativa = true
    AND matid != 1;
end#
delimiter ;

delimiter #
create procedure sp_consultamaterias()
begin
	SELECT * FROM materia
    WHERE matid != 1;
end#
delimiter ;

delimiter #
create function f_inscreveraluno(p_alucpf varchar(15), p_miaid int) returns varchar(50)
begin
    declare v_vagas int;
    declare v_materia varchar(20);
    declare v_hora varchar(20);
    declare v_dia varchar(20);
    
    DECLARE excessao SMALLINT DEFAULT 0;
    
    set v_vagas = (SELECT miavagas FROM monitoria WHERE miaid = p_miaid);
    if(v_vagas>0)then
		INSERT INTO inscricao VALUES (p_alucpf, p_miaid);
        UPDATE monitoria SET miavagas = miavagas -1 WHERE miaid = p_miaid;
        RETURN '';
	else
		SELECT matnome, dianome, miahorhora
        INTO v_materia, v_hora, v_dia
        FROM monitoria
		INNER JOIN usuario ON usucpf = miausucpf
		INNER JOIN tipousuario ON usucpf = tususucpf
		INNER JOIN materia ON tusmatid = matid
        INNER JOIN diadasemana ON miadiaid = diaid
		WHERE miaid = p_miaid;
		RETURN concat('Não há vagas para monitoria de\n ', v_materia, ' na ', v_dia, ' as ', v_hora);
	end if;
    
end#
delimiter ;

delimiter #
create procedure sp_consultasalasativas()
begin
	SELECT * FROM sala WHERE salativa = true;
end#
delimiter ;

delimiter #
create procedure sp_registrarmonitor(p_moncpf varchar(15), p_matid int)
begin
	declare v_dia date;
    declare v_boo boolean;
    
    set v_dia = (select tusdatafim from tipousuario
				inner join funcao ON fcoid = tusfcoid
				where tususucpf = p_moncpf
                AND fconome = 'Monitor'
                ORDER BY tusdatafim desc
                limit 1);
	if(extract(year from v_dia) = extract(year from curdate()))then
		set @num = extract(month from v_dia);
        select @num;
        if(@num<=6)then
			if(extract(month from curdate())>=7)then
				set v_boo = true;
			else
				set v_boo = false;
			end if;
		else
			set v_boo = false;
		end if;
	else
		set v_boo = true;
	end if;
    
    if(v_boo)then
		INSERT INTO tipousuario(tususucpf, tusfcoid, tusdatainicio, tusmatid) 
		values(p_moncpf, 3, curdate(), p_matid);
	end if;
end #
delimiter;


delimiter #
create procedure sp_inativarmonitor(p_moncpf varchar(15), p_matid int)
begin   
    UPDATE tipousuario 
    SET tusdatafim = curdate()
    WHERE tususucpf = p_moncpf 
    AND tusdatafim is null;
    
    UPDATE monitoria SET miausucpf = ''
    WHERE miadatafim IS NULL
    AND miausucpf = p_moncpf;
end #
delimiter;
drop procedure sp_inativarmonitor;


-- Procedure para consultar as horas disponíveis para uma sala em um determinado dia
-- HorarioDAO >>> consultarHora()
delimiter #
create procedure sp_consultarhorasdisponiveis(p_salnome varchar(20), p_dianome varchar(20))
begin
	SELECT * FROM horario
	WHERE horhora NOT IN (SELECT miahorhora FROM monitoria
							INNER JOIN sala ON salid = miasalid
							INNER JOIN diadasemana ON diaid = miadiaid
							WHERE salnome = p_salnome AND dianome = p_dianome
                            AND miadatafim IS NULL);
end#
delimiter ;
call sp_consultarhorasdisponiveis('LAB V', 'Segunda');


delimiter $
create procedure sp_relatoriodemonitorias()
begin
SELECT matnome, count(*) FROM materia
INNER JOIN tipousuario ON tusmatid = matid
INNER JOIN monitoria ON miausucpf = tususucpf
INNER JOIN inscricao ON insmiaid = miaid
GROUP BY matnome
ORDER BY count(*) desc;
end$
delimiter ;
call sp_relatoriodemonitorias();

-- Procedures para gerar relatórios de monitores mais requisitados, dia da semana, horario e matéria mais requisitados
-- E monitores com mais e menos horarios oferecidos
delimiter $
create procedure sp_monitoresmaisrequisitados()
begin
	SELECT  usucpf, usunome, matnome,matid, count(*)'numero de inscricoes' 
			FROM usuario
			INNER JOIN tipousuario ON tususucpf = usucpf
			INNER JOIN funcao ON fcoid = tusfcoid
			INNER JOIN materia ON tusmatid = matid
			INNER JOIN monitoria ON usucpf = miausucpf
			INNER JOIN inscricao ON miaid = insmiaid
			WHERE tusdatafim is null
			GROUP BY usucpf
			ORDER BY count(*) DESC 
			;
end$
delimiter ;
call sp_monitoresmaisrequisitados();
delimiter $

create procedure sp_disciplinasmaisrequisitadas()
begin
SELECT  matid, matnome, count(*)'numero de inscricoes' FROM usuario
					INNER JOIN tipousuario ON tususucpf = usucpf
                    INNER JOIN funcao ON fcoid = tusfcoid
					INNER JOIN materia ON tusmatid = matid
                    INNER JOIN monitoria ON usucpf = miausucpf
                    INNER JOIN inscricao ON miaid = insmiaid
                    WHERE tusdatafim IS NULL
                    GROUP BY matid
                    ORDER BY count(*) DESC 
                    ;
end$
delimiter ;
call sp_disciplinasmaisrequisitadas();

delimiter $
create procedure sp_horariosmaisrequisitados()
begin
	SELECT  horhora, count(*)'numero de inscricoes' 
				FROM horario
				INNER JOIN monitoria ON horhora = miahorhora
                INNER JOIN inscricao ON miaid = insmiaid
                GROUP BY horhora
                ORDER BY count(*) DESC 
                ;
end$
delimiter ;

call sp_horariosmaisrequisitados();

delimiter $
create procedure sp_diadasemanamaisrequisitados()
begin
SELECT  diaid, dianome, count(*)'numero de inscricoes' 
					FROM diadasemana
					INNER JOIN monitoria ON diaid = miadiaid
                    INNER JOIN inscricao ON miaid = insmiaid
                    GROUP BY diaid
                    ORDER BY count(*) DESC
                    ;
end$
delimiter ;

call sp_diadasemanamaisrequisitados();

delimiter $
create procedure sp_monitoresqueoferecemmaishorarios()
begin
SELECT  usucpf,usunome,matid,matnome, count(*)'horarios oferecidos' FROM usuario
					INNER JOIN tipousuario ON tususucpf = usucpf
                    INNER JOIN funcao ON fcoid = tusfcoid
					INNER JOIN materia ON tusmatid = matid
                    INNER JOIN monitoria ON usucpf = miausucpf
					WHERE tusdatafim IS NULL
                    GROUP BY usucpf
                    ORDER BY count(*) DESC
                    ;
end$
delimiter ;
call sp_monitoresqueoferecemmaishorarios();


delimiter $
create procedure sp_monitoresqueoferecemmenoshorarios()
begin
SELECT  usucpf,usunome,matid,matnome, count(*)'horarios oferecidos' FROM usuario
					INNER JOIN tipousuario ON tususucpf = usucpf
                    INNER JOIN funcao ON fcoid = tusfcoid
					INNER JOIN materia ON tusmatid = matid
                    INNER JOIN monitoria ON usucpf = miausucpf
					WHERE tusdatafim IS NULL
                    GROUP BY usucpf
                    ORDER BY count(*) DESC
                    ;
end$
delimiter ;
call sp_monitoresqueoferecemmenoshorarios();

delimiter #
create function f_numerodemonitorias(p_moncpf varchar(15))returns int
begin
	declare v_num int;
    
    set v_num = (SELECT count(*) FROM monitoria
				WHERE miausucpf = p_moncpf AND
                miadatafim IS NULL);
	return v_num;
end#
delimiter ;


delimiter #
create procedure sp_validacao(p_usulogin varchar(50), p_ususenha varchar(50), p_funcao varchar(20))
begin
	SELECT * FROM usuario
    INNER JOIN tipousuario ON usucpf = tususucpf
    INNER JOIN funcao ON fcoid = tusfcoid
    WHERE usulogin = p_usulogin AND
    ususenha = p_ususenha AND
    fconome = p_funcao AND
    tusdatafim IS NULL;
end#
delimiter ;

CALL sp_validacao('sandro.teixeira', 'tads', 'Aluno');