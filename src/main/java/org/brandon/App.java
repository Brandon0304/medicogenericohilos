package org.brandon;

import org.brandon.entity.Medico;
import org.brandon.entity.EstadoMedico;
import org.brandon.service.MedicoService;
import org.brandon.threads.MedicoThread;

import java.util.List;
import java.util.Scanner;

public class App {

    private static final MedicoService medicoService = new MedicoService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- SISTEMA DE INFORMACIÓN DE MÉDICOS ---");
            System.out.println("1. Crear médico");
            System.out.println("2. Leer médico");
            System.out.println("3. Actualizar médico");
            System.out.println("4. Eliminar médico");
            System.out.println("5. Listar todos los médicos");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1 -> crearMedico();
                case 2 -> leerMedico();
                case 3 -> actualizarMedico();
                case 4 -> eliminarMedico();
                case 5 -> listarMedicos();
                case 6 -> salir = true;
                default -> System.out.println("Opción no válida");
            }
        }
        medicoService.cerrar();
        scanner.close();
    }

    private static void crearMedico() {
        System.out.print("Nombre del medico: ");
        String nombre = scanner.nextLine();
        System.out.println("Edad del medico: ");
        int edad = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        Medico medico = new Medico();
        medico.setNombre(nombre);
        medico.setEdad(edad);
        medico.setEstado(EstadoMedico.ACTIVO);

        // Crear hilos para procesar médicos
        MedicoThread thread = new MedicoThread(medico, medicoService);
        thread.start();

        System.out.println("Medico creado con éxito");
    }

    private static void leerMedico() {
        System.out.print("ID del medico: ");
        Long id = scanner.nextLong();
        Medico medico = medicoService.obtenerMedico(id);
        if (medico != null) {
            System.out.println(medico);
        } else {
            System.out.println("Medico no encontrado");
        }
    }

    private static void actualizarMedico() {
        System.out.print("ID del medico a actualizar: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consumir el salto de línea
        Medico medico = medicoService.obtenerMedico(id);
        if (medico != null) {
            System.out.print("Nuevo nombre: ");
            String nombre = scanner.nextLine();
            medico.setNombre(nombre);
            medicoService.actualizarMedico(medico);
            System.out.println("Medico actualizado con éxito");
        } else {
            System.out.println("Medico no encontrado");
        }
    }

    private static void eliminarMedico() {
        System.out.print("ID del medico a eliminar: ");
        Long id = scanner.nextLong();
        medicoService.eliminarMedico(id);
        System.out.println("Medico eliminado con éxito");
    }

    private static void listarMedicos() {
        List<Medico> medicos = medicoService.obtenerTodosLosMedicos();
        for (Medico medico : medicos) {
            System.out.println(medico);
        }
    }
}
