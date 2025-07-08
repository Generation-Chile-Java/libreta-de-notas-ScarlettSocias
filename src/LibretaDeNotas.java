import java.util.*;

public class LibretaDeNotas {
    private HashMap<String, ArrayList<Double>> calificaciones;

    public LibretaDeNotas() {
        calificaciones = new HashMap<>();
    }

    public void agregarNotas(String alumno, ArrayList<Double> notas) {
        calificaciones.put(alumno, notas);
    }

    public double calcularPromedio(ArrayList<Double> notas) {
        double suma = 0;
        for (double nota : notas) suma += nota;
        return suma / notas.size();
    }

    public double calcularNotaMaxima(ArrayList<Double> notas) {
        return Collections.max(notas);
    }

    public double calcularNotaMinima(ArrayList<Double> notas) {
        return Collections.min(notas);
    }

    public double promedioCurso() {
        double suma = 0;
        int cantidadNotas = 0;
        for (ArrayList<Double> notas : calificaciones.values()) {
            for (double nota : notas) {
                suma += nota;
                cantidadNotas++;
            }
        }
        return suma / cantidadNotas;
    }

    public void mostrarPromedios() {
        System.out.println("\n📊 Promedio de Notas por Estudiante:");
        System.out.println("-------------------------------");
        for (Map.Entry<String, ArrayList<Double>> entrada : calificaciones.entrySet()) {
            String alumno = entrada.getKey();
            ArrayList<Double> notas = entrada.getValue();
            double promedio = calcularPromedio(notas);
            double max = calcularNotaMaxima(notas);
            double min = calcularNotaMinima(notas);

            System.out.printf("👤 %s\n", alumno);
            System.out.printf("   📌 Promedio: %.2f | 📈 Máxima: %.2f | 📉 Mínima: %.2f\n",
                    promedio, max, min);
            System.out.println("-------------------------------");
        }
    }

    public void evaluarNota(String alumno, double nota, double notaAprobacion) {
        System.out.println("\n🔎 Evaluación de Nota:");
        if (!calificaciones.containsKey(alumno)) {
            System.out.println("⚠️ Estudiante no encontrado.");
            return;
        }
        if (nota >= notaAprobacion) {
            System.out.println("✅ Nota aprobatoria.");
        } else {
            System.out.println("❌ Nota reprobatoria.");
        }
    }

    public void evaluarNotasRegistradas(String alumno, double notaAprobacion) {
        System.out.println("\n🧾 Evaluando todas las notas registradas de: " + alumno);
        if (!calificaciones.containsKey(alumno)) {
            System.out.println("⚠️ Estudiante no encontrado.");
            return;
        }
        ArrayList<Double> notas = calificaciones.get(alumno);
        for (int i = 0; i < notas.size(); i++) {
            double nota = notas.get(i);
            String estado = nota >= notaAprobacion ? "✅ Aprobada" : "❌ Reprobada";
            System.out.printf("Nota %d: %.2f -> %s\n", i + 1, nota, estado);
        }
    }

    public void compararConPromedioCurso(String alumno, double nota) {
        System.out.println("\n📈 Comparación con Promedio del Curso:");
        double promedioCurso = promedioCurso();
        if (!calificaciones.containsKey(alumno)) {
            System.out.println("⚠️ Estudiante no encontrado.");
            return;
        }
        if (nota > promedioCurso) {
            System.out.println("🔺 Nota por sobre el promedio del curso.");
        } else {
            System.out.println("🔻 Nota por debajo del promedio del curso.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibretaDeNotas libreta = new LibretaDeNotas();

        System.out.println("📝 Bienvenido al Sistema Evaluador de Clases\n");

        System.out.print("Ingrese la cantidad de alumnos: ");
        int cantidadAlumnos = scanner.nextInt();
        if (cantidadAlumnos <= 0) {
            System.out.println("⚠️ La cantidad de alumnos debe ser mayor que 0.");
            return;
        }

        System.out.print("Ingrese la cantidad de notas por alumno: ");
        int cantidadNotas = scanner.nextInt();
        if (cantidadNotas <= 0) {
            System.out.println("⚠️ La cantidad de notas debe ser mayor que 0.");
            return;
        }

        scanner.nextLine(); // limpiar buffer

        for (int i = 0; i < cantidadAlumnos; i++) {
            System.out.printf("\n👤 Alumno %d de %d\n", i + 1, cantidadAlumnos);

            String nombre;
            do {
                System.out.print("Ingrese el nombre del alumno: ");
                nombre = scanner.nextLine().trim();
                if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+")) {
                    System.out.println("⚠️ El nombre no puede contener números ni caracteres especiales.");
                    nombre = "";
                }
            } while (nombre.isEmpty());

            ArrayList<Double> notas = new ArrayList<>();
            for (int j = 0; j < cantidadNotas; j++) {
                double nota;
                do {
                    System.out.printf("Ingrese la nota %d (1.0 - 7.0): ", j + 1);
                    String inputNota = scanner.nextLine().replace(",", ".");
                    try {
                        nota = Double.parseDouble(inputNota);
                    } catch (NumberFormatException e) {
                        nota = -1;
                    }
                    if (nota < 1.0 || nota > 7.0) {
                        System.out.println("⚠️ Nota inválida. Debe estar entre 1.0 y 7.0.");
                    }
                } while (nota < 1.0 || nota > 7.0);
                notas.add(nota);
            }
            libreta.agregarNotas(nombre, notas);
        }

        int opcion;
        do {
            System.out.println("\n📋 Menú de Opciones:");
            System.out.println("1. Mostrar Promedio de Notas por Estudiante");
            System.out.println("2. Evaluar si la Nota es Aprobatoria o Reprobatoria");
            System.out.println("3. Comparar Nota con Promedio del Curso");
            System.out.println("4. Evaluar todas las notas registradas de un estudiante");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    libreta.mostrarPromedios();
                    break;
                case 2:
                    System.out.print("Ingrese el nombre del estudiante: ");
                    String alumnoEval = scanner.nextLine();
                    System.out.print("Ingrese la nota a evaluar: ");
                    String inputEval = scanner.nextLine().replace(",", ".");
                    double notaEval;
                    try {
                        notaEval = Double.parseDouble(inputEval);
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Nota inválida.");
                        break;
                    }
                    libreta.evaluarNota(alumnoEval, notaEval, 4.0);
                    break;
                case 3:
                    System.out.print("Ingrese el nombre del estudiante: ");
                    String alumnoComp = scanner.nextLine();
                    System.out.print("Ingrese la nota a comparar: ");
                    String inputComp = scanner.nextLine().replace(",", ".");
                    double notaComp;
                    try {
                        notaComp = Double.parseDouble(inputComp);
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Nota inválida.");
                        break;
                    }
                    libreta.compararConPromedioCurso(alumnoComp, notaComp);
                    break;
                case 4:
                    System.out.print("Ingrese el nombre del estudiante: ");
                    String alumnoNotas = scanner.nextLine();
                    libreta.evaluarNotasRegistradas(alumnoNotas, 4.0);
                    break;
                case 0:
                    System.out.println("👋 Gracias por usar el sistema. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("❌ Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }
}
