#  PetDayCare - Sistema de Gestión de Centro de Cuidado de Mascotas

Sistema completo de gestión para centros de cuidado de mascotas implementado en Java, que demuestra la aplicación práctica de múltiples patrones de diseño orientados a objetos.

##  Descripción

PetDayCare es una aplicación que permite gestionar reservas, servicios, pagos y notificaciones para centros de cuidado de mascotas. El sistema está diseñado siguiendo principios SOLID y utiliza patrones de diseño creacionales, estructurales y comportamentales.

##  Características Principales

- **Gestión de Centros**: Soporte para diferentes tipos de centros (grandes y pequeños)
- **Servicios Personalizables**: Guardería, hospedaje, paseo, entrenamiento y bienestar
- **Sistema de Pagos**: Integración con PayPal y Stripe mediante adaptadores
- **Notificaciones**: Sistema de notificaciones multi-canal (Email, SMS, Mensajería)
- **Gestión de Reservas**: Estados, cancelaciones y políticas de penalización
- **Manejo de Incidentes**: Sistema escalado de atención de incidentes

##  Patrones de Diseño Implementados

### Creacionales
- **Abstract Factory**: Creación de familias de servicios según tipo de centro
- **Builder**: Construcción paso a paso de reservas complejas

### Estructurales
- **Decorator**: Extensión dinámica de funcionalidades de servicios
- **Adapter**: Integración con sistemas de pago externos (PayPal, Stripe)

### Comportamentales
- **Chain of Responsibility**: Manejo escalado de incidentes

##  Estructura del Proyecto

```
PetDayCare/
├── adapters/          # Adaptadores para sistemas de pago
├── builders/          # Builders para construcción de objetos
├── decoradores/       # Decoradores de servicios
├── dominio/           # Entidades de dominio
├── enums/             # Enumeraciones
├── estados/           # Estados de reservas
├── factories/         # Factories para creación de servicios
├── incidentes/        # Sistema de manejo de incidentes
├── interfaces/        # Interfaces del sistema
├── notificaciones/    # Sistema de notificaciones
├── roles/             # Roles de empleados
├── servicios/         # Servicios base del sistema
├── strategies/        # Estrategias de pricing y compatibilidad
├── test/              # Pruebas unitarias
└── valueobjects/      # Objetos de valor (Money, Periodo, etc.)
```

## Cómo Ejecutar

### Requisitos
- Java JDK 11 o superior
- Maven 3.6+ (opcional, para ejecutar pruebas)

### Compilación y Ejecución

```bash
# Compilar el proyecto
javac -d build **/*.java

# Ejecutar la aplicación principal
java -cp build Main

# Ejecutar pruebas (con Maven)
mvn test
```

### Ejecutar desde IDE
1. Importa el proyecto en tu IDE favorito (IntelliJ IDEA, Eclipse, VS Code)
2. Ejecuta la clase `Main.java` para ver el sistema en acción
3. Ejecuta las pruebas desde la carpeta `test/`

##  Pruebas Unitarias

El proyecto incluye un conjunto completo de pruebas unitarias usando JUnit 5:

- Value Objects (Money, Periodo, Porcentaje)
- Entidades de Dominio (Usuario, Mascota)
- Builders (ReservaBuilder)
- Factories y Decoradores
- Estrategias y Adapters

Ver `test/README.md` para más detalles sobre las pruebas.

## Tecnologías

- **Lenguaje**: Java 11+
- **Testing**: JUnit 5
- **Build Tool**: Maven (opcional)


## 👥Autor

Proyecto desarrollado por estudiantes de la materia de Diseño de software en la universidad ESPOL.

## 📄 Licencia

Este proyecto es de uso educativo y demostrativo.

---

⭐ Si este proyecto te resultó útil, considera darle una estrella en GitHub.
