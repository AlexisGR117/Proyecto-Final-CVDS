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
import org.primefaces.oasis.exceptions.ConsultasException;
import org.primefaces.oasis.repository.ConsultaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultaSTest {
    LocalDate date = LocalDate.now();
    LocalTime time = LocalTime.now();
    @Mock
    private ConsultaRepository consultaRepository;
    @InjectMocks
    private ConsultaService consultaService;
    private Consulta consulta;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setUsuarioId(1L);
        usuario.setNombre("Juan Perez");
        usuario.setEmail("juan.perez@example.com");
        consulta = new Consulta();
        consulta.setId(new ConsultaId(date, time));
        consulta.setUsuario(usuario);
        consulta.setRazonConsulta("PRUEBA");
    }

    @Test
    void pruebaAgregarConsulta() {
        try {
            when(consultaRepository.save(any(Consulta.class))).thenReturn(consulta);
            Consulta consultaGuardada = consultaService.addConsulta(consulta);
            verify(consultaRepository, times(1)).save(consulta);
            assertEquals(consulta, consultaGuardada);
        } catch (Exception e) {
            fail("Threw a exception");
        }
    }

    @Test
    void pruebaObtenerConsulta() {
        when(consultaRepository.findById(any(ConsultaId.class))).thenReturn(Optional.of(consulta));
        Optional<Consulta> consultaEncontrada = consultaService.getConsulta(new ConsultaId(date, time));
        verify(consultaRepository, times(1)).findById(new ConsultaId(date, time));
        assertEquals(consulta, consultaEncontrada.get());
    }

    @Test
    void pruebaObtenerTodasLasConsultas() {
        List<Consulta> consultasL = new ArrayList<>();
        consultasL.add(consulta);
        when(consultaRepository.findAll()).thenReturn(consultasL);
        List<Consulta> consultasEncontradas = consultaService.getAllConsultas();
        verify(consultaRepository, times(1)).findAll();
        assertEquals(consultasL, consultasEncontradas);
    }

    @Test
    void pruebaActualizarConsulta() {
        when(consultaRepository.existsById(any(ConsultaId.class))).thenReturn(true);
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consulta);
        Consulta consultaActualizado = consultaService.updateConsulta(consulta);
        verify(consultaRepository, times(1)).existsById(new ConsultaId(date, time));
        verify(consultaRepository, times(1)).save(consulta);
        assertEquals(consulta, consultaActualizado);
    }

    @Test
    void pruebaEliminarConsulta() {
        doNothing().when(consultaRepository).deleteById(any(ConsultaId.class));
        consultaService.deleteConsulta(new ConsultaId(date, time));

        verify(consultaRepository, times(1)).deleteById(new ConsultaId(date, time));
    }

    @Test
    void  pruebaObtenerSoloLasConsultasDisponibles() {
        try {
            Consulta con1 = new Consulta();
            con1.setId(new ConsultaId(LocalDate.of(2023, 5, 6), LocalTime.of(7, 0, 0)));
            Consulta con2 = new Consulta();
            con2.setId(new ConsultaId(LocalDate.of(2023, 5, 6), LocalTime.of(7, 30, 0)));
            Consulta con3 = new Consulta();
            con3.setId(new ConsultaId(LocalDate.of(2023, 5, 6), LocalTime.of(8, 0, 0)));
            LocalDate fecha = LocalDate.of(2023, 5, 6);
            ArrayList<Consulta> consultas = new ArrayList<>();
            consultas.add(con1);
            consultas.add(con2);
            consultas.add(con3);
            when(consultaRepository.findByIdFecha(fecha)).thenReturn(consultas);
            List<String> horas = consultaService.getConsultasFecha(fecha);
            assertFalse(horas.contains(LocalTime.of(7, 0, 0).toString()));
            assertFalse(horas.contains(LocalTime.of(7, 30, 0).toString()));
            assertFalse(horas.contains(LocalTime.of(8, 0, 0).toString()));
        } catch (ConsultasException e) {
            fail("Threw a exception");
        }
    }
}
