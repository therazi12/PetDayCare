# Pruebas Unitarias - PetDayCare

Este directorio contiene las pruebas unitarias para el sistema PetDayCare.

## Estructura

Las pruebas están organizadas siguiendo la misma estructura de paquetes que el código fuente:



## Requisitos

Para ejecutar las pruebas necesitas:

1. **Java JDK 11 o superior**
2. **JUnit 5 (Jupiter)**
   - `junit-jupiter-api` (versión 5.9.0 o superior)
   - `junit-jupiter-engine` (versión 5.9.0 o superior)

## Configuración

### Opción 1: Usando Maven (Recomendado)

Si decides usar Maven, crea un archivo `pom.xml` en la raíz del proyecto:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.petdaycare</groupId>
    <artifactId>petdaycare</artifactId>
    <version>1.0.0</version>
    
    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <junit.version>5.9.2</junit.version>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    <build>
        <sourceDirectory>.</sourceDirectory>
        <testSourceDirectory>test</testSourceDirectory>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
                <configuration>
                    <source>11</source>
                    <target>11</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.0.0</version>
            </plugin>
        </plugins>
    </build>
</project>
```

Luego ejecuta:
```bash
mvn test
```

### Opción 2: Usando IDE (IntelliJ IDEA, Eclipse, VS Code)

1. **IntelliJ IDEA:**
   - Abre el proyecto
   - Click derecho en la carpeta `test/` → "Run All Tests"
   - O ejecuta cada clase de prueba individualmente

2. **Eclipse:**
   - Importa el proyecto
   - Click derecho en la carpeta `test/` → "Run As" → "JUnit Test"

3. **VS Code:**
   - Instala la extensión "Java Test Runner"
   - Las pruebas aparecerán automáticamente con botones para ejecutarlas

### Opción 3: Compilación Manual

Si prefieres compilar manualmente:

```bash
# Compilar código fuente
javac -d build/ *.java **/*.java

# Compilar pruebas (necesitas tener JUnit en el classpath)
javac -cp ".:junit-jupiter-api-5.9.2.jar:junit-jupiter-engine-5.9.2.jar" \
      -d build/test test/**/*.java

# Ejecutar pruebas
java -cp "build:build/test:junit-jupiter-api-5.9.2.jar:junit-jupiter-engine-5.9.2.jar" \
     org.junit.platform.console.ConsoleLauncher --class-path build/test
```

## Ejecutar Pruebas Específicas

### Desde línea de comandos (Maven):
```bash
# Ejecutar todas las pruebas
mvn test

# Ejecutar pruebas de una clase específica
mvn test -Dtest=MoneyTest

# Ejecutar pruebas de un paquete
mvn test -Dtest=valueobjects.*
```

### Desde IDE:
- Click derecho en la clase de prueba → "Run"
- Click derecho en el método de prueba → "Run"

## Cobertura de Pruebas

Las pruebas cubren:

- **Value Objects**: Money, Periodo, Porcentaje
- **Entidades de Dominio**: Usuario, Mascota
- **Builders**: ReservaBuilder
- **Factories**: CentroGrandeFactory, CentroPequenaFactory
- **Decoradores**: CamaraEnVivoDecorator, ReporteTiempoRealDecorator, AtencionVeterinariaDecorator
- **Estrategias**: PricingStrategyStandard, PricingStrategyPremium
- **Adapters**: PayPalAdapter, StripeAdapter

## Agregar Nuevas Pruebas

Para agregar nuevas pruebas:

1. Crea una clase de prueba en el paquete correspondiente
2. Usa la anotación `@Test` para métodos de prueba
3. Usa `@DisplayName` para nombres descriptivos
4. Usa `@BeforeEach` para configuración inicial
5. Usa assertions de JUnit 5 (`assertEquals`, `assertTrue`, `assertThrows`, etc.)

Ejemplo:
```java
package test.dominio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas para MiClase")
class MiClaseTest {
    
    @Test
    @DisplayName("Mi método debe hacer X")
    void miMetodoTest() {
        // Arrange
        MiClase objeto = new MiClase();
        
        // Act
        String resultado = objeto.miMetodo();
        
        // Assert
        assertEquals("esperado", resultado);
    }
}
```

## Notas

- Las pruebas están diseñadas para ser independientes y ejecutarse en cualquier orden
- Cada prueba debe poder ejecutarse de forma aislada


