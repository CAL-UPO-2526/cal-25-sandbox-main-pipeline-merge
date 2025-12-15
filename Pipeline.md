### 📌 **1. ¿Quién debe hacer los commits del pipeline y configuración?**

Según los roles que definiste:

| Rol                        | Responsabilidad relevante                                              | ¿Debe editar pipeline?                                                            |
| -------------------------- | ---------------------------------------------------------------------- | --------------------------------------------------------------------------------- |
| **QA Lead / Scrum Master** | Facilita, organiza releases, puede asumir tareas si el equipo no puede | **Puede apoyar, pero no es su tarea principal**                                   |
| **Tester**                 | Escribe código de test                                                 | ❌ No debe crear el pipeline                                                       |
| **Tester QA**              | Valida PR de test, diseña test plan                                    | **🟢 Debe encargarse de la calidad del código y PR → SÍ puede crear el pipeline** |

📌 **Conclusión clara:**
El **Tester QA** debe **crear, mantener y validar** el pipeline de calidad.
El **QA Lead** puede intervenir si hay bloqueos o backlog sin dueño.

---

### 🔄 **2. Flujo recomendado para una Pull Request**

```
Desarrollador hace commit → PR
       ↓
Pipeline (GitHub Actions):
   ✔ Ejecuta tests
   ✔ Ejecuta análisis en SonarQube
   ✔ Revisa cobertura, duplicados, vulnerabilidades, complejidad
       ↓
Si NO pasa SonarQube → ❌ Se rechaza automáticamente
Si pasa:
       ↓
Tester QA revisa la PR
    ↓
Acepta/Rechaza manualmente
```

💡 **La clave:** La decisión final siempre la hace el **Tester QA**, pero SonarQube puede bloquear antes.

---

### 🧪 **3. Pipeline para GitHub Actions + SonarQube (Ejemplo)**

📍 Supongamos un proyecto **Java / Maven**, pero puedo adaptarlo a JS, Python, C#, etc. si me lo dices.

```yaml
name: Quality Pipeline

on:
  pull_request:
    branches: [ "main", "develop" ]

jobs:
  quality-check:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout repository
        uses: actions/checkout@v3
        with:
          fetch-depth: 0

      - name: Set up JDK
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Install dependencies & Run tests
        run: mvn clean verify

      - name: SonarQube Scan
        uses: sonarsource/sonarqube-scan-action@v1.0.2
        with:
          host-url: ${{ secrets.SONAR_HOST_URL }}
          token: ${{ secrets.SONAR_TOKEN }}

      - name: SonarQube Quality Gate
        uses: sonarsource/sonarqube-quality-gate-action@v1.1.0
        with:
          host-url: ${{ secrets.SONAR_HOST_URL }}
          token: ${{ secrets.SONAR_TOKEN }}
```

🔐 **SONAR_HOST_URL y SONAR_TOKEN** deben guardarse como *secrets* en el repositorio.

---

### 🧭 **4. ¿Qué métricas debe bloquear SonarQube?**

| Métrica                 | Umbral recomendado       | Si falla… |
| ----------------------- | ------------------------ | --------- |
| Cobertura mínima test   | ≥ 80%                    | ❌ Bloquea |
| Comentarios             | ≥ 15% del código público | ❌ Bloquea |
| Duplicidad              | < 5%                     | ❌ Bloquea |
| Bugs                    | 0                        | ❌ Bloquea |
| Vulnerabilidades        | 0                        | ❌ Bloquea |
| Complejidad ciclomática | < 12 por función         | ❌ Bloquea |
| Code smells críticos    | 0                        | ❌ Bloquea |

📌 Esto convierte a SonarQube en un **primer filtro automático**, y al **Tester QA en el segundo filtro manual**.
