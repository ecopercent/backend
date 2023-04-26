package sudols.ecopercent.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.user.CreateUserRequest;
import sudols.ecopercent.dto.user.UpdateUserRequest;
import sudols.ecopercent.dto.user.UserResponse;
import sudols.ecopercent.exception.UserAlreadyExistsException;
import sudols.ecopercent.exception.UserNotExistsException;
import sudols.ecopercent.mapper.UserMapper;
import sudols.ecopercent.repository.ItemRepository;
import sudols.ecopercent.repository.UserRepository;
import sudols.ecopercent.security.JwtTokenProvider;
import sudols.ecopercent.security.OAuth2ResponseProvider;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2ResponseProvider oAuth2ResponseProvider;

    // TODO: 구현. 유저 생성 시 등록된 아이템을 대표 아이템으로 등록
    @Override
    public UserResponse createKakaoUser(HttpServletRequest request, HttpServletResponse response, CreateUserRequest createUserRequest) {
        User user = userRepository.findByEmail(createUserRequest.getEmail())
                .orElseGet(() -> userRepository.save(userMapper.createUserRequestToUser(createUserRequest)));
        oAuth2ResponseProvider.generateTokenAndReturnResponseWithCookie(response, user);
        return userMapper.userToUserResponse(user);
    }

    @Override
    public UserResponse createAppleUser(HttpServletRequest request, HttpServletResponse response, CreateUserRequest createUserRequest) {
        User user = userRepository.findByEmail(createUserRequest.getEmail())
                .orElseGet(() -> userRepository.save(userMapper.createUserRequestToUser(createUserRequest)));
        oAuth2ResponseProvider.generateTokenAndReturnResponseWithBody(user);
        return userMapper.userToUserResponse(user);
    }

    @Override
    public ResponseEntity<?> isNicknameDuplicate(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public UserResponse getCurrentUserInfo(HttpServletRequest request) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotExistsException(email));
        return userMapper.userToUserResponse(user);

    }

    @Override
    public UserResponse updateUser(HttpServletRequest request, UpdateUserRequest updateUserRequest) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotExistsException(email));
        BeanUtils.copyProperties(updateUserRequest, user, "id");
        userRepository.save(user);
        return userMapper.userToUserResponse(user);
    }

    @Override
    public void deleteUser(HttpServletRequest request) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        if (userRepository.existsByEmail(email)) {
            itemRepository.deleteByUser_Email(email);
            userRepository.deleteByEmail(email);
        } else {
            throw new UserNotExistsException(email);
        }
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
