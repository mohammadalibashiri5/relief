package com.mohammad.relief.service;

import com.mohammad.relief.data.dto.request.UserRequestDto;
import com.mohammad.relief.data.entity.User;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @Captor
    ArgumentCaptor<User> captor;

    @Test
    void createNullUser() {
        ReliefApplicationException reliefException = assertThrows(ReliefApplicationException.class, () -> userService.registerUser(null));
        assertEquals("UserRequestDto is null", reliefException.getMessage());
    }

//    @Test
//    void creatClientMineur() {
//        ClientDto clientDto = new ClientDto(1, "mimi", "momo", "ldfldlfjl@gmail", "fdfdfd", new AdresseDto(1, "3", "dfdfd", "Nantes"), LocalDate.of(2010, 2, 2), LocalDate.now(), List.of(Permis.C));
//        ApplicationException ue = assertThrows(ApplicationException.class, () -> userService.createClient(clientDto));
//        assertEquals("Le Client Doit être Majeur", ue.getMessage());
//    }
//
//    @Test
//    void createClientMajeur() {
//        ClientDto clientDto = new ClientDto(1, "mimi", "momo", "ldfldlfjl@gmail", "fdfdfd", new AdresseDto(1, "3", "dfdfd", "Nantes"), LocalDate.of(2000, 2, 2), LocalDate.now(), List.of(Permis.C));
//        Client client = userMapper.clientDtoToClient(clientDto);
//        assertDoesNotThrow(() -> clientDao.save(client));
//    }
//
//    @Test
//    void createClientEmailAlreadyExist() {
//        when(userRepository.save(any())).thenThrow(DataIntegrityViolationException.class);
//        UserRequestDto userRequestDto = new UserRequestDto("Test", "TesTson", "testison", "test@test.com","Test12345@", LocalDate.of(2004,8,3));
//        User user1 = new User( "testison", "test@test.com" ,"Test12345@", LocalDateTime.now(), LocalDateTime.now(), "USER", LocalDate.of(2004,8,3));
//        ReliefApplicationException userException = assertThrows(ReliefApplicationException.class, () -> userService.registerUser(userRequestDto));
//        assertEquals("Le Mail Existe Déjà", userException.getMessage());
//    }

    @Test
    void readAClientWhoIsNotInDatabase() {

        ReliefApplicationException userException = assertThrows(ReliefApplicationException.class, () -> userService.getUserDetails("dupont"));

        assertEquals("User not found", userException.getMessage());

      }
//
//    @Test
//    void readClientOk() {
//        Client client = new Client("mimi", "momo", "ldfldlfjl@gmail", "fdfdfd", new Adresse("64", "dfdfd", "Nantes"), LocalDate.of(2000, 2, 2), LocalDate.now(), List.of(Permis.C), true);
//        Optional<Client> optClient = Optional.of(client);
//        Mockito.when(clientDao.findByEmail("ldfldlfjl@gmail.com")).thenReturn(optClient);
//        ClientDto clientDto = new ClientDto(1, "mimi", "momo", "ldfldlfjl@gmail", "fdfdfd", new AdresseDto(1, "3", "dfdfd", "Nantes"), LocalDate.of(2010, 2, 2), LocalDate.now(), List.of(Permis.C));
//        Mockito.when(userMapper.clientToClientDto(any())).thenReturn(clientDto);
//        Optional<ClientDto> clientDtoOpt = userService.readClient("ldfldlfjl@gmail.com");
//        assertTrue(clientDtoOpt.isPresent());
//    }
//
//    @Test
//    void deleteClientPasEnBase() {
//        ClientDto clientDto = new ClientDto(1, "mimi", "momo", "ldfldlfjl@gmail", "fdfdfd", new AdresseDto(1, "3", "dfdfd", "Nantes"), LocalDate.of(2000, 2, 2), LocalDate.now(), List.of(Permis.C));
//        Mockito.when(clientDao.findByEmail(any())).thenReturn(Optional.empty());
//        Exception ue = assertThrows(NoSuchElementException.class, () -> userService.deleteClient(clientDto.email()));
//        assertEquals("No value present", ue.getMessage());
//    }
//
//    @Test
//    void deleteClientOK() {
//        ClientDto clientDto = new ClientDto(1, "mimi", "momo", "ldfldlfjl@gmail", "fdfdfd", new AdresseDto(1, "6", "dfdfd", "Nantes"), LocalDate.of(2000, 2, 2), LocalDate.now(), List.of(Permis.C));
//        Client client = new Client("mimi", "momo", "ldfldlfjl@gmail", "fdfdfd", new Adresse("A1", "dfdfd", "Nantes"), LocalDate.of(2000, 2, 2), LocalDate.now(), List.of(Permis.C), true);
//        Mockito.when(clientDao.findByEmail(clientDto.email())).thenReturn(Optional.of(client));
//        assertDoesNotThrow(() -> userService.deleteClient(clientDto.email()));
//        Mockito.verify(clientDao, Mockito.times(1)).delete(captor.capture());
//        assertNotNull(captor.getValue());
//    }
//
//    @Test
//    void updateClientPasEnbase() {
//        ClientDto clientDto = new ClientDto(1, "mimi", "momo", "ldfldlfjl@gmail", "fdfdfd", new AdresseDto(1, "3", "dfdfd", "Nantes"), LocalDate.of(2000, 2, 2), LocalDate.now(), List.of(Permis.C));
//        Mockito.when(clientDao.findByEmail(any())).thenReturn(Optional.empty());
//        ApplicationException ue = assertThrows(ApplicationException.class, () -> userService.updateClient(clientDto,clientDto.email()));
//        assertEquals("Ce Client n'existe Pas", ue.getMessage());
//    }
//
//    @Test
//    void updateClientOK() {
//        Adresse adresse = new Adresse("6", "44200", "Nantes");
//        Adresse adresse2 = new Adresse("8", "5500", "Nantes");
//        AdresseDto adresseDto1 = new AdresseDto(1,"6","44200","Nantes");
//        Mockito.when(userMapper.adresseDtoToAdresse(adresseDto1)).thenReturn(adresse);
//        ClientDto clientDto = new ClientDto(1, "mimi", "momo", "ldfldlfjl@gmail", "fdfdfd", adresseDto1, LocalDate.of(2000, 2, 2), LocalDate.now(),List.of(Permis.A) );
//        Client client = new Client("mimi", "momo", "ldfldlfjl@gmail", "fdfdfd", adresse2, LocalDate.of(2000, 2, 2), LocalDate.now(),List.of(Permis.A)  , true);
//        Mockito.when((clientDao.findByEmail( ArgumentMatchers.anyString()))).thenReturn(Optional.of(client));
//
//        assertDoesNotThrow(() -> userService.updateClient(clientDto,clientDto.email()));
//        Mockito.verify(clientDao).save(captor.capture());
//
//        assertEquals(0, captor.getValue().getId());
//        assertEquals("mimi", captor.getValue().getNom());
//        assertEquals("momo", captor.getValue().getPrenom());
//        assertEquals("6", captor.getValue().getAdresse().getRue());
//        assertEquals("44200", captor.getValue().getAdresse().getCodePostal());
//        assertEquals("Nantes", captor.getValue().getAdresse().getVille());
//        assertEquals(LocalDate.of(2000, 2, 2), captor.getValue().getDateDeNaissance());
//        assertEquals(List.of(Permis.A), captor.getValue().getPermisList());
//
//    }
//
//    @Test
//    void createAdminNull() {
//        ApplicationException ue = assertThrows(ApplicationException.class, () -> userService.createAdmin(null));
//        assertEquals("Admin null ne peut pas exister", ue.getMessage());
//    }
//    @Test
//    void createAdminEmailExistante() {
//        when(adminDao.save(any())).thenThrow(DataIntegrityViolationException.class);
//        AdminDto adminDto = new AdminDto(1,"Bashiri","Mohammad","mohammadalibashiri@yahoo.com","password123@@@","Manager");
//        Admin admin = new Admin ("Bashiri","Mohammad Ali","mohammadalibashiri@yahoo.com","password123@@@","Manager");
//        Mockito.when(userMapper.admindtoToAdmin(adminDto)).thenReturn(admin);
//        ApplicationException userException = assertThrows(ApplicationException.class, () -> userService.createAdmin(adminDto));
//        assertEquals("Le Mail Existe Déjà", userException.getMessage());
//    }
}


//    @Test
//    void registerUser() {
//        //GIVEN
//        //WHEN
//        //THEN
//    }
//
//    @Test
//    void updateUser() {
//    }
//
//    @Test
//    void getUserDetails() {
//    }
//
//    @Test
//    void deleteUser() {
//    }
//
//    @Test
//    void findByUsername() {
//    }
//}