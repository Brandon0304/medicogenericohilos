package org.brandon.threads;

import org.brandon.entity.Medico;
import org.brandon.service.MedicoService;

public class MedicoThread extends Thread {

    private final Medico medico;
    private final MedicoService service;

    public MedicoThread(Medico medico, MedicoService service) {
        this.medico = medico;
        this.service = service;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            service.crearMedico(medico);
            System.out.println("Medico " + medico.getNombre() + " procesado por " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

