package org.primefaces.oasis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.primefaces.oasis.data.Admin;
import org.primefaces.oasis.repository.AdminRepository;
import org.primefaces.oasis.service.AdminService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminSTest{
    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    private Admin admin;

    @BeforeEach
    public void setUp() {
        admin = new Admin();
        admin.setId("Santiago Administrador");
        admin.setPassword("12345");
    }

    @Test
    public void testAddAdmin() {
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        Admin adminGuardado = adminService.addAdmin(admin);

        verify(adminRepository, times(1)).save(admin);
        assertEquals(admin, adminGuardado);
    }

    @Test
    public void testGetAdmin() {
        when(adminRepository.findById(anyString())).thenReturn(Optional.of(admin));

        Admin adminEncontrado = adminService.getAdmin("Santiago Administrador");

        verify(adminRepository, times(1)).findById("Santiago Administrador");
        assertEquals(admin, adminEncontrado);
    }

    @Test
    public void testGetAllAdmin() {
        List<Admin> admins = new ArrayList<>();
        admins.add(admin);

        when(adminRepository.findAll()).thenReturn(admins);

        List<Admin> adminsEncontrados = adminService.getAllAdmin();

        verify(adminRepository, times(1)).findAll();
        assertEquals(admins, adminsEncontrados);
    }

    @Test
    public void testUpdateAdmin() {
        when(adminRepository.existsById(anyString())).thenReturn(true);
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        Admin adminActualizado = adminService.updateAdmin(admin);

        verify(adminRepository, times(1)).existsById("Santiago Administrador");
        verify(adminRepository, times(1)).save(admin);
        assertEquals(admin, adminActualizado);
    }

    @Test
    public void testDeleteAdmin() {
        doNothing().when(adminRepository).deleteById(anyString());

        adminService.deleteAdmin("Santiago Administrador");

        verify(adminRepository, times(1)).deleteById("Santiago Administrador");
    }
}
