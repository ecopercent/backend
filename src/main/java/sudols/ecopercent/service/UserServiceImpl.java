package sudols.ecopercent.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.user.CreateUserRequest;
import sudols.ecopercent.dto.user.UpdateUserRequest;
import sudols.ecopercent.dto.user.UserResponse;
import sudols.ecopercent.exception.UserAlreadyExistsException;
import sudols.ecopercent.mapper.UserMapper;
import sudols.ecopercent.repository.ItemRepository;
import sudols.ecopercent.repository.UserRepository;
import sudols.ecopercent.security.OAuth2Provider;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final UserMapper userMapper;
    private final OAuth2Provider oAuth2Provider;

    // TODO: 구현. 유저 생성 시 등록된 아이템을 대표 아이템으로 등록
    @Override
    public UserResponse createUser(HttpServletRequest request, HttpServletResponse response, CreateUserRequest createUserRequest) {
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new UserAlreadyExistsException(createUserRequest.getEmail());
        }
        User user = userRepository.save(userMapper.createUserRequestToUser(createUserRequest));
        oAuth2Provider.generateTokenAndReturnResponseWithCookie(response, user);
        return userMapper.userToUserResponse(user);
    }

    @Override
    public ResponseEntity<?> isNicknameDuplicate(String nickname) {
        boolean isDuplicate = userRepository.existsByNickname(nickname);
        if (isDuplicate) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public Optional<UserResponse> getUser(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::userToUserResponse);
    }

    @Override
    public Optional<UserResponse> updateUser(Long userId, UpdateUserRequest updateUserRequest) {
        return userRepository.findById(userId)
                .map(user -> {
                    BeanUtils.copyProperties(updateUserRequest, user, "id");
                    return userRepository.save(user);
                })
                .map(userMapper::userToUserResponse);
    }

    @Override
    public void deleteUser(Long userId) {
        itemRepository.deleteByUser_Id(userId);
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserResponse> getAllUser() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::userToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllUser() {
        itemRepository.deleteAll();
        userRepository.deleteAll();
    }
}
