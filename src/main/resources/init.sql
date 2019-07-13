/*
    Database initialization script that runs on every web-application redeployment.
*/
DROP TABLE IF EXISTS users CASCADE ;
DROP TABLE IF EXISTS userInfos CASCADE ;
DROP TABLE IF EXISTS games CASCADE ;
drop table if exists usersGame cascade ;


CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
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
    id serial primary key ,
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
            balance integer;
            price integer;
                begin
                    select balance into balance from users join usersGame on users.id = usersGame.userId where users.id = new.id;
                    select price into price from usersGame join games on usersGame.userId = games.id where games.id = new.id;
                    if  balance < price then
                        raise exception ''Balance is not enough'';
                    else
                        update users set balance=balance-price where id = new.id;
                    end if;
            end;
        return new;
    end;
' language plpgsql;

insert into games (name, platform, imageUrl, price) VALUES
('Spider-Man', 'PS4', 'https://p1.akcdn.net/full/422035987.sony-marvel-spider-man-ps4.jpg', 50),
('God of War', 'PS4', 'https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%3Fid%3DOIP.eF4doJiTCFL4q0nOfuNQBAHaI_%26pid%3DApi&f=1', 50),
('Until Dawn', 'PS4', 'https://media.psu.com/media/articles/image/PS4_UntilDawnExtendedBox.jpg', 30),
('Become Human', 'PS4', 'http://images.pushsquare.com/news/2018/04/detroit_become_humans_box_art_is_just_as_bad_in_japan/attachment/0/900x.jpg', 35),
('Halo: The Master Chief Collection', 'XboxOne', 'http://ecx.images-amazon.com/images/I/8175Btj5gGL._SL1500_.jpg', 25),
('Gears of War 4', 'XboxOne', 'https://www.gamepur.com/files/images/2016/gears-of-war-4-box-art_480x611.jpg', 30),
('The Legend of Zelda: Breath of The Wild', 'Nintendo', 'http://images.eurogamer.net/2017/articles/1/8/7/8/8/7/0/lets-compare-and-contrast-the-us-and-european-legend-of-zelda-breath-of-the-wild-box-art-148430788189.jpg/EG11/resize/600x-1/quality/80/format/jpg', 40),
('Diablo 3', 'PC', 'http://vignette2.wikia.nocookie.net/diablo/images/f/f2/Diablo_3_reaper_of_souls_box_art_0.jpg/revision/latest?cb=20130821152656', 20);

