Desktop—Java

Bemutatás:Ez a része a projektünknek az adminisztratív részét fogja ellátni. Ez azt jelenti, hogy az admin, ha szeretne tudja módosítani az adattáblát, tud törölni az adattáblából és feltud venni egy teljesen új elemet is.

Első lépésben ( ha még nincs meg) indítsuk el a XAMPP Control Panelt-t és azon belül indítsuk el Apache és a Mysql szolgáltatásokat. Ezután a phpMyAdmin-ban importálja be az f1.sql-t (ha ez a lépés még nincs meg).

Második lépés, nyissa meg az Intellij-t és válassza ki a „Get from VCS” opciót és másolja be ezt az URL-t: https://github.com/Fuzi18/vizsgaremek-java. Miután ez megvan nyomjon rá a Clone gombra és megfogja nyitni magát a kódot. A kód 18.0.2-es SDK-val készült, tehát ha ez nincs telepítve, akkor mindenféleképpen töltse le, különben nem fog működni.

Miután sikerült megnyitni a kódot az „App.java”-n belül tudja megjeleníteni az adattáblákat. Mindegyik adattáblának van egy view.fxml-je, az adminnak csupán annyi a dolga, hogy az „App.java”-ban a „-view” elé beírja a nevét annak az adattáblának amelyiket megakarja jeleníteni. Miután átírta, indítsa el a programot.

Ha az admin a pilótáknál szeretne módosítani valamit akkor először kikell választania egy sort amit módosítani szeretne és utána rákell nyomnia az „Update” gombra, majd miután a kiválasztott sornak egyik vagy több részét módosította, utána rákell nyomnia a „Submit” gombra, ha törölni szeretne, ott is először kikell választani egy törölni kívánt sort és ha ez megvan a program megfogja kérdezni, hogy biztosan törölni akarja e, és ha a „Nem”-re nyom akkor nemfogja törölni, viszont ha az „Igen”-re nyom akkor törölni fogja azt. Az új adat felvételét a panel jobb oldalán tudja megtenni, ahol megtudja adni a pilóta nevét, életkorát, nemzetségét, csapatát, szerzettpontjait, kategóriáját és a helyezését, ha mindent megadott rákell nyomni a „Submit” gombra és hozzáfogja adni az új elemet az adattáblához.



A boltnál is ugyanúgy kell csinálni mindent, mint a pilótáknál, csak itt az új adat felvételénél a termék típusát, csapatát, méretét, színét, árát és a készleten lévő darabszámát tudja megadni az admin.

A motornál is ugyanúgy kell csinálni mindent, mint a pilótáknál, csak itt az új adat felvételénél a motor leírását, a motorkomponens nevét, az árát és a készleten lévő darabszámát tudja megadni az admin.


A kaszninál is ugyanúgy kell csinálni mindent, mint a pilótáknál, csak itt az új adat felvételénél a kaszni leírását, a kasznikomponens nevét, az árát és a készleten lévő darabszámát tudja megadni az admin.

A vezérlőegységnél is ugyanúgy kell csinálni mindent, mint a pilótáknál, csak itt az új adat felvételénél a vezérlőegység leírását, a vezérlőegység komponens nevét, az árát és a készleten lévő darabszámát tudja megadni az admin.



