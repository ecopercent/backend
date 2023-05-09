package sudols.ecopercent.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.item.CreateItemRequest;
import sudols.ecopercent.dto.item.ItemResponse;
import sudols.ecopercent.dto.oauth2.apple.AppleTokenResponse;
import sudols.ecopercent.dto.user.CreateUserRequest;
import sudols.ecopercent.dto.user.UpdateUserRequest;
import sudols.ecopercent.dto.user.UserResponse;
import sudols.ecopercent.exception.NicknameAlreadyExistsException;
import sudols.ecopercent.exception.UserAlreadyExistsException;
import sudols.ecopercent.exception.UserNotExistsException;
import sudols.ecopercent.mapper.UserMapper;
import sudols.ecopercent.repository.ItemRepository;
import sudols.ecopercent.repository.UserRepository;
import sudols.ecopercent.security.JwtTokenProvider;
import sudols.ecopercent.security.OAuth2ResponseProvider;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ItemService itemService;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2ResponseProvider oAuth2ResponseProvider;

    @Override
    public UserResponse createKakaoUser(HttpServletRequest request, HttpServletResponse response,
                                        CreateUserRequest createUserRequest, MultipartFile profileImageMultipartFile,
                                        CreateItemRequest createTumblerRequest, MultipartFile tumblerImageMultipartFile,
                                        CreateItemRequest createEcobagRequest, MultipartFile ecobagImageMultipartFile) {
        if (userRepository.existsByNickname(createUserRequest.getNickname())) {
            throw new NicknameAlreadyExistsException(createUserRequest.getNickname());
        }
        String email = jwtTokenProvider.getEmailFromRequest(request);
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }
        final String domain = "https://www.ecopercent.com";
        User user = userMapper.createUserRequestToUser(createUserRequest);
        user.setEmail(email);
        try {
            user.setProfileImage(profileImageMultipartFile.getBytes());
        } catch (Exception e) {
            user.setProfileImage(null);
        }
        userRepository.save(user);
        oAuth2ResponseProvider.generateAccessRefreshTokenAndAddTokenCookie(response, user, domain);
        if (createTumblerRequest != null) {
            ItemResponse tumblerResponse = itemService.createItem(request, createTumblerRequest, tumblerImageMultipartFile);
            itemService.changeTitleTumbler(request, tumblerResponse.getId());
        }
        if (createEcobagRequest != null) {
            ItemResponse ecobagResponse = itemService.createItem(request, createEcobagRequest, ecobagImageMultipartFile);
            itemService.changeTitleEcobag(request, ecobagResponse.getId());
        }
        return userMapper.userToUserResponse(user);
    }

    @Override
    public ResponseEntity<AppleTokenResponse> createAppleUser(HttpServletRequest request, HttpServletResponse response,
                                                              CreateUserRequest createUserRequest, MultipartFile profileImage) {
        if (userRepository.existsByNickname(createUserRequest.getNickname())) {
            throw new NicknameAlreadyExistsException(createUserRequest.getNickname());
        }
        String email = jwtTokenProvider.getEmailFromRequest(request);
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }
        User user = userRepository.save(userMapper.createUserRequestToUser(createUserRequest));
        user.setEmail(email);
        try {
            user.setProfileImage(profileImage.getBytes());
        } catch (Exception e) {
            user.setProfileImage(null);
        }
        userRepository.save(user);
        AppleTokenResponse appleTokenResponse = oAuth2ResponseProvider.generateTokenAndGetTokenResponse(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(appleTokenResponse);
    }

    @Override
    public UserResponse getMyInfo(HttpServletRequest request) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotExistsException(email));
        return userMapper.userToUserResponse(user);
    }

    @Override
    public UserResponse updateUser(HttpServletRequest request, UpdateUserRequest updateUserRequest, MultipartFile profileImageMultipartFile) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotExistsException(email));
        BeanUtils.copyProperties(updateUserRequest, user);
        try {
            user.setProfileImage(profileImageMultipartFile.getBytes());
        } catch (Exception ignore) {
        }
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
