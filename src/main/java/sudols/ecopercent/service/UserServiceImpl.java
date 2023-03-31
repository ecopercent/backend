package sudols.ecopercent.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.user.CreateUserRequest;
import sudols.ecopercent.dto.user.UpdateUserRequest;
import sudols.ecopercent.dto.user.UserResponse;
import sudols.ecopercent.mapper.UserMapper;
import sudols.ecopercent.repository.ItemRepository;
import sudols.ecopercent.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ItemRepository itemRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.userMapper = userMapper;
    }

    // TODO: 구현. 유저 생성 시 등록된 아이템을 대표 아이템으로 등록
    public UserResponse createUser(CreateUserRequest createUserRequest) {
        User user = userRepository.save(userMapper.createUserRequestToUser(createUserRequest));
        return userMapper.userToUserResponse(user);
    }

    public Optional<UserResponse> getUser(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::userToUserResponse);
    }

    public Optional<UserResponse> updateUser(Long userId, UpdateUserRequest updateUserRequest) {
        return userRepository.findById(userId)
                .map(user -> {
                    BeanUtils.copyProperties(updateUserRequest, user, "id");
                    return userRepository.save(user);
                })
                .map(userMapper::userToUserResponse);
    }

    public void deleteUser(Long userId) {
        itemRepository.deleteByUser_Id(userId);
        userRepository.deleteById(userId);
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
