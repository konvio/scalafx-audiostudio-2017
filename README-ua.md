#Лабораторна робота
######Кононенко Віталій, К-24

##Огляд
Лабораторна робота є спрощеною версію інформаційної системи студії
звукозапису. Для заданої схеми бази даних повністю реалізовано
CRUD-операції за допомогою інтерфейсу користувача, а також реалізовані
параметризовані запити різного рівня складності.

> CУБД: PostgreSQL  
> ОС: Windows 10 
> Мова: Scala (JVM-мова, взаємосумісна з Java)
 
##Cхема бази даних
![scheme](http://i.imgur.com/YL12u4t.png)

##Форми введення
### Новий запис
![addrecord](http://i.imgur.com/oJBBbOd.png)

* **Title** - непорожній текст
* **Price** - непожнє число
* **Artist** - випадаючий список
* **Genre** - випадаючий список
* **Path** - рядок, може бути порожній

### Новий виконавець
![](http://i.imgur.com/njOTZUP.png)

* **Name** - непорожній рядок

### Новий альбом
![](http://i.imgur.com/DENIC1q.png)

* **Title** - непорожній текст
* **Price** - непожнє число

### Новий жанр
![](http://i.imgur.com/YXQoKhD.png)

* **Genre** - непорожній текст
* **Description** - можливо порожній текст

### Зв'язати альбом з записом
![](http://i.imgur.com/2lQQ4gQ.png)

* **Album** - випадаючий список, має бути обраний
* **Record** - випадаючий список, має бути обраний




## Запити
>У формах введення параметрів для запитів усі текстові поля мають бути непорожні
### Знайти назви виконавців, що виконують альобом з заданою назвою.  

```sql
SELECT name
FROM artist JOIN record ON artist.id = record.artist_id
            JOIN album_record ON record.id = album_record.record_id
            JOIN album ON album_record.album_id = album.id
WHERE album.title = '#$input';                 
```
### Знайти загальний прибуток по місяцям від запису альбомів та компрозицій
```sql
WITH income AS (
    SELECT
      price,
      released_date AS date
    FROM record
    UNION ALL
    SELECT
      price,
      released_date AS date
    FROM album
  )
  SELECT
    sum(price)               AS income,
    EXTRACT(MONTH FROM date) AS month,
    EXTRACT(YEAR FROM date)  AS year
  FROM income
  GROUP BY MONTH, YEAR
  ORDER BY year, month;
```
### Знайти назви виконавців, що мають записи в заданому жанрі
```sql
SELECT DISTINCT artist.name
FROM artist INNER JOIN record ON artist.id = record.artist_id
            INNER JOIN genre ON record.genre_id = genre.id
WHERE genre.name = ${genre};
```                  

### Знайти назви виконавців, що не мають композицій в заданому жанрі
```sql
WITH artists_of_genre AS (
                    SELECT record.artist_id AS id
                    FROM record JOIN genre ON record.genre_id = genre.id
                    WHERE genre.name = '#$input')
SELECT artist.name
FROM artist
WHERE artist.id NOT IN (SELECT id FROM artists_of_genre);
```
### Знайти назви виконавцій, що працюють в тих і тільки тих жанрах, що і заданий
```sql
SELECT artist.name
FROM artist
GROUP BY artist.id
HAVING artist.id = ${id} OR 2 = ALL(
    SELECT count(DISTINCT record.artist_id)
    FROM record
    WHERE record.artist_id = artist.id OR record.artist_id = ${id}
    GROUP BY record.genre_id);
 ```
### Знайти назви виконавців, що працюють принаймі в тих жанрах, що і заданий
```sql
SELECT artist.name
FROM artist
WHERE (SELECT count(DISTINCT record.genre_id)
      FROM record
      WHERE record.artist_id = ${id})
          =
      (SELECT count(genre_id) OVER()
       FROM record
       WHERE artist_id = artist.id OR artist_id = ${id}
       GROUP BY genre_id
       HAVING count(DISTINCT artist_id) = 2)
```
### Знайти назви винонавців, що працюють тільки в тих жанрах, що і заданий
```sql
SELECT artist.name
FROM artist
WHERE (SELECT count(DISTINCT record.genre_id)
       FROM record
       WHERE record.artist_id = artist.id)
              =
      (SELECT count(genre_id) OVER()
       FROM record
       WHERE artist_id = artist.id OR artist_id = ${id}
       GROUP BY genre_id
       HAVING count(DISTINCT artist_id) = 2);
```
## Вимоги до користувача
> Кваліфікація: базові знання ПК   
> Знання англійської мови для
інтерфейсу.