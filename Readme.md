# Challenge Decrypto

### Resumen
Esta aplicación nos permite obtener el porcentaje de comitentes por mercado que existen, teniendo en cuenta que cada comitente puede tener mas de un mercado asociado, y a su vez, que cada mercado tiene unicamente un pais en el cual funciona.
Ademas, nos permite dar de alta, modificar y eliminar, comitentes, mercados y países.

### Estado
El proyecto en este momento se encuentra finalizado.

### Base de datos:
Al momento de ejecutar el proyecto, por defecto se carga en la base de datos sql, la siguiente información:
```
Países:
   id:1 - name: Argentina
   id:2 - name: Uruguay
Market:
   id:1 - code: MAE - description: Mercado Argentino de Valores - pais: rgentina
   id:2 - code: ROFEX - description: Mercado de Futuros - pais: argentina
   id:3 - code: UFEX - description: Uruguay Futures Exchange - pais: uruguay
Comitentes:
   id:1 - description: Comitente1 - markest: MAE, UFEX
   id:2 - description: Comitente2 - markest: MAE, UFEX, ROFEX
```
### Resultado de ejecución de estadísticas:
```
Argentina
    ROFEX: 50,00 % (1 cuentas en mercado / 2 cuentas totales)
    MAE: 100,00 %  (2 cuentas en mercado / 2 cuentas totales)
Uruguay
    UFEX: 100,00 %  (2 cuentas en mercado / 2 cuentas totales)
```
### Ejecución
El proyecto solamente requiere de gradle y java 17. Una vez compilado gradle, se puede ejecutar el proyecto. El mismo cuenta con una base de datos H2 que corre en memoría, y a su vez, con una cache que funciona de igual forma. Estas herramientas no requieren configuración adicional.

### Requisitos:
1. **Verificar que se encuentra instalado gradle**
2. **Verificar que se encuentra instalado una version de java 17 o superior**

### Instalación:
* **Clonación del proyecto:** ```git clone https://github.com/AleFalcon/challenge-decrypto.git```
* Abrir el proyecto con el IDE que corresponda.
* **Compilar gradle:** para que logre descargar e instalar las dependencias: ```bash ./gradlew clean build```
* **Correr el proyecto:** Dicho proyecto escucha el puerto 8080, en caso de no estar seteado en el application.properties otro puerto

### Ejecución de Tests
Todos los test se ejecutan correctamente. Para esto, podemos hacer click derecho en la carpeta src/test, opción

# Uso de la aplicación
## Uso con Postman
* **Importa la colección de Postman** que se encuentra en **'postman/'**, con el nombre **'challenge.postman_collection.json'**.
Para hacerlo, ve a **'File -> Import -> Seleccionar el archivo mencionado'**.
* A su vez, se disponibiliza un swagger para poder acceder a la documentación de los endpoints en la gisuiente url: ```http://{server}:{port}/swagger-ui```
por ejemplo, si se se accede al swagger de prod la url seria: ```https://challenge-decrypto-production.up.railway.app/swagger-ui/index.html```
