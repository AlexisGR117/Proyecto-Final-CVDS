package org.primefaces.oasis.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.primefaces.oasis.data.Consulta;
import org.primefaces.oasis.data.ConsultaId;
import org.primefaces.oasis.data.Usuario;
import org.primefaces.oasis.repository.ConsultaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsultaSTest {
    @Mock
    private ConsultaRepository consultaRepository;
    @InjectMocks
    private ConsultaService consultaService;

    private Consulta consulta;
    private Usuario usuario;
    LocalDate date = LocalDate.now();
    LocalTime time = LocalTime.now();


    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setUsuaioId(1L);
        usuario.setNombreUsuario("Juan Perez");
        usuario.setEmailUsuario("juan.perez@example.com");
        consulta = new Consulta();
        consulta.setId(new ConsultaId(date,time));
        consulta.setUsuario(usuario);
        consulta.setRazonConsulta("PRUEBA");
    }

    @Test
    public void testAnadirConsulta(){
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consulta);
        Consulta consultaGuardada = consultaService.addConsulta(consulta);
        verify(consultaRepository, times(1)).save(consulta);
        assertEquals(consulta,consultaGuardada);
    }

    @Test
    public void testObtenerConsulta(){
        when(consultaRepository.findById(any(ConsultaId.class))).thenReturn(Optional.of(consulta));
        Optional<Consulta> consultaEncontrada = consultaService.getConsulta(new ConsultaId(date, time));
        verify(consultaRepository, times(1)).findById(new ConsultaId(date, time));
        assertEquals(consulta,consultaEncontrada.get());
    }
    @Test
    public void testObtenerTodasLasConsultas(){
        List<Consulta> consultasL = new ArrayList<>();
        consultasL.add(consulta);
        when(consultaRepository.findAll()).thenReturn(consultasL);
        List<Consulta> consultasEncontradas = consultaService.getAllConsultas();
        verify(consultaRepository, times(1)).findAll();
        assertEquals(consultasL,consultasEncontradas);
    }
    @Test
    public void testActualizarConsultas(){
        when(consultaRepository.existsById(any(ConsultaId.class))).thenReturn(true);
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consulta);
        Consulta consultaActualizado = consultaService.updateConsulta(consulta);
        verify(consultaRepository, times(1)).existsById(new ConsultaId(date, time));
        verify(consultaRepository, times(1)).save(consulta);
        assertEquals(consulta,consultaActualizado);
    }
}
