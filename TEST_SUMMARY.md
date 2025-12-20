# Resumen de Pruebas Unitarias - PetDayCare

## Pruebas Creadas

Se han creado **8 pruebas** que cubren los componentes principales del sistema:

### 1. Value Objects (3 suites)
- **MoneyTest.java** - 15 pruebas
  - Creación y validación
  - Operaciones matemáticas (sumar, restar, multiplicar)
  - Comparaciones
  - Validación de monedas
  
- **PeriodoTest.java** - 16 pruebas
  - Creación y validación de períodos
  - Cálculo de duración
  - Verificación de solapamiento
  - Clasificación (diario, semanal, mensual)
  
- **PorcentajeTest.java** - 11 pruebas
  - Creación desde diferentes formatos
  - Aplicación a valores Money
  - Validación de rangos

### 2. Entidades de Dominio (2 suites)
- **UsuarioTest.java** - 8 pruebas
  - Creación y validación
  - Registro de mascotas
  - Validación de email
  
- **MascotaTest.java** - 6 pruebas
  - Creación y validación
  - Gestión de perfil
  - Verificación de cachorros

### 3. Builders (1 suite)
- **ReservaBuilderTest.java** - 6 pruebas
  - Construcción completa de reservas
  - Validación de datos obligatorios
  - Interfaz fluida
  - Múltiples servicios

### 4. Factories (1 suite)
- **CentroFactoryTest.java** - 5 pruebas
  - Creación de servicios para centros grandes
  - Creación de servicios para centros pequeños
  - Verificación de implementación de interfaces

### 5. Decoradores (1 suite)
- **DecoradorTest.java** - 6 pruebas
  - Decoradores individuales
  - Encadenamiento de decoradores
  - Aumento de precios

### 6. Estrategias (1 suite)
- **PricingStrategyTest.java** - 6 pruebas
  - Cálculo de precios estándar
  - Cálculo de precios premium
  - Ajustes por período
  - Inclusión de opciones

### 7. Adapters (1 suite)
-  **PagoAdapterTest.java** - 8 pruebas
  - Autorización de pagos (PayPal y Stripe)
  - Captura de pagos
  - Reembolsos
  - Manejo de errores

## Estadísticas
- **Total de suites de prueba**: 8
- **Total de pruebas**: ~61 pruebas unitarias

## Cómo Ejecutar

### Con Maven (Recomendado):
```bash
mvn test
```

### Con IDE:
- **IntelliJ IDEA**: Click derecho en `test/` → "Run All Tests"
- **Eclipse**: Click derecho en `test/` → "Run As" → "JUnit Test"
- **VS Code**: Instalar extensión "Java Test Runner"

##  Notas

1. **JUnit 5 requerido**: Las pruebas usan JUnit 5 .

2. **Errores de compilación esperados**: Si ves errores de compilación en el IDE, es porque JUnit no está en el classpath. Esto se resuelve al usar Maven o configurar las dependencias manualmente.




Ver `test/README.md` para instrucciones detalladas sobre cómo ejecutar y agregar nuevas pruebas.

