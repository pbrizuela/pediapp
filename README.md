# Explciacion Breve de la APP realizada: "pediapp" - Aplicación android para el manejo de historial medico, vacunación,
Integrantes del Grupo: Pedro Brizuela, Celia Saldivar y Karen Rojas 

1. Proyecto Login, se realiza el logueo con cuenta google, realizado, se obtiene el Json, siguiendo los pasos:
-> https://developers.google.com/mobile
La cuenta Google se verifica en Android Studio-src-main-java-MainActivity 
En SignInResult // acceso realizado, autentica el correo.  eN Netbeans, se verifica si se encuentra en la base de datos, 
mediante Verificar que se encuentra en Netbeans en la clase Usuarios.
-El servicio REST consulta el usuario mediante EntityManager que se define mediante una unidad Persistence esto lleva a la BD. 
- En Abstract Facade se verifica el objeto email que se instancia en criteriaBuilder , se consulta si es igual al email de la BD
Y Retorna el ID del usuario (madre)
2.Una vez ingresado con el respectivo correo, la mamá o papá usuario debe estar en la Base de datos logueado con el email elegido
anteriormente. 
al dar el boton siguiente lleva a la pantalla donde debemos seleccionar los datos de los hijos mediante un Spiner nos da la opcion
de elegir al hijo que quisieramos verificar. 
3. DatosFacadeREST /WHERE/idusiario:  va a Abstract Facade donde verifica el usuario (id) con los de los hijos 
(mediante findAllwhere(verifica vacunaPkcon idHijo) ambien con usuario si corresponde, es donde estan las tablas igualadas. 

4. en  llenar_lv1, se llenan los datos de los hijos, que aparece en primera plana NOMBRE HIJO-FECHA_NAC- SEXO
(Solicitado en un laboratorio). extrae los datos del hijo seleccionado de la BD. 
Luego si seleccionamos la opcion Boton: DATOS DEL HIJO/A , lo que hacemos es ver todo lo cargado en Lista.java.
Para volver atras, simplemente le damos con el boton atras de tu celular. 

5. Para ver VACUNAS: TareaWSVListarMes se verifican las vacunas dependiendo del hijo que eligio va a 
en VerHijos,    ArrayList<Integer> mes = obtenerMes va a CalcularFechaAAplicar que se encuentra en Configuraciones 
en VerVacunas, se usa spinner para filtrar el orden que uno quiere ver los datos de las vacunas. 
tambien dibujamos la tabla "dibujarTabla" dependiendo del filtro que elegimos, ya que la cantidad de filas son distintas. 
las columnas se mantienen VACUNA-FECHA APLICACION 


**** Para la conexion si o si el servidor debe estar en la misma red de los usuarios por lo que el telefono celular debe 
estar conectado al mismo.

En Android Studio,-src-main-java-Listar: aca mediante HttpGet(Configuraciones.Irnet va a NETBEANS, estira el get de la clase
de los datos de los hijos, IrDatosHijosLista = "http://" + ip + ":8084/pediappis/webresources/datos

***Si no seleecionamos un hijo y le damos el boton Datos Hijos o Vacunas en , saltara la notificacion  "SELECCIONE UN HIJO/A!!

*** Para la notificacion, usamos la AlarmManager.RTC_WAKEUP,la misma se activa cuando el telefono tenga inacitividad y no este en modo
depuracion. avisa: NombreHijo RECUERDE COLOCAR VACUNA DEL "X"ºMES
En Configuraciones de Configuracion de java de main de src, se encuentra el metodo calcularNotificacion donde se le resta "2" dias 
comparando del calculo realizado de cuantos meses tiene el hijo dependiendo de su fecha de nacimiento,(calcularFechaAAplicar),
AlarmManager (Se encuentra Android Studio-src-main-java-Notificacion), este llama A AlarmReceiver 
(es donde se verifican los datos de cada hijo de la Base de datos)

***Cargamos en el Git varios proyecto, pero tuvimos problemas muchas veces por lo que enviamos por correo la gran mayoria. 

**Es psible probar mediante 
http://localhost:8084/pediappis/webresources/datos/    - para ver los hijos
http://localhost:8084/pediappis/webresources/usuarios/ - para ver los padres
http://localhost:8084/pediappis/webresources/vacunas/  - para ver todas las vacunas
http://localhost:8084/pediappis/webresources/usuarios/verificar/xxxxxxxxxxx@gmail.com - se ve el id del correo colocado
http://localhost:8084/pediappis/webresources/datos/where/id_usuario - se ven los hijos por usuario
http://localhost:8084/pediappis/webresources/vacunas/where/ordenado/cihijo - ven vacunas ordenadas
