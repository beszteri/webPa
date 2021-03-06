/*
    Database initialization script that runs on every web-application redeployment.
*/
DROP TABLE IF EXISTS users CASCADE ;
DROP TABLE IF EXISTS userInfos CASCADE ;
DROP TABLE IF EXISTS games CASCADE ;
drop table if exists usersGame cascade ;


CREATE TABLE users (
                       id SERIAL unique PRIMARY KEY,
                       email TEXT UNIQUE NOT NULL,
                       password TEXT NOT NULL,
                       balance integer not null,
                       role varchar(15) not null ,
                       CONSTRAINT email_not_empty CHECK (email <> ''),
                       CONSTRAINT password_not_empty CHECK (password <> '')
);

CREATE TABLE userInfos (
                           id SERIAL PRIMARY KEY,
                           address TEXT ,
                           name varchar(25) ,
                           phoneNumber varchar(20) ,
                           userId int not null ,
                           foreign key (userId) references users(id)
);

CREATE TABLE games (
    id serial unique primary key ,
    name text not null ,
    platform text not null , --enumra
    imageUrl text not null ,
    price integer not null
);

create table usersGame(
  userId integer,
  gameId integer,
  foreign key (userId) references users(id),
  foreign key (gameId) references games(id),
  unique (userId, gameId)
);

create or replace function shop_balance() returns trigger as '
    begin
        declare
            varBalance integer;
            varPrice integer;
                begin
                    select users.balance into varBalance from users join usersGame on users.id = usersGame.userId where users.id = new.userId;
                    select games.price into varPrice from usersGame join games on usersGame.gameId = usersGame.gameId where games.id = new.gameId;
                    if  varBalance < varPrice then
                        raise exception ''Balance is not enough'';
                    else
                        update users set balance=balance-varPrice where users.id = new.userId;
                    end if;
            end;
        return new;
    end;
' language plpgsql;

create trigger shop_balance
    after insert on usersGame
    for each row execute procedure shop_balance();

insert into users (email, password, balance, role) VALUES
('a', '1000:409fe2cfb15529fcbcc703c6ec160803:b06f766b8e27629b174f389e378e7e8b81b108d05a48e54e73efd92ca0398266c9bf9aef05191aff03595804636159ce029795404791b4806069c2a554f2c650', 0, 'ADMIN');

insert into games (name, platform, imageUrl, price) VALUES
('Spider-Man', 'PS4', 'https://p1.akcdn.net/full/422035987.sony-marvel-spider-man-ps4.jpg', 50),
('God of War', 'PS4', 'https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%3Fid%3DOIP.eF4doJiTCFL4q0nOfuNQBAHaI_%26pid%3DApi&f=1', 50),
('Until Dawn', 'PS4', 'https://media.psu.com/media/articles/image/PS4_UntilDawnExtendedBox.jpg', 30),
('Become Human', 'PS4', 'http://images.pushsquare.com/news/2018/04/detroit_become_humans_box_art_is_just_as_bad_in_japan/attachment/0/900x.jpg', 35),
('Halo: The Master Chief Collection', 'XboxOne', 'http://ecx.images-amazon.com/images/I/8175Btj5gGL._SL1500_.jpg', 25),
('Gears of War 4', 'XboxOne', 'https://www.gamepur.com/files/images/2016/gears-of-war-4-box-art_480x611.jpg', 30),
('The Legend of Zelda: Breath of The Wild', 'Nintendo', 'http://images.eurogamer.net/2017/articles/1/8/7/8/8/7/0/lets-compare-and-contrast-the-us-and-european-legend-of-zelda-breath-of-the-wild-box-art-148430788189.jpg/EG11/resize/600x-1/quality/80/format/jpg', 40),
('Diablo 3', 'PC', 'http://vignette2.wikia.nocookie.net/diablo/images/f/f2/Diablo_3_reaper_of_souls_box_art_0.jpg/revision/latest?cb=20130821152656', 20);

