  BookManager  
Sistema de Gestión de Biblioteca  

  Descripción  
BookManager es una aplicación desarrollada en Java que permite gestionar los procesos de una biblioteca de forma eficiente. El sistema facilita el registro de libros y usuarios, la realización de préstamos y devoluciones, y almacena la información tanto en archivos de texto (`.txt`) como en una base de datos **SQL Server**. La interfaz gráfica está construida con **Java Swing** para una experiencia de usuario intuitiva.

  Objetivos  
 Objetivo General  
Desarrollar un sistema de gestión de biblioteca que permita administrar libros, usuarios y préstamos mediante Java Swing, archivos `.txt` y base de datos SQL Server.

 Objetivos Específicos  
- Registrar, editar y eliminar libros y usuarios.  
- Gestionar préstamos y devoluciones.  
- Almacenar datos en archivos `.txt` y en SQL Server.  
- Consultar el historial de préstamos realizados.  

  Funcionalidades  
- Registro de libros y usuarios  
- Préstamos y devoluciones  
- Consulta de libros disponibles y prestados  
- Historial de préstamos  
- Interfaz gráfica con Java Swing  
- Almacenamiento local (`.txt`) y en base de datos (SQL Server)  

   Roles del Sistema  
| Rol | Descripción |
|-----|-------------|
| Administrador | Gestiona libros, usuarios y préstamos |
| Bibliotecario/Usuario | Registra devoluciones y realiza consultas |

  Tecnologías Utilizadas  
- Lenguaje: Java JDK 17  
- IDE: IntelliJ IDEA  
- Interfaz Gráfica: Java Swing  
- Base de Datos: SQL Server + JDBC  
- Archivos: `.txt` con `java.io` / `java.nio`  
- Control de Versiones: Git + GitHub  
- Diseño UML: draw.io / StarUML  
- Documentación: Microsoft Word / Google Docs  

  Integrantes del Equipo  
| Cif | Nombre | Rol |
|-----|--------|-----|
| 24011529 | Diego David Fonseca Moody | Líder de proyecto y desarrollador principal (Interfaz Swing) |
|  | Siles Abraham Flores Urcuyo | Desarrollador Backend y administrador de BD (SQL Server + .txt) |
| 24011465 | Diego Alejandro Palacios Parada | QA, Documentación y soporte (Pruebas y manuales) |

Justificación

El desarrollo del sistema BookManager se justifica plenamente por la necesidad crítica de modernizar y optimizar los procesos operativos inherentes a la gestión de una biblioteca, superando las limitaciones impuestas por los métodos manuales o las soluciones informáticas fragmentadas. La justificación se articula en torno a la eficiencia operativa, la integración tecnológica y la robustez en la gestión de datos, elementos esenciales para cualquier entidad que maneje un inventario dinámico y un flujo constante de transacciones con usuarios.

1. Justificación Operacional: Eficiencia y Control Centralizado

La principal justificación para la creación de BookManager radica en la urgencia de reemplazar la ineficiencia de los procesos manuales con un sistema automatizado y centralizado. En un entorno bibliotecario tradicional, la gestión de libros, el registro de usuarios y, fundamentalmente, el control de préstamos y devoluciones, son tareas susceptibles a errores humanos, duplicidad de registros y demoras significativas. BookManager aborda este desafío directamente al ofrecer una plataforma unificada que permite el registro, edición y eliminación inmediata de libros y usuarios, garantizando la integridad y la unicidad de la información.

El sistema se justifica por su capacidad para transformar la gestión de transacciones (préstamos y devoluciones) de un proceso laborioso a una operación rápida y precisa. Al automatizar la verificación de disponibilidad y el registro de la fecha de devolución, se minimiza el riesgo de pérdida de material y se mejora la capacidad de la biblioteca para hacer cumplir las políticas de préstamo. Además, la funcionalidad de consulta del historial de préstamos es vital, ya que proporciona una herramienta de auditoría indispensable para el personal, permitiendo rastrear el ciclo de vida de cada ejemplar y analizar patrones de uso, lo cual es fundamental para la toma de decisiones sobre la adquisición de nuevo material y la optimización del inventario.

2. Justificación Tecnológica: Robustez, Dualidad de Almacenamiento y Experiencia de Usuario

La selección de la arquitectura tecnológica para BookManager se justifica por la búsqueda de robustez, portabilidad y una gestión de datos jerárquica.
Java y Java Swing: La elección de Java JDK 17 como lenguaje de desarrollo se justifica por su naturaleza multiplataforma y su madurez en el desarrollo de aplicaciones empresariales. Esto asegura que el sistema sea escalable y portable a diferentes entornos operativos. La interfaz gráfica desarrollada con Java Swing se justifica por la necesidad de proporcionar una experiencia de usuario intuitiva y de escritorio para el personal de la biblioteca (Administrador y Bibliotecario), ofreciendo un control preciso sobre los elementos visuales sin depender de un navegador web, lo cual es ideal para entornos de trabajo estables y dedicados.

Dualidad de Almacenamiento (SQL Server y .txt): La decisión de implementar un mecanismo de almacenamiento dual es una justificación clave. El uso de SQL Server a través de JDBC garantiza la persistencia, integridad y concurrencia de los datos críticos (inventario y transacciones), aprovechando las capacidades de un sistema gestor de bases de datos relacional de nivel empresarial. Paralelamente, la inclusión del almacenamiento en archivos de texto (.txt) mediante las librerías java.io o java.nio se justifica como una capa de respaldo, portabilidad o para fines de logging de transacciones específicas. Esta dualidad ofrece una solución de gestión de datos flexible y resiliente, adecuada tanto para un entorno de producción con alta disponibilidad como para escenarios de prueba o backup rápido.
Metodología y Control: El uso de Git y GitHub se justifica como un estándar de la industria para el control de versiones y la colaboración, asegurando la trazabilidad de los cambios y facilitando el desarrollo en equipo. De igual manera, la creación de un Diseño UML previo se justifica para establecer una estructura clara y modular del sistema, minimizando la complejidad y los errores de diseño en etapas posteriores.

3. Justificación Educativa y de Alcance del Proyecto

Finalmente, BookManager se justifica como un proyecto integral de desarrollo de software que cumple con múltiples objetivos educativos y de alcance. El proyecto no solo busca resolver un problema funcional (gestión de biblioteca), sino que también sirve como un demostrador de la integración de tecnologías clave en un único sistema. La combinación de una interfaz gráfica de escritorio (Swing), la lógica de negocio en Java, la persistencia de datos en una base de datos robusta (SQL Server) y la manipulación de archivos locales, justifica su valor como un ejercicio de ingeniería de software completo. El sistema está diseñado para ser un modelo de gestión de roles (Administrador y Bibliotecario/Usuario), lo que justifica su aplicabilidad en un entorno real con diferentes niveles de acceso y responsabilidad. En esencia, el desarrollo de BookManager se justifica como una solución tecnológica robusta, eficiente y bien estructurada para la gestión bibliotecaria, diseñada para optimizar los recursos y mejorar la calidad del servicio ofrecido a los usuarios.
