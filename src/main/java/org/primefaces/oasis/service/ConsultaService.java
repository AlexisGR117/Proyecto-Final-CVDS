package org.primefaces.oasis.service;

import org.primefaces.oasis.data.Consulta;
import org.primefaces.oasis.data.ConsultaId;
import org.primefaces.oasis.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService implements Serializable {
    private final ConsultaRepository consultaRepository;
    @Autowired
    public ConsultaService(ConsultaRepository consultaRepository){
        this.consultaRepository = consultaRepository;
    }
    public Consulta addConsulta(Consulta consulta){
        return consultaRepository.save(consulta);
    }
    public Optional<Consulta> getConsulta(ConsultaId consultaId){
        return consultaRepository.findById(consultaId);
    }
    public List<Consulta> getAllConsultas(){
        return consultaRepository.findAll();
    }

    public Consulta updateConsulta(Consulta consulta){
        if(consultaRepository.existsById(consulta.getId())){
            return consultaRepository.save(consulta);
        }
        return null;
    }
    public void deleteConsulta(ConsultaId consultaId){
        consultaRepository.deleteById(consultaId);
    }


}
