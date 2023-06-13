package sudols.ecopercent.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.auth.apple.AppleSignInResponse;
import sudols.ecopercent.dto.item.CreateItemRequest;
import sudols.ecopercent.dto.item.ItemResponse;
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
import sudols.ecopercent.security.TokenResponseProvider;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final ItemService itemService;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final UserMapper userMapper;
    private final TokenResponseProvider TokenResponseProvider;

    public UserResponse createKakaoUser(HttpServletResponse response, String email, String referer,
                                        CreateUserRequest createUserRequest, MultipartFile profileImageMultipartFile,
                                        CreateItemRequest createTumblerRequest, MultipartFile tumblerImageMultipartFile,
                                        CreateItemRequest createEcobagRequest, MultipartFile ecobagImageMultipartFile) {
        if (userRepository.existsByNickname(createUserRequest.getNickname())) {
            throw new NicknameAlreadyExistsException(createUserRequest.getNickname());
        }
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }
        User user = userMapper.createUserRequestToUser(createUserRequest);
        user.setEmail(email);
        try {
            user.setProfileImage(profileImageMultipartFile.getBytes());
        } catch (Exception e) {
            user.setProfileImage(null);
        }
        userRepository.save(user);
        try {
            final String domain = new URL(referer).getHost();
            TokenResponseProvider.generateTokensAndAddCookieForWeb(response, user, domain);
        } catch (Exception e) {
            TokenResponseProvider.generateTokensAndAddCookieForIos(response, user);
        }
        if (createTumblerRequest != null) {
            ItemResponse tumblerResponse = itemService.createItem(email, createTumblerRequest, tumblerImageMultipartFile);
            itemService.changeTitleTumbler(email, tumblerResponse.getId());
        }
        if (createEcobagRequest != null) {
            ItemResponse ecobagResponse = itemService.createItem(email, createEcobagRequest, ecobagImageMultipartFile);
            itemService.changeTitleEcobag(email, ecobagResponse.getId());
        }
        return userMapper.userToUserResponse(user);
    }

    public AppleSignInResponse createAppleUser(String email, CreateUserRequest createUserRequest, MultipartFile profileImage) {
        if (userRepository.existsByNickname(createUserRequest.getNickname())) {
            throw new NicknameAlreadyExistsException(createUserRequest.getNickname());
        }
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
        return TokenResponseProvider.generateTokenAndGetTokenResponse(user);
    }

    public UserResponse getMyInfo(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotExistsException(email));
        return userMapper.userToUserResponse(user);
    }

    public UserResponse updateUser(String email, UpdateUserRequest updateUserRequest, MultipartFile profileImageMultipartFile) {
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

    public void deleteUser(String email) {
        if (userRepository.existsByEmail(email)) {
            itemRepository.deleteByUser_Email(email);
            userRepository.deleteByEmail(email);
        } else {
            throw new UserNotExistsException(email);
        }
    }

    public List<UserResponse> getAllUser() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::userToUserResponse)
                .collect(Collectors.toList());
    }

    public void deleteAllUser() {
        itemRepository.deleteAll();
        userRepository.deleteAll();
    }
}
