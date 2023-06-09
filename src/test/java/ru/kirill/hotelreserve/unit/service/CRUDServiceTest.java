package ru.kirill.hotelreserve.unit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.kirill.hotelreserve.dto.UserRequest;
import ru.kirill.hotelreserve.dto.UserResponse;
import ru.kirill.hotelreserve.entity.User;
import ru.kirill.hotelreserve.exception.EntityNotFoundException;
import ru.kirill.hotelreserve.mapper.UserMapper;
import ru.kirill.hotelreserve.repository.UserRepository;
import ru.kirill.hotelreserve.service.UserService;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static ru.kirill.hotelreserve.util.TestObjectBuilder.*;

@ExtendWith(MockitoExtension.class)
public class CRUDServiceTest {

    @Mock
    private UserRepository repository;
    @Mock
    private UserMapper mapper;
    @InjectMocks
    private UserService service;

    @Test
    void shouldGetByCorrectId() {
        User expectedUser = getUser();
        doReturn(Optional.of(expectedUser))
                .when(repository)
                .findById(anyLong());
        UserResponse expectedResponse = getUserResponse();
        doReturn(expectedResponse)
                .when(mapper)
                .toDto(any(User.class));

        long correctId = 1;
        UserResponse actualResponse = service.get(correctId);

        assertThat(actualResponse)
                .isNotNull()
                .isEqualTo(expectedResponse);
        verify(repository).findById(correctId);
        verify(mapper).toDto(any(User.class));
        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void shouldNotGetByWrongId() {
        doReturn(Optional.empty())
                .when(repository)
                .findById(anyLong());
        long wrongId = -1;

        assertThrows(EntityNotFoundException.class, () -> service.get(wrongId));
        verify(repository).findById(wrongId);
    }

    @Test
    void shouldGetAll() {
        List<User> expectedUsers = getUserList();
        doReturn(expectedUsers)
                .when(repository)
                .findAll();
        UserResponse expectedResponse = getUserResponse();
        doReturn(expectedResponse)
                .when(mapper)
                .toDto(any(User.class));
        List<UserResponse> expectedResponses = getUserListResponse();

        List<UserResponse> actualResult = service.getAll();

        assertThat(actualResult)
                .isNotNull()
                .hasSize(expectedResponses.size());
        verify(repository).findAll();
        verify(mapper, times(expectedUsers.size())).toDto(any(User.class));
        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void shouldGetPage() {
        List<User> users = getUserList();
        int page = 0;
        int size = 2;
        Page<User> expectedPage = new PageImpl<>(
                users,
                PageRequest.of(page, size),
                users.size()
        );
        doReturn(expectedPage)
                .when(repository)
                .findAll(any(PageRequest.class));
        UserResponse expectedResponse = getUserResponse();
        doReturn(expectedResponse)
                .when(mapper)
                .toDto(any(User.class));

        List<UserResponse> actualResponse = service.getPage(page, size);

        assertThat(actualResponse)
                .isNotNull()
                .hasSize(expectedPage.getContent().size());
        verify(mapper, times(expectedPage.getContent().size())).toDto(any(User.class));
        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(mapper);
        ArgumentCaptor<PageRequest> pageRequestCaptor = ArgumentCaptor.forClass(PageRequest.class);
        verify(repository).findAll(pageRequestCaptor.capture());
        assertThat(pageRequestCaptor.getValue().getPageNumber()).isEqualTo(page);
        assertThat(pageRequestCaptor.getValue().getPageSize()).isEqualTo(size);
    }

    @Test
    void shouldCreate() {
        User user = getUser();
        doReturn(user)
                .when(mapper)
                .toEntity(any(UserRequest.class));
        doReturn(user)
                .when(repository)
                .save(user);
        UserResponse expectedResponse = getUserResponse();
        doReturn(expectedResponse)
                .when(mapper)
                .toDto(any(User.class));

        UserResponse actualResponse = service.create(getCorrectUserRequest());

        assertThat(actualResponse).isEqualTo(expectedResponse);
        verify(mapper).toDto(any(User.class));
        verify(mapper).toEntity(any(UserRequest.class));
        verify(repository).save(any(User.class));
        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void shouldUpdateWithCorrectId() {
        User user = getUser();
        doReturn(Optional.of(user))
                .when(repository)
                .findById(anyLong());
        User updatedUser = getUpdatedUser();
        doReturn(updatedUser)
                .when(repository)
                .saveAndFlush(any(User.class));
        UserResponse expectedResponse = getUserResponse();
        doReturn(expectedResponse)
                .when(mapper)
                .toDto(any(User.class));

        long id = 1;
        UserRequest updateUser = getUpdatedRequest();
        UserResponse actualResponse = service.update(id, updateUser);

        assertThat(actualResponse).isEqualTo(expectedResponse);
        verify(repository).findById(id);
        verify(mapper).updateEntity(any(UserRequest.class), any(User.class));
        verify(repository).saveAndFlush(any(User.class));
        verify(mapper).toDto(updatedUser);
        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void shouldNotUpdateWithWrongId() {
        doReturn(Optional.empty())
                .when(repository)
                .findById(anyLong());
        long wrongId = -1;

        UserRequest updateUser = getUpdatedRequest();
        assertThrows(EntityNotFoundException.class, () -> service.update(wrongId, updateUser));
        verify(repository).findById(wrongId);
    }

    @Test
    void shouldDeleteWithCorrectId() {
        User user = getUser();
        doReturn(Optional.of(user))
                .when(repository)
                .findById(anyLong());

        long id = 1;
        service.delete(id);

        verify(repository).findById(id);
        verify(repository).delete(any(User.class));
    }

    @Test
    void shouldNotDeleteWithWrongId() {
        doReturn(Optional.empty())
                .when(repository)
                .findById(anyLong());
        long wrongId = -1;

        assertThrows(EntityNotFoundException.class, () -> service.delete(wrongId));
        verify(repository).findById(wrongId);
    }
}
