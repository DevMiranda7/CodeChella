create table ingressos(
    id bigserial not null,
    categoria varchar(30) not null,
    quantidade  int not null,
    data date,
    primary key(id)
);