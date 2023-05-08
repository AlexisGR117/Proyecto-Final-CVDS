package org.primefaces.oasis.service;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.primefaces.oasis.data.Admin;
import org.primefaces.oasis.exceptions.AdminException;
import org.primefaces.oasis.repository.AdminRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminSTest{
    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    private Admin admin;

    @BeforeEach
    public void setUp() {
        admin = new Admin();
        admin.setNombre("Santiago Administrador");
        admin.setContrasena("12345");
    }

    @Test
    void testAddAdmin() {
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);
        Admin adminGuardado = adminService.addAdmin(admin);
        verify(adminRepository, times(1)).save(admin);
        assertEquals(admin, adminGuardado);
    }

    @Test
    void testGetAdmin() {
        try {
            when(adminRepository.findById(anyString())).thenReturn(Optional.of(admin));
            Admin adminEncontrado = adminService.getAdmin("Santiago Administrador");
            verify(adminRepository, times(1)).findById("Santiago Administrador");
            assertEquals(admin, adminEncontrado);
        } catch (Exception e){
            fail("Threw a exception");
        }

    }

    @Test
    void testGetAllAdmin() {
        List<Admin> admins = new ArrayList<>();
        admins.add(admin);
        when(adminRepository.findAll()).thenReturn(admins);
        List<Admin> adminsEncontrados = adminService.getAllAdmin();
        verify(adminRepository, times(1)).findAll();
        assertEquals(admins, adminsEncontrados);
    }

    @Test
    void testUpdateAdmin() {
        when(adminRepository.existsById(anyString())).thenReturn(true);
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);
        Admin adminActualizado = adminService.updateAdmin(admin);
        verify(adminRepository, times(1)).existsById("Santiago Administrador");
        verify(adminRepository, times(1)).save(admin);
        assertEquals(admin, adminActualizado);
    }

    @Test
    void testDeleteAdmin() {
        doNothing().when(adminRepository).deleteById(anyString());
        adminService.deleteAdmin("Santiago Administrador");
        verify(adminRepository, times(1)).deleteById("Santiago Administrador");
    }

    @Test
    void testContrasenaIncorrecta(){
        when(adminRepository.findById("Santiago Administrador")).thenReturn(Optional.of(admin));
        try {
            adminService.login("Santiago Administrador", "78956");
            fail("Did not throw exception");
        } catch (AdminException e) {
            assertEquals(AdminException.CONSTRASENA_INVALIDA, e.getMessage());
        }
    }

    @Test
    void testUsuarioIncorrecto() {
        try {
            when(adminRepository.findById("Andres Administrador")).thenReturn(Optional.empty());
            adminService.login("Andres Administrador", "12345");
            fail("Did not throw exception");
        } catch (AdminException e) {
            assertEquals(AdminException.NOMBRE_INVALIDO, e.getMessage());
        }
    }

    @Test
    void testUsuarioCorrecto(){
        when(adminRepository.findById("Santiago Administrador")).thenReturn(Optional.of(admin));
        try {
            adminService.login("Santiago Administrador", "12345");
        } catch (Exception e){
            fail("Threw a exception");
        }
    }
}