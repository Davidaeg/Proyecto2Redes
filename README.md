# ProyectoRedesII
El siguiente manual guiara a los usuarios que harán soporte al sistema o cambios, el cual les dará a
conocer los requerimientos  para la construcción y la funcionalidad del sistema

**Requerimientos de software**

  1. NetBeans IDE 8.2
    [Link de desarga](https://netbeans.org/downloads/8.2/rc/)
       
###### Descargar el Repositorio
Para obtener este repositorio debes descargarlo desde el botón código(code) y seleccionar descargar cómo zip

[![Captura-de-pantalla-2020-11-28-153959.png](https://i.postimg.cc/8cX7T1Sb/Captura-de-pantalla-2020-11-28-153959.png)](https://postimg.cc/GTYLQn9B)


Al descargarlo tendremos un .zip que debemos desempaquetar y adentro de la carpeta "Proyecto2Redes-master" 

[![2.png](https://i.postimg.cc/wj86Lt0m/2.png)](https://postimg.cc/Mv528Gk6)

Abrimos Netbeans y en File seleccionamos Open Project

[![3.png](https://i.postimg.cc/mDb3D2vx/3.png)](https://postimg.cc/mc51pRZV)

Buscamos el proyecto en la ruta que lo guardamos en el computador y luego presionamos Open Project

[![4.png](https://i.postimg.cc/k4Fj1HmC/4.png)](https://postimg.cc/gXjqrNjt)

**Crear el certificado**

Para el servidor HTTPS el programa usa un certificado que esta dentro del proyecto, en caso de querer utilizar uno propio siga los siguientes pasos para la creación

**Generar el key Store**

1. Abrir una linea de comand, lo puede realizar escribiendo en el buscador de windows cmd

[![5.png](https://i.postimg.cc/d3fSryFZ/5.png)](https://postimg.cc/8fhHVsjT)

2. Luego escriba lo siguiente en la linea de comandos donde **-alias** corresponde al nombres que usted desee el **-keyalg** es el algoritmo que se usara en este caso RSA y **-keystore** donde quiere que se almacenado y presione Enter

[![6.png](https://i.postimg.cc/nrKGc9zt/6.png)](https://postimg.cc/T5wDtwgk)

3. Le pedira una contraseña, escriba la que usted desee pero debe ser tener presente que deberá contener 6 caracteres con numeros y letras de lo contrario le lanzara un mensaje indicandoselo, para este ejemplo la clave que se pondra sera redes123, al presionar enter en la linea de comandos le pedira re escribirla como confirmaciób, ingresela de nuevo y presione enter

[![7.png](https://i.postimg.cc/FRH4x3MT/7.png)](https://postimg.cc/JyvvrD4X)

4. Escriba un nombre deseado



















