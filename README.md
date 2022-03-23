# Szigorú szerződések Kft szoftverlicenciroda

## Bevezetés

Szoftverlicenc irodának készítesz programot, szükség lenne a szoftverlicencek különböző szempontok szerinti csoportosítására a statisztikák
készítéshez.

## Feladatleírás

Készíts egy, a teszteknek megfelelő `License` osztályt, amiben tárolni tudod a következő adatokat:
a szoftver nevét, lejárati dátumát és az érvényes felhasználóit (String setben tárolva). 
Azt is meg tudja mondani, hogy egy adott dátumkor épp mennyi év van még hátra a licenc lejáratáig: `int getTimeLeftUntilExpiration(LocalDate now)`.

Készíts el egy, a teszteknek megfelelő `LicenseManager` osztályt, mely:

- a szoftverlicencek listáját konstruktor paraméterben kapja meg, ha ez null, dobjon `IllegalArgumentException`-t
- képes visszaadni az adott napon még legfeljebb 10 évig érvényes szoftverlicenceket
    - `List<License> findLicensesValidFor10MoreYears(LocalDate now)`
- képes visszaadni adott napon, a már lejárt szoftverlicenceket
    - `List<License> findExpiredLicenses(LocalDate now)`
- képes visszaadni az elkövetkezendő évben (12 hónapban) megújítandó szoftverlicencek dátumaival, és hogy azon a dátumon épp
  hány szoftverlicenct kell megújítani - `findExpiresNextYear(LocalDate now)`
- képes visszaadni azokat a szoftverlicenceket, akiknek legalább 1 közös felhasználójuk van a paraméterben megadottakkal. Ha nem
  adtunk meg legalább 1 felhasználót, dobjunk IllegalArgumentException-t!
    - `List<License> findLicensesWithUsers(Set<String> users)`
- képes visszaadni egy, a szoftverlicencek neveit tartalmazó, regisztrálási hónap alapján csoportokba rendezett
  map-et `Map<Month, List<String>> getNamesPerMonth()`

A munka során a kísérletezéshez tetszőlegesen létrehozhatsz `main` metódust, illetve akár készíthetsz másik
osztályt `main` metódussal.

A teszteket nem szabad módosítani!

## Pontozás

Egy feladatra 0 pontot ér, ha:

- nem fordul le
- lefordul, de egy teszteset sem fut le sikeresen.
- ha a forráskód olvashatatlan, nem felel meg a konvencióknak, nem követi a clean code alapelveket.

0 pont adandó továbbá, ha:

- kielégíti a teszteseteket, de a szöveges követelményeknek nem felel meg

Pontokat a további működési funkciók megfelelősségének arányában kell adni a vizsgafeladatra:

- 5 pont: az adott projekt lefordul, néhány teszteset sikeresen lefut, és ezek funkcionálisan is helyesek. Azonban több
  teszteset nem fut le, és a kód is olvashatatlan.
- 10 pont: a projekt lefordul, a tesztesetek legtöbbje lefut, ezek funkcionálisan is helyesek, és a clean code elvek
  nagyrészt betartásra kerültek.
- 20 pont: ha a projekt lefordul, a tesztesetek lefutnak, funkcionálisan helyesek, és csak apróbb funkcionális vagy
  clean code hibák szerepelnek a megoldásban.

Gyakorlati pontozás a project feladatokhoz:

- Alap pontszám egy feladatra(max 20): lefutó egység tesztek száma / összes egység tesztek száma * 20, feltéve, hogy a
  megoldás a szövegben megfogalmazott feladatot valósítja meg
- Clean kód, programozási elvek, bevett gyakorlat, kód formázás megsértéséért - pontlevonás jár. Szintén pontlevonás
  jár, ha valaki a feladatot nem a leghatékonyabb módszerrel oldja meg - amennyiben ez értelmezhető.
