# Strength Your Skills

Plataforma web ...

## Instalação

É necessário ter o MySQL 5.7.x instalado. A respectiva base de dados pode ser criada da seguinte forma:

```
create database badge;

create user 'badge'@'localhost' identified by 'badge';

grant all privileges on badge.* to 'badge'@'localhost';
```